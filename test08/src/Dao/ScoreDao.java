package Dao;

import java.security.interfaces.RSAKey;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import entity.Department;
import entity.Employee;
import entity.Project;
import entity.Score;
import util.Grade;;

public class ScoreDao extends BaseDao {

	public List<Score> search(int begin, int size) {
		List<Score> list = new ArrayList<Score>();
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
			String sql = "select * from v_emp_sc limit " + begin + "," + size;
			rs = stat.executeQuery(sql);
			// 6:对结果集进行处理
			while (rs.next()) {
				Score sc = new Score();
				sc.setId(rs.getInt("s_id"));

				Employee emp = new Employee();
				emp.setId(rs.getInt("e_id"));
				emp.setName(rs.getString("e_name"));

				Department dep = new Department();
				dep.setId(rs.getInt("d_id"));
				dep.setName(rs.getString("d_Name"));

				emp.setDep(dep);
				sc.setEmp(emp);

				Project pro = new Project();
				pro.setId(rs.getInt("p_id"));
				pro.setName(rs.getString("p_name"));

				sc.setPro(pro);
				sc.setValue((Integer) rs.getObject("value"));			
				Grade g = Grade.getGrade(rs.getString("grade"));
				sc.setGrade(g);

				list.add(sc);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeAll(conn, stat, rs);
		}

		return list;
	}

	public int searchCount() {
		int count = 0;
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
			String sql = "select count(*) from v_emp_sc";
			rs = stat.executeQuery(sql);
			// 6:对结果集进行处理
			if (rs.next()) {
				count = rs.getInt(1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeAll(conn, stat, rs);
		}

		return count;
	}

	public List<Score> searchByCondition(Score condition) {
		List<Score> list = new ArrayList<Score>();
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
			String where = "where 1=1";
			if (!condition.getEmp().getName().equals("")) {
				where += " and e_name ='" + condition.getEmp().getName() + "' ";
			}
			if (!condition.getDep().getName().equals("")) {
				where += " and d_name ='" + condition.getDep().getName() + "' ";
			}
			if (!condition.getPro().getName().equals("")) {
				where += " and p_name ='" + condition.getPro().getName() + "' ";
			}
			if (!condition.getGrade().equals("")) {
				where += " and grade ='" + condition.getGrade() + "'";
			}
			String sql = "select * from v_emp_sc " + where;
			rs = stat.executeQuery(sql);
			// 6:对结果集进行处理
			while (rs.next()) {
				Score sc = new Score();
				sc.setId(rs.getInt("s_id"));

				Employee emp = new Employee();
				emp.setId(rs.getInt("e_id"));
				emp.setName(rs.getString("e_name"));

				Department dep = new Department();
				dep.setId(rs.getInt("d_id"));
				dep.setName(rs.getString("d_Name"));

				emp.setDep(dep);
				sc.setEmp(emp);

				Project pro = new Project();
				pro.setId(rs.getInt("p_id"));
				pro.setName(rs.getString("p_name"));
				sc.setPro(pro);
				sc.setValue((Integer) rs.getObject("value"));
				Grade g = Grade.getGrade(rs.getString("grade"));
				sc.setGrade(g);

				list.add(sc);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeAll(conn, stat, rs);
		}

		return list;
	}


	public boolean add(Score sco) {
		int rs = 0;
		Connection conn = null;
		PreparedStatement pstat = null;
		// Statement stat = null;
		try {
			conn = getConnection();

			String sql = "insert into score(e_id,p_id,value) values (?,?,?)";
			// stat = conn.createStatement();
			pstat = conn.prepareStatement(sql);
			pstat.setInt(1, sco.getEmp().getId());
			pstat.setInt(2, sco.getPro().getId());
			pstat.setInt(3, sco.getValue());
			rs = pstat.executeUpdate();
			// rs = stat.executeUpdate(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeAll(conn, pstat, null);
		}
		return rs > 0;
	}

	public boolean update(Score sco) {
		int rs = 0;
		Connection conn = null;
		PreparedStatement pstat = null;

		try {
			conn = getConnection();

			String sql = "update score set value=? where id =?";
			pstat = conn.prepareStatement(sql);
			pstat.setInt(1, sco.getValue());
			pstat.setInt(2, sco.getId());
			rs = pstat.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeAll(conn, pstat, null);
		}
		return rs > 0;
	}
}
