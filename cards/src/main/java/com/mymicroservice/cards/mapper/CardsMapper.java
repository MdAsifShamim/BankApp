package com.mymicroservice.cards.mapper;

import com.mymicroservice.cards.dto.CardsDto;
import com.mymicroservice.cards.entity.Cards;

public class CardsMapper {

    public static Cards toCards(CardsDto cardsDto, Cards cards){

        cards.setCardNumber(cardsDto.getCardNumber());
        cards.setCardNumber(cardsDto.getCardNumber());
        cards.setCardType(cardsDto.getCardType());
        cards.setTotalLimit(cardsDto.getTotalLimit());
        cards.setAmountUsed(cardsDto.getAmountUsed());
        cards.setAvailableAmount(cardsDto.getAvailableBalance());
        return cards;
    }


    public static CardsDto toCardsDto(Cards cards, CardsDto cardsDto){

        cardsDto.setCardNumber(cards.getCardNumber());
        cardsDto.setMobileNumber(cards.getMobileNumber());
        cardsDto.setCardType(cards.getCardType());
        cardsDto.setTotalLimit(cards.getTotalLimit());
        cardsDto.setAmountUsed(cards.getAmountUsed());
        cardsDto.setAvailableBalance(cards.getAvailableAmount());
        return cardsDto;
    }
}
