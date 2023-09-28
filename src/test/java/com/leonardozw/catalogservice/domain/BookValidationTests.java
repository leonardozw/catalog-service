package com.leonardozw.catalogservice.domain;

import static org.assertj.core.api.Assertions.*;

import java.util.Set;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

public class BookValidationTests {
    
    private static Validator validator;

    @BeforeAll
    static void setUp(){
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void whenAllFieldCorrectThenValidationSucceeds(){
        var book = new Book("1234567890", "Title", "Author", 9.99);
        Set<ConstraintViolation<Book>> violations = validator.validate(book);
        assertThat(violations).isEmpty();
    }

    @Test
    void whenIsbnDefinedButIncorretThenValidationFails(){
        var book = new Book("a234567890", "Title", "Author", 9.99);
        Set<ConstraintViolation<Book>> violations = validator.validate(book);
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).isEqualTo("ISBN must be 10 or 13 digits long.");
    }

    @Test
    void whenTitleNotDefinedThenValidationFails(){
        var book = new Book("1234567890", "", "Author", 9.99);
        Set<ConstraintViolation<Book>> violations = validator.validate(book);
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).isEqualTo("Title is required.");
    }

    @Test
    void whenAuthorNotDefinedThenValidationFails(){
        var book = new Book("1234567890", "Title", "", 9.99);
        Set<ConstraintViolation<Book>> violations = validator.validate(book);
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).isEqualTo("Author is required.");
    }

    @Test
    void whenPriceNotDefinedThenValidationFails(){
        var book = new Book("1234567890", "Title", "Author", null);
        Set<ConstraintViolation<Book>> violations = validator.validate(book);
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).isEqualTo("Price is required.");
    }
}
