package org.example;

import static org.example.TransactionType.DEPOSIT;

public class DepositCommandParser extends CommandParser {

    public static final int ACCOUNT_NUMBER_STARTING_INDEX = 4;
    public static final int TRANSACTION_AMOUNT_STARTING_INDEX = 6;

    public DepositCommandParser(AccountDao accountDao) {
        super(accountDao, DEPOSIT);
    }

    @Override
    public void parse(String transaction) {
        String accountNumber = extractAccountNumber(transaction, ACCOUNT_NUMBER_STARTING_INDEX);
        Integer transactionAmount = extractTransactionAmount(transaction, TRANSACTION_AMOUNT_STARTING_INDEX + accountNumber.length());

        accountDao.deposit(accountNumber, transactionAmount, true);
    }
}
