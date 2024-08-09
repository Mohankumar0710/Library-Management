package com.onedata.repository;

import com.onedata.dto.BookDto;
import com.onedata.exception.BookNotFoundException;
import org.springframework.dao.DataAccessException;

import java.util.List;

public interface BookDetailsRepo  {


        BookDto findById(Long bookId) throws BookNotFoundException;
        List<BookDto> findAll();
        void save(BookDto book) throws DataAccessException;
        void update(BookDto book) throws BookNotFoundException, DataAccessException;
        void delete(Long bookId) throws BookNotFoundException, DataAccessException;
        List<BookDto> findBooks(String title, String author, String isbn);
    }




