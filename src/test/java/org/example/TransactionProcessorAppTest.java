package org.example;


import org.example.dao.AccountDaoImpl;
import org.example.domain.Account;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;

// Test Class
public class TransactionProcessorAppTest {

    @Test
    public void testProcessor() {
        String[] inputFileLines = loadFileLines();
        TransactionProcessorApp app = new TransactionProcessorApp();
        AccountDaoImpl accountDao = new AccountDaoImpl();
        app.processTransactions(inputFileLines, accountDao);

        Map<String, Account> accounts = accountDao.getAccounts();

        assertEquals(accounts.get("444777").getAmount().intValue(), 100000);
        assertEquals(accounts.get("234591").getAmount().intValue(), 45000);
        assertEquals(accounts.get("1234567").getAmount().intValue(), 45000);
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