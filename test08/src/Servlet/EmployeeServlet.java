package Servlet;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import Dao.DepartmentDao;
import Dao.EmployeeDao;
import entity.Department;
import entity.Employee;
import util.Constant;
import util.Pagination;

public class EmployeeServlet extends HttpServlet {

	private static final String path = "WEB-INF/employee/";

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
		} else if ("showAdd2".equals(type)) {
			showAdd2(request, response);
		} else if ("add".equals(type)) {
			add(request, response);
		} else if ("showmodify".equals(type)) {
			showmodify(request, response);
		} else if ("modify".equals(type)) {
			modify(request, response);
		} else if ("delete".equals(type)) {
			delete(request, response);
		} else if ("deleteBatch".equals(type)) {
			deleteBatch(request, response);
		} else if ("showmodifyBatch1".equals(type)) {
			showmodifyBatch1(request, response);
		} else if ("modifyBatch1".equals(type)) {
			modifyBatch1(request, response);
		} else if ("showmodifyBatch2".equals(type)) {
			showmodifyBatch2(request, response);
		} else if ("modifyBatch2".equals(type)) {
			modifyBatch2(request, response);
		}else if ("upload".equals(type)) {
			upload(request, response);
		}else if ("add2".equals(type)) {
			add2(request, response);
		}
	}

	// public void show(HttpServletRequest request, HttpServletResponse response) {
	// try {
	//
	// EmployeeDao empDao = new EmployeeDao();
	// int ye = 1;
	// if (request.getParameter("ye") != null) {
	// ye = Integer.parseInt(request.getParameter("ye"));
	// }
	// int count = empDao.searchCount();
	//
	// Pagination p = new Pagination(ye, count, Constant.EMP_NUM_IN_PAGE,
	// Constant.EMP_NUM_OF_PAGE);
	//
	// List<Employee> list = empDao.search(p.getBegin(), Constant.EMP_NUM_IN_PAGE);
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
			EmployeeDao empDao = new EmployeeDao();
			DepartmentDao depDao = new DepartmentDao();
			Employee condition = new Employee();
			String name = request.getParameter("name");
			String sex = request.getParameter("sex");
			int age = -1;
			if (request.getParameter("age") != null && !"".equals(request.getParameter("age"))) {
				age = Integer.parseInt(request.getParameter("age"));
			}

			int depId = -1;
			if (request.getParameter("depId") != null && !"".equals(request.getParameter("depId"))) {
				depId = Integer.parseInt(request.getParameter("depId"));
			}

			condition.setName(name);
			condition.setSex(sex);
			condition.setAge(age);
			Department dep = new Department();
			dep.setId(depId);
			condition.setDep(dep);

			int ye = 1;
			if (request.getParameter("ye") != null) {
				ye = Integer.parseInt(request.getParameter("ye"));
			}
			int count = empDao.searchCount(condition);

			Pagination p = new Pagination(ye, count, Constant.EMP_NUM_IN_PAGE, Constant.EMP_NUM_OF_PAGE);
			List<Employee> list = empDao.search(condition, p.getBegin(), Constant.EMP_NUM_IN_PAGE);
			List<Department> depList = depDao.search();
			request.setAttribute("p", p);
			request.setAttribute("depList", depList);
			request.setAttribute("c", condition);
			request.setAttribute("list", list);
			request.getRequestDispatcher(path + "list.jsp").forward(request, response);
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void showAdd(HttpServletRequest request, HttpServletResponse response) {
		try {

			DepartmentDao depDao = new DepartmentDao();
			List<Department> depList = depDao.search();
			request.setAttribute("depList", depList);
			request.getRequestDispatcher(path + "add.jsp").forward(request, response);
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void showAdd2(HttpServletRequest request, HttpServletResponse response) {
		try {

			DepartmentDao depDao = new DepartmentDao();
			List<Department> depList = depDao.search();
			request.setAttribute("depList", depList);
			request.getRequestDispatcher(path + "add2.jsp").forward(request, response);
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
			String name = "";
			String sex = "";
			String age = "";
			String depId = "";
			String path = "d:/test08/pic";
			FileItemFactory factory = new DiskFileItemFactory();
			ServletFileUpload upload = new ServletFileUpload(factory);
			List<FileItem> items = upload.parseRequest(request);
			String photoName = "";
			for (int i = 0; i < items.size(); i++) {
				FileItem item = items.get(i);
				if (item.getFieldName().equals("photo")) {
					UUID uuid = UUID.randomUUID();
					String houzhui = item.getName().substring(item.getName().lastIndexOf("."));
					photoName = uuid.toString() + houzhui;
					File savedFile = new File(path, photoName);
					item.write(savedFile);
				} else if (item.getFieldName().equals("name")) {
					name = new String(item.getString().getBytes("ISO-8859-1"), "utf-8");
				} else if (item.getFieldName().equals("sex")) {
					sex = new String(item.getString().getBytes("ISO-8859-1"), "utf-8");
				} else if (item.getFieldName().equals("age")) {
					age = new String(item.getString());
				} else if (item.getFieldName().equals("depId")) {
					depId = new String(item.getString());
				}
			}

			Employee emp = new Employee();
			Department dep = new Department();
			if (!"".equals(depId)) {
				dep.setId(Integer.parseInt(depId));
			}
			emp.setDep(dep);
			emp.setName(name);
			emp.setSex(sex);
			emp.setAge(Integer.parseInt(age));
			emp.setPhoto(photoName);
			EmployeeDao empDao = new EmployeeDao();
			boolean flag = empDao.add(emp);
			// show(request, response);
			response.sendRedirect("emp");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileUploadException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	

	public void add2(HttpServletRequest request, HttpServletResponse response) {

		try {
			Employee emp = new Employee();
			String name = request.getParameter("name");
			String sex = request.getParameter("sex");
			int age = Integer.parseInt(request.getParameter("age"));
			Integer depId = null;
			if (!"".equals(request.getParameter("depId"))) {
				depId = Integer.parseInt(request.getParameter("depId"));
			}
			String photo = request.getParameter("picture");
			emp.setName(name);
			emp.setSex(sex);
			emp.setAge(age);
			emp.setPhoto(photo);
			Department dep = new Department();
			dep.setId(depId);
			emp.setDep(dep);

			EmployeeDao empDao = new EmployeeDao();
			boolean flag = empDao.add(emp);
			// show(request, response);
			response.sendRedirect("emp");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	

	public void upload(HttpServletRequest request, HttpServletResponse response) {

		try {

			String path = "d:/test08/pic";
			FileItemFactory factory = new DiskFileItemFactory();
			ServletFileUpload upload = new ServletFileUpload(factory);
			List<FileItem> items = upload.parseRequest(request);
			String photoName = "";
				FileItem item = items.get(0);
				if (item.getFieldName().equals("photo")) {
					UUID uuid = UUID.randomUUID();
					String houzhui = item.getName().substring(item.getName().lastIndexOf("."));
					photoName = uuid.toString() + houzhui;
					File savedFile = new File(path, photoName);
					item.write(savedFile);
			}
			PrintWriter out = response.getWriter();
			out.print(photoName);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileUploadException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void showmodify(HttpServletRequest request, HttpServletResponse response) {
		try {

			int id = Integer.parseInt(request.getParameter("id"));
			EmployeeDao empDao = new EmployeeDao();
			DepartmentDao depDao = new DepartmentDao();
			List<Department> depList = depDao.search();
			request.setAttribute("depList", depList);
			Employee emp = empDao.search(id);
			request.setAttribute("emp", emp);
			request.getRequestDispatcher(path + "modify.jsp").forward(request, response);
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
			Employee emp = new Employee();
			int id = Integer.parseInt(request.getParameter("id"));
			String name = request.getParameter("name");
			String sex = request.getParameter("sex");
			int age = Integer.parseInt(request.getParameter("age"));
			Integer depId = null;
			if (!"".equals(request.getParameter("depId"))) {
				depId = Integer.parseInt(request.getParameter("depId"));
			}
			emp.setId(id);
			emp.setName(name);
			emp.setSex(sex);
			emp.setAge(age);
			Department dep = new Department();
			dep.setId(depId);
			emp.setDep(dep);

			EmployeeDao empDao = new EmployeeDao();
			boolean flag = empDao.modify(emp);
			// show(request, response);
			response.sendRedirect("emp");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void delete(HttpServletRequest request, HttpServletResponse response) {
		try {
			int id = Integer.parseInt(request.getParameter("id"));
			EmployeeDao empDao = new EmployeeDao();
			boolean flag = empDao.delete(id);
			// show(request, response);
			response.sendRedirect("emp");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void deleteBatch(HttpServletRequest request, HttpServletResponse response) {
		try {
			String ids = request.getParameter("ids");
			EmployeeDao empDao = new EmployeeDao();
			boolean flag = empDao.deleteBatch(ids);
			// show(request, response);
			response.sendRedirect("emp");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void showmodifyBatch1(HttpServletRequest request, HttpServletResponse response) {
		try {

			String ids = request.getParameter("ids");
			EmployeeDao empDao = new EmployeeDao();
			List<Employee> list = empDao.search(ids);
			request.setAttribute("emp", list.get(0));
			request.setAttribute("ids", ids);
			request.getRequestDispatcher(path + "modifyBatch1.jsp").forward(request, response);
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void modifyBatch1(HttpServletRequest request, HttpServletResponse response) {
		try {
			Employee emp = new Employee();
			String ids = request.getParameter("ids");
			String name = request.getParameter("name");
			String sex = request.getParameter("sex");
			int age = Integer.parseInt(request.getParameter("age"));

			emp.setName(name);
			emp.setSex(sex);
			emp.setAge(age);

			EmployeeDao empDao = new EmployeeDao();
			boolean flag = empDao.modifyBatch1(ids, emp);
			// show(request, response);
			response.sendRedirect("emp");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void showmodifyBatch2(HttpServletRequest request, HttpServletResponse response) {
		try {

			String ids = request.getParameter("ids");
			EmployeeDao empDao = new EmployeeDao();
			List<Employee> list = empDao.search(ids);
			request.setAttribute("list", list);
			request.getRequestDispatcher(path + "modifyBatch2.jsp").forward(request, response);
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void modifyBatch2(HttpServletRequest request, HttpServletResponse response) {
		try {

			String emps = request.getParameter("emps");
			String[] array = emps.split(";");
			List<Employee> list = new ArrayList<>();
			for (int i = 0; i < array.length; i++) {
				String[] temp = array[i].split(",");
				Employee emp = new Employee();
				emp.setId(Integer.parseInt(temp[0]));
				emp.setName(temp[1]);
				emp.setSex(temp[2]);
				emp.setAge(Integer.parseInt(temp[3]));
				list.add(emp);
			}
			EmployeeDao empDao = new EmployeeDao();
			boolean flag = empDao.modifyBatch2(list);
			response.sendRedirect("emp");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) {
		doGet(request, response);
	}

}