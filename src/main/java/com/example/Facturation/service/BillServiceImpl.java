package com.example.Facturation.service;

import com.example.Facturation.domain.*;
import com.example.Facturation.repository.*;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
public class BillServiceImpl implements BillService {

    private final ClientRepository clientRepository;
    private final PayModeRepository payModeRepository;
    private final ProductRepository productRepository;
    private final BillRepository billRepository;
    private final DetailRepository detailRepository;

    @Override
    public Page<Bill> findAll(Pageable pageable) {
        return billRepository.findAll(pageable);
    }

    @Override
    public Bill save(Bill bill) {
        Client client = getClientById(bill.getClient().getClient_id());
        PayMode paymentMode = getPaymentModeById(bill.getPay_mode().getPay_mode_id());

        bill.setClient(client);
        bill.setPay_mode(paymentMode);
        bill.setDate_creation(LocalDate.now());
        float total = calculateTotal(bill.getDetail());
        bill.setTotal(total);

        Bill savedBill = saveBill(bill);
        saveDetails(savedBill, bill.getDetail());

        return savedBill;
    }

    @Override
    public void deleteById(Integer billId) {
        Bill bill = billRepository.findById(billId)
                .orElseThrow(() -> new RuntimeException("bill no encontrado"));
        billRepository.delete(bill);
    }

    @Override
    public void deleteDetailByBill(Integer billId, Integer detailId) {
        Bill bill = billRepository.findById(billId)
                .orElseThrow(() -> new RuntimeException("Bill no encontrado"));
        Detail detail = detailRepository.findById(detailId)
                .orElseThrow(() -> new RuntimeException("detail no encontrado"));
        if (!detail.getBill().equals(bill)) {
            throw new RuntimeException("Detalle no encontrado en la factura especificada");
        }
        detailRepository.delete(detail);
        updateTotalBill(bill);
    }

    private void updateTotalBill(Bill bill) {
        float total = calculateTotal(bill.getDetail());
        bill.setTotal(total);
        billRepository.save(bill);
    }

    private float calculateTotal(List<Detail> details) {
        float total = 0.0f;
        for (Detail detail : details) {
            Product product = getProductById(detail.getProduct().getProduct_id());
            float price = product.getPrice() * detail.getAmount();
            total += price;
        }
        return total;
    }

    private Client getClientById(int clientId) {
        return clientRepository.findById(clientId)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
    }

    private PayMode getPaymentModeById(int paymentModeId) {
        return payModeRepository.findById(paymentModeId)
                .orElseThrow(() -> new RuntimeException("Modo de pago no encontrado"));
    }

    private Bill saveBill(Bill bill) {
        return billRepository.save(bill);
    }

    private void saveDetails(Bill bill, List<Detail> details) {
        for (Detail detail : details) {
            Product product = getProductById(detail.getProduct().getProduct_id());
            float price = product.getPrice() * detail.getAmount();
            detail.setPrice(price);
            detail.setBill(bill);
            detail.setProduct(product);
            detailRepository.save(detail);
            updateStockProduct(product, detail.getAmount());
        }
    }

    private Product getProductById(int productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
    }

    private void updateStockProduct(Product product, int soldAmount) {
        int newStock = product.getStock() - soldAmount;
        product.setStock(newStock);
        productRepository.save(product);
    }
}
