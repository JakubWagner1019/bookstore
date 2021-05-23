package com.example.bookstore;

import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class LocalBookService implements BookService {

    private final AtomicLong indexer = new AtomicLong();
    private final Map<Long, Book> bookStore = new HashMap<>();

//    @PostConstruct
//    void init(){
//        Book book = new Book.BookBuilder()
//                .title("Dragons")
//                .author("JW")
//                .isbn("123-43wzs-vzd")
//                .publisher("Penguin")
//                .genre(Genre.FANTASY)
//                .publicationDate(LocalDate.of(2007,1,1))
//                .build();
//        Book book2 = new Book.BookBuilder()
//                .title("Dragons are flat")
//                .author("MW")
//                .isbn("5329-321-453")
//                .publisher("Penguin")
//                .genre(Genre.FANTASY)
//                .publicationDate(LocalDate.of(2013, 2, 2))
//                .build();
//        Book book3 = new Book.BookBuilder()
//                .title("La Cudele")
//                .author("PO")
//                .isbn("123-45-123")
//                .publisher("Ossolineum")
//                .genre(Genre.PORN)
//                .publicationDate(LocalDate.of(1969,6,9))
//                .build();
//        addBook(book);
//        addBook(book2);
//        addBook(book3);
//    }

    @Override
    public Map<Long, Book> getAll() {
        return Collections.unmodifiableMap(bookStore);
    }

    @Override
    public Optional<Book> getBookById(long id) {
        return Optional.ofNullable(bookStore.get(id));
    }

    @Override
    public long addBook(Book book) {
        long key = indexer.incrementAndGet();
        bookStore.put(key, book);
        return key;
    }

    @Override
    public Book updateBook(long id, Book book) {
        return bookStore.put(id, book);
    }

    @Override
    public Book deleteBook(long id) {
        return bookStore.remove(id);
    }
}
