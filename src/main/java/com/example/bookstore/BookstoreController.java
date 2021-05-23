package com.example.bookstore;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping("/")
public class BookstoreController {

    private final BookService bookService;

    public BookstoreController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public String showFrontPage() {
        return "index";
    }

    @GetMapping("/books")
    public String showBookIndexPage(Model model) {
        Map<Long, Book> bookMap = bookService.getAll();
        model.addAttribute("bookMap", bookMap);
        return "bookindex";
    }

    @GetMapping("/book/{id}")
    public String showBookDetailsPage(@PathVariable("id") long id, Model model) {
        Optional<Book> book = bookService.getBookById(id);
        if (book.isPresent()) {
            model.addAttribute("book", book.get());
            return "book";
        } else {
            throw new IllegalArgumentException("No book with given index exists");
        }
    }

}
