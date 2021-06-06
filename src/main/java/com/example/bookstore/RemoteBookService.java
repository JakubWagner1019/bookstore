package com.example.bookstore;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
@Profile("remote")
public class RemoteBookService implements BookService {

    private final BookRepository repository;

    public RemoteBookService(BookRepository repository) {
        this.repository = repository;
    }

    @Override
    public Map<Long, Book> getAll() {
        return repository.getAll();
    }

    @Override
    public Optional<Book> getBookById(long id) {
        return repository.getBookById(id);
    }

    @Override
    public long addBook(Book book) {
        return repository.addBook(book);
    }

    @Override
    public Book updateBook(long id, Book book) {
        return repository.updateBook(id, book);
    }

    @Override
    public Book deleteBook(long id) {
        return repository.deleteBook(id);
    }
}
