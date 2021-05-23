package com.example.bookstore;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public class Book {
    private String title;
    private String author;
    private String isbn;
    private String publisher;
    private Genre genre;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate publicationDate;

    public Book() {
    }

    public Book(String title, String author, String isbn, String publisher, Genre genre, LocalDate publicationDate) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.publisher = publisher;
        this.genre = genre;
        this.publicationDate = publicationDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public LocalDate getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(LocalDate publicationDate) {
        this.publicationDate = publicationDate;
    }

    public static class BookBuilder {
        private String title;
        private String author;
        private String isbn;
        private String publisher;
        private Genre genre;
        private LocalDate publicationDate;


        public BookBuilder title(String title) {
            this.title = title;
            return this;
        }

        public BookBuilder author(String author) {
            this.author = author;
            return this;
        }

        public BookBuilder isbn(String isbn) {
            this.isbn = isbn;
            return this;
        }

        public BookBuilder publisher(String publisher) {
            this.publisher = publisher;
            return this;
        }

        public BookBuilder genre(Genre genre) {
            this.genre = genre;
            return this;
        }

        public BookBuilder publicationDate(LocalDate publicationDate) {
            this.publicationDate = publicationDate;
            return this;
        }

        public Book build(){
            return new Book(title, author, isbn, publisher, genre, publicationDate);
        }
    }
}
