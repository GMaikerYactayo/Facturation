package com.example.Facturation.service;

import com.example.Facturation.domain.PayMode;

import java.util.List;

public interface PayModeService {

    PayMode save(PayMode payMode);
    List<PayMode> findAll();

}
