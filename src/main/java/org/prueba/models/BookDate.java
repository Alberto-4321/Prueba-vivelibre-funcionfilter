package org.prueba.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Random;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class BookDate {
    Integer id;
    String title;
    Long publicationTimestamp;
    Integer pages;
    String summary;
    String timeStamp;
    Author author;

    public static BookDate initializeNullValues(BookDate book) {
        if (book.getId() == null) book.setId(0);
        if (book.getTitle() == null) book.setTitle("");
        if (book.getPublicationTimestamp() == null) book.setPublicationTimestamp(0L);
        if (book.getPages() == null) book.setPages(0);
        if (book.getSummary() == null) book.setSummary("");
        if (book.getAuthor() == null) {
            book.setAuthor(Author.initializeNullValues(new Author()));

        } else {
            book.setAuthor(Author.initializeNullValues(book.getAuthor()));
        }

        return book;
    }

    @Override
    public String toString() {
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        try {
            return ow.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}