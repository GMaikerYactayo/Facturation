package com.example.Facturation.rest;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.example.Facturation.domain.PayMode;
import com.example.Facturation.service.PayModeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PayModeControllerTest {

    private MockMvc mockMvc;

    @Mock
    private PayModeService payModeService;

    @InjectMocks
    private PayModeController payModeController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(payModeController).build();
    }

    @Test
    void getAllPayMode() throws Exception {
        //given
        PayMode payMode = new PayMode();
        payMode.setPay_mode_id(1);
        payMode.setName("Pay");
        payMode.setOther_detail("xd");

        PayMode payMode2 = new PayMode();
        payMode2.setPay_mode_id(1);
        payMode2.setName("Pay");
        payMode2.setOther_detail("xd");

        List<PayMode> payModes = Arrays.asList(payMode, payMode2);
        given(payModeService.findAll()).willReturn(payModes);

        //when
        ResultActions response = mockMvc.perform(get("/api/v1/payMode"));

        //then
        response.andDo(print())
                .andExpect(jsonPath("$.size()").value(payModes.size()));
    }

    @Test
    void create() throws Exception {
        //given
        PayMode payMode = new PayMode();
        payMode.setPay_mode_id(1);
        payMode.setName("Pay");
        payMode.setOther_detail("xd");

        given(payModeService.save(any(PayMode.class))).willReturn(payMode);

        //when
        ResultActions response = mockMvc.perform(post("/api/v1/payMode")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(payMode)));

        //then
        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(payMode.getName()))
                .andExpect(jsonPath("$.other_detail").value(payMode.getOther_detail()));
    }
}