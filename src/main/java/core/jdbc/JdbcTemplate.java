package core.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JdbcTemplate {
	public void update(String sql, PreparedStatementSetter pss) throws SQLException {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = ConnectionManager.getConnection();
			pstmt = con.prepareStatement(sql);
			pss.setValues(pstmt);

			pstmt.executeUpdate();
		} finally {
			if (pstmt != null) {
				pstmt.close();
			}

			if (con != null) {
				con.close();
			}
		}
	}
	
	public void update(String sql, Object... objects) throws SQLException {
		PreparedStatementSetter pss = createPreparedStatementSetter(objects);
		update(sql, pss);
	}

	public <T> List<T> query(String sql, RowMapper<T> rm, PreparedStatementSetter pss) throws SQLException {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = ConnectionManager.getConnection();
			pstmt = con.prepareStatement(sql);
			pss.setValues(pstmt);

			rs = pstmt.executeQuery();

			List<T> result = new ArrayList<T>();
			while (rs.next()) {
				result.add(rm.mapRow(rs));
			}

			return result;
		} finally {
			if (rs != null) {
				rs.close();
			}
			if (pstmt != null) {
				pstmt.close();
			}
			if (con != null) {
				con.close();
			}
		}
	}
	
	public <T> List<T> query(String sql, RowMapper<T> rm, Object...objects) throws SQLException {
		PreparedStatementSetter pss = createPreparedStatementSetter(objects);
		return query(sql, rm, pss);
	}

	public <T> T queryForObject(String sql, RowMapper<T> rm, PreparedStatementSetter pss) throws SQLException {
		List<T> result = query(sql, rm, pss);
		if (result.isEmpty()) {
			return null;
		}
		return result.get(0);
	}
	
	public <T> T queryForObject(String sql, RowMapper<T> rm, Object...objects) throws SQLException {
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
