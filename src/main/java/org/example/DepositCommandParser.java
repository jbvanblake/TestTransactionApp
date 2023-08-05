package org.example;

import static org.example.TransactionType.DEPOSIT;

public class DepositCommandParser extends CommandParser {

    public DepositCommandParser(AccountDao accountDao) {
        super(accountDao, DEPOSIT);
    }

    @Override
    public void parse(String transaction) {
        String accountNumber = extractAccountNumber(transaction, 4);
        Integer transactionAmount = extractTransactionAmount(transaction, 6 + accountNumber.length());

        accountDao.deposit(accountNumber, transactionAmount, true);
    }
}
