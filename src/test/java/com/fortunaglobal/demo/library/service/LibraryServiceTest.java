package com.fortunaglobal.demo.library.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.fortunaglobal.demo.library.exception.LibraryException;
import com.fortunaglobal.demo.library.model.Book;
import com.fortunaglobal.demo.library.model.Borrower;
import com.fortunaglobal.demo.library.repository.BookRepository;
import com.fortunaglobal.demo.library.repository.BorrowerRepository;
import com.fortunaglobal.demo.library.service.impl.LibraryServiceImpl;

@ExtendWith(MockitoExtension.class)
class LibraryServiceTest {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private BorrowerRepository borrowerRepository;

    @InjectMocks
    private LibraryServiceImpl libraryService;

    private Book testBook;
    private Borrower testBorrower;
    private Book borrowedBook;

    @BeforeEach
    void setUp() {
        testBook = new Book("978-0-7475-3269-9", "Harry Potter and the Philosopher's Stone", "J.K. Rowling");
        testBook.setId(1L);

        testBorrower = new Borrower("john.doe@example.com", "John Doe");
        testBorrower.setId(1L);

        borrowedBook = new Book("978-0-7475-3270-5", "Harry Potter and the Chamber of Secrets", "J.K. Rowling");
        borrowedBook.setId(2L);
        borrowedBook.setBorrower(testBorrower);
    }

    @Test
    void registerBook_ShouldReturnSavedBook() {
        when(bookRepository.save(any(Book.class))).thenReturn(testBook);

        Book result = libraryService.registerBook("978-0-7475-3269-9", "Harry Potter and the Philosopher's Stone", "J.K. Rowling");

        assertNotNull(result);
        assertEquals("978-0-7475-3269-9", result.getISBN());
        assertEquals("Harry Potter and the Philosopher's Stone", result.getTitle());
        assertEquals("J.K. Rowling", result.getAuthor());
        verify(bookRepository).save(any(Book.class));
    }

    @Test
    void listBooks_ShouldReturnAllBooks() {
        List<Book> expectedBooks = Arrays.asList(testBook, borrowedBook);
        when(bookRepository.findAll()).thenReturn(expectedBooks);

        List<Book> result = libraryService.listBooks();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(expectedBooks, result);
        verify(bookRepository).findAll();
    }

    @Test
    void registerBorrower_ShouldReturnSavedBorrower() {
        when(borrowerRepository.save(any(Borrower.class))).thenReturn(testBorrower);

        Borrower result = libraryService.registerBorrower("john.doe@example.com", "John Doe");

        assertNotNull(result);
        assertEquals("john.doe@example.com", result.getEmail());
        assertEquals("John Doe", result.getName());
        verify(borrowerRepository).save(any(Borrower.class));
    }

    @Test
    void borrowBook_WithAvailableBook_ShouldBorrowSuccessfully() {
        when(borrowerRepository.findByEmail("john.doe@example.com")).thenReturn(testBorrower);
        when(bookRepository.findById(1L)).thenReturn(Optional.of(testBook));
        when(bookRepository.save(any(Book.class))).thenReturn(testBook);

        libraryService.borrowBook("john.doe@example.com", 1L);

        verify(borrowerRepository).findByEmail("john.doe@example.com");
        verify(bookRepository).findById(1L);
        verify(bookRepository).save(testBook);
        assertEquals(testBorrower, testBook.getBorrower());
    }

    @Test
    void borrowBook_WithAlreadyBorrowedBook_ShouldThrowException() {
        when(borrowerRepository.findByEmail("john.doe@example.com")).thenReturn(testBorrower);
        when(bookRepository.findById(2L)).thenReturn(Optional.of(borrowedBook));

        LibraryException exception = assertThrows(LibraryException.class, 
            () -> libraryService.borrowBook("john.doe@example.com", 2L));
        
        assertEquals("Book with id 2 is already borrowed by someone.", exception.getMessage());
    }

    @Test
    void returnBook_WithCorrectBorrower_ShouldReturnSuccessfully() {
        when(bookRepository.findById(2L)).thenReturn(Optional.of(borrowedBook));
        when(bookRepository.save(any(Book.class))).thenReturn(borrowedBook);

        libraryService.returnBook("john.doe@example.com", 2L);

        verify(bookRepository).findById(2L);
        verify(bookRepository).save(borrowedBook);
        assertNull(borrowedBook.getBorrower());
    }

} 