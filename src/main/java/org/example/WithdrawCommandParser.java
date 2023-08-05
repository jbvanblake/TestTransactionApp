package org.example;

import static org.example.TransactionType.WITHDRAW;

public class WithdrawCommandParser extends CommandParser {

    public static final int ACCOUNT_STARTING_INDEX = 4;
    public static final int TRANSACTION_AMOUNT_STARTING_INDEX = 6;

    public WithdrawCommandParser(AccountDao accountDao) {
        super(accountDao, WITHDRAW);
    }

    @Override
    public void parse(String transaction) {
        String accountNumber = extractAccountNumber(transaction, ACCOUNT_STARTING_INDEX);
                Integer transactionAmount = extractTransactionAmount(transaction, TRANSACTION_AMOUNT_STARTING_INDEX + accountNumber.length());

                accountDao.withdraw(accountNumber, transactionAmount);
    }
}
