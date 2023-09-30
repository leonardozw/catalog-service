package com.leonardozw.catalogservice.domain;

import static org.assertj.core.api.Assertions.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

class BookValidationTests {
    
    private static Validator validator;

    @BeforeAll
    static void setUp(){
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void whenAllFieldCorrectThenValidationSucceeds(){
        var book = Book.of("1234567890", "Title", "Author", 9.99, null);
        Set<ConstraintViolation<Book>> violations = validator.validate(book);
        assertThat(violations).isEmpty();
    }

    @Test
    void whenIsbnDefinedButEmptyThenValidationFails(){
        var book = Book.of("", "Title", "Author", 9.90, null);
        Set<ConstraintViolation<Book>> violations = validator.validate(book);
        assertThat(violations).hasSize(2);
        List<String> constraintViolationMessages = violations.stream()
                .map(ConstraintViolation::getMessage).collect(Collectors.toList());
        assertThat(constraintViolationMessages)
                .contains("ISBN is required.")
				.contains("ISBN must be 10 or 13 digits long.");
    }

    @Test
    void whenIsbnDefinedButIncorretThenValidationFails(){
        var book = Book.of("a234567890", "Title", "Author", 9.99, null);
        Set<ConstraintViolation<Book>> violations = validator.validate(book);
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).isEqualTo("ISBN must be 10 or 13 digits long.");
    }

    @Test
    void whenTitleNotDefinedThenValidationFails(){
        var book = Book.of("1234567890", "", "Author", 9.99, null);
        Set<ConstraintViolation<Book>> violations = validator.validate(book);
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).isEqualTo("Title is required.");
    }

    @Test
    void whenAuthorNotDefinedThenValidationFails(){
        var book = Book.of("1234567890", "Title", "", 9.99, null);
        Set<ConstraintViolation<Book>> violations = validator.validate(book);
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).isEqualTo("Author is required.");
    }

    @Test
    void whenPriceNotDefinedThenValidationFails(){
        var book = Book.of("1234567890", "Title", "Author", null, null);
        Set<ConstraintViolation<Book>> violations = validator.validate(book);
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).isEqualTo("Price is required.");
    }

    @Test
    void whenPriceDefinedButZeroThenValidationFails(){
        var book = Book.of("1234567890", "Title", "Author", 0.0, null);
        Set<ConstraintViolation<Book>> violations = validator.validate(book);
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).isEqualTo("Price must be greater than zero.");
    }

    @Test
    void whenPriceDefinedButNegativeThenValidationFails(){
        var book = Book.of("1234567890", "Title", "Author", -9.99, null);
        Set<ConstraintViolation<Book>> violations = validator.validate(book);
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).isEqualTo("Price must be greater than zero.");
    }
}
