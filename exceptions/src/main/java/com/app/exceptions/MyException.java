package com.app.exceptions;

import java.time.LocalDateTime;

public class MyException extends RuntimeException {

    public MyException(String exceptionMessage) {
        super(exceptionMessage);
    }
}
