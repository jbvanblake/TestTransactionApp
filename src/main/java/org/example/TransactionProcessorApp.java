package org.example;

// Main Class
public class TransactionProcessorApp {

    public void processTransactions(String[] transactions, AccountDao accountDao) {
        CommandParserProvider commandParserProvider = new CommandParserProvider(accountDao);

        log("Start Inputs:");
        for (String transaction : transactions) {
            CommandParser parser = commandParserProvider.getParserFromTransactionString(transaction);

            parser.parse(transaction);

            log(transaction);
        }
        log("End Inputs:");
        accountDao.printAccounts();
    }

    private static void log(String message) {
        System.out.println(message);
    }

}
