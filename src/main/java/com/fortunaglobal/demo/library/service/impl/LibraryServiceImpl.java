package com.fortunaglobal.demo.library.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.fortunaglobal.demo.library.exception.LibraryException;
import com.fortunaglobal.demo.library.model.Book;
import com.fortunaglobal.demo.library.model.Borrower;
import com.fortunaglobal.demo.library.repository.BookRepository;
import com.fortunaglobal.demo.library.repository.BorrowerRepository;
import com.fortunaglobal.demo.library.service.LibraryService;

@Service
public class LibraryServiceImpl implements LibraryService {

    private final BookRepository bookRepository;
    private final BorrowerRepository borrowerRepository;

    public LibraryServiceImpl(BookRepository bookRepository, BorrowerRepository borrowerRepository) {
        this.bookRepository = bookRepository;
        this.borrowerRepository = borrowerRepository;
    }

    @Override
    public Book registerBook(String isbn, String title, String author) {
        return bookRepository.save(new Book(isbn, title, author));
    }

    @Override
    public List<Book> listBooks() {
        return bookRepository.findAll();
    }

    @Override
    public Borrower registerBorrower(String email, String name) {
        return borrowerRepository.save(new Borrower(email, name));

    }

    @Override
    public void borrowBook(String email, long bookId) {
        Borrower borrower = getBorrowerByEmail(email);
        Book book = getBookById(bookId);

        if (book.getBorrower() != null) {
            throw new LibraryException("Book with id " + bookId + " is already borrowed by someone.");
        }

        book.setBorrower(borrower);
        bookRepository.save(book);
    }

    @Override
    public void returnBook(String email, long bookId) {
        Book book = getBookById(bookId);

        if (book.getBorrower() == null) {
            throw new LibraryException(String.format("Book with id %d is not currently borrowed.", bookId));
        }

        if (!email.equals(book.getBorrower().getEmail())) {
            throw new LibraryException(String.format(
                    "Book with id %d is not borrowed by borrower with email %s.", bookId, email));
        }

        book.setBorrower(null);
        bookRepository.save(book);
    }

    private Borrower getBorrowerByEmail(String email) {
        Borrower borrower = borrowerRepository.findByEmail(email);
        if (borrower == null) {
            throw new LibraryException("Borrower with email " + email + " not found.");
        }
        return borrower;
    }

    private Book getBookById(long bookId) {
        return bookRepository.findById(bookId)
                .orElseThrow(() -> new LibraryException(String.format("Book with id %d not found.", bookId)));
    }
}
