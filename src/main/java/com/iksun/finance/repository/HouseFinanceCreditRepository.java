package com.iksun.finance.repository;

import com.iksun.finance.domain.HouseFinanceCredit;
import com.iksun.finance.domain.HouseFinanceCreditIds;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;

public interface HouseFinanceCreditRepository extends JpaRepository<HouseFinanceCredit, HouseFinanceCreditIds> {

    @Query(value = "SELECT " +
            "    a.year as year, institute_name as bank, a.amount as amount " +
            "FROM " +
            "    (SELECT " +
            "        year, h1.institute_code AS bank, SUM(amount) AS amount " +
            "    FROM " +
            "        house_finance_credit h1 " +
            "    GROUP BY year , h1.institute_code " +
            "    ORDER BY year) a " +
            "        INNER JOIN " +
            "    institute ins ON a.bank = ins.institute_code", nativeQuery = true)
    public List<Map<String, Object>> findTotalAmountByYear();

    @Query(value = "SELECT  " +
            "    a.year, ins.institute_name AS bank " +
            "FROM " +
            "    (SELECT  " +
            "        year, institute_code, MAX(amount) AS amount " +
            "    FROM " +
            "        house_finance_credit " +
            "    WHERE " +
            "        institute_code = :institute " +
            "    GROUP BY year , institute_code " +
            "    ORDER BY amount " +
            "    LIMIT 1) a " +
            "        INNER JOIN " +
            "    institute ins ON a.institute_code = ins.institute_code", nativeQuery = true)
    public Map<String, Object> findMaxAmountInstituteByYear(@Param("institute") String institute);

    @Query(value = "SELECT " +
            " year, ROUND(AVG(amount), 0) AS amount " +
            "FROM " +
            " house_finance_credit " +
            "WHERE " +
            " institute_code = :institute " +
            "GROUP BY year ORDER BY amount", nativeQuery = true)
    public List<Map<String, Object>> findAvgAmountByYear(@Param("institute") String institute);

}
