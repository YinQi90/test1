package Dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.sun.javafx.collections.SetAdapterChange;

import entity.Project;

public class Dep2ProDao extends BaseDao {

	public List<Project> searchByDepartment(int depId) {
		List<Project> list = new ArrayList<Project>();
		Connection conn = null;
		Statement stat = null;
		ResultSet rs = null;
		try {
			conn = getConnection();
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/company?characterEncoding=utf-8", "root",
					"123456");
			// 4:建立Statement sql语句执行器
			stat = conn.createStatement();
			// 5：执行sql语句并得到结果
			rs = stat.executeQuery("select * from v_dep_pro where d_id=" + depId);
			// 6:对结果集进行处理
			while (rs.next()) {
				Project pro = new Project();
				pro.setId(rs.getInt("p_id"));
				pro.setName(rs.getString("p_name"));
				// pro.setProject(rs.getInt("d_id"));
				list.add(pro);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeAll(conn, stat, rs);
		}

		return list;
	}

	public List<Project> searchByNotDepartment(int depId) {
		List<Project> list = new ArrayList<Project>();
		Connection conn = null;
		Statement stat = null;
		ResultSet rs = null;
		try {
			conn = getConnection();
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/company?characterEncoding=utf-8", "root",
					"123456");
			// 4:建立Statement sql语句执行器
			stat = conn.createStatement();
			// 5：执行sql语句并得到结果
			String sql = "select * from project where id not in(select p_id from v_dep_pro where d_id=" + depId + ")";
			rs = stat.executeQuery(sql);
			// 6:对结果集进行处理
			while (rs.next()) {
				Project pro = new Project();
				pro.setId(rs.getInt("id"));
				pro.setName(rs.getString("name"));
				// pro.setProject(rs.getInt("d_id"));
				list.add(pro);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeAll(conn, stat, rs);
		}

		return list;
	}

	public boolean add(int depId, int proId) {
		Connection conn = null;
		Statement stat = null;
		int rs = 0;
		try {
			conn = getConnection();
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/company?characterEncoding=utf-8", "root",
					"123456");
			// 4:建立Statement sql语句执行器
			stat = conn.createStatement();
			// 5：执行sql语句并得到结果
			String sql = ("insert into r_dep_pro(d_id,p_id) values(" + depId + "," + proId + ")");
			rs = stat.executeUpdate(sql);
			// 6:对结果集进行处理

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeAll(conn, stat, null);
		}
		return rs > 0;
	}

	public boolean add(Connection conn, int depId, int proId) {

		Statement stat = null;
		int rs = 0;
		try {
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/company?characterEncoding=utf-8", "root",
					"123456");
			// 4:建立Statement sql语句执行器
			stat = conn.createStatement();
			// 5：执行sql语句并得到结果
			String sql = ("insert into r_dep_pro(d_id,p_id) values(" + depId + "," + proId + ")");
			rs = stat.executeUpdate(sql);
			// 6:对结果集进行处理

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeAll(null, stat, null);
		}
		return rs > 0;
	}

	public boolean addBatch(int depId, String[] proIds) {

		Connection conn = null;
		int rs = 1;
		try {

			conn = getConnection();
			conn.setAutoCommit(false);
			for (int i = 0; i < proIds.length; i++) {

				add(conn, depId, Integer.parseInt(proIds[i]));
			}
			conn.commit();
			conn.setAutoCommit(true);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			rs = 0;
		} finally {
			closeAll(conn, null, null);
		}
		return rs > 0;
	}

	public boolean delete(int depId, int proId) {

		Connection conn = null;
		Statement stat = null;
		int rs = 0;
		try {
			conn = getConnection();
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/company?characterEncoding=utf-8", "root",
					"123456");
			// 4:建立Statement sql语句执行器
			stat = conn.createStatement();
			// 5：执行sql语句并得到结果
			String sql = ("delete from r_dep_pro where d_id=" + depId + " and p_id=" + proId + " ");
			rs = stat.executeUpdate(sql);
			// 6:对结果集进行处理

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeAll(conn, stat, null);
		}
		return rs > 0;
	}

	public boolean delete(Connection conn, int depId, int proId) {
		Statement stat = null;
		int rs = 0;
		try {
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/company?characterEncoding=utf-8", "root",
					"123456");
			// 4:建立Statement sql语句执行器
			stat = conn.createStatement();
			// 5：执行sql语句并得到结果
			String sql = ("delete from r_dep_pro where d_id=" + depId + " and p_id=" + proId + " ");
			rs = stat.executeUpdate(sql);
			// 6:对结果集进行处理

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeAll(null, stat, null);
		}
		return rs > 0;
	}

	public boolean deleteBatch(int depId, String[] proIds) {

		Connection conn = null;
		int rs = 1;
		try {
			conn = getConnection();
			conn.setAutoCommit(false);
			for (int i = 0; i < proIds.length; i++) {

				delete(conn, depId, Integer.parseInt(proIds[i]));
			}
			conn.commit();
			conn.setAutoCommit(true);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			rs = 0;
		} finally {
			closeAll(conn, null, null);
		}
		return rs > 0;
	}

}
