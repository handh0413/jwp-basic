package next.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import core.jdbc.JdbcTemplate;
import core.jdbc.PreparedStatementSetter;
import core.jdbc.RowMapper;
import next.model.User;

public class UserDao {
    public void insert(User user) throws SQLException {
    	
    	PreparedStatementSetter pss = new PreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement pstmt) throws SQLException {
				pstmt.setString(1, user.getUserId());
				pstmt.setString(2, user.getPassword());
				pstmt.setString(3, user.getName());
				pstmt.setString(4, user.getEmail());
			}
		};
    	
    	JdbcTemplate jdbcTemplate = new JdbcTemplate();
        String sql = "INSERT INTO USERS VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, pss);
    }

    public void update(User user) throws SQLException {
    	
    	PreparedStatementSetter pss = new PreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement pstmt) throws SQLException {
				pstmt.setString(1, user.getName());
				pstmt.setString(2, user.getPassword());
				pstmt.setString(3, user.getEmail());
				pstmt.setString(4, user.getUserId());
			}
		};
    	
    	JdbcTemplate jdbcTemplate = new JdbcTemplate();
    	String sql = "UPDATE USERS SET name = ?, password = ?, email = ? WHERE userId = ?";
    	jdbcTemplate.update(sql, pss);
    }

    @SuppressWarnings("unchecked")
	public List<User> findAll() throws SQLException {
    	
    	PreparedStatementSetter pss = new PreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement pstmt) throws SQLException {
				
			}
		}; 
		
		RowMapper<User> rm = new RowMapper<User>() {
			@Override
			public User mapRow(ResultSet rs) throws SQLException {
				return new User(
						rs.getString("userId"), 
						rs.getString("password"), 
						rs.getString("name"),
				        rs.getString("email"));
			}
		};
    	
    	JdbcTemplate template = new JdbcTemplate();
		
		String sql = "SELECT * FROM USERS";
		return (List<User>)template.query(sql, pss, rm);
    }

    public User findByUserId(String userId) throws SQLException {
    	
    	PreparedStatementSetter pss = new PreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement pstmt) throws SQLException {
				pstmt.setString(1, userId);
			}
		};
		
		RowMapper<User> rm = new RowMapper<User>() {
			@Override
			public User mapRow(ResultSet rs) throws SQLException {
				return new User(
						rs.getString("userId"), 
						rs.getString("password"), 
						rs.getString("name"),
				        rs.getString("email"));
			}
		};
    	
    	JdbcTemplate template = new JdbcTemplate();
		
		String sql = "SELECT userId, password, name, email FROM USERS WHERE userid=?";
		return (User)template.queryForObject(sql, pss, rm);
    }
}
