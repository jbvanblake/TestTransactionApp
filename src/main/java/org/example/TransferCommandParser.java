package org.example;

public class TransferCommandParser extends CommandParser {
    public TransferCommandParser(AccountDao accountDao) {
        super(accountDao, TransactionType.TRANSFER);
    }

    @Override
    public void parse(String transaction) {
        String sourceAccountNumber = extractAccountNumber(transaction, 4);
                String destinationAccountNumber = extractAccountNumber(transaction, 6 + sourceAccountNumber.length());


                int amountNotationBeginningIndex =  TRANSACTION_TYPE_STRING_LENGTH +
                        ACCOUNT_NUMBER_LENGTH_DENOTATION_LENGTH +
                        sourceAccountNumber.length() +
                        ACCOUNT_NUMBER_LENGTH_DENOTATION_LENGTH +
                        destinationAccountNumber.length();

                Integer transactionAmount = extractTransactionAmount(transaction, amountNotationBeginningIndex);

                boolean successfulWithdraw = accountDao.withdraw(sourceAccountNumber, transactionAmount);

                if(successfulWithdraw) {
                    boolean successfulDeposit = accountDao.deposit(destinationAccountNumber, transactionAmount, false);
                    accountDao.forceDeposit(sourceAccountNumber, transactionAmount);
                }
    }
}
