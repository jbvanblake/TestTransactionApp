package org.example;

// Main Class
public class TransactionProcessorApp {

    public void processTransactions(String[] transactions, AccountDao accountDao) {
        CommandParserProvider commandParserProvider = new CommandParserProvider(accountDao);

        log("Start Inputs:");
        for (String transaction : transactions) {
            Command command = new Command(transaction);
            CommandParser parser = commandParserProvider.getParserFromTransactionString(command.getRemainingCommandString());
            command.consume(4);

            parser.parse(command);

            log(transaction);
        }
        log("End Inputs:");
        accountDao.printAccounts();
    }

    private static void log(String message) {
        System.out.println(message);
    }

}
