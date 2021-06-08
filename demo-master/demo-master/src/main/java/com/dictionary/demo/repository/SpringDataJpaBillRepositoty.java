package com.dictionary.demo.repository;

import com.dictionary.demo.domain.Bill;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SpringDataJpaBillRepositoty extends JpaRepository<Bill, Long>, BillRepository{
    @Override
    Optional<Bill> findById(Long id);
}
