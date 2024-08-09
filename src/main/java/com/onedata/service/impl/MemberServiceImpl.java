package com.onedata.service.impl;

import com.onedata.dto.Member;
import com.onedata.exception.MemberNotFoundException;
import com.onedata.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;

import java.util.List;

public class MemberServiceImpl implements MemberService {

    @Autowired
    public MemberService memberDao;

    @Override
    public Member findById(Long id) throws MemberNotFoundException {
        return memberDao.findById(id);
    }

    @Override
    public List<Member> findAll() {
        return memberDao.findAll();
    }

    @Override
    public void save(Member member) throws DataAccessException {
        memberDao.save(member);
    }

    @Override
    public void update(Member member) throws MemberNotFoundException, DataAccessException {
        memberDao.update(member);
    }

    @Override
    public void delete(Long id) throws MemberNotFoundException, DataAccessException {
        memberDao.delete(id);
    }

    @Override
    public List<Member> findMembers(String name, String phone) {
        return memberDao.findMembers(name, phone);
    }
}
