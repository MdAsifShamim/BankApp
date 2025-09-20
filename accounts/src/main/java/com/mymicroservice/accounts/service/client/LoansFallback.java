package com.mymicroservice.accounts.service.client;

import com.mymicroservice.accounts.dto.LoanDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class LoansFallback implements LoansFeignClient{
    @Override
    public ResponseEntity<LoanDto> fetchLoanDetail(String correlationId, String mobileNumber) {
        return null;
    }
}
