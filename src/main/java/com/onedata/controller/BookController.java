package com.onedata.controller;

import com.onedata.dto.BookDto;
import com.onedata.exception.BookNotFoundException;
import com.onedata.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping("/{bookId}")
    public ResponseEntity<BookDto> getBookById(@PathVariable Long bookId) {
        try {
            BookDto book = bookService.findById(bookId);
            return new ResponseEntity<>(book, HttpStatus.OK);
        } catch (BookNotFoundException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    public ResponseEntity<List<BookDto>> getAllBooks() {
        List<BookDto> books = bookService.findAll();
        return new ResponseEntity<>(books, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Void> createBook(@RequestBody BookDto book) {
        try {
            bookService.save(book);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (DataAccessException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping
    public ResponseEntity<Void> updateBook(@RequestBody BookDto book) {
        try {
            bookService.update(book);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (BookNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (DataAccessException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{bookId}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long bookId) {
        try {
            bookService.delete(bookId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (BookNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (DataAccessException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/search")
    public ResponseEntity<List<BookDto>> searchBooks(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String author,
            @RequestParam(required = false) String isbn) {
        List<BookDto> books = bookService.findBooks(title, author, isbn);
        return new ResponseEntity<>(books, HttpStatus.OK);
    }



}
