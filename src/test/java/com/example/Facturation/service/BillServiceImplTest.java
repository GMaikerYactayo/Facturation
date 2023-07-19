package com.example.Facturation.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.example.Facturation.domain.*;
import com.example.Facturation.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class BillServiceImplTest {

    @Mock
    private BillRepository billRepository;

    @Mock
    private PayModeRepository payModeRepository;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ClientRepository clientRepository;

    @Mock
    private DetailRepository detailRepository;

    @InjectMocks
    private BillServiceImpl billService;

    private Client client;
    private PayMode payMode;
    private Product product;

    @BeforeEach
    void setUp() {
        client = new Client();
        client.setClient_id(1);
        clientRepository.save(client);

        payMode = new PayMode();
        payMode.setPay_mode_id(1);
        payModeRepository.save(payMode);

        product = new Product();
        product.setProduct_id(1);
        productRepository.save(product);
    }

    @Test
    void save() {
        //give
        given(clientRepository.findById(client.getClient_id()))
                .willReturn(Optional.of(client));

        given(payModeRepository.findById(payMode.getPay_mode_id()))
                .willReturn(Optional.of(payMode));

        given(productRepository.findById(product.getProduct_id()))
                .willReturn(Optional.of(product));

        Bill bill = new Bill();
        bill.setClient(client);
        bill.setPay_mode(payMode);

        Detail detail = new Detail();
        detail.setProduct(product);
        detail.setAmount(2);
        bill.setDetail(Collections.singletonList(detail));
        given(billRepository.save(bill)).willReturn(bill);

        //when
        Bill billSaved = billService.save(bill);

        //then
        assertThat(billSaved).isNotNull();
    }

    @Test
    void findAll() {
        //given
        Pageable pageable = PageRequest.of(0, 10);
        Bill bill = new Bill();
        bill.setBill_id(1);

        Bill bill2 = new Bill();
        bill2.setBill_id(2);

        List<Bill> bills = Arrays.asList(bill, bill2);
        Page<Bill> billsPage = new PageImpl<>(bills, pageable, bills.size());

        given(billRepository.findAll(pageable)).willReturn(billsPage);

        //when
        Page<Bill> billsListPage = billService.findAll(pageable);

        //then
        assertThat(billsListPage).isNotNull();
        assertThat(billsListPage.getTotalElements()).isEqualTo(2);

    }

    @Test
    void deleteById() {
        //given
        Bill bill = new Bill();
        bill.setBill_id(1);
        bill.setTotal(4.00f);
        given(billRepository.findById(bill.getBill_id()))
                .willReturn(Optional.of(bill));

        //when
        billService.deleteById(bill.getBill_id());

        //then
        verify(billRepository, times(1)).delete(bill);
    }

    @Test
    void deleteDetailByBill() {
        //given
        Bill bill = new Bill();
        bill.setClient(client);
        bill.setPay_mode(payMode);

        Detail detail = new Detail();
        detail.setProduct(product);
        detail.setBill(bill);

        given(billRepository.findById(bill.getBill_id()))
                .willReturn(Optional.of(bill));
        given(detailRepository.findById(detail.getDetail_id()))
                .willReturn(Optional.of(detail));

        //when
        billService.deleteDetailByBill(bill.getBill_id(), detail.getDetail_id());

        //then
        verify(detailRepository, times(1)).delete(detail);
    }
}