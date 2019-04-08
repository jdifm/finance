package com.iksun.finance.service;

import com.iksun.finance.controller.dto.FinanceYearlyResult;
import com.iksun.finance.domain.HouseFinanceCredit;

import java.util.List;
import java.util.Map;

public interface FinanceService {
    /**
     * CSV의 RAW 데이터 입력
     * @param inputRaw
     * @return
     */
    public List<HouseFinanceCredit> insert(String inputRaw);

    /**
     * 각 해마다 기관별 투자금액
     * @return
     */
    public List<FinanceYearlyResult>  findAllTotalAmountRecordByYear();

    /**
     * 기관의 최고 투자 금액
     * @param bankCode
     * @return
     */
    public Map<String, Object> findMaxTotalAmountYearByInstitute(String bankCode);

    /**
     * 기관의 최대 최소 평균 투자 해 금액
     * @param bankCode
     * @return
     */
    public Map<String, Object> findMinMaxAmountYearByinstitute(String bankCode);

    /**
     * 내년도 예상치
     * @param bankCode
     * @param year
     * @param month
     * @return
     */
    public Map<String, Object> predictAmount(String bankCode, String year, String month);
}
