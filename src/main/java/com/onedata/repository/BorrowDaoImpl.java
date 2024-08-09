package com.onedata.repository;

import com.onedata.dto.Borrow;
import com.onedata.exception.BorrowNotFoundException;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

@Repository
public class BorrowDaoImpl implements BorrowDao{

    private final JdbcTemplate jdbcTemplate;

    public BorrowDaoImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Borrow findById(Long id) throws BorrowNotFoundException {
        try {
            return jdbcTemplate.queryForObject("SELECT * FROM borrows WHERE id = ?", new Object[]{id}, new BorrowRowMapper());
        } catch (DataAccessException e) {
            throw new BorrowNotFoundException("Borrow record not found with id: " + id, e);
        }
    }

    @Override
    public List<Borrow> findAll() {
        return jdbcTemplate.query("SELECT * FROM borrows", new BorrowRowMapper());
    }

    @Override
    public void save(Borrow borrow) throws DataAccessException {
        jdbcTemplate.update("INSERT INTO borrows (member_id, book_id, borrow_date, due_date) VALUES (?, ?, ?, ?)",
                borrow.getMemberId(), borrow.getBookId(), borrow.getBorrowDate(), borrow.getDueDate());
    }

    @Override
    public void update(Borrow borrow) throws BorrowNotFoundException, DataAccessException {
        int updated = jdbcTemplate.update("UPDATE borrows SET member_id = ?, book_id = ?, borrow_date = ?, due_date = ? WHERE id = ?",
                borrow.getMemberId(), borrow.getBookId(), borrow.getBorrowDate(), borrow.getDueDate(), borrow.getId());
        if (updated == 0) {
            throw new BorrowNotFoundException("Borrow record not found with id: " + borrow.getId());
        }
    }

    @Override
    public void delete(Long id) throws BorrowNotFoundException, DataAccessException {
        int deleted = jdbcTemplate.update("DELETE FROM borrows WHERE id = ?", id);
        if (deleted == 0) {
            throw new BorrowNotFoundException("Borrow record not found with id: " + id);
        }
    }

    @Override
    public List<Borrow> findByMemberId(Long memberId) {
        return jdbcTemplate.query("SELECT * FROM borrows WHERE member_id = ?", new Object[]{memberId}, new BorrowRowMapper());
    }

    @Override
    public List<Borrow> findByBookId(Long bookId) {
        return jdbcTemplate.query("SELECT * FROM borrows WHERE book_id = ?", new Object[]{bookId}, new BorrowRowMapper());
    }

    private static class BorrowRowMapper implements RowMapper<Borrow> {
        @Override
        public Borrow mapRow(ResultSet rs, int rowNum) throws SQLException {
            Borrow borrow = new Borrow();
            borrow.setId(rs.getLong("id"));
            borrow.setMemberId(rs.getLong("member_id"));
            borrow.setBookId(rs.getLong("book_id"));
            borrow.setBorrowDate(rs.getObject("borrow_date", LocalDate.class));
            borrow.setDueDate(rs.getObject("due_date", LocalDate.class));
            return borrow;
        }
    }
}
