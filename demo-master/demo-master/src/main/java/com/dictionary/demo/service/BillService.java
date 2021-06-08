package com.dictionary.demo.service;

import com.dictionary.demo.repository.BillRepository;

public class BillService {
    private final BillRepository billRepository;

    public BillService(BillRepository billRepository) { this.billRepository = billRepository; }


}
