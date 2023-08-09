package org.example.command;

import org.example.domain.TransactionType;
import org.example.dao.AccountDao;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.example.domain.TransactionType.valueOfPrefix;

public class CommandParserProvider {
    List<CommandParser> parsers;

    public CommandParserProvider(AccountDao accountDao) {
        this.parsers = new ArrayList<>();
        parsers.add(new DepositCommandParser(accountDao));
        parsers.add(new WithdrawCommandParser(accountDao));
        parsers.add(new TransferCommandParser(accountDao));
    }

    public CommandParser getParserFromTransactionString(Command command) {
        TransactionType transactionType = extractTransactionType(command.getRemainingCommandString());
        command.consumeNumCommandChars(4);

        return parsers.stream()
                .filter(commandParser -> commandParser.type == transactionType)
                .findFirst()
                .get();
    }

    public TransactionType extractTransactionType(String transaction) {
        String transactionTypePrefix = transaction.substring(0, 4);
        Optional<TransactionType> transactionType = valueOfPrefix(transactionTypePrefix);
        return transactionType.get();
    }
}
