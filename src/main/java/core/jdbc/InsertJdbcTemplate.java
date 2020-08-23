package core.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import next.dao.UserDao;
import next.model.User;

public abstract class InsertJdbcTemplate {
	public void insert(User user, UserDao userDao) throws SQLException {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = ConnectionManager.getConnection();
			String sql = createQueryForInsert();
			pstmt = con.prepareStatement(sql);
			setValuesForInsert(user, pstmt);

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
	
	protected abstract String createQueryForInsert();
	protected abstract void setValuesForInsert(User user, PreparedStatement pstmt);
	
}
