package com.onedata.service.impl;

import com.onedata.dto.BookDto;
import com.onedata.exception.BookNotFoundException;
import com.onedata.repository.BookDetailsRepo;
import com.onedata.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {


    @Autowired
    private BookDetailsRepo bookDao;

    @Override
    public BookDto findById(Long bookId) throws BookNotFoundException {
        return bookDao.findById(bookId);
    }

    @Override
    public List<BookDto> findAll() {
        return bookDao.findAll();
    }

    @Override
    public void save(BookDto book) throws DataAccessException {
        bookDao.save(book);
    }

    @Override
    public void update(BookDto book) throws BookNotFoundException, DataAccessException {
        bookDao.update(book);
    }

    @Override
    public void delete(Long bookId) throws BookNotFoundException, DataAccessException {
        bookDao.delete(bookId);
    }

    @Override
    public List<BookDto> findBooks(String title, String author, String isbn) {
        // Pass search parameters to DAO or modify DAO to support this search
        return bookDao.findBooks(title, author, isbn);
    }
}
