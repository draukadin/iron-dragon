package com.pphi.iron.dragon.exceptions;

public class DemandCardCapcityExceededException extends RuntimeException {

    public DemandCardCapcityExceededException() {
        super("Players may only have a max of three demand cards");
    }
}
