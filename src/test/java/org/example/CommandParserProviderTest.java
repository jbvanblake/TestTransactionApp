package org.example;

import org.junit.Test;

import static org.example.TransactionType.DEPOSIT;
import static org.junit.Assert.assertEquals;

public class CommandParserProviderTest {
    @Test
    public void testExtractTransactionType() {
        CommandParserProvider commandParserProvider = new CommandParserProvider(null);
        TransactionType transactionType = commandParserProvider.extractTransactionType("10100712345670000200000");
        assertEquals(transactionType, DEPOSIT);
    }
}