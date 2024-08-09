package com.onedata.service;

import com.onedata.dto.Member;
import com.onedata.exception.MemberNotFoundException;
import org.springframework.dao.DataAccessException;

import java.util.List;

public interface MemberService {
    Member findById(Long id) throws MemberNotFoundException;
    List<Member> findAll();
    void save(Member member) throws DataAccessException;
    void update(Member member) throws MemberNotFoundException, DataAccessException;
    void delete(Long id) throws MemberNotFoundException, DataAccessException;
    List<Member> findMembers(String name, String phone);
}

