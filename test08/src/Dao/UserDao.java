package Dao;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import entity.User;

import java.sql.Connection;

public class UserDao extends BaseDao {

	public boolean search(User user) {
		PreparedStatement pstat = null;
		ResultSet rs = null;
		Connection conn = null;
		boolean flag = false;
		try {

			conn = getConnection();
			String sql = "select * from user where username=? and password=?";
			pstat = conn.prepareStatement(sql);
			pstat.setString(1, user.getUsername());
			pstat.setString(2, user.getPassword());
			rs = pstat.executeQuery();
			if (rs.next()) {
				flag = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeAll(conn, pstat, rs);
		}
		return flag;
	}
	
	
	public boolean add(User user) {
		PreparedStatement pstat = null;
		int rs = 0;
		Connection conn = null;
		try {

			conn = getConnection();
			String sql = "insert into user(username,password) values(?,?)";
			pstat = conn.prepareStatement(sql);
			pstat.setString(1, user.getUsername());
			pstat.setString(2, user.getPassword());
			rs = pstat.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeAll(conn, pstat, null);
		}
		return rs>0;
	}

}
