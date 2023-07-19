package com.example.Facturation.rest;

import com.example.Facturation.domain.Bill;
import com.example.Facturation.service.BillService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class BillController {

    private final BillService billService;

    @GetMapping("/bills")
    public ResponseEntity<Page<Bill>> findAllBill(Pageable pageable) {
        Page<Bill> bills = billService.findAll(pageable);
        return ResponseEntity.ok(bills);
    }

    @PostMapping("/bills")
    public ResponseEntity<Bill> create(@RequestBody Bill bill) {
        return ResponseEntity.ok(billService.save(bill));
    }

    @DeleteMapping("/bills/{billId}")
    public ResponseEntity<String> deleteByBill (@PathVariable Integer billId){
        billService.deleteById(billId);
        return ResponseEntity.ok("Bill liminada con éxito");
    }

    @DeleteMapping("/bills/{billId}/details/{detailId}")
    public ResponseEntity<String> deleteByDetail (@PathVariable Integer billId, @PathVariable Integer detailId){
        billService.deleteDetailByBill(billId, detailId);
        return ResponseEntity.ok("Detalle eliminada con éxito");
    }

}
