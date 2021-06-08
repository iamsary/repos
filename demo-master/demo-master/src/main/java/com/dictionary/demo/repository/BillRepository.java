package com.dictionary.demo.repository;

import com.dictionary.demo.domain.Bill;

import java.util.List;
import java.util.Optional;

public interface BillRepository {
    Bill save(Bill bill);
    Optional<Bill> findById(Long id);
    List<Bill> findAll();
}
