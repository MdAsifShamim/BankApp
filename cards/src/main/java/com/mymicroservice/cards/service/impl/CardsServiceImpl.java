package com.mymicroservice.cards.service.impl;

import com.mymicroservice.cards.constants.CardsConstants;
import com.mymicroservice.cards.dto.CardsDto;
import com.mymicroservice.cards.entity.Cards;
import com.mymicroservice.cards.exception.CardAlreadyExistsException;
import com.mymicroservice.cards.exception.ResourceNotFoundException;
import com.mymicroservice.cards.mapper.CardsMapper;
import com.mymicroservice.cards.repository.CardsRepository;
import com.mymicroservice.cards.service.ICardsService;
import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
@AllArgsConstructor
public class CardsServiceImpl implements ICardsService {

    private CardsRepository cardsRepository;

    /**
     * @param mobileNumber Mobile Number of the customer
     */
    @Override
    public void createCard(String mobileNumber) {

        Optional<Cards> card=cardsRepository.findByMobileNumber(mobileNumber);
        if(card.isPresent()) {
              throw new CardAlreadyExistsException("Card already registered with given mobileNumber "+mobileNumber);
        }
        cardsRepository.save(createNewCard(mobileNumber));

    }

    private Cards createNewCard(String mobileNumber) {
        Cards card=new Cards();
        long cardNumber=100000000000L+new Random().nextInt(999999999);
        card.setCardNumber(Long.toString(cardNumber));
        card.setMobileNumber(mobileNumber);
        card.setCardType(CardsConstants.CREDIT_CARD);
        card.setTotalLimit(CardsConstants.NEW_CARD_LIMIT);
        card.setAmountUsed(0);
        card.setAvailableAmount(CardsConstants.NEW_CARD_LIMIT);
        return card;
    }

    /**
     * @param mobileNumber Customer mobile number
     * @return Card Detail of specific customer
     */
    @Override
    public CardsDto fetchCard(String mobileNumber) {

        Cards cards=cardsRepository.findByMobileNumber(mobileNumber).orElseThrow(
                ()->new ResourceNotFoundException("Cards","mobileNumber",mobileNumber));
        return CardsMapper.toCardsDto(cards,new CardsDto());
    }

    /**
     * @param cardsDto Have customer card detail
     * @return update status
     */
    @Override
    public boolean updateCard(CardsDto cardsDto) {

        Cards cards=cardsRepository.findByCardNumber(cardsDto.getCardNumber())
                .orElseThrow(()->new ResourceNotFoundException("Cards","AccountNumber",cardsDto.getCardNumber()));
        CardsMapper.toCards(cardsDto,cards);
        cardsRepository.save(cards);
        return true;
    }

    /**
     * @param mobileNumber Customer Mobile Number
     * @return delete status
     */
    @Override
    public boolean deleteCard(String mobileNumber) {

        Cards cards=cardsRepository.findByMobileNumber(mobileNumber)
                .orElseThrow(()->new ResourceNotFoundException("Cards","MobileNumber",mobileNumber));
        cardsRepository.deleteById(cards.getCardId());
        return true;
    }


}
