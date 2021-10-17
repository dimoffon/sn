package io.dimoffon.sn.repository;

import io.dimoffon.sn.dto.UserFilter;
import io.dimoffon.sn.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

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

    public Collection<User> getUsers(final UserFilter filter) {
        Collection<User> result = new LinkedList<>();
        jdbcTemplate.query("select * from users where enabled = 1 and username like ?",
                ps -> {
                    ps.setString(1, filter.getUsername());
                },
                rs -> {
                    result.add(User.builder()
                            .id(rs.getLong("id"))
                            .username(rs.getString("username"))
                            .firstName(rs.getString("first_name"))
                            .lastName(rs.getString("last_name"))
                            .age(rs.getInt("age"))
                            .gender(rs.getString("gender"))
                            .city(rs.getString("city"))
                            .build());
                });
        return result;
    }

    public User getUserById(final Long userId) {
        return jdbcTemplate.queryForObject("select * from users where enabled = 1 and id = ?",
                new Object[] {userId}, (ResultSet rs, int rowNum)  -> User.builder()
                        .id(rs.getLong("id"))
                        .username(rs.getString("username"))
                        .firstName(rs.getString("first_name"))
                        .lastName(rs.getString("last_name"))
                        .age(rs.getInt("age"))
                        .gender(rs.getString("gender"))
                        .city(rs.getString("city"))
                        .build());
    }

    public List<User> getFriends(final UserFilter filter) {
        List<User> result = new LinkedList<>();
        jdbcTemplate.query("select * from users u join friends f on u.id = f.friend_id " +
                        "where u.enabled = 1 " +
                        "and f.user_id = ? ",
                ps -> {
                    ps.setLong(1, filter.getId());
                },
                rs -> {
                    result.add(User.builder()
                            .id(rs.getLong("id"))
                            .username(rs.getString("username"))
                            .firstName(rs.getString("first_name"))
                            .lastName(rs.getString("last_name"))
                            .age(rs.getInt("age"))
                            .gender(rs.getString("gender"))
                            .city(rs.getString("city"))
                            .build());
                });
        return result;
    }

    public Collection<User> getStrangers(final UserFilter filter) {
        Collection<User> result = new LinkedList<>();
        jdbcTemplate.query("select * from users u left outer join friends f on u.id = f.friend_id " +
                        "where f.enabled = 1 " +
                        "and f.friend_id is null " +
                        "and f.user_id = ? " +
                        "and f.user_id <> ? " +
                        "and u.username like ?",
                ps -> {
                    ps.setLong(1, filter.getId());
                    ps.setLong(2, filter.getId());
                    ps.setString(3, filter.getUsername());
                },
                rs -> {
                    result.add(User.builder()
                            .id(rs.getLong("id"))
                            .username(rs.getString("username"))
                            .firstName(rs.getString("first_name"))
                            .lastName(rs.getString("last_name"))
                            .age(rs.getInt("age"))
                            .gender(rs.getString("gender"))
                            .city(rs.getString("city"))
                            .build());
                });
        return result;
    }

}
