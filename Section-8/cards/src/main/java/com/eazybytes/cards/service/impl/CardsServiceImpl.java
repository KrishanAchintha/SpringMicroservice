package com.eazybytes.cards.service.impl;

import com.eazybytes.cards.constants.CardsConstants;
import com.eazybytes.cards.dto.CardsDto;
import com.eazybytes.cards.entity.Cards;
import com.eazybytes.cards.exception.CardAlreadyExistsException;
import com.eazybytes.cards.mapper.CardsMapper;
import com.eazybytes.cards.repository.CardsRepo;
import com.eazybytes.cards.service.ICardsService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
@AllArgsConstructor
public class CardsServiceImpl implements ICardsService {

    private CardsRepo cardsRepo;
    /**
     * This method is used to create a new card
     *
     * @param mobileNumber - Mobile Number of the Customer
     */
    @Override
    public void createCard(String mobileNumber) {
        Optional<Cards> cards = cardsRepo.findByMobileNumber(mobileNumber);
        if(cards.isPresent()){
            throw new CardAlreadyExistsException("Card already exists for the mobile number: "+ mobileNumber);
        }
        cardsRepo.save(createNewCard(mobileNumber));
    }
    /**
     * @param mobileNumber - Mobile Number of the Customer
     * @return the new card details
     */
    private Cards createNewCard(String mobileNumber) {
        Cards newCard = new Cards();
        long randomCardNumber = 100000000000L + new Random().nextInt(900000000);
        newCard.setCardNumber(Long.toString(randomCardNumber));
        newCard.setMobileNumber(mobileNumber);
        newCard.setCardType(CardsConstants.CREDIT_CARD);
        newCard.setTotalLimit(CardsConstants.NEW_CARD_LIMIT);
        newCard.setAmountUsed(0);
        newCard.setAvailableAmount(CardsConstants.NEW_CARD_LIMIT);
        return newCard;
    }



    /**
     * @param mobileNumber - Input mobile Number
     * @return Card Details based on a given mobileNumber
     */
    @Override
    public CardsDto fetchCard(String mobileNumber) {
        Cards cards = cardsRepo.findByMobileNumber(mobileNumber).orElseThrow(
                ()-> new CardAlreadyExistsException("Card does not exist for the mobile number: "+ mobileNumber)
        );
        CardsDto cardsDto = new CardsDto();
        CardsMapper.mapToCardsDto(cards, cardsDto);
        return cardsDto;
    }

    /**
     * @param cardsDto - CardsDto Object
     * @return boolean indicating if the update of card details is successful or not
     */
    @Override
    public boolean updateCard(CardsDto cardsDto) {
        Cards cards = cardsRepo.findByCardNumber(cardsDto.getCardNumber()).orElseThrow(
                ()-> new CardAlreadyExistsException("Card does not exist for the card number: "+ cardsDto.getCardNumber())
        );
        CardsMapper.mapToCards(cardsDto, cards);
        cardsRepo.save(cards);
        return true;
    }

    /**
     * @param mobileNumber - Input Mobile Number
     * @return boolean indicating if the delete of card details is successful or not
     */
    @Override
    public boolean deleteCard(String mobileNumber) {
        Cards cards = cardsRepo.findByMobileNumber(mobileNumber).orElseThrow(
                ()-> new CardAlreadyExistsException("Card does not exist for the mobile number: "+ mobileNumber)
        );
        cardsRepo.deleteById(cards.getCardId());
        return true;
    }

}
