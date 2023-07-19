package com.example.Facturation.repository;

import com.example.Facturation.domain.PayMode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PayModeRepository extends JpaRepository<PayMode, Integer> {
}
