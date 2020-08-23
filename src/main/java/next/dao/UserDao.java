package next.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import core.jdbc.JdbcTemplate;
import core.jdbc.RowMapper;
import next.model.User;

public class UserDao {
	public void insert(User user) throws SQLException {
		JdbcTemplate jdbcTemplate = new JdbcTemplate();
		String query = "INSERT INTO USERS VALUES(?, ?, ?, ?)";
		jdbcTemplate.update(query, user.getUserId(), user.getPassword(), user.getName(), user.getEmail());
	}

	public void update(User user) throws SQLException {
		JdbcTemplate jdbcTemplate = new JdbcTemplate();
		String query = "UPDATE USERS SET password = ?, name = ?, email = ? WHERE userId = ?";
		jdbcTemplate.update(query, user.getPassword(), user.getName(), user.getEmail(), user.getUserId());
	}

	@SuppressWarnings("unchecked")
	public List<User> findAll() throws SQLException {
		JdbcTemplate template = new JdbcTemplate();
		String sql = "SELECT userId, password, name, email FROM USERS";
		return (List<User>) template.query(sql, (ResultSet rs) -> {
			return new User(rs.getString("userId"), rs.getString("password"), rs.getString("name"),
					rs.getString("email"));
		});
	}

	public User findByUserId(String userId) throws SQLException {
		JdbcTemplate template = new JdbcTemplate();
		String sql = "SELECT userId, password, name, email FROM USERS WHERE userid=?";
		return (User) template.queryForObject(sql, (ResultSet rs) -> {
			return new User(rs.getString("userId"), rs.getString("password"), rs.getString("name"),
					rs.getString("email"));
		}, userId);
	}
}
