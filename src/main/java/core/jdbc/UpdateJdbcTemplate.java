package core.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import next.dao.UserDao;
import next.model.User;

public abstract class UpdateJdbcTemplate {
	public void update(User user, UserDao userDao) throws SQLException {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = ConnectionManager.getConnection();
			String sql = createQueryForUpdate();
			pstmt = con.prepareStatement(sql);
			setValuesForUpdate(user, pstmt);

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
	
	protected abstract String createQueryForUpdate();
	protected abstract void setValuesForUpdate(User user, PreparedStatement pstmt);
	
}
