package com.dictionary.demo.repository;

import com.dictionary.demo.domain.MP;

import java.util.List;
import java.util.Optional;

public interface MPRepository {
    MP save(MP mp);
    Optional<MP> findByMona(String mona);
    Optional<MP> findById(Long id);
    List<MP> findAll();
}
