package org.example;

public class TransferCommandParser extends CommandParser {

    public static final int SOURCE_ACCOUNT_STARTING_INDEX = 4;
    public static final int DESTINATION_ACCOUNT_STARTING_INDEX = 6;

    public TransferCommandParser(AccountDao accountDao) {
        super(accountDao, TransactionType.TRANSFER);
    }

    @Override
    public void parse(String transaction) {
        String sourceAccountNumber = extractAccountNumber(transaction, SOURCE_ACCOUNT_STARTING_INDEX);
        String destinationAccountNumber = extractAccountNumber(transaction, DESTINATION_ACCOUNT_STARTING_INDEX + sourceAccountNumber.length());

        int amountNotationBeginningIndex = DESTINATION_ACCOUNT_STARTING_INDEX +
                sourceAccountNumber.length() +
                ACCOUNT_NUMBER_LENGTH_DENOTATION_LENGTH +
                destinationAccountNumber.length();

        Integer transactionAmount = extractTransactionAmount(transaction, amountNotationBeginningIndex);

        boolean successfulWithdraw = accountDao.withdraw(sourceAccountNumber, transactionAmount);

        if (successfulWithdraw) {
            boolean successfulDeposit = accountDao.deposit(destinationAccountNumber, transactionAmount, false);
            if(!successfulDeposit) {
                accountDao.forceDeposit(sourceAccountNumber, transactionAmount);
            }
        }
    }
}
