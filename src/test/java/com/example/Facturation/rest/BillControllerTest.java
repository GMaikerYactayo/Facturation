package com.example.Facturation.rest;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.example.Facturation.domain.*;
import com.example.Facturation.service.BillService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BillControllerTest {

    private MockMvc mockMvc;

    @Mock
    private BillService billService;

    @InjectMocks
    private BillController billController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(billController)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver()).build();
    }

    @Test
    void findAllBill() throws Exception {
        // Given
        Pageable pageable = PageRequest.of(0, 10);

        Bill bill1 = new Bill();
        bill1.setBill_id(1);

        Bill bill2 = new Bill();
        bill2.setBill_id(2);

        List<Bill> bills = Arrays.asList(bill1, bill2);
        Page<Bill> billPage = new PageImpl<>(bills, pageable, bills.size());

        given(billService.findAll(pageable)).willReturn(billPage);

        // When
        ResultActions response = mockMvc.perform(get("/api/v1/bills")
                .param("page", String.valueOf(pageable.getPageNumber()))
                .param("size", String.valueOf(pageable.getPageSize())));

        // Then
        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.content", Matchers.hasSize(bills.size())))
                .andExpect(jsonPath("$.content[0].bill_id").value(bill1.getBill_id()))
                .andExpect(jsonPath("$.content[1].bill_id").value(bill2.getBill_id()));
    }

    @Test
    void create() throws Exception {
        //given
        Client client = new Client();
        client.setClient_id(1);

        PayMode payMode = new PayMode();
        payMode.setPay_mode_id(1);

        Product product = new Product();
        product.setProduct_id(1);

        Bill bill = new Bill();
        bill.setBill_id(1);
        bill.setClient(client);
        bill.setPay_mode(payMode);


        Detail detail = new Detail();
        detail.setDetail_id(1);
        detail.setProduct(product);

        bill.setDetail(Collections.singletonList(detail));
        given(billService.save(any(Bill.class))).willReturn(bill);

        //when
        ResultActions response = mockMvc.perform(post("/api/v1/bills")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(bill)));

        //then
        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.bill_id").exists());
    }

    @Test
    void deleteByBill() throws Exception {
        //given
        Bill bill = new Bill();
        bill.setBill_id(1);

        willDoNothing().given(billService).deleteById(bill.getBill_id());

        //when
        ResultActions response = mockMvc.perform(delete("/api/v1/bills/{bill_id}", bill.getBill_id()));

        //then
        response.andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void deleteByDetail() throws Exception {
        //given
        Client client = new Client();
        client.setClient_id(1);

        PayMode payMode = new PayMode();
        payMode.setPay_mode_id(1);

        Product product = new Product();
        product.setProduct_id(1);

        Bill bill = new Bill();
        bill.setBill_id(1);
        bill.setClient(client);
        bill.setPay_mode(payMode);


        Detail detail = new Detail();
        detail.setDetail_id(1);
        detail.setProduct(product );

        bill.setDetail(Collections.singletonList(detail));
        willDoNothing().given(billService).deleteDetailByBill(bill.getBill_id(), detail.getDetail_id());

        //when
        ResultActions response = mockMvc.perform(delete("/api/v1/bills/{billI d}/details/{detailId}", bill.getBill_id(), detail.getDetail_id()));

        //then
        response.andDo(print())
                .andExpect(status().isOk());
    }
}