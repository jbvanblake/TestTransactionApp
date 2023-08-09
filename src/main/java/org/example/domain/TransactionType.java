package org.example.domain;

import java.util.Arrays;
import java.util.Optional;

public enum TransactionType {
    DEPOSIT ("1010"),
    WITHDRAW ("1020"),
    TRANSFER("2010");


    private final String commandPrefix;

    TransactionType(String prefix) {
        this.commandPrefix = prefix;
    }
    public static Optional<TransactionType> valueOfPrefix(String prefix) {
        return Arrays.stream(TransactionType.values()).filter(type->type.commandPrefix.equals(prefix)).findFirst();
    }
}
