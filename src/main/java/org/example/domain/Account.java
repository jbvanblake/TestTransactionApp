package org.example.domain;

public class Account {
    private Integer amount;
    private Integer dailyWithdraw;

    public Account(Integer transactionAmount, Integer dailyWithdraw) {
        this.amount = transactionAmount;
        this.dailyWithdraw = dailyWithdraw;
    }

    public Integer getAmount() {
        return amount;
    }

    public Integer getDailyWithdraw() {
        return dailyWithdraw;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public void setDailyWithdraw(Integer dailyWithdraw) {
        this.dailyWithdraw = dailyWithdraw;
    }
}
