package org.example.command;

import org.example.command.CommandParserProvider;
import org.example.domain.TransactionType;
import org.junit.Test;

import static org.example.domain.TransactionType.DEPOSIT;
import static org.junit.Assert.assertEquals;

public class CommandParserProviderTest {
    @Test
    public void testExtractTransactionType() {
        CommandParserProvider commandParserProvider = new CommandParserProvider(null);
        TransactionType transactionType = commandParserProvider.extractTransactionType("10100712345670000200000");
        assertEquals(transactionType, DEPOSIT);
    }
}