package com.iksun.finance.domain;

import lombok.Data;

import java.io.Serializable;

@Data
public class HouseFinanceCreditIds implements Serializable {
    private static final long serialVersionUID = 5224148532659147382L;
    private int year;
    private int month;
    private String instituteCode;
}
