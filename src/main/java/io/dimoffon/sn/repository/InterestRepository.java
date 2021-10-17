package io.dimoffon.sn.repository;

import io.dimoffon.sn.entity.Interest;
import io.dimoffon.sn.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

@Repository
public class InterestRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public InterestRepository(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void removeInterests(final User user) {
        jdbcTemplate.update("delete from user_interests where user_id = ?",
                user.getId());
    }

    public void addInterests(final User user) {
        jdbcTemplate.batchUpdate("insert into user_interests (user_id, interest_id) values (?, ?)",
                new BatchPreparedStatementSetter() {
                    @Override
                    public void setValues(final PreparedStatement ps, final int i) throws SQLException {
                        ps.setLong(1, user.getId());
                        ps.setLong(2, user.getInterests().get(i).getId());
                    }
                    @Override
                    public int getBatchSize() {
                        return user.getInterests().size();
                    }
                });
    }

    public List<Interest> getInterests() {
        List<Interest> result = new LinkedList<>();
        jdbcTemplate.query("select * from interests",
                rs -> {
                    result.add(Interest.builder()
                            .id(rs.getLong("id"))
                            .name(rs.getString("name"))
                            .build());
                });
        return result;
    }

    public List<Interest> getUserInterests(final User user) {
        List<Interest> result = new LinkedList<>();
        jdbcTemplate.query("select ui.interest_id, i.name from user_interests ui " +
                        "join interests i on ui.interest_id = i.id " +
                        "where ui.user_id = ?",
                ps -> {
                    ps.setLong(1, user.getId());
                },
                rs -> {
                    result.add(Interest.builder()
                            .id(rs.getLong("interest_id"))
                            .name(rs.getString("name"))
                            .build());
                });
        return result;
    }

}
