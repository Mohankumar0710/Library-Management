package com.onedata.controller;

import com.onedata.dto.Member;
import com.onedata.exception.MemberNotFoundException;
import com.onedata.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class MemberController {

    @Autowired
    private MemberService memberService;

    @GetMapping("/{id}")
    public ResponseEntity<Member> getMemberById(@PathVariable Long id) {
        try {
            Member member = memberService.findById(id);
            return new ResponseEntity<>(member, HttpStatus.OK);
        } catch (MemberNotFoundException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    public ResponseEntity<List<Member>> getAllMembers() {
        List<Member> members = memberService.findAll();
        return new ResponseEntity<>(members, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Void> createMember(@RequestBody Member member) {
        try {
            memberService.save(member);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (DataAccessException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping
    public ResponseEntity<Void> updateMember(@RequestBody Member member) {
        try {
            memberService.update(member);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (MemberNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (DataAccessException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMember(@PathVariable Long id) {
        try {
            memberService.delete(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (MemberNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (DataAccessException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
