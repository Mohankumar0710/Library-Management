package com.onedata.service.impl;


import com.onedata.dto.Borrow;
import com.onedata.exception.BorrowNotFoundException;
import com.onedata.repository.BorrowDao;
import com.onedata.service.BorrowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BorrowServiceImpl implements BorrowService {

    private final BorrowDao borrowDao;

    @Autowired
    public BorrowServiceImpl(BorrowDao borrowDao) {
        this.borrowDao = borrowDao;
    }

    @Override
    public Borrow findById(Long id) throws BorrowNotFoundException {
        return borrowDao.findById(id);
    }

    @Override
    public List<Borrow> findAll() {
        return borrowDao.findAll();
    }

    @Override
    public void save(Borrow borrow) throws DataAccessException {
        borrowDao.save(borrow);
    }

    @Override
    public void update(Borrow borrow) throws BorrowNotFoundException, DataAccessException {
        borrowDao.update(borrow);
    }

    @Override
    public void delete(Long id) throws BorrowNotFoundException, DataAccessException {
        borrowDao.delete(id);
    }

    @Override
    public List<Borrow> findByMemberId(Long memberId) {
        return borrowDao.findByMemberId(memberId);
    }

    @Override
    public List<Borrow> findByBookId(Long bookId) {
        return borrowDao.findByBookId(bookId);
    }
}
