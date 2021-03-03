package next.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import core.jdbc.JdbcTemplate;
import core.jdbc.RowMapper;
import next.model.Answer;

public class AnswerDao {
	public void insert(Answer answer) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        String sql = "INSERT INTO ANSWERS(writer, contents, createdDate, questionId)" + 
        		" VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, answer.getWriter(), answer.getContents(),
        		answer.getCreatedDate(), answer.getQuestionId());
    }
	
	public Answer findByAnswerId(int answerId) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        String sql = "SELECT answerId, writer, contents, createdDate, questionId FROM ANSWERS WHERE answerId = ?";

        RowMapper<Answer> rm = new RowMapper<Answer>() {
            @Override
            public Answer mapRow(ResultSet rs) throws SQLException {
                return new Answer(
                		rs.getLong("answerId"),
                		rs.getString("writer"),
                		rs.getString("contents"),
                		rs.getDate("createdDate"),
                		rs.getInt("questionId"));
            }
        };

        return jdbcTemplate.queryForObject(sql, rm, answerId);
    }
	
	public List<Answer> findAll(int questionId) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        String sql = "SELECT answerId, writer, contents, createdDate, questionId FROM ANSWERS WHERE questionId = ?";

        RowMapper<Answer> rm = new RowMapper<Answer>() {
            @Override
            public Answer mapRow(ResultSet rs) throws SQLException {
            	return new Answer(
                		rs.getLong("answerId"),
                		rs.getString("writer"),
                		rs.getString("contents"),
                		rs.getDate("createdDate"),
                		rs.getInt("questionId"));
            }
        };

        return jdbcTemplate.query(sql, rm, questionId);
    }
	
	public void update(Answer answer) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        String sql = "UPDATE ANSWERS set writer = ?, contents = ? WHERE answerId = ?";
        jdbcTemplate.update(sql, answer.getWriter(), answer.getContents(), answer.getAnswerId());
    }
	
	public void delete(int answerId) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate();
        String sql = "DELETE FROM ANSWERS WHERE answerId = ?";
        jdbcTemplate.update(sql, answerId);
	}
}
