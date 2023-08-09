package org.example.command;

import org.example.command.Command;
import org.example.command.CommandParser;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CommandParserTest {

    private CommandParser parser = new CommandParser(null, null) {
        @Override
        public void parse(Command transaction) {
        }
    };
    Command depositCommand = new Command("0712345670000200000");
    Command transferCommand = new Command("064447770712345670000020000");

    @Test
    public void testConsumeAccountNumber() {
        String accountNumber = parser.consumeCommandAccountName(depositCommand);
        assertEquals(accountNumber, "1234567");
    }
    @Test
    public void testExtractAccountNumberForTransfer() {
        String sourceAccountNumber = parser.consumeCommandAccountName(transferCommand);
        assertEquals( "444777", sourceAccountNumber);

        String destinationAccountNumber = parser.consumeCommandAccountName(transferCommand);
        assertEquals( "1234567", destinationAccountNumber);
    }
    @Test
    public void testExtractTransactionAmount() {
        Command testCommand = new Command ("0000020000");

        Integer transactionAmount = parser.consumeCommandTransactionAmount(testCommand);
        assertTrue(transactionAmount == 20000);
        assertEquals(testCommand.getRemainingCommandString(), "");

    }

}