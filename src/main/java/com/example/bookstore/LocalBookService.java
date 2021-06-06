package com.example.bookstore;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Service
@Profile("local")
public class LocalBookService implements BookService {

    private final AtomicLong indexer = new AtomicLong();
    private final Map<Long, Book> bookStore = new HashMap<>();

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
