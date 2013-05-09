package com.miracle.secretra;

import info.xmark.base.DBPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.TimeZone;

import org.apache.log4j.Logger;

/**
 * 为什么不能叫dog，嘿嘿
 * 
 * @author hyliu
 * 
 */
public class DBDog {

	public static void main(String args[]) {
		System.out.println(new Timestamp(Long.valueOf(1367828663000l)));
		System.out.println(Calendar.getInstance(TimeZone.getTimeZone("GMT+01:00")).getTimeInMillis());
		System.out.println(new Timestamp(Calendar.getInstance().getTimeInMillis()));
		System.out.println(new Timestamp(Calendar.getInstance().getTimeInMillis() + 28800000));
	}

	private static Logger log = Logger.getLogger(DBDog.class);

	// 从最新的50个秘密当中随即获取一个秘密
	public static String getSecretPic(String fromUsername) {
		String ret = null;
		Connection conn = DBPool.getInstance().getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		String sql = "select secret,create_dt from secret WHERE uid<>" + fromUsername + " ORDER BY RAND() LIMIT 1";
		log.debug("sql : " + sql);
		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				ret = rs.getString("secret");
			}

		} catch (SQLException e) {
			e.printStackTrace();
			log.error("~~~~~~~~~~~~~~~ query secret error ! ", e);
		} finally {
			DBPool.getInstance().close(pstmt, rs, conn);
		}
		return ret;

	}

	public static void saveSecret(String fromUsername, String picUrl) {
		Connection conn = DBPool.getInstance().getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "insert into secret(uid,secret,own,create_dt) values(?,?,?,?)";
		try {

			Timestamp t = new Timestamp(Calendar.getInstance().getTimeInMillis() + 28800000);
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, fromUsername);
			pstmt.setString(2, picUrl);
			pstmt.setInt(3, 1);
			pstmt.setTimestamp(4, t);
			pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
			log.error("~~~~~~~~~~~~~~~ save error ! ", e);
		} finally {
			DBPool.getInstance().close(pstmt, rs, conn);
		}
	}
}