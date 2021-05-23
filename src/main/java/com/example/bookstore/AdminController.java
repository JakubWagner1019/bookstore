package com.example.bookstore;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final BookService bookService;

    public AdminController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public String getAdminPage(Model model) {
        Map<Long, Book> bookMap = bookService.getAll();
        model.addAttribute("bookMap", bookMap);
        return "admin";
    }

    @GetMapping("/create")
    public String getCreatePage(Model model){
        model.addAttribute("genres", Genre.values());
        model.addAttribute("book", new Book());
        return "create";
    }

    @PostMapping("/create")
    public String doCreate(@ModelAttribute("book") Book book){
        bookService.addBook(book);
        return "redirect:/admin";
    }

    @GetMapping("/edit/{id}")
    public String getEditPage(@PathVariable("id") long id, Model model){
        Optional<Book> bookById = bookService.getBookById(id);
        if (bookById.isPresent()) {
            model.addAttribute("id", id);
            model.addAttribute("genres", Genre.values());
            model.addAttribute("book", bookById.get());
            return "edit";
        } else {
            throw new IllegalArgumentException("No book with given index exists");
        }
    }

    @PostMapping("/edit/{id}")
    public String doEdit(@ModelAttribute("book") Book book, @PathVariable("id") long id){
        bookService.updateBook(id, book);
        return "redirect:/admin";
    }

    @GetMapping("/delete/{id}")
    public String getDeletePage(@PathVariable("id") long id, Model model){
        Optional<Book> bookById = bookService.getBookById(id);
        if (bookById.isPresent()) {
            model.addAttribute("id", id);
            model.addAttribute("book", bookById.get());
            return "delete";
        } else {
            throw new IllegalArgumentException("No book with given index exists");
        }
    }

    @PostMapping("/delete/{id}")
    public String doEdit(@PathVariable("id") long id){
        bookService.deleteBook(id);
        return "redirect:/admin";
    }

}
