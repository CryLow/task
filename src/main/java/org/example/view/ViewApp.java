package org.example.view;

import org.example.controller.AppController;
import org.example.controller.CardController;
import org.example.model.BankCard;
import org.example.model.BankPrincipal;

import java.io.IOException;
import java.util.Scanner;
import java.util.regex.Pattern;

public class ViewApp
{
    private static final Scanner scanner = new Scanner(System.in);
    private final AppController appController;
    public ViewApp(AppController appController){
        this.appController = appController;
    }
    public void start(){
        while (appController.getLoad()){
            appController.bankCardCheck();
            if(appController.isAuth()){
                appController.mainView();
            }
        }
    }
}
