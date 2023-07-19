package com.example.Facturation.service;

import com.example.Facturation.domain.PayMode;
import com.example.Facturation.repository.PayModeRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class PayModeServiceImpl implements PayModeService {

    private final PayModeRepository payModeRepository;

    @Override
    public PayMode save(PayMode payMode) {
        return payModeRepository.save(payMode);
    }

    @Override
    public List<PayMode> findAll() {
        return payModeRepository.findAll();
    }
}
