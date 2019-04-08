package com.iksun.finance.repository;

import com.iksun.finance.domain.Institute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InstituteRepository extends JpaRepository<Institute, String> {
}
