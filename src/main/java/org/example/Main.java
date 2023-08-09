package org.example;

import org.example.controller.AppController;
import org.example.controller.CardController;
import org.example.repository.BankCardRepository;
import org.example.view.ViewApp;

public class Main {
    public static void main(String[] args) {
        BankCardRepository cardRepository = new BankCardRepository();
        CardController cardController = new CardController(cardRepository);
        AppController appController = new AppController(cardController);
        ViewApp viewApp = new ViewApp(appController);
        viewApp.start();
    }
}