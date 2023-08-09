package org.example;

public class Command {
    private final String command;
    private String remainingCommandString;

    public Command(String commandString) {
        this.command = commandString;
        this.remainingCommandString = commandString;
    }

    public String getCommand() {
        return command;
    }

    public String getRemainingCommandString() {
        return remainingCommandString;
    }

    public void consume(int i) {
        this.remainingCommandString = this.remainingCommandString.substring(i);
    }
}
