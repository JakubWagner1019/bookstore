package com.example.bookstore;

import java.util.Map;
import java.util.Optional;

public interface BookRepository {
    Map<Long,Book> getAll();
    Optional<Book> getBookById(long id);
    long addBook(Book book);
    Book updateBook(long id, Book book);
    Book deleteBook(long id);
}
