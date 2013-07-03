package info.xmark.base;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.log4j.Logger;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class DBPool {
	private static Logger log = Logger.getLogger(DBPool.class);
	private static DBPool dbPool;
	private ComboPooledDataSource dataSource;
	private String dbUrl = null;
	private String dbUser = null;
	private String dbPwd = null;
	static {
		dbPool = new DBPool();
	}

	public DBPool() {
		try {
			log.debug("1:initial");
			// （2）直接从请求header中获取ip、端口、用户名和密码信息
			// String host = request.getHeader("BAE_ENV_ADDR_SQL_IP");
			// String port = request.getHeader("BAE_ENV_ADDR_SQL_PORT");
			// String username = request.getHeader("BAE_ENV_AK");
			// String password = request.getHeader("BAE_ENV_SK");
			// （3）从线程变量BaeEnv接口获取ip、端口、用户名和密码信息
			// 从平台查询应用要使用的数据库名

			dbUrl = "jdbc:mysql://" + System.getenv("BAE_ENV_ADDR_SQL_IP") + ":" + System.getenv("BAE_ENV_ADDR_SQL_PORT") + "/" + "wTnuNwxewwFbuooQmTmu";
			dbUser = System.getenv("BAE_ENV_AK");
			dbPwd = System.getenv("BAE_ENV_SK");

			String driver = "com.mysql.jdbc.Driver";
			dataSource = new ComboPooledDataSource();
			log.debug("3:dataSource " + dbUrl + "|" + dbUser + "|" + dbPwd);
			dataSource.setUser(dbUser);
			dataSource.setPassword(dbPwd);
			dataSource.setJdbcUrl(dbUrl);
			dataSource.setDriverClass(driver);
			dataSource.setInitialPoolSize(5);
			dataSource.setMinPoolSize(5);
			dataSource.setMaxPoolSize(20);
			dataSource.setMaxIdleTime(15);
			dataSource.setAcquireIncrement(5);

		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
		}
	}

	/*
	 * 
	 * public DBPool() { try { log.debug("1:initial"); InputStream is =
	 * DBPool.class.getResourceAsStream("/jdbc.properties"); Properties p = new
	 * Properties(); p.load(is); log.debug("2:loadproperties " + is.toString());
	 * dbUrl = p.getProperty("cdn.url"); dbUser = p.getProperty("cdn.username");
	 * dbPwd = p.getProperty("cdn.password"); String driver =
	 * p.getProperty("driver"); dataSource = new ComboPooledDataSource();
	 * log.debug("3:dataSource " + dbUrl + "|" + dbUser + "|" + dbPwd);
	 * dataSource.setUser(dbUser); dataSource.setPassword(dbPwd);
	 * dataSource.setJdbcUrl(dbUrl); dataSource.setDriverClass(driver);
	 * dataSource.setInitialPoolSize(5); dataSource.setMinPoolSize(5);
	 * dataSource.setMaxPoolSize(20); dataSource.setMaxIdleTime(15);
	 * dataSource.setAcquireIncrement(5); is.close(); } catch
	 * (PropertyVetoException e) { e.printStackTrace();
	 * log.error(e.getMessage()); } catch (Exception e) { e.printStackTrace();
	 * log.error(e.getMessage()); } }
	 */
	public final static DBPool getInstance() {
		log.debug("4:get instance");
		return dbPool;

	}

	public final Connection getConnection() {
		try {
			log.debug("dbPool getconnection");
			Connection conn = dataSource.getConnection();
			return conn;
		} catch (SQLException e) {
			e.printStackTrace();
			log.error("5:can't get the connection :" + e);
			throw new RuntimeException("unable to connect to the database ", e);
		}
	}

	public final void close(Statement pstmt, ResultSet rs, Connection conn) {
		try {
			if (rs != null) {
				rs.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
		} finally {
			try {
				if (pstmt != null) {
					pstmt.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
				log.error(e.getMessage());
			} finally {
				try {
					if (conn != null) {
						conn.close();
					}
				} catch (Exception e) {
					e.printStackTrace();
					log.error(e.getMessage());
				}
			}
		}
	}

	/**
	 * 通用更改数据库操作（可以用于update，delete，insert）
	 * 
	 * @param sql
	 * @return
	 */
	public static int commonExe(String sql) throws SQLException {

		int flag = 0;
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		conn = DBPool.getInstance().getConnection();
		pstmt = conn.prepareStatement(sql);
		flag = pstmt.executeUpdate();
		int autoid = 1;
		rs = pstmt.getGeneratedKeys();
		if (rs != null && rs.next()) {
			autoid = rs.getInt(1);
		}
		DBPool.getInstance().close(pstmt, rs, conn);

		if (flag != 0) {
			return autoid;
		} else {
			return -1;
		}
	}

	/*
	 * public static void main(String[] args) throws SQLException { String
	 * inUsersite =
	 * "insert into user_site(user_id,site_id,category,descrip,in_time) values("
	 * + 31 + "," + 12 + ",'未归档','还没归档的网页',now());";
	 * System.out.println(inUsersite);
	 * System.out.println(DBPool.commonExe(inUsersite)); }
	 */
}
