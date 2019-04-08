package com.iksun.finance.service;

import com.iksun.finance.controller.dto.FinanceYearlyResult;
import com.iksun.finance.domain.HouseFinanceCredit;
import com.iksun.finance.domain.Institute;
import com.iksun.finance.repository.HouseFinanceCreditRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.*;

@Slf4j
@Service
public class FinanceServiceImpl implements FinanceService {
    private static final String TEMP_YEAR_INITIALIZE_NUMBER = "0";

    @Autowired
    private HouseFinanceCreditRepository houseFinanceCreditRepository;

    @Autowired
    private InstituteService instituteService;

    @Transactional
    @Override
    public List<HouseFinanceCredit> insert(String inputRaw) {
        List<String> csvs = this.parseStringCsv(inputRaw);
        List<HouseFinanceCredit> creditList = new ArrayList<>();

        try {
            int year = Integer.valueOf(csvs.get(0)); // year 세팅
            int month = Integer.valueOf(csvs.get(1)); // month 세팅
            List<Institute> institutes = instituteService.getAll();
            for (int i = 0; i < csvs.size() - 2; i++) { // 몸통 세팅
                HouseFinanceCredit houseFinanceCredit = new HouseFinanceCredit();
                houseFinanceCredit.setYear(year);
                houseFinanceCredit.setMonth(month);
                houseFinanceCredit.setInstituteCode(institutes.get(i).getInstatuteCode());
                houseFinanceCredit.setAmount(this.getAmount(csvs.get(i+2)));
                creditList.add(houseFinanceCredit);
            }

        } catch (NumberFormatException nfe) {
            log.error("Exception to change csv to house finance credit", nfe);
            throw nfe;
        }

        this.insertAll(creditList);
        return creditList;
    }

    @Transactional(readOnly = true)
    @Override
    public List<FinanceYearlyResult> findAllTotalAmountRecordByYear() {
        return makeFinanceForm(houseFinanceCreditRepository.findTotalAmountByYear());
    }

    @Transactional(readOnly = true)
    @Override
    public Map<String, Object> findMinMaxAmountYearByinstitute(String bankCode) {

        List<Map<String, Object>> allResultByInstitute = houseFinanceCreditRepository.findAvgAmountByYear(bankCode);
        if (CollectionUtils.isEmpty(allResultByInstitute) == false) {
            List<Map<String, Object>> supportAmount = new ArrayList<>();
            supportAmount.add(allResultByInstitute.get(0)); //가장 큰 금액
            supportAmount.add(allResultByInstitute.get(allResultByInstitute.size() - 1)); //가장 작은 금액
            Map<String, Object> resultMap = new HashMap<>();

            Institute institute = instituteService.getOneByCode(bankCode);
            if (institute != null) {
                resultMap.put("bank", institute.getInstatuteName());
                resultMap.put("support_amount", supportAmount);
                return resultMap;
            }
        }

        return null;
    }

    @Override
    public Map<String, Object> predictAmount(String bankCode, String year, String month) {
        // TODO: 2019-04-08 미래 세액 예측 추가
        return null;
    }

    @Override
    public Map<String, Object> findMaxTotalAmountYearByInstitute(String bankCode) {
        if (instituteService.getOneByCode(bankCode) == null) {
            return null;
        }

        return houseFinanceCreditRepository.findMaxAmountInstituteByYear(bankCode);
    }

    private FinanceYearlyResult setupFinanceRecord(String year, BigDecimal totalAmount, Map<String, String> details) {
        FinanceYearlyResult financeYearlyResult = new FinanceYearlyResult();
        financeYearlyResult.setYear(year);
        financeYearlyResult.setTotalAmount(totalAmount);
        financeYearlyResult.setDetailAmount(details);
        return financeYearlyResult;
    }

    /**
     * Detail에 들어갈 내용을 만들어준다.
     * @param raws
     * @return
     */
    private List<FinanceYearlyResult> makeFinanceForm(List<Map<String, Object>> raws) {
        List<FinanceYearlyResult> financeYearlyResults = new LinkedList<>();

        Map<String, String> amountByInstitute = new HashMap<>();
        String lastRecordYear = TEMP_YEAR_INITIALIZE_NUMBER;
        BigDecimal totalAmount = BigDecimal.ZERO;

        for (int i = 0; i< raws.size(); i++) {
            Map<String, Object> raw = raws.get(i);
            String year = MapUtils.getString(raw, "year");
            String bank = MapUtils.getString(raw, "bank");
            long amount = MapUtils.getLong(raw, "amount");

            if (isNotSameYear(lastRecordYear, year)) { // 연도가 바뀌었을 경우
                financeYearlyResults.add(setupFinanceRecord(lastRecordYear, totalAmount, amountByInstitute)); // list에 저장
                // for용 내용 초기화
                year = TEMP_YEAR_INITIALIZE_NUMBER;
                amountByInstitute = new HashMap<>();
                totalAmount = BigDecimal.ZERO;
                lastRecordYear = TEMP_YEAR_INITIALIZE_NUMBER;
            }

            totalAmount = totalAmount.add(BigDecimal.valueOf(amount));
            amountByInstitute.put(bank, String.valueOf(amount));

            // temp 세팅
            lastRecordYear = year;

            if (i == raws.size() -1) { // 마지막레코드 처리용
                financeYearlyResults.add(setupFinanceRecord(year, totalAmount, amountByInstitute));
            }
        }
        return financeYearlyResults;
    }

    private boolean isNotSameYear(String tempYear, String nowYear) {
        return tempYear.equals("0") == false && tempYear.equals(nowYear) == false;
    }

    /**
     * CSV String 열 parsing
     * "" 존재 시 "" 삭제, "***,**" 일시 *****로 " 와 , 삭제
     * 참조 : https://www.mkyong.com/java/how-to-read-and-parse-csv-file-in-java/
     * @param csvLine
     * @return
     */
    private List<String> parseStringCsv(String csvLine) {
        List<String> result = new ArrayList<>();
        StringBuilder stringBuilder = new StringBuilder();

        char[] chars = csvLine.toCharArray();
        boolean inField = false; // " " 두개 사이를 inField라 칭함
        for (int i = 0; i < chars.length;i ++) {
            if (inField) {
                if (chars[i] == '"') {
                    inField = false;
                } else {
                    if (chars[i] != ',') {
                        stringBuilder.append(chars[i]);
                    }
                }
            } else {
                if (chars[i] == '"') {
                    inField = true;
                } else if (chars[i] == ',') {
                    if (StringUtils.isEmpty(stringBuilder.toString()) == false) {
                        result.add(stringBuilder.toString());
                        stringBuilder.delete(0, stringBuilder.length());
                    }
                } else if (chars[i] == '\r') {
                    //ignore LF characters
                    continue;
                } else if (chars[i]  == '\n') {
                    //the end, break!
                    break;
                } else {
                    stringBuilder.append(chars[i]);
                }
            }
        }
        return result;
    }

    private BigDecimal getAmount(String amount) throws NumberFormatException {
        return BigDecimal.valueOf(Integer.valueOf(amount));
    }

    private void insertAll(List<HouseFinanceCredit> credits) {
        if (CollectionUtils.isEmpty(credits) == false) {
            houseFinanceCreditRepository.saveAll(credits);
        }
    }

}
