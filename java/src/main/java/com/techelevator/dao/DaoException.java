package com.techelevator.dao;

import org.springframework.dao.DataIntegrityViolationException;

public class DaoException extends Throwable {
    private String violation;
    private Exception e;

    public DaoException(String violation, Exception e) {
        this.violation = violation;
        this.e = e;
    }
}
