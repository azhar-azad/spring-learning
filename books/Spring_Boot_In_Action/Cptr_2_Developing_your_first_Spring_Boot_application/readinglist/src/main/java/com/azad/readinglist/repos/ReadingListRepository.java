package com.azad.readinglist.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.azad.readinglist.entities.Book;

public interface ReadingListRepository extends JpaRepository<Book, Long> {

	List<Book> findByReader(String reader);
}
