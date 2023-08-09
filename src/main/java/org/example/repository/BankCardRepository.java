package org.example.repository;

import org.example.model.BankCard;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Objects;

public class BankCardRepository {
    private static final String nameFile = "dataBank.txt";
    private double limitForWithdraw = 0;
    private ArrayList<BankCard> bankCards = new ArrayList<>();

    public ArrayList<BankCard> getBankCards() {
        return bankCards;
    }
    public BankCardRepository(){
        setBankCards();
    }

    public void setBankCards(){
        String[] StringData = getStringData();
        for(int i = 0; i <= StringData.length-1;i+=4){
            BankCard temp = new BankCard(StringData[i],
                    StringData[i+1],
                    Double.parseDouble(StringData[i+2]),
                    !Objects.equals(StringData[i + 3], "0") ? LocalDate.parse(StringData[i+3]) : null
                    );
            limitForWithdraw+=Double.parseDouble(StringData[i+2]);
            bankCards.add(temp);
        }
        LocalDate localDate = LocalDate.now();
        for(var templates : bankCards){
            if(templates.getDateBlocked()!=null && templates.getDateBlocked().isBefore(localDate)){
                templates.setUnBlocked();
            }
        }
    }
    private String[] getStringData(){
        try(BufferedReader bufferedReader = new BufferedReader(new FileReader(nameFile))) {
            String date = bufferedReader.readLine();
            return date.split(" ");
        }
        catch (Exception e){
           throw new RuntimeException(e.getMessage());
        }
    }
    public void updateCards() {
        try(BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(nameFile))){
            String date = setStringDate();
            bufferedWriter.write(date);

        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    private String setStringDate() {
        String date = "";
        for(var templates : bankCards) {
            date += (templates.getNumberCard() +
                    " " + templates.getPinCod() +
                    " " + templates.getBankPrincipal().getBalance() +
                    " " + (templates.getDateBlocked()==null ? 0 : templates.getDateBlocked()) + " ");
        }
        return date.substring(0,date.length()-1);
    }

    public void withdrawFunds(double amount, String numberCard){
        if(amount>limitForWithdraw){
            System.out.println("Сумма превышает лимит банкоманата: " + limitForWithdraw);
        }
        else {
            for(var i : bankCards){
                if (i.getNumberCard().equals(numberCard)) {
                    i.getBankPrincipal().setBalance(i.getBankPrincipal().getBalance()-amount);
                    limitForWithdraw-=amount;
                }
            }
        }
    }
    public void blockedCard(String numberCard) {
        for(var i : bankCards){
            if (i.getNumberCard().equals(numberCard)) i.setDateBlocked();
        }
    }

    public void topUpBalance(double amount, String numberCard) {
        for(var i : bankCards){
            if (i.getNumberCard().equals(numberCard)) {
                i.getBankPrincipal().setBalance(i.getBankPrincipal().getBalance() + amount);
                limitForWithdraw+=amount;
            }
        }
    }
}
