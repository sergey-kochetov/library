package ru.com.melt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.com.melt.model.Author;
import ru.com.melt.model.Book;
import ru.com.melt.model.Comment;
import ru.com.melt.model.Genre;
import ru.com.melt.service.BookService;
import ru.com.melt.service.CommentService;
import ru.com.melt.service.LibraryService;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
public class BookController {

    @Autowired
    private LibraryService libraryService;
    @Autowired
    private BookService bookService;
    @Autowired
    private CommentService commentService;

    @GetMapping("/")
    public String allBooksOnIndexPage(Model model) {
        List<Book> books = bookService.getAllBooks();
        List<BookDto> bookDtos = books.stream()
                .map(BookDto::new)
                .collect(Collectors.toList());
        model.addAttribute("books", bookDtos);
        return "index";
    }

    @GetMapping("/edit")
    public String showBookById(@RequestParam(name = "id") Long id, Model model) {
        Book book = bookService.getBookById(id);
        model.addAttribute("book", new BookDto(book));
        return "editBook";
    }

    @GetMapping("/update")
    public String updateTitleById(@RequestParam(name = "id") Long id,
                                  @RequestParam(name = "title") String title,
                                  Model model) {
        Book book = bookService.updateBookTitleById(id, title);
        model.addAttribute("book", new BookDto(book));
        return "editBook";
    }

    @GetMapping("/comment")
    public String showCommentsById(@RequestParam(name = "id") Long id,
                                   Model model) {
        List<Comment> comments = commentService.findAllById(id);
        model.addAttribute("comments", comments);
        return "commentBook";
    }

    @GetMapping("/delete")
    public String delBookById(@RequestParam(name = "id") Long id) {
        bookService.deleteBookById(id);
        return "redirect:/";
    }

    @PostMapping("/add")
    public String addNewBook(@RequestParam(name = "authors") String authors,
                             @RequestParam(name = "title") String title,
                             @RequestParam(name = "genre") String genre,
                             Model model) {
        Book book = libraryService.addNewBook(new Book(
                title, new Genre(genre), mapAuthors(authors)));
        model.addAttribute("addBookResult", book.getId() != null);
        return "redirect:/";
    }

    private Set<Author> mapAuthors(String authors) {
        String[] authorsArr = authors
                .replaceAll(" ", "")
                .split(",");
        return Arrays.stream(authorsArr)
                .map(Author::new).collect(Collectors.toSet());
    }
}
