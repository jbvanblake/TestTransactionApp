package org.example;


import org.example.TransactionProcessorApp.TransactionType;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.example.TransactionProcessorApp.ACCOUNT_NUMBER_LENGTH_DENOTATION_LENGTH;
import static org.example.TransactionProcessorApp.TRANSACTION_TYPE_STRING_LENGTH;
import static org.example.TransactionProcessorApp.TransactionType.DEPOSIT;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

// Test Class
public class TransactionProcessorAppTest {
    @Test
    public void testProcessor() {
        String[] inputFileLines = loadFileLines();
        TransactionProcessorApp app = new TransactionProcessorApp();
        app.processTransactions(inputFileLines);
    }
    @Test
    public void testExtractTransactionType() {
        TransactionProcessorApp app = new TransactionProcessorApp();
        TransactionType transactionType = app.extractTransactionType("10100712345670000200000");
        assertEquals(transactionType, DEPOSIT);
    }
    @Test
    public void testExtractAccountNumber() {
        TransactionProcessorApp app = new TransactionProcessorApp();
        String accountNumber = app.extractAccountNumber("10100712345670000200000", 4);
        assertEquals(accountNumber, "1234567");
    }
    @Test
    public void testExtractAccountNumberForTransfer() {
        TransactionProcessorApp app = new TransactionProcessorApp();
        String sourceAccountNumber = app.extractAccountNumber("2010064447770712345670000020000", 4);
        assertEquals( "444777", sourceAccountNumber);

        String destinationAccountNumber = app.extractAccountNumber("2010064447770712345670000020000", sourceAccountNumber.length() + 6);
        assertEquals( "1234567", destinationAccountNumber);
    }

    @Test
    public void testExtractTransactionAmount() {
        TransactionProcessorApp app = new TransactionProcessorApp();
        int amountNotationBeginningIndex =  TRANSACTION_TYPE_STRING_LENGTH +
                ACCOUNT_NUMBER_LENGTH_DENOTATION_LENGTH +
                6 +
                ACCOUNT_NUMBER_LENGTH_DENOTATION_LENGTH +
                7;


        Integer transactionAmount = app.extractTransactionAmount("2010064447770712345670000020000", amountNotationBeginningIndex);
        assertTrue(transactionAmount == 20000);
    }


    private static String[] loadFileLines() {
        try {
            BufferedReader in = new BufferedReader(
                    new FileReader("src/main/resources/input.txt"));
            String str;
            List<String> list = new ArrayList<>();
            while ((str = in.readLine()) != null) {
                list.add(str);
            }
            String[] stringArr = list.toArray(new String[0]);
            return stringArr;

        } catch (IOException e) {
            System.out.println("Could not load file." + e.getMessage());
        }
        return null;
    }
}