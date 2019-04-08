package com.iksun.finance.service;

import com.iksun.finance.domain.Institute;

import java.util.List;
import java.util.Map;

public interface InstituteService {
    /**
     * 전체 은행 목록 출력 order by를 csv순서대로
     * @return
     */
    public List<Institute> getAll();

    public Institute getOneByCode(String code);

    public Institute getOneByName(String code);

}
