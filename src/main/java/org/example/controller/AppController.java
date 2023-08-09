package org.example.controller;

import org.example.model.BankCard;
import org.example.model.BankPrincipal;

import java.io.IOException;
import java.util.Scanner;
import java.util.regex.Pattern;

public class AppController {
    private final CardController cardController;
    private static final Scanner scanner = new Scanner(System.in);
    private BankPrincipal bankPrincipal;
    private BankCard bankCard;

    public boolean isAuth(){
        if(bankPrincipal==null) return false;
        return bankPrincipal.isAuthorization();
    }
    public void bankCardCheck(){
        System.out.println("Введите номер карты в формате XXXX-XXXX-XXXX-XXXX:");
        String numberCard = scanner.next();
        Pattern patternForNumberCard = Pattern.compile("^[0-9]{4}[\\-][0-9]{4}[\\-][0-9]{4}[\\-][0-9]{4}");//Сравнение с ожидаемым форматом
        if (!Pattern.matches(String.valueOf(patternForNumberCard),numberCard)) {
            System.out.println("Не корректный ввод");
        }
        else {
            BankCard bankCard = cardController.findBankCardByNumber(numberCard);
            if(bankCard==null){
                System.out.println("Банковская карточка с таким номером не найдена");
            }
            else {
                pinCodeCheck(bankCard); //Карта с таким номером найдена, проверка пин-кода
            }
        }
    }
    private void pinCodeCheck(BankCard bankCardLocal){
        int checkPin = bankCardLocal.getDateBlocked()==null ? 3 : 0;
        while (checkPin!=0){
            System.out.println("Введите пин-код:");
            String pinCode = scanner.next();
            Pattern patternForPinCode = Pattern.compile("^[0-9]{4}");
            if(!Pattern.matches(String.valueOf(patternForPinCode),pinCode)){
                System.out.println("Не корректный ввод");
            }
            else {
                if(!pinCode.equals(bankCardLocal.getPinCod())){
                    checkPin--;
                    System.out.println("Неверный пин-код. У вас осталось " + checkPin + " попытки");
                    if(checkPin==0){
                        cardController.blockedCard(bankCardLocal);
                        updateCards();
                    }
                }
                else    {
                    System.out.println("Добро пожаловать!");
                    checkPin=0;
                    bankCard = bankCardLocal;
                    bankPrincipal = bankCard.getBankPrincipal();
                    bankPrincipal.setAuthorization(true);
                    setLoad(false);
                }
            }
        }
        if(checkPin==0 && isAuth()==false){
            System.out.println("Доступ заблокирован до " + bankCardLocal.getDateBlocked().plusDays(1));
        }
    }

    public void mainView() {
        boolean load = true;
        while(true){
        System.out.println("Чтобы посмотреть баланс нажмите - 1");
        System.out.println("Чтобы пополнить средства нажмите - 2");
        System.out.println("Чтобы снять средства нажмите - 3");
            System.out.println("Чтобы выйти - 4");
        int answer = Integer.parseInt(scanner.next());
        switch (answer){
            case 1:
                showBalance();
                break;
            case 2:
                topUpBalance();
                updateCards();
                break;
            case 3:
                withdrawFunds();
                updateCards();
                break;
            case 4:
                load = false;
                return;
        }}
    }

    private void updateCards() {
        cardController.updateCards();
    }

    private void withdrawFunds() {
        System.out.println("Введите сумму для вывода ");
        double amount = Double.parseDouble(scanner.next());
        if(amount<=bankPrincipal.getBalance())   cardController.withdrawFunds(amount,bankCard.getNumberCard());
        else {
            System.out.println("Сумма превышает ваш баланс");
        }
    }

    private void topUpBalance() {
        System.out.println("Введите сумму для пополнения ");
        double amount = Double.parseDouble(scanner.next());
        if(amount <= 1000000){
            cardController.topUpBalance(amount,bankCard.getNumberCard());
        }
        else {
            System.out.println("Сумма превышает лимит для пополенния в 1 000 000");
        }
    }

    private void showBalance() {
        double balance = bankPrincipal.getBalance();
        System.out.println("Ваш баланс: " + balance);
    }

    private boolean load = true;
    public void setLoad(boolean load) {
        this.load = load;
    }
    public AppController(CardController cardController){
        this.cardController = cardController;
    }
    public boolean getLoad(){
        return load;
    }
}
