package org.example;

import java.util.HashMap;
import java.util.Map;

import static org.example.TransactionProcessorApp.TransactionType.DEPOSIT;
import static org.example.TransactionProcessorApp.TransactionType.WITHDRAW;

// Main Class
public class TransactionProcessorApp {

    private static final Integer WITHDRAW_LIMIT_CENTS = 20000;
    private static final Integer WITHDRAW_DAILY_LIMIT_CENTS = 50000;
    private String DEPOSIT_TYPE_STRING = "1010";
    private String WITHDRAW_TYPE_STRING = "1020";
    private Integer DEPOSIT_LIMIT_CENTS = 100000;
    private Map<String, Account> accounts = new HashMap();

    public void processTransactions(String[] transactions) {

        log("Starting Transaction Processor:");
        log("Start Inputs:");
        for (String transaction : transactions) {
            TransactionType transactionType = extractTransactionType(transaction);
            String accountNumber = extractAccountNumber(transaction);
            Integer transactionAmount = extractTransactionAmount(transaction, 6 + accountNumber.length());

            if (transactionType == DEPOSIT) {
                deposit(accountNumber, transactionAmount);
            }

            if (transactionType == WITHDRAW) {
                withdraw(accountNumber, transactionAmount);
            }

            log(transaction);

        }
        log("End Inputs:");

        accounts.entrySet().stream()
                .forEach(entry -> System.out.println(entry.getKey() + " - $" + entry.getValue().getAmount()));
        ;
// TODO: Implement
    }

    private static void log(String message) {
        System.out.println(message);
    }

    private void withdraw(String accountNumber, Integer transactionAmount) {
        if (transactionAmount > WITHDRAW_LIMIT_CENTS) {
            return;
        }
        if (!accounts.containsKey(accountNumber)) {
            return;
        }
        Account account = accounts.get(accountNumber);
        if (account.getDailyWithdraw() + transactionAmount > WITHDRAW_DAILY_LIMIT_CENTS) {
            return;
        }
        if (account.getAmount() < transactionAmount) {
            return;
        }

        account.setAmount(account.getAmount() - transactionAmount);
        account.setDailyWithdraw(transactionAmount + account.getDailyWithdraw());

    }

    private void deposit(String accountNumber, Integer transactionAmount) {

        if (transactionAmount > DEPOSIT_LIMIT_CENTS) {
            return;
        }

        if (!accounts.containsKey(accountNumber)) {
            Account account = new Account(transactionAmount, 0);
            accounts.put(accountNumber, account);
            return;
        }

        Account account = accounts.get(accountNumber);
        account.setAmount(transactionAmount + account.getAmount());

    }

    private Integer extractTransactionAmount(String transaction, int startingIndex) {
        return Integer.parseInt(transaction.substring(startingIndex));
    }

    public String extractAccountNumber(String transaction) {

        String accountNumberLengthString = transaction.substring(4, 6);
        Integer accountNumberLength = Integer.parseInt(accountNumberLengthString);

        return transaction.substring(6, 6 + accountNumberLength);


    }

    public TransactionType extractTransactionType(String transaction) {
        String transactionTypePrefix = transaction.substring(0, 4);
        if (transactionTypePrefix.equals(DEPOSIT_TYPE_STRING)) {
            return DEPOSIT;
        }
        if (transactionTypePrefix.equals(WITHDRAW_TYPE_STRING)) {
            return WITHDRAW;
        }
        return null;
    }

    public enum TransactionType {
        DEPOSIT,
        WITHDRAW
    }

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


}
