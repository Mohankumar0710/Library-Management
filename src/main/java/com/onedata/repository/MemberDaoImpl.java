package com.onedata.repository;

import com.onedata.dto.Member;
import com.onedata.exception.MemberNotFoundException;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MemberDaoImpl implements MemberDao{

    private final JdbcTemplate jdbcTemplate;

    public MemberDaoImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Member findById(Long id) throws MemberNotFoundException {
        try {
            return jdbcTemplate.queryForObject("SELECT * FROM members WHERE id = ?", new Object[]{id}, new MemberRowMapper());
        } catch (DataAccessException e) {
            throw new MemberNotFoundException("Member not found with id: " + id, e);
        }
    }

    @Override
    public List<Member> findAll() {
        return jdbcTemplate.query("SELECT * FROM members", new MemberRowMapper());
    }

    @Override
    public void save(Member member) throws DataAccessException {
        jdbcTemplate.update("INSERT INTO members (name, phone, registered_date) VALUES (?, ?, ?)",
                member.getName(), member.getPhone(), member.getRegisteredDate());
    }

    @Override
    public void update(Member member) throws MemberNotFoundException, DataAccessException {
        int updated = jdbcTemplate.update("UPDATE members SET name = ?, phone = ?, registered_date = ? WHERE id = ?",
                member.getName(), member.getPhone(), member.getRegisteredDate(), member.getId());
        if (updated == 0) {
            throw new MemberNotFoundException("Member not found with id: " + member.getId());
        }
    }

    @Override
    public void delete(Long id) throws MemberNotFoundException, DataAccessException {
        int deleted = jdbcTemplate.update("DELETE FROM members WHERE id = ?", id);
        if (deleted == 0) {
            throw new MemberNotFoundException("Member not found with id: " + id);
        }
    }

    @Override
    public List<Member> findMembers(String name, String phone) {
        StringBuilder sql = new StringBuilder("SELECT * FROM members WHERE 1=1");
        List<Object> params = new ArrayList<>();

        if (name != null && !name.isEmpty()) {
            sql.append(" AND name LIKE ?");
            params.add("%" + name + "%");
        }

        if (phone != null && !phone.isEmpty()) {
            sql.append(" AND phone = ?");
            params.add(phone);
        }

        return jdbcTemplate.query(sql.toString(), params.toArray(), new MemberRowMapper());
    }

    private static class MemberRowMapper implements RowMapper<Member> {
        @Override
        public Member mapRow(ResultSet rs, int rowNum) throws SQLException {
            Member member = new Member();
            member.setId(rs.getLong("id"));
            member.setName(rs.getString("name"));
            member.setPhone(rs.getString("phone"));
            member.setRegisteredDate(rs.getObject("registered_date", LocalDate.class));
            return member;
        }
    }
}
