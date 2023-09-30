package com.leonardozw.catalogservice.domain;

import java.time.Instant;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.Version;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

public record Book(

    @Id
    Long id,

    @NotBlank(message = "ISBN is required.")
    @Pattern(
        regexp = "^([0-9]{10}|[0-9]{13}])$",
        message = "ISBN must be 10 or 13 digits long."
    )
    String isbn,

    @NotBlank(message = "Title is required.")
    String title,

    @NotBlank(message = "Author is required.")
    String author,

    @NotNull(message = "Price is required.")
    @Positive(
        message = "Price must be greater than zero."
    )
    Double price,

    String publisher,

    @CreatedDate
    Instant createdDate,

    @LastModifiedDate
    Instant lastModifiedDate,

    @Version
    int version
) {
    public static Book of(String isbn, String title, String author, Double price, String publisher){
        return new Book(null, isbn, title, author, price, publisher, null, null, 0);
    }
}
