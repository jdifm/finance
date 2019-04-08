package com.iksun.finance.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Code Table
 */
@NoArgsConstructor
@Entity
@Data
public class Institute {

    @Id
    @Column(name = "institute_code")
    private String instatuteCode;

    @Column(name = "institute_name")
    private String instatuteName;

    @Column(name = "column_number")
    private int columnNumber;

    public Institute(String instatuteCode, String instatuteName) {
        this.instatuteCode = instatuteCode;
        this.instatuteName = instatuteName;
    }
}
