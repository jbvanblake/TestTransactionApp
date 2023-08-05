package org.example;

import java.util.HashMap;
import java.util.Map;

public class AccountDaoImpl implements AccountDao {
    private static final Integer WITHDRAW_LIMIT_CENTS = 20000;
    private static final Integer WITHDRAW_DAILY_LIMIT_CENTS = 50000;
    private Integer DEPOSIT_LIMIT_CENTS = 100000;

    private Map<String, Account> accounts = new HashMap();

    @Override
    public boolean deposit(String accountNumber, Integer transactionAmount, boolean createOnNotExist) {
        log("Deposit AccountNumber:" + accountNumber + " transaction Amount " + transactionAmount);

        if (transactionAmount > DEPOSIT_LIMIT_CENTS) {
            return false;
        }

        if (!accounts.containsKey(accountNumber)) {
            if (!createOnNotExist) {

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

    @Override
    public boolean withdraw(String accountNumber, Integer transactionAmount) {

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

    @Override
    public void forceDeposit(String accountNumber, Integer transactionAmount) {
        if (!accounts.containsKey(accountNumber)) {
            return;
        }
        Account account = accounts.get(accountNumber);
        account.setAmount(transactionAmount + account.getAmount());
        log("Successful Deposit");
    }

    @Override
    public void printAccounts() {
        accounts.entrySet().stream()
                .forEach(entry -> log(entry.getKey() + " - $" + entry.getValue().getAmount()));
        ;
    }

    private static void log(String message) {
        System.out.println(message);
    }
}
