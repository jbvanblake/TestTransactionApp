package org.example;

import org.example.command.Command;
import org.example.command.CommandParser;
import org.example.command.CommandParserProvider;
import org.example.dao.AccountDao;

// Main Class
public class TransactionProcessorApp {

    public void processTransactions(String[] transactions, AccountDao accountDao) {
        CommandParserProvider commandParserProvider = new CommandParserProvider(accountDao);

        log("Start Inputs:");
        for (String transaction : transactions) {
            Command command = new Command(transaction);
            CommandParser parser = commandParserProvider.getParserFromTransactionString(command);
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
