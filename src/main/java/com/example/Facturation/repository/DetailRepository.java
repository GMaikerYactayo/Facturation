package com.example.Facturation.repository;

import com.example.Facturation.domain.Detail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DetailRepository extends JpaRepository<Detail, Integer> {
}
