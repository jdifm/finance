package com.iksun.finance;

import com.iksun.finance.domain.HouseFinanceCredit;
import com.iksun.finance.repository.HouseFinanceCreditRepository;
import com.iksun.finance.repository.InstituteRepository;
import com.iksun.finance.service.FinanceService;
import com.iksun.finance.service.FinanceServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@Slf4j
@RunWith(MockitoJUnitRunner.class)
public class FinanceManageServiceTest {
    @Mock
    private HouseFinanceCreditRepository houseFinanceCreditRepository;

    @Mock
    private InstituteRepository instituteRepository;

    @InjectMocks
    private FinanceServiceImpl financeService;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void testInsert_SUCCESS() {
        // given
        String insert = "2005,1,1019,846,82,95,30,157,57,80,99,,,,,,,";
        // when
        List<HouseFinanceCredit> result = financeService.insert(insert);

        // then
        verify(houseFinanceCreditRepository, times(1)).saveAll(any());
    }


    @Test
    public void testInsert_FAIL() {
        // given
        String insert = "testtest,testtest";

        // then
        thrown.expect(NumberFormatException.class);

        // when
        List<HouseFinanceCredit> result = financeService.insert(insert);

    }
}
