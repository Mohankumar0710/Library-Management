package com.onedata.repository;

import com.onedata.dto.BookDto;
import com.onedata.exception.BookNotFoundException;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Repository
public class BookDetailsRepoImpl implements BookDetailsRepo {

    private final JdbcTemplate jdbcTemplate;

    public BookDetailsRepoImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }


    @Override
    public BookDto findById(Long bookId) throws BookNotFoundException {
        try {
            return jdbcTemplate.queryForObject("SELECT * FROM books WHERE book_id = ?", new Object[]{bookId}, new BookRowMapper());
        } catch (DataAccessException e) {
            throw new BookNotFoundException("Book not found with id: " + bookId, e);
        }
    }

    @Override
    public List<BookDto> findAll() {
        return jdbcTemplate.query("SELECT * FROM books", new BookRowMapper());
    }

    @Override
    public void save(BookDto book) throws DataAccessException {
        jdbcTemplate.update("INSERT INTO books (title, author, isbn, published_date, available_copies) VALUES (?, ?, ?, ?, ?)",
                book.getTitle(), book.getAuthor(), book.getIsbn(), book.getPublishedDate(), book.getAvailableCopies());
    }

    @Override
    public void update(BookDto book) throws BookNotFoundException, DataAccessException {
        int updated = jdbcTemplate.update("UPDATE books SET title = ?, author = ?, isbn = ?, published_date = ?, available_copies = ? WHERE book_id = ?",
                book.getTitle(), book.getAuthor(), book.getIsbn(), book.getPublishedDate(), book.getAvailableCopies(), book.getBookId());
        if (updated == 0) {
            throw new BookNotFoundException("Book not found with id: " + book.getBookId());
        }
    }

    @Override
    public void delete(Long bookId) throws BookNotFoundException, DataAccessException {
        int deleted = jdbcTemplate.update("DELETE FROM books WHERE book_id = ?", bookId);
        if (deleted == 0) {
            throw new BookNotFoundException("Book not found with id: " + bookId);
        }
    }

    @Override
    public List<BookDto> findBooks(String title, String author, String isbn) {
        StringBuilder sql = new StringBuilder("SELECT * FROM books WHERE 1=1");
        List<Object> params = new ArrayList<>();

        if (title != null && !title.isEmpty()) {
            sql.append(" AND title LIKE ?");
            params.add("%" + title + "%");
        }

        if (author != null && !author.isEmpty()) {
            sql.append(" AND author LIKE ?");
            params.add("%" + author + "%");
        }

        if (isbn != null && !isbn.isEmpty()) {
            sql.append(" AND isbn = ?");
            params.add(isbn);
        }

        return jdbcTemplate.query(sql.toString(), params.toArray(), new BookRowMapper());
    }

    private static class BookRowMapper implements RowMapper<BookDto> {
        @Override
        public BookDto mapRow(ResultSet rs, int rowNum) throws SQLException {
            BookDto book = new BookDto();
            book.setBookId(rs.getLong("book_id"));
            book.setTitle(rs.getString("title"));
            book.setAuthor(rs.getString("author"));
            book.setIsbn(rs.getString("isbn"));
            book.setPublishedDate(rs.getObject("published_date", LocalDate.class));
            book.setAvailableCopies(rs.getInt("available_copies"));
            return book;
        }
    }

    }

