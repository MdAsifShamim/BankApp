package com.mymicroservice.accounts.service.client;

import com.mymicroservice.accounts.dto.CardsDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class CardsFallBack implements CardsFeignClient{
    @Override
    public ResponseEntity<CardsDto> getCardDetail(String correlationId, String mobileNumber) {
        return null;
    }
}
