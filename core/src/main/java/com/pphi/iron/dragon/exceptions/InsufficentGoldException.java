package com.pphi.iron.dragon.exceptions;

public class InsufficentGoldException extends RuntimeException {

    public InsufficentGoldException(int required, int onhand) {
        super(String.format("That action requires %d gold and you only have %d gold", required, onhand));
    }
}
