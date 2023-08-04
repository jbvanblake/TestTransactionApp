package org.example;


import org.example.TransactionProcessorApp.TransactionType;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.example.TransactionProcessorApp.TransactionType.DEPOSIT;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;

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
        String accountNumber = app.extractAccountNumber("10100712345670000200000");
        assertEquals(accountNumber, "1234567");
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