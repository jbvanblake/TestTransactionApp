package org.example;

import java.util.Optional;

import static org.example.TransactionType.*;

// Main Class
public class TransactionProcessorApp {

    public static final int TRANSACTION_TYPE_STRING_LENGTH = 4;
    public static final int ACCOUNT_NUMBER_LENGTH_DENOTATION_LENGTH = 2;

    public void processTransactions(String[] transactions) {
        AccountDao accountDao = new AccountDaoImpl();
        CommandParserProvider commandParserProvider = new CommandParserProvider(accountDao);

        log("Starting Transaction Processor:");
        log("Start Inputs:");
        for (String transaction : transactions) {
            TransactionType transactionType = extractTransactionType(transaction);

            CommandParser parser = commandParserProvider.getParser(transactionType);

            parser.parse(transaction);

            log(transaction);

        }
        log("End Inputs:");
        accountDao.printAccounts();

    }

    private static void log(String message) {
        System.out.println(message);
    }

    public String extractAccountNumber(String transaction, int beginIndex) {

        int accountLengthEndIndex = beginIndex + 2;
        String accountNumberLengthString = transaction.substring(beginIndex, accountLengthEndIndex);
        Integer accountNumberLength = Integer.parseInt(accountNumberLengthString);

        return transaction.substring(accountLengthEndIndex, accountLengthEndIndex + accountNumberLength);
    }

    public TransactionType extractTransactionType(String transaction) {
        String transactionTypePrefix = transaction.substring(0, 4);
        Optional<TransactionType> transactionType = valueOfPrefix(transactionTypePrefix);
        return transactionType.get();
    }

}
