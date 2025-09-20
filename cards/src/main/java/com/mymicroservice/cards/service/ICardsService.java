package com.mymicroservice.cards.service;

import com.mymicroservice.cards.dto.CardsDto;

public interface ICardsService {

    /**
     * @param mobileNumber Mobile Number of the customer
     */
    void createCard(String mobileNumber);


    /**
     * @param mobileNumber Customer mobile number
     * @return Card Detail of specific customer
     */
    CardsDto fetchCard(String mobileNumber);


    /**
     * @param cardsDto Have customer card detail
     * @return update status
     */
    boolean updateCard(CardsDto cardsDto);


    /**
     * @param mobileNumber Customer Mobile Number
     * @return delete status
     */
    boolean deleteCard(String mobileNumber);
}
