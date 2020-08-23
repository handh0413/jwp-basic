package core.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JdbcTemplate {
	private static final Logger logger = LoggerFactory.getLogger(JdbcTemplate.class);
	
	public void update(String sql, Object... parameters) throws DataAccessException {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = ConnectionManager.getConnection();
			pstmt = con.prepareStatement(sql);
			for (int i = 0; i < parameters.length; i++) {
				pstmt.setObject(i + 1, parameters[i]);
			}
			pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new DataAccessException(e);
		}
		finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					throw new DataAccessException(e);
				}
			}

			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					throw new DataAccessException(e);
				}
			}
		}
	}

	@SuppressWarnings("rawtypes")
	public <T> List<T> query(String sql, RowMapper<T> rowMapper, Object... parameters) throws DataAccessException {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ConnectionManager.getConnection();
			pstmt = con.prepareStatement(sql);
			logger.debug("sql : {}", sql);
			for (int i = 0; i < parameters.length; i++) {
				pstmt.setObject(i + 1, parameters[i]);
			}
			rs = pstmt.executeQuery();

			List<T> result = new ArrayList<T>();
			while (rs.next()) {
				result.add(rowMapper.mapRow(rs));
			}
			return result;
		} catch (Exception e) {
			throw new DataAccessException(e);
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					throw new DataAccessException(e);
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					throw new DataAccessException(e);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					throw new DataAccessException(e);
				}
			}
		}
	}

	@SuppressWarnings("rawtypes")
	public <T> T queryForObject(String sql, RowMapper<T> rowMapper, Object... parameters) throws DataAccessException {
		List<T> result = query(sql, rowMapper, parameters);
		if (result.isEmpty()) {
			return null;
		}
		return result.get(0);
	}

}
