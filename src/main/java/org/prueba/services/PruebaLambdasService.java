package org.prueba.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.prueba.PruebaLambdasController;
import org.prueba.models.Book;
import org.prueba.models.BookDate;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class PruebaLambdasService {

    public static final String SEPARATOR = "------------------------------------------------------------------------------------------------";
    private ObjectMapper objectMapper = new ObjectMapper();

    public void prueba1() {
        System.out.println("Escriba por pantalla los libros que no tengan fecha de publicación");
        try {
            readBooks().stream()
                    .filter(book -> book.getPublicationTimestamp() == null)
                    .map(Book::initializeNullValues)
                    .collect(Collectors.toList())
                    .forEach(System.out::println);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        System.out.println(SEPARATOR);
    }

    public void prueba2() {
        System.out.println(new StringBuilder()
                .append("Devuelve los libros que contengan la cadena de caracteres en el nombre, en el\n")
                .append("resumen y en la biografia del autor del libro. En caso de encontrar más de un\n")
                .append("libro en la lista devolver aquel más recientemente publicado. Además se deberá\n")
                .append("devolver el libro con un campo date adicional que contenga el timestamp con el\n")
                .append("siguiente formato de fecha: mm-dd-yyyy.").toString());

        Scanner scanner = new Scanner(System.in);
        System.out.println("Filtro: ");
        String filter = scanner.next();

        readBooksDate().stream()
                .map(BookDate::initializeNullValues)
                .filter(bookDate -> bookDate.getAuthor().getName().contains(filter) ||
                                    bookDate.getSummary().contains(filter) ||
                                    bookDate.getAuthor().getBio().contains(filter))
                .sorted(Comparator.comparing(BookDate::getPublicationTimestamp))
                .findFirst()
                .ifPresent(bookDate -> {bookDate.setTimeStamp(randomLocalDate());
                    System.out.println(new StringBuilder().append("Libro más reciente: ")
                                               .append(bookDate.getTitle())
                                                .append(" con fecha ")
                                                .append(bookDate.getTimeStamp())
                                                .append("\n").append(bookDate));
                    });

        System.out.println(SEPARATOR);
    }

    public void prueba3() {
        System.out.println(new StringBuilder()
                .append("La lista deberá quedar ordenada de la siguiente manera: Primero\n")
                .append("agrupada por fecha de publicación y luego ordenada por la biografia de autor\n")
                .append("más corta.").toString());

        this.maptoString(readBooks().stream()
                .filter(book -> book.getPublicationTimestamp()!=null)
                .sorted((book1, book2) -> book1.getPages().compareTo(book2.getPages()))
                .collect(Collectors.groupingBy(Book::getPublicationTimestamp)));

        System.out.println(SEPARATOR);
    }

    private List<Book> readBooks() {
        List<Book> booksTemp = null;

        try (Stream<String> stream =  Files.lines(Paths.get(PruebaLambdasController.class.getResource("/books.json").toURI()), StandardCharsets.UTF_8)){
            String booksJson =  stream.reduce(String::concat).get();
            booksTemp = objectMapper.readValue(booksJson, new TypeReference<List<Book>>(){});
        } catch(Exception ex) {
            ex.printStackTrace();
        }

        return booksTemp;
    }

    private List<BookDate> readBooksDate() {
        List<BookDate> booksTemp = null;

        try (Stream<String> stream =  Files.lines(Paths.get(PruebaLambdasController.class.getResource("/books.json").toURI()), StandardCharsets.UTF_8)){
            String booksJson =  stream.reduce(String::concat).get();
            booksTemp = objectMapper.readValue(booksJson, new TypeReference<List<BookDate>>(){});
        } catch(Exception ex) {
            ex.printStackTrace();
        }
        return booksTemp;
    }

    private String randomLocalDate() {
        LocalDate start = LocalDate.of(1970, Month.JANUARY, 1);
        long days = ChronoUnit.DAYS.between(start, LocalDate.now());
        LocalDate randomDate = start.plusDays(new Random().nextInt((int) days + 1));
        return randomDate.format(DateTimeFormatter.ofPattern("MM-dd-yyyy"));
    }

    private void maptoString(Map<Long, List<Book>> map) {
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        try {
            System.out.println(ow.writeValueAsString(map));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
