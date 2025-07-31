package com.fortunaglobal.demo.library.service;

import java.util.List;

import com.fortunaglobal.demo.library.model.Book;
import com.fortunaglobal.demo.library.model.Borrower;

public interface LibraryService {

    public Book registerBook(String isbn, String title, String author);

    public List<Book> listBooks();

    public Borrower registerBorrower(String email, String name);

    public void borrowBook(String email, long bookId);

    public void returnBook(String email, long bookId);
}
