package com.onedata.controller;

import com.onedata.dto.Borrow;
import com.onedata.exception.BorrowNotFoundException;
import com.onedata.service.BorrowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class BorrowController {

    @Autowired
    private  BorrowService borrowService;



    @GetMapping("/{id}")
    public ResponseEntity<Borrow> getBorrowById(@PathVariable Long id) {
        try {
            Borrow borrow = borrowService.findById(id);
            return new ResponseEntity<>(borrow, HttpStatus.OK);
        } catch (BorrowNotFoundException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    public ResponseEntity<List<Borrow>> getAllBorrows() {
        List<Borrow> borrows = borrowService.findAll();
        return new ResponseEntity<>(borrows, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<String> createBorrow(@Valid @RequestBody Borrow borrow, BindingResult result) {
        if (result.hasErrors()) {
            return new ResponseEntity<>(result.getAllErrors().toString(), HttpStatus.BAD_REQUEST);
        }
        try {
            borrowService.save(borrow);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (DataAccessException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping
    public ResponseEntity<String> updateBorrow(@Valid @RequestBody Borrow borrow, BindingResult result) {
        if (result.hasErrors()) {
            return new ResponseEntity<>(result.getAllErrors().toString(), HttpStatus.BAD_REQUEST);
        }
        try {
            borrowService.update(borrow);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (BorrowNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (DataAccessException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBorrow(@PathVariable Long id) {
        try {
            borrowService.delete(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (BorrowNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (DataAccessException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/searchByMember")
    public ResponseEntity<List<Borrow>> searchByMemberId(@RequestParam Long memberId) {
        List<Borrow> borrows = borrowService.findByMemberId(memberId);
        return new ResponseEntity<>(borrows, HttpStatus.OK);
    }

    @GetMapping("/searchByBook")
    public ResponseEntity<List<Borrow>> searchByBookId(@RequestParam Long bookId) {
        List<Borrow> borrows = borrowService.findByBookId(bookId);
        return new ResponseEntity<>(borrows, HttpStatus.OK);
    }

}
