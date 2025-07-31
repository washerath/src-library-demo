package com.fortunaglobal.demo.library.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fortunaglobal.demo.library.model.Book;
import com.fortunaglobal.demo.library.model.Borrower;
import com.fortunaglobal.demo.library.model.Loan;
import com.fortunaglobal.demo.library.service.LibraryService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/library")
public class LibraryController {

    @Autowired
    private LibraryService libraryService;

    // Register a new book
    @PostMapping("/books")
    public ResponseEntity<Book> registerBook(@RequestBody @Valid Book book) {
        Book bookReturned = libraryService.registerBook(book.getISBN(), book.getTitle(), book.getAuthor());
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(bookReturned);
    }

    // List all books
    @GetMapping("/books")
    public ResponseEntity<List<Book>> listBooks() {
        return ResponseEntity.status(HttpStatus.OK).body(libraryService.listBooks());
    }

    // Register a new borrower
    @PostMapping("/borrowers")
    public ResponseEntity<Borrower> registerBorrower(@RequestBody @Valid Borrower borrower) {
        Borrower registeredBorrower = libraryService.registerBorrower(borrower.getEmail(), borrower.getName());
        return ResponseEntity.status(HttpStatus.CREATED).body(registeredBorrower);
    }

    // Borrow a book
    @PostMapping("/books/{bookId}/loans")
    public ResponseEntity<Void> borrowBook(@RequestBody Loan loan, @PathVariable long bookId) {
        libraryService.borrowBook(loan.getBorrowerEmail(), bookId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    // Return a book
    @DeleteMapping("/books/{bookId}/loans")
    public ResponseEntity<Void> returnBook(@RequestBody @Valid Loan loan, @PathVariable long bookId) {
        libraryService.returnBook(loan.getBorrowerEmail(), bookId);
        return ResponseEntity.noContent().build();
    }
}
