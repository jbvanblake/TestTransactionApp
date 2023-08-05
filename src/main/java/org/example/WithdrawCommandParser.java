package org.example;

import static org.example.TransactionType.WITHDRAW;

public class WithdrawCommandParser extends CommandParser {
    public WithdrawCommandParser(AccountDao accountDao) {
        super(accountDao, WITHDRAW);
    }

    @Override
    public void parse(String transaction) {
        String accountNumber = extractAccountNumber(transaction, 4);
                Integer transactionAmount = extractTransactionAmount(transaction, 6 + accountNumber.length());

                accountDao.withdraw(accountNumber, transactionAmount);
    }
}
