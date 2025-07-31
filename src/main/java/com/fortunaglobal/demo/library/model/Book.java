package com.fortunaglobal.demo.library.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String author;
    private String ISBN;

    public Book(String ISBN, String title, String author) {
        this.title = title;
        this.author = author;
        this.ISBN = ISBN;
    }

    public Book() {
    }

    @ManyToOne
    @JoinColumn(name = "borrower_id")
    @JsonBackReference
    private Borrower borrower;
}