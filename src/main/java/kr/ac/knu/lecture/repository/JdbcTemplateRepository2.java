package kr.ac.knu.lecture.repository;

import kr.ac.knu.lecture.domain.User;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by rokim on 2018. 11. 16..
 */
@Repository
@AllArgsConstructor
public class JdbcTemplateRepository2 {
    private final DataSource dataSource;
    private final JdbcTemplate jdbcTemplate;

    public ArrayList<User> getAllUsers() throws SQLException {
        Connection connection = dataSource.getConnection();
        PreparedStatement pst = connection.prepareStatement("SELECT * FROM user");

        pst.executeQuery();

        ResultSet rs = pst.executeQuery();

        ArrayList<User> users = new ArrayList<>();

        while (rs.next()) {
            users.add(new User(rs.getString(1), null, null, rs.getLong(2)));
        }

        return users;
    }

    public ArrayList<User> getAllUsersOrderByAccount() throws SQLException {
        Connection connection = dataSource.getConnection();
        PreparedStatement pst = connection.prepareStatement("SELECT * FROM user order by account DESC");

        ResultSet rs = pst.executeQuery();

        ArrayList<User> users = new ArrayList<>();

        while (rs.next()) {
            users.add(new User(rs.getString(1), null, null, rs.getLong(2)));
        }

        connection.close();

        return users;
    }

}
