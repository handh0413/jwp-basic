package core.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JdbcTemplate {
	public void update(String sql, PreparedStatementSetter pss) throws DataAccessException {
		try(Connection con = ConnectionManager.getConnection();
			PreparedStatement pstmt = con.prepareStatement(sql)) {
			pss.setValues(pstmt);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new DataAccessException(e);
		}
	}
	
	public void update(String sql, Object... objects) throws DataAccessException {
		PreparedStatementSetter pss = createPreparedStatementSetter(objects);
		update(sql, pss);
	}

	public <T> List<T> query(String sql, RowMapper<T> rm, PreparedStatementSetter pss) throws DataAccessException {
		
		
		ResultSet rs = null;
		try(Connection con = ConnectionManager.getConnection();
			PreparedStatement pstmt = con.prepareStatement(sql)){
			pss.setValues(pstmt);
			rs = pstmt.executeQuery();

			List<T> result = new ArrayList<T>();
			while (rs.next()) {
				result.add(rm.mapRow(rs));
			}

			return result;
		} catch (SQLException e) {
			throw new DataAccessException(e);
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					throw new DataAccessException(e);
				}
			} 
		}
	}
	
	public <T> List<T> query(String sql, RowMapper<T> rm, Object...objects) throws DataAccessException {
		PreparedStatementSetter pss = createPreparedStatementSetter(objects);
		return query(sql, rm, pss);
	}

	public <T> T queryForObject(String sql, RowMapper<T> rm, PreparedStatementSetter pss) 
			throws DataAccessException {
		List<T> result = query(sql, rm, pss);
		if (result.isEmpty()) {
			return null;
		}
		return result.get(0);
	}
	
	public <T> T queryForObject(String sql, RowMapper<T> rm, Object...objects) throws DataAccessException {
		PreparedStatementSetter pss = createPreparedStatementSetter(objects);
		return queryForObject(sql, rm, pss);
	}
	
	private PreparedStatementSetter createPreparedStatementSetter(Object... objects) {
		return new PreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement pstmt) throws SQLException {
				for (int i = 0; i < objects.length; i++) {
					pstmt.setObject(i + 1, objects[i]);
				}
			}
		};
	}
}
