package com.example.bookstore;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@PropertySources({
        @PropertySource("classpath:db.properties"),
        @PropertySource("classpath:sql.properties")
})
public class BookRepositoryImpl implements BookRepository {

    Logger logger = LoggerFactory.getLogger(BookRepositoryImpl.class);

    private Connection connection;

    @Value("${db.driver.classname}")
    private String driverClassName;
    @Value("${db.driver.identifier}")
    private String driverIdentifier;
    @Value("${db.host}")
    private String host;
    @Value("${db.port}")
    private String port;

    @Value("${db.username}")
    private String username;

    @Value("${db.password}")
    private String password;

    private List<String> initQueries;
    @Value("${sql.init.database.create}")
    private String createDatabase;
    @Value("${sql.init.database.use}")
    private String useDatabase;
    @Value("${sql.init.table.create}")
    private String createTable;

    @Value("${sql.runtime.book.getall}")
    private String getAllBooks;
    @Value("${sql.runtime.book.get}")
    private String getBookById;
    @Value("${sql.runtime.book.delete}")
    private String deleteBook;
    @Value("${sql.runtime.book.add}")
    private String addBook;
    @Value("${sql.runtime.book.update}")
    private String updateBook;


    @PostConstruct
    void init() throws ClassNotFoundException, SQLException {
        Class.forName(driverClassName);
        String connectionUrl = String.format("jdbc:%s://%s:%s", driverIdentifier, host, port);
        connection = DriverManager.getConnection(connectionUrl, username, password);
        initQueries = Arrays.asList(
                createDatabase,
                useDatabase,
                createTable
        );
        for (String initQuery : initQueries) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(initQuery)) {
                preparedStatement.execute();
            }
        }
    }


    @Override
    public Map<Long, Book> getAll() {
        Map<Long, Book> result = new HashMap<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(getAllBooks)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                long id = resultSet.getLong("ID");
                Book book = getBook(resultSet);
                result.put(id, book);
            }
        } catch (SQLException throwables) {
            logger.error(throwables.getMessage());
            return result;
        }
        return result;
    }


    private Book getBook(ResultSet resultSet) throws SQLException {
        String title = resultSet.getString("TITLE");
        String author = resultSet.getString("AUTHOR");
        String isbn = resultSet.getString("ISBN");
        String publisher = resultSet.getString("PUBLISHER");
        String genre = resultSet.getString("GENRE");
        String publicationDate = resultSet.getString("PUBLICATION_DATE");
        Book book = new Book.BookBuilder()
                .title(title)
                .author(author)
                .isbn(isbn)
                .publisher(publisher)
                .genre(Genre.valueOf(genre))
                .publicationDate(LocalDate.parse(publicationDate))
                .build();
        return book;
    }

    @Override
    public Optional<Book> getBookById(long id) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(getBookById)) {
            preparedStatement.setLong(1,id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Book book = getBook(resultSet);
                return Optional.ofNullable(book);
            }
        } catch (SQLException throwables) {
            logger.error(throwables.getMessage());
            return Optional.empty();
        }
        return Optional.empty();
    }

    //TITLE, AUTHOR, ISBN, PUBLISHER, GENRE, PUBLICATION_DATE

    @Override
    public long addBook(Book book) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(addBook)) {
            preparedStatement.setString(1,book.getTitle());
            preparedStatement.setString(2, book.getAuthor());
            preparedStatement.setString(3, book.getIsbn());
            preparedStatement.setString(4, book.getPublisher());
            preparedStatement.setString(5, book.getGenre().toString());
            preparedStatement.setString(6, book.getPublicationDate().toString());
            preparedStatement.executeUpdate();
            return 0;
        } catch (SQLException throwables) {
            logger.error(throwables.getMessage());
            return -1;
        }
    }

    @Override
    public Book updateBook(long id, Book book) {
        return null;
    }

    @Override
    public Book deleteBook(long id) {
        return null;
    }
}
