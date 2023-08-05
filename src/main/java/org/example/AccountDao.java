package org.example;

public interface AccountDao {
    boolean deposit(String accountNumber, Integer transactionAmount, boolean b);

    boolean withdraw(String accountNumber, Integer transactionAmount);

    void forceDeposit(String sourceAccountNumber, Integer transactionAmount);

    void printAccounts();
}
