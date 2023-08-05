package org.example;

import java.util.HashMap;
import java.util.Map;

import static org.example.TransactionProcessorApp.TransactionType.*;
import static org.example.TransactionProcessorApp.TransactionType.DEPOSIT;
import static org.example.TransactionProcessorApp.TransactionType.WITHDRAW;

// Main Class
public class TransactionProcessorApp {

    private static final Integer WITHDRAW_LIMIT_CENTS = 20000;
    private static final Integer WITHDRAW_DAILY_LIMIT_CENTS = 50000;
    public static final int TRANSACTION_TYPE_STRING_LENGTH = 4;
    public static final int ACCOUNT_NUMBER_LENGTH_DENOTATION_LENGTH = 2;
    private String DEPOSIT_TYPE_STRING = "1010";
    private String WITHDRAW_TYPE_STRING = "1020";
    private String TRANSFER_TYPE_STRING = "2010";
    private Integer DEPOSIT_LIMIT_CENTS = 100000;
    private Map<String, Account> accounts = new HashMap();

    public void processTransactions(String[] transactions) {

        log("Starting Transaction Processor:");
        log("Start Inputs:");
        for (String transaction : transactions) {
            TransactionType transactionType = extractTransactionType(transaction);

            if (transactionType == DEPOSIT) {
                String accountNumber = extractAccountNumber(transaction, 4);
                Integer transactionAmount = extractTransactionAmount(transaction, 6 + accountNumber.length());

                deposit(accountNumber, transactionAmount, true);
            }

            if (transactionType == WITHDRAW) {
                String accountNumber = extractAccountNumber(transaction, 4);
                Integer transactionAmount = extractTransactionAmount(transaction, 6 + accountNumber.length());

                withdraw(accountNumber, transactionAmount);
            }

            if (transactionType == TRANSFER) {
                String sourceAccountNumber = extractAccountNumber(transaction, 4);
                String destinationAccountNumber = extractAccountNumber(transaction, 6 + sourceAccountNumber.length());


                int amountNotationBeginningIndex =  TRANSACTION_TYPE_STRING_LENGTH +
                        ACCOUNT_NUMBER_LENGTH_DENOTATION_LENGTH +
                        sourceAccountNumber.length() +
                        ACCOUNT_NUMBER_LENGTH_DENOTATION_LENGTH +
                        destinationAccountNumber.length();

                Integer transactionAmount = extractTransactionAmount(transaction, amountNotationBeginningIndex);

                boolean successfulWithdraw = withdraw(sourceAccountNumber, transactionAmount);

                if(successfulWithdraw) {
                    boolean successfulDeposit = deposit(destinationAccountNumber, transactionAmount, false);
                    forceDeposit(sourceAccountNumber, transactionAmount);
                }

            }

            log(transaction);

        }
        log("End Inputs:");

        accounts.entrySet().stream()
                .forEach(entry -> log(entry.getKey() + " - $" + entry.getValue().getAmount()));
        ;
// TODO: Implement
    }

    private static void log(String message) {
        System.out.println(message);
    }

    private boolean withdraw(String accountNumber, Integer transactionAmount) {

        log("Withdraw AccountNumber:" + accountNumber + " transaction Amount " + transactionAmount);
        if (transactionAmount > WITHDRAW_LIMIT_CENTS) {
            return false;
        }
        if (!accounts.containsKey(accountNumber)) {
            return false;
        }
        Account account = accounts.get(accountNumber);
        if (account.getDailyWithdraw() + transactionAmount > WITHDRAW_DAILY_LIMIT_CENTS) {
            return false;
        }
        if (account.getAmount() < transactionAmount) {
            return false;
        }
        log("Success Withdraw");

        account.setAmount(account.getAmount() - transactionAmount);
        account.setDailyWithdraw(transactionAmount + account.getDailyWithdraw());

        return true;
    }

    private boolean deposit(String accountNumber, Integer transactionAmount, boolean createOnNotExist) {
        log("Deposit AccountNumber:" + accountNumber + " transaction Amount " + transactionAmount);

        if (transactionAmount > DEPOSIT_LIMIT_CENTS) {
            return false;
        }

        if (!accounts.containsKey(accountNumber)) {
            if(!createOnNotExist) {

                return false;
            }
            Account account = new Account(transactionAmount, 0);
            accounts.put(accountNumber, account);
            return true;
        }

        Account account = accounts.get(accountNumber);
        account.setAmount(transactionAmount + account.getAmount());
        log("Successful Deposit");

        return true;
    }

    public Integer extractTransactionAmount(String transaction, int startingIndex) {
        return Integer.parseInt(transaction.substring(startingIndex));
    }

    public String extractAccountNumber(String transaction, int beginIndex) {

        int accountLengthEndIndex = beginIndex + 2;
        String accountNumberLengthString = transaction.substring(beginIndex, accountLengthEndIndex);
        Integer accountNumberLength = Integer.parseInt(accountNumberLengthString);

        return transaction.substring(accountLengthEndIndex, accountLengthEndIndex + accountNumberLength);
    }

    public TransactionType extractTransactionType(String transaction) {
        String transactionTypePrefix = transaction.substring(0, 4);
        if (transactionTypePrefix.equals(DEPOSIT_TYPE_STRING)) {
            return DEPOSIT;
        }
        if (transactionTypePrefix.equals(WITHDRAW_TYPE_STRING)) {
            return WITHDRAW;
        }

        if (transactionTypePrefix.equals(TRANSFER_TYPE_STRING)) {
            return TRANSFER;
        }

        return null;
    }

    public enum TransactionType {
        DEPOSIT,
        WITHDRAW,
        TRANSFER
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
