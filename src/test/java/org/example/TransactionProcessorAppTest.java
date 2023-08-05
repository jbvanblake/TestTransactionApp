package org.example;


import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.example.CommandParser.ACCOUNT_NUMBER_LENGTH_DENOTATION_LENGTH;
import static org.example.CommandParser.TRANSACTION_TYPE_STRING_LENGTH;
import static org.example.TransactionType.DEPOSIT;
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