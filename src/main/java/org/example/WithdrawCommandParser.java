package org.example;

import static org.example.TransactionType.WITHDRAW;

public class WithdrawCommandParser extends CommandParser {

    public WithdrawCommandParser(AccountDao accountDao) {
        super(accountDao, WITHDRAW);
    }

    @Override
    public void parse(Command command) {
        String accountNumber = consumeCommandAccountName(command);
        Integer transactionAmount = consumeCommandTransactionAmount(command);
        accountDao.withdraw(accountNumber, transactionAmount);
    }
}
