package com.pphi.iron.dragon.exceptions;

public class EmptyDiscardPileException extends RuntimeException {

    public EmptyDiscardPileException() {
    }

    public EmptyDiscardPileException(String message) {
        super(message);
    }
}
