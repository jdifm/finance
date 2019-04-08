package com.iksun.finance.domain;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import java.math.BigDecimal;
import java.time.ZonedDateTime;

@Entity
@Data
@IdClass(HouseFinanceCreditIds.class)
public class HouseFinanceCredit {

    @Id
    @Column(name = "year")
    private int year;

    @Id
    @Column(name = "month")
    private int month;

    @Id
    @Column(name = "institute_code")
    private String instituteCode;

    @Column(name = "amount")
    private BigDecimal amount;

    @Column(name = "created_at", insertable = false, updatable =  false)
    private ZonedDateTime createdAt;

    @Column(name = "updated_at", insertable = false, updatable =  false)
    private ZonedDateTime updatedAt;

}
