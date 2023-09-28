package com.leonardozw.catalogservice.domain;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

public record Book(

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
    Double price
) {}
