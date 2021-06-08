package com.dictionary.demo.repository;

import com.dictionary.demo.domain.MP;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SpringDataJpaMPRepository extends JpaRepository<MP, Long>, MPRepository{
    @Override
    Optional<MP> findById(Long id);

    @Override
    Optional<MP> findByMona(String mona);
}
