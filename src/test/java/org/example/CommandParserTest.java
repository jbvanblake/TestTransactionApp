package org.example;

import org.junit.Test;

import static org.example.CommandParser.ACCOUNT_NUMBER_LENGTH_DENOTATION_LENGTH;
import static org.example.CommandParser.TRANSACTION_TYPE_STRING_LENGTH;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CommandParserTest {

    private CommandParser parser = new CommandParser(null, null) {
        @Override
        public void parse(String transaction) {
        }
    };
    @Test
    public void testExtractAccountNumber() {
        TransactionProcessorApp app = new TransactionProcessorApp();
        String accountNumber = parser.extractAccountNumber("10100712345670000200000", 4);
        assertEquals(accountNumber, "1234567");
    }
    @Test
    public void testExtractAccountNumberForTransfer() {
        String sourceAccountNumber = parser.extractAccountNumber("2010064447770712345670000020000", 4);
        assertEquals( "444777", sourceAccountNumber);

        String destinationAccountNumber = parser.extractAccountNumber("2010064447770712345670000020000", sourceAccountNumber.length() + 6);
        assertEquals( "1234567", destinationAccountNumber);
    }
    @Test
    public void testExtractTransactionAmount() {
        int amountNotationBeginningIndex =  TRANSACTION_TYPE_STRING_LENGTH +
                ACCOUNT_NUMBER_LENGTH_DENOTATION_LENGTH +
                6 +
                ACCOUNT_NUMBER_LENGTH_DENOTATION_LENGTH +
                7;


        Integer transactionAmount = parser.extractTransactionAmount("2010064447770712345670000020000", amountNotationBeginningIndex);
        assertTrue(transactionAmount == 20000);
    }

}