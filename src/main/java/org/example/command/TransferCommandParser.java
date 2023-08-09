package org.example.command;

import org.example.domain.TransactionType;
import org.example.dao.AccountDao;

public class TransferCommandParser extends CommandParser {

    public TransferCommandParser(AccountDao accountDao) {
        super(accountDao, TransactionType.TRANSFER);
    }

    @Override
    public void parse(Command command) {

        String sourceAccountNumber = consumeCommandAccountName(command);
        String destinationAccountNumber = consumeCommandAccountName(command);

        Integer transactionAmount = consumeCommandTransactionAmount(command);

        boolean successfulWithdraw = accountDao.withdraw(sourceAccountNumber, transactionAmount);

        if (successfulWithdraw) {
            boolean successfulDeposit = accountDao.deposit(destinationAccountNumber, transactionAmount, false);
            if(!successfulDeposit) {
                accountDao.forceDeposit(sourceAccountNumber, transactionAmount);
            }
        }
    }
}
