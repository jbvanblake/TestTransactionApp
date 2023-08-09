package org.example;

public abstract class CommandParser {
    public static final int TRANSACTION_COMMAND_AMOUNT_LENGTH = 10;
    public static final int TRANSACTION_COMMAND_ACCOUNT_NUMBER_LENGTH_DENOTER = 2;

    protected final AccountDao accountDao;
    protected TransactionType type;

    public CommandParser(AccountDao accountDao, TransactionType type) {
        this.accountDao = accountDao;
        this.type = type;
    }

    public abstract void parse(Command transaction);
    protected Integer consumeCommandTransactionAmount(Command command) {
        int transactionAmount = Integer.parseInt(command.getRemainingCommandString().substring(0, TRANSACTION_COMMAND_AMOUNT_LENGTH));

        command.consume(TRANSACTION_COMMAND_AMOUNT_LENGTH);

        return transactionAmount;
    }

    protected String consumeCommandAccountName(Command command) {

        String accountNumberLengthString = command.getRemainingCommandString().substring(0, TRANSACTION_COMMAND_ACCOUNT_NUMBER_LENGTH_DENOTER);
        Integer accountNumberLength = Integer.parseInt(accountNumberLengthString);

        command.consume(TRANSACTION_COMMAND_ACCOUNT_NUMBER_LENGTH_DENOTER);

        String accountNumber = command.getRemainingCommandString().substring(0, accountNumberLength);
        command.consume(accountNumberLength);

        return accountNumber;
    }
}
