package com.iksun.finance;

import com.iksun.finance.controller.dto.FinanceYearlyResult;
import com.iksun.finance.domain.Institute;
import com.iksun.finance.repository.HouseFinanceCreditRepository;
import com.iksun.finance.service.FinanceServiceImpl;
import com.iksun.finance.service.InstituteService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.annotation.PostConstruct;
import java.util.*;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@Slf4j
@RunWith(MockitoJUnitRunner.class)
public class FinanceServiceTest {
    @Mock
    private HouseFinanceCreditRepository houseFinanceCreditRepository;

    @Mock
    private InstituteService instituteService;

    @InjectMocks
    private FinanceServiceImpl financeService;

    private Institute institute = new Institute();

    @PostConstruct
        public void setup() {
        institute.setColumnNumber(1);
        institute.setInstatuteCode("code");
        institute.setInstatuteName("name");
    }
    @Test
    public void testFindMinMaxAmountYearByinstitute_SUCCESS() {
        List<Map<String, Object>> mockResult = new ArrayList<>();
        mockResult.add(new HashMap<>());
        Institute institute = new Institute();
        institute.setColumnNumber(1);
        institute.setInstatuteCode("code");
        institute.setInstatuteName("name");
        when(houseFinanceCreditRepository.findAvgAmountByYear(anyString())).thenReturn(mockResult);
        when(instituteService.getOneByCode(anyString())).thenReturn(institute);

        Map<String,Object> result = financeService.findMinMaxAmountYearByinstitute(anyString());

        assertThat(result, is(notNullValue()));
        assertThat(result.get("bank"), is("name"));
    }

    @Test
    public void testFindMinMaxAmountYearByinstitute_NULL() {
        when(houseFinanceCreditRepository.findAvgAmountByYear(anyString())).thenReturn(null);

        Map<String,Object> result = financeService.findMinMaxAmountYearByinstitute(anyString());

        assertThat(result, is(nullValue()));
    }

    @Test
    public void test_findAllTotalAmountRecordByYear_SUCCESS() {
        List<Map<String, Object>> mockResult = new LinkedList<>();
        when(houseFinanceCreditRepository.findTotalAmountByYear()).thenReturn(mockResult);
        List<FinanceYearlyResult> result =  financeService.findAllTotalAmountRecordByYear();

        assertThat(result, is(notNullValue()));

    }

    @Test
    public void test_findMinMaxAmountYearByinstitute_SUCCESS() {
        when(houseFinanceCreditRepository.findMaxAmountInstituteByYear(anyString())).thenReturn(null);

        when(instituteService.getOneByCode(anyString())).thenReturn(institute);

        financeService.findMaxTotalAmountYearByInstitute(anyString());

        verify(houseFinanceCreditRepository, times(1)).findMaxAmountInstituteByYear(any());
    }
}
