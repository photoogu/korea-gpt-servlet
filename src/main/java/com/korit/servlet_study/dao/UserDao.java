package com.korit.servlet_study.dao;

import com.korit.servlet_study.config.DBConnectionMgr;
import com.korit.servlet_study.entity.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserDao {
    private static UserDao userDao = null;

    private UserDao() {}

    public static UserDao getInstance() {
        if (userDao == null) {
            userDao = new UserDao();
        }
        return userDao;
    }

    public List<User> findAllBySearchValue(String searchValue) {
        List<User> users = new ArrayList<>();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            con = DBConnectionMgr.getInstance().getConnection();
            String sql = """
                    select
                        user_id,
                        username,
                        password,
                        name,
                        email
                    from
                        user_tb
                    where
                        username like concat('%', ?, '%')
                    """;
            ps = con.prepareStatement(sql);
            ps.setString(1, searchValue);
            rs = ps.executeQuery();

            while (rs.next()) {
                users.add(User.builder()
                        .userId(rs.getInt(1))
                        .username(rs.getString(2))
                        .password(rs.getString(3))
                        .name(rs.getString(4))
                        .email(rs.getString(5))
                        .build());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return users;
    }

    public List<User> findAll() {
        List<User> users = new ArrayList<>();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            con = DBConnectionMgr.getInstance().getConnection();
            String sql = """
                select 
                    user_id,
                    username,
                    password,
                    name,
                    email
                from
                    user_tb
            """;
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                users.add(User.builder()
                            .userId(rs.getInt(1))
                            .username(rs.getString(2))
                            .password(rs.getString(3))
                            .name(rs.getString(4))
                            .email(rs.getString(5))
                            .build());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return users;
    }

    public Optional<User> save(User user) {
        Connection con = null;
        PreparedStatement ps = null;

        try {
            con = DBConnectionMgr.getInstance().getConnection();
            String sql = """
                insert into user_tb 
                values(default, ?, ?, ?, ?)
            """;
            ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getName());
            ps.setString(4, user.getEmail());
            ps.executeUpdate();  // execute: query 문 실행 - 성공횟수 반환(행이 1줄이니까 무조건 1이 나와야함) insert, update, delete 에서 execute 사용
            ResultSet keyRs = ps.getGeneratedKeys();
            keyRs.next();                   // next : ResultSet 에서 다음 row 로 커서 이동
            int userId = keyRs.getInt(1);   // getInt(column 선택)
            user.setUserId(userId);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBConnectionMgr.getInstance().freeConnection(con, ps);
        }

        return Optional.ofNullable(user);
    }
}
