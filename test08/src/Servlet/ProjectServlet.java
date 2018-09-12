package Servlet;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Dao.ProjectDao;
import entity.Project;
import util.Constant;
import util.Pagination;

public class ProjectServlet extends HttpServlet {

	private static final String path = "WEB-INF/project/";

	public void doGet(HttpServletRequest request, HttpServletResponse response) {
		try {
			request.setCharacterEncoding("utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String type = request.getParameter("type");
		if (type == null) {
			search(request, response);
		} else if ("showAdd".equals(type)) {
			showAdd(request, response);
		} else if ("add".equals(type)) {
			add(request, response);
		} else if ("showmodify".equals(type)) {
			showmodify(request, response);
		} else if ("modify".equals(type)) {
			modify(request, response);
		} else if ("delete".equals(type)) {
			delete(request, response);
		} 
	}

	// public void show(HttpServletRequest request, HttpServletResponse response) {
	// try {
	//
	// ProjectDao proDao = new ProjectDao();
	// int ye = 1;
	// if (request.getParameter("ye") != null) {
	// ye = Integer.parseInt(request.getParameter("ye"));
	// }
	// int count = proDao.searchCount();
	//
	// Pagination p = new Pagination(ye, count, Constant.pro_NUM_IN_PAGE,
	// Constant.pro_NUM_OF_PAGE);
	//
	// List<Project> list = proDao.search(p.getBegin(), Constant.pro_NUM_IN_PAGE);
	// request.setAttribute("p", p);
	// request.setAttribute("list", list);
	// request.getRequestDispatcher(path+"list.jsp").forward(request, response);
	// } catch (ServletException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// } catch (IOException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// }

	
	public void search(HttpServletRequest request, HttpServletResponse response) {
		try {
			ProjectDao proDao = new ProjectDao();
			Project condition = new Project();
			String name = request.getParameter("name");
			
			int empCount = -1;
			if (request.getParameter("empCount") != null && !"".equals(request.getParameter("empCount"))) {
				empCount = Integer.parseInt(request.getParameter("empCount"));
			}
			
			condition.setName(name);



			
			int ye = 1;
			if (request.getParameter("ye") != null) {
				ye = Integer.parseInt(request.getParameter("ye"));
			}
			int count = proDao.searchCount(condition);
			Pagination p = new Pagination(ye, count, Constant.EMP_NUM_IN_PAGE, Constant.EMP_NUM_OF_PAGE);
			List<Project> list = proDao.search(condition, p.getBegin(), Constant.EMP_NUM_IN_PAGE);
			request.setAttribute("p", p);

			request.setAttribute("c", condition);
			request.setAttribute("list", list);
			request.getRequestDispatcher(path+"list.jsp").forward(request, response);
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void showAdd(HttpServletRequest request, HttpServletResponse response) {
		try {
			request.getRequestDispatcher(path+"add.jsp").forward(request, response);
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void add(HttpServletRequest request, HttpServletResponse response) {

		try {
			Project pro = new Project();
			String name = request.getParameter("name");

			pro.setName(name);
			
			ProjectDao proDao = new ProjectDao();
			boolean flag = proDao.add(pro);
			// show(request, response);
			response.sendRedirect("pro");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void showmodify(HttpServletRequest request, HttpServletResponse response) {
		try {

			int id = Integer.parseInt(request.getParameter("id"));
			ProjectDao proDao = new ProjectDao();
			Project pro = proDao.search(id);
			request.setAttribute("pro", pro);
			request.getRequestDispatcher(path+"modify.jsp").forward(request, response);
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void modify(HttpServletRequest request, HttpServletResponse response) {
		try {
			Project pro = new Project();
			int id = Integer.parseInt(request.getParameter("id"));
			String name = request.getParameter("name");
			
			pro.setId(id);
			pro.setName(name);

			ProjectDao proDao = new ProjectDao();
			boolean flag = proDao.modify(pro);
			// show(request, response);
			response.sendRedirect("pro");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void delete(HttpServletRequest request, HttpServletResponse response) {
		try {
			int id = Integer.parseInt(request.getParameter("id"));
			ProjectDao proDao = new ProjectDao();
			boolean flag = proDao.delete(id);
			// show(request, response);
			response.sendRedirect("pro");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	

	public void doPost(HttpServletRequest request, HttpServletResponse response) {
		doGet(request, response);
	}

}