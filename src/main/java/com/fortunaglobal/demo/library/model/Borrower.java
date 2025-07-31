package com.fortunaglobal.demo.library.model;

import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Data
public class Borrower {
    public Borrower(String email, String name) {
        this.email = email;
        this.name = name;
    }

    
    public Borrower() {
    }


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @Column(unique = true)
    private String email;

    @OneToMany(mappedBy = "borrower")
    @JsonManagedReference
    private Set<Book> borrowedBooks;

}
