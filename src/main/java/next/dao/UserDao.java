package next.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import core.jdbc.JdbcTemplate;
import next.model.User;

public class UserDao {
    public void insert(User user) throws SQLException {
    	JdbcTemplate jdbcTemplate = new JdbcTemplate() {
			@Override
			public void setValues(PreparedStatement pstmt) throws SQLException {
				pstmt.setString(1, user.getUserId());
				pstmt.setString(2, user.getPassword());
				pstmt.setString(3, user.getName());
				pstmt.setString(4, user.getEmail());
			}

			@Override
			public Object mapRow(ResultSet rs) throws SQLException {
				return null;
			}
        };
        String sql = "INSERT INTO USERS VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql);
    }

    public void update(User user) throws SQLException {
    	JdbcTemplate jdbcTemplate = new JdbcTemplate() {
			@Override
			public void setValues(PreparedStatement pstmt) throws SQLException {
				pstmt.setString(1, user.getName());
				pstmt.setString(2, user.getPassword());
				pstmt.setString(3, user.getEmail());
				pstmt.setString(4, user.getUserId());
			}

			@Override
			public Object mapRow(ResultSet rs) throws SQLException {
				return null;
			}
    	};
    	String sql = "UPDATE USERS SET name = ?, password = ?, email = ? WHERE userId = ?";
    	jdbcTemplate.update(sql);
    }

    @SuppressWarnings("unchecked")
	public List<User> findAll() throws SQLException {
    	JdbcTemplate template = new JdbcTemplate() {
			@Override
			public User mapRow(ResultSet rs) throws SQLException {
				return new User(
						rs.getString("userId"), 
						rs.getString("password"), 
						rs.getString("name"),
				        rs.getString("email"));
			}

			@Override
			public void setValues(PreparedStatement pstmt) throws SQLException {
				
			}
		};
		
		String sql = "SELECT * FROM USERS";
		return (List<User>)template.query(sql);
    }

    public User findByUserId(String userId) throws SQLException {
    	JdbcTemplate template = new JdbcTemplate() {
			@Override
			public void setValues(PreparedStatement pstmt) throws SQLException {
				pstmt.setString(1, userId);
			}
			
			@Override
			public User mapRow(ResultSet rs) throws SQLException {
				return new User(
						rs.getString("userId"), 
						rs.getString("password"), 
						rs.getString("name"),
				        rs.getString("email"));
			}
		};
		
		String sql = "SELECT userId, password, name, email FROM USERS WHERE userid=?";
		return (User)template.queryForObject(sql);
    }
}
