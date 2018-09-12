package Servlet;

import java.io.IOException;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.websocket.Session;

import com.mysql.jdbc.ResultSetInternalMethods;
import com.sun.org.apache.xerces.internal.impl.dv.ValidatedInfo;
import com.sun.prism.Image;

import Dao.UserDao;
import entity.RandomNumber;
import entity.User;
import entity.ValidateCode;
import util.AppMD5Util;

public class UserServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) {
		String type = request.getParameter("type");
		if (type == null) {
			showRegister(request, response);
		} else if ("showLogin".equals(type)) {
			showLogin(request, response);
		} else if ("doLogin".equals(type)) {
			doLogin(request, response);
		} else if ("randomImage".equals(type)) {
			randomImage(request, response);
		} else if ("doRegister".equals(type)) {
			doRegister(request, response);
		}
	}

	private void doRegister(HttpServletRequest request, HttpServletResponse response) {

		try {
			String username = request.getParameter("username");
			String password = request.getParameter("password");
			
			User user = new User();
			user.setUsername(username);
			user.setPassword(AppMD5Util.getMD5(password));
			UserDao userDao = new UserDao();
			
			boolean flag=userDao.add(user);
			if (flag) {
				response.sendRedirect("user?type=showLogin");
			}else {
				response.sendRedirect("user");

			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void showRegister(HttpServletRequest request, HttpServletResponse response) {
		try {
			request.getRequestDispatcher("WEB-INF/user/register.jsp").forward(request, response);
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void showLogin(HttpServletRequest request, HttpServletResponse response) {
		try {
			request.getRequestDispatcher("WEB-INF/user/login.jsp").forward(request, response);
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void doLogin(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		try {
			String username = request.getParameter("username");
			String password = request.getParameter("password");
			String random = request.getParameter("random");
			if (random.equals(session.getAttribute("rand"))) {

				User user = new User();
				user.setUsername(username);
				user.setPassword(AppMD5Util.getMD5(password));
				UserDao userDao = new UserDao();
				boolean flag = userDao.search(user);
				session.setAttribute("user", username);
				if (flag) {
					response.sendRedirect("index");
				} else {
					response.sendRedirect("user?type=showLogin");
				}
			} else {
				request.setAttribute("mes", "验证码错误");
				request.getRequestDispatcher("WEB-INF/user/login.jsp").forward(request, response);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void randomImage(HttpServletRequest request, HttpServletResponse response) {
		RandomNumber rn = new RandomNumber();
		try {

			response.setHeader("Pragma", "No-cache");
			response.setHeader("Cache-Control", "No-cache");
			response.setDateHeader("Expires", 0);

			ValidateCode vc = rn.generateImage();
			ServletOutputStream outputStream = response.getOutputStream();

			ImageIO.write(vc.getImage(), "JPEG", outputStream);

			outputStream.close();
			request.getSession().setAttribute("rand", vc.getRand());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) {
		doGet(request, response);
	}

}
