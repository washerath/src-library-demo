package com.fortunaglobal.demo.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fortunaglobal.demo.library.model.Borrower;

public interface BorrowerRepository extends JpaRepository<Borrower, Long> {

    Borrower findByEmail(String email);

}
