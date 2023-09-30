package com.leonardozw.catalogservice.web;

import static org.assertj.core.api.Assertions.*;

import java.time.Instant;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;

import com.leonardozw.catalogservice.domain.Book;

@JsonTest
public class BookJsonTests {
    
    @Autowired
    private JacksonTester<Book> json;

    @Test
    void testSerializer() throws Exception{
        var now = Instant.now();
        var book = new Book(394L, "1234567890", "Title", "Author", 9.90, null, now, now, 21);
        var jsonContent = json.write(book);
        assertThat(jsonContent).extractingJsonPathNumberValue("@.id").isEqualTo(book.id().intValue());
        assertThat(jsonContent).extractingJsonPathStringValue("@.isbn").isEqualTo(book.isbn());
        assertThat(jsonContent).extractingJsonPathStringValue("@.title").isEqualTo(book.title());
        assertThat(jsonContent).extractingJsonPathStringValue("@.author").isEqualTo(book.author());
        assertThat(jsonContent).extractingJsonPathNumberValue("@.price").isEqualTo(book.price());
        assertThat(jsonContent).extractingJsonPathStringValue("@.publisher").isEqualTo(book.publisher());
        assertThat(jsonContent).extractingJsonPathStringValue("@.createdDate").isEqualTo(book.createdDate().toString());
        assertThat(jsonContent).extractingJsonPathStringValue("@.lastModifiedDate").isEqualTo(book.lastModifiedDate().toString());
        assertThat(jsonContent).extractingJsonPathNumberValue("@.version").isEqualTo(book.version());
    }

    @Test
    void testDeserializer() throws Exception{
        var instant = Instant.parse("2021-09-07T22:50:37.135029Z");
        var content = """
            {
                "id": 394,
                "isbn": "1234567890",
                "title": "Title",
                "author": "Author",
                "price": 9.90,
                "publiser": null,
                "createdDate": "2021-09-07T22:50:37.135029Z",
                "lastModifiedDate": "2021-09-07T22:50:37.135029Z",
                "version": 21
            }
            """;
        assertThat(json.parse(content))
            .usingRecursiveComparison()
            .isEqualTo(new Book(394L, "1234567890", "Title", "Author", 9.90, null,instant, instant, 21));
    }
}
