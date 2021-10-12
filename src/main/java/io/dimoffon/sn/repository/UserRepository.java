package io.dimoffon.sn.repository;

import io.dimoffon.sn.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public UserRepository(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void addUser(final User user) {
        jdbcTemplate.update("insert into users (username, password, enabled, first_name, " +
                "last_name, age, gender, city) values (?, ?, ?, ?, ?, ?, ?, ?)",
                user.getUsername(),
                user.getPassword(),
                1,
                user.getFirstName(),
                user.getLastName(),
                user.getAge(),
                user.getGender(),
                user.getCity());
    }

    public void addUserRole(final User user, final String role) {
        jdbcTemplate.update("insert into roles (username, role) values (?, ?)",
                user.getUsername(),
                role);
    }

}
