package com.fortunaglobal.demo.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fortunaglobal.demo.library.model.Book;

public interface BookRepository extends JpaRepository<Book, Long> {

}
