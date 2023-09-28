package com.leonardozw.catalogservice.domain;

public class BookAlreadyExistsException extends RuntimeException{
    public BookAlreadyExistsException(String isbn) {
        super("Book with ISBN " + isbn + " already exists.");
    }
}
