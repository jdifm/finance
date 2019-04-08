package com.iksun.finance.service;

import com.iksun.finance.domain.Institute;
import com.iksun.finance.repository.InstituteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InstituteServiceImpl implements InstituteService {

    @Autowired
    private InstituteRepository instituteRepository;

    @Override
    public List<Institute> getAll() {
        Sort sort = new Sort(Sort.Direction.ASC, "columnNumber");
        return instituteRepository.findAll(sort);
    }

    @Override
    public Institute getOneByCode(String code) {
        return instituteRepository.getOne(code);
    }

    @Override
    public Institute getOneByName(String code) {
        return null;
    }
}
