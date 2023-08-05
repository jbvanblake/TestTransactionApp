package org.example;

public abstract class CommandParser {
    protected final AccountDao accountDao;
    protected TransactionType type;


    public CommandParser(AccountDao accountDao, TransactionType type) {
        this.accountDao = accountDao;
        this.type = type;
    }

    public abstract void parse(String transaction);

    public String extractAccountNumber(String transaction, int beginIndex) {

        int accountLengthEndIndex = beginIndex + 2;
        String accountNumberLengthString = transaction.substring(beginIndex, accountLengthEndIndex);
        Integer accountNumberLength = Integer.parseInt(accountNumberLengthString);

        return transaction.substring(accountLengthEndIndex, accountLengthEndIndex + accountNumberLength);
    }

    public Integer extractTransactionAmount(String transaction, int startingIndex) {
        return Integer.parseInt(transaction.substring(startingIndex));
    }


}
