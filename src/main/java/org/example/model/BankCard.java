package org.example.model;

import java.time.LocalDate;

public class BankCard {
    private final String numberCard;
    private String pinCod;
    private BankPrincipal bankPrincipal;
    private LocalDate dateBlocked;

    public BankCard(String numberCard){
        this.numberCard = numberCard;
    }
    public BankCard(String numberCard, String pinCod, double balance , LocalDate dateBlocked){
        this.numberCard = numberCard;
        this.pinCod = pinCod;
        this.bankPrincipal = new BankPrincipal(balance);
        this.dateBlocked = dateBlocked;
    }

    public String getNumberCard() {
        return numberCard;
    }

    public BankPrincipal getBankPrincipal() {
        return bankPrincipal;
    }

    public String getPinCod() {
        return pinCod;
    }

    public void setPinCod(String pinCod) {
        this.pinCod = pinCod;
    }

    public LocalDate getDateBlocked() {
        return dateBlocked;
    }

    public void setUnBlocked(){
        this.dateBlocked = null;
    }
    public void setDateBlocked() {
        this.dateBlocked = LocalDate.now();
    }
}
