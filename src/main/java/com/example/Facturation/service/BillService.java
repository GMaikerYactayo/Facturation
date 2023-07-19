package com.example.Facturation.service;

import com.example.Facturation.domain.Bill;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BillService {

    Page<Bill> findAll(Pageable pageable);
    Bill save(Bill bill);
    void deleteById(Integer billId);
    void deleteDetailByBill(Integer billId, Integer detailId);

}
