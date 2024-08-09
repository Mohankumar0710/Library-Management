package com.onedata.service;

import com.onedata.dto.Borrow;
import com.onedata.exception.BorrowNotFoundException;
import org.springframework.dao.DataAccessException;

import java.util.List;

public interface BorrowService {
    Borrow findById(Long id) throws BorrowNotFoundException;
    List<Borrow> findAll();
    void save(Borrow borrow) throws DataAccessException;
    void update(Borrow borrow) throws BorrowNotFoundException, DataAccessException;
    void delete(Long id) throws BorrowNotFoundException, DataAccessException;
    List<Borrow> findByMemberId(Long memberId);
    List<Borrow> findByBookId(Long bookId);
}
