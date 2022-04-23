package com.azad.readinglist.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.azad.readinglist.entities.Reader;

public interface ReaderRepository extends JpaRepository<Reader, String> {

}
