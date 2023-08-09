package org.example.controller;

import org.example.model.BankCard;
import org.example.repository.BankCardRepository;

import java.io.IOException;

public class CardController {
    private BankCardRepository bankCardRepository;
    public CardController(BankCardRepository bankCardRepository){
        this.bankCardRepository = bankCardRepository;
    }

    public BankCard findBankCardByNumber(String numberCard) {
        for(var card : bankCardRepository.getBankCards()){
            if(card.getNumberCard().equals(numberCard)) return card;
        }
        return null;
    }

    public void blockedCard(BankCard bankCard) {
        bankCardRepository.blockedCard(bankCard.getNumberCard());
    }

    public void withdrawFunds(double amount,String numberCard) {
        bankCardRepository.withdrawFunds(amount,numberCard);
    }

    public void topUpBalance(double amount, String numberCard) {
        bankCardRepository.topUpBalance(amount,numberCard);
    }

    public void updateCards() {
        bankCardRepository.updateCards();
    }
}
