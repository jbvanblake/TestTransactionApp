package org.example;

import java.util.ArrayList;
import java.util.List;

public class CommandParserProvider {
    List<CommandParser> parsers;

    public CommandParserProvider(AccountDao accountDao) {
        this.parsers = new ArrayList<>();
        parsers.add(new DepositCommandParser(accountDao));
        parsers.add(new WithdrawCommandParser(accountDao));
        parsers.add(new TransferCommandParser(accountDao));
    }

    public CommandParser getParser(TransactionType t) {
        return parsers.stream().filter(commandParser -> commandParser.type == t).findFirst().get();}
}
