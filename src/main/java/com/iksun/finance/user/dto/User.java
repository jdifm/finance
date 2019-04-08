package com.iksun.finance.user.dto;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
public class User {
    @Id
    @Column(name = "id")
    private String id;
    @Column(name = "password")
    private String password;
    @Column(name = "token")
    private String token;
}
