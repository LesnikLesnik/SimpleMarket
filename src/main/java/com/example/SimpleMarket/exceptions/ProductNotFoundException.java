package com.example.SimpleMarket.exceptions;

public class ProductNotFoundException extends RuntimeException {


    public ProductNotFoundException(String message) {
        super(message);
    }
}
