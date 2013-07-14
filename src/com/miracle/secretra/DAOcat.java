package com.miracle.secretra;

import info.xmark.base.DBPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

public class DAOcat {

	private static Logger log = Logger.getLogger(DAOcat.class);

	public static List<MM> queryMMbyUid(String uid) {
		List<MM> ret = new ArrayList<MM>();
		Connection conn = DBPool.getInstance().getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select * from secret where uid=? limit 5";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, uid);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				MM mm = new MM();
				mm.setComment(rs.getString("comment"));
				mm.setCreate_dt(rs.getTimestamp("create_dt"));
				mm.setOwn(rs.getInt("own"));
				mm.setUid(rs.getString("uid"));
				mm.setSecret(rs.getString("secret"));
				ret.add(mm);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			log.error("~~~~~~~~~~~~~~~ queryMMbyUid error ! ", e);
		} finally {
			DBPool.getInstance().close(pstmt, rs, conn);
		}
		return ret;

	}
}
