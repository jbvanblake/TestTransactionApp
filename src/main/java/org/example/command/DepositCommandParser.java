package org.example.command;

import org.example.dao.AccountDao;

import static org.example.domain.TransactionType.DEPOSIT;

public class DepositCommandParser extends CommandParser {

    public DepositCommandParser(AccountDao accountDao) {
        super(accountDao, DEPOSIT);
    }

    @Override
    public void parse(Command command) {
        String accountNumber = consumeCommandAccountName(command);
        Integer transactionAmount = consumeCommandTransactionAmount(command);

        accountDao.deposit(accountNumber, transactionAmount, true);
    }
}
