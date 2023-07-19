package com.example.Facturation.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import com.example.Facturation.domain.PayMode;
import com.example.Facturation.repository.PayModeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

@ExtendWith(MockitoExtension.class)
class PayModeServiceImplTest {

    @Mock
    private PayModeRepository payModeRepository;

    @InjectMocks
    private PayModeServiceImpl payModeService;

    private PayMode payMode;

    @BeforeEach
    void setUp() {
        payMode = new PayMode();
        payMode.setName("demo");
        payMode.setOther_detail("description");
    }

    @DisplayName("Save Pay Mode")
    @Test
    void save() {
        //given
        given(payModeRepository.save(payMode)).willReturn(payMode);

        //when
        PayMode payModeSaved = payModeService.save(payMode);

        //then
        assertThat(payModeSaved).isNotNull();
    }

    @Test
    void findAll() {
        //given
        PayMode payMode2 = new PayMode();
        payMode2.setName("demo2");
        payMode2.setOther_detail("Description2");
        given(payModeRepository.findAll()).willReturn(List.of(payMode2,payMode));

        //when
        List<PayMode> payModes = payModeService.findAll();

        //then
        assertThat(payModes).isNotNull();
        assertThat(payModes.size()).isEqualTo(2);
    }
}