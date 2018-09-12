package Servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Dao.DepartmentDao;
import Dao.ProjectDao;
import Dao.ScoreDao;
import Dao.UserDao;
import entity.Department;
import entity.Employee;
import entity.Project;
import entity.Score;
import util.Constant;
import util.Grade;
import util.Pagination;

public class ScoreServlet extends HttpServlet {

	private static final String path = "WEB-INF/score/";

	public void doGet(HttpServletRequest request, HttpServletResponse response) {
		String type = request.getParameter("type");
		if (type == null) {
			search(request, response);
		} else if ("manage".equals(type)) {
			manage(request, response);
		} else if ("input".equals(type)) {
			input(request, response);
		}

	}

	public void basic(HttpServletRequest request, HttpServletResponse response) {
	
		ScoreDao scDao = new ScoreDao();
		DepartmentDao depDao = new DepartmentDao();
		ProjectDao proDao = new ProjectDao();
		Score condition = new Score();
		Department dep = new Department();
		Employee emp = new Employee();
		emp.setDep(dep);
		condition.setEmp(emp);
		int ye = 1;
		if (request.getParameter("ye") != null) {
			ye = Integer.parseInt(request.getParameter("ye"));
		}
		int count = scDao.searchCount();

		Pagination p = new Pagination(ye, count, Constant.EMP_NUM_IN_PAGE, Constant.EMP_NUM_OF_PAGE);
		List<Score> list = scDao.search(p.getBegin(), Constant.EMP_NUM_IN_PAGE);
		List<Department> depList = depDao.search();
		List<Project> proList = proDao.search();
		request.setAttribute("p", p);
		request.setAttribute("list", list);
		request.setAttribute("depList", depList);
		request.setAttribute("proList", proList);
		request.setAttribute("c", condition);
		
		


	}

	private void search(HttpServletRequest request, HttpServletResponse response) {
		try {
			basic(request, response);
			request.getRequestDispatcher(path + "list.jsp").forward(request, response);
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		;

	}

	private void manage(HttpServletRequest request, HttpServletResponse response) {
		try {

			basic(request, response);
			request.getRequestDispatcher(path + "manage.jsp").forward(request, response);
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		;
	}

	private void input(HttpServletRequest request, HttpServletResponse response) {
		int empId=Integer.parseInt(request.getParameter("empid"));
		int proId=Integer.parseInt(request.getParameter("proid"));
		int value=Integer.parseInt(request.getParameter("value"));
		Score score = new Score();
		Employee emp = new Employee();
		emp.setId(empId);
		Project pro = new Project();
		pro.setId(proId);
		score.setEmp(emp);
		score.setPro(pro);
		score.setValue(value);
		
		ScoreDao scDao = new ScoreDao();
		scDao.add(score);
		

	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) {
		doGet(request, response);
	}

}
