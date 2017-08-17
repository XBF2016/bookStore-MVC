package bs.web.servlet;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bs.domain.User;
import bs.service.CommonService;
import bs.utils.Conditions;
import bs.utils.Md5Utils;
import bs.utils.Utils;

//用于处理与用户有关的登录注册和注销操作
public class UserServlet extends HttpServlet {

	private CommonService commonService = new CommonService();

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 首先要获取动作的类型
		String action = request.getParameter("action");
		// 然后判断
		if ("register".equals(action)) {
			register(request, response);
		} else if ("login".equals(action)) {
			login(request, response);
		} else if ("logout".equals(action)) {
			logout(request, response);
		}

	}

	// 退出登录
	private void logout(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		request.getSession().setAttribute("user", null);
		response.sendRedirect(request.getContextPath() + "/index.jsp");
	}

	// 登录方法
	private void login(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 获取数据
		String userName = request.getParameter("userName");
		String password = request.getParameter("password");

		// 添加条件
		Conditions conditions = new Conditions();
		conditions.addConditions("userName", userName, false);
		conditions.addConditions("password", Md5Utils.Md5(password), false);
		// 使用service的条件查询方法查询
		List<User> users = commonService.queryByCondition(User.class,
				conditions);

		User user = Utils.getFirst(users);

		// 如果找不到
		if (user == null) {
			request.setAttribute("message", "用户名或密码错误");
			request.getRequestDispatcher("/login.jsp").forward(request,
					response);
			return;
		}

		// 登录并重定向到首页
		request.getSession().setAttribute("user", user);

		//
		if (user.isAdmin()) {
			response.sendRedirect(request.getContextPath()
					+ "/manager/index.jsp");
		} else {
			response.sendRedirect(request.getContextPath() + "/index.jsp");

		}

	}

	// 注册方法
	private void register(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// 获取数据
		String userName = request.getParameter("userName");
		String password = request.getParameter("password");
		String repassword = request.getParameter("repassword");

		// 进行有效性检查：非空检查，密码一致检查，用户名唯一性检查
		if (Utils.hasEmpty(userName, password, repassword)) {
			// 设置错误信息
			request.setAttribute("message", "用户名和密码不能为空");
			request.getRequestDispatcher("/register.jsp").forward(request,
					response);
		}
		if (!password.equals(repassword)) {
			// 设置错误信息
			request.setAttribute("message", "两次密码输入不一致");
			request.getRequestDispatcher("/register.jsp").forward(request,
					response);
		}
		if (!commonService.checkUnique(User.class, "userName", userName)) {
			// 设置错误信息
			request.setAttribute("message", "此用户名：" + userName + "已经存在");
			request.getRequestDispatcher("/register.jsp").forward(request,
					response);
		}

		// 检查完后，保存用户到数据库
		User user = new User();
		user.setUserName(userName);
		user.setAdmin(false);
		user.setId(UUID.randomUUID().toString());
		user.setPassword(Md5Utils.Md5(password));

		commonService.add(user);

		// 进行登录
		request.getSession().setAttribute("user", user);
		response.sendRedirect(request.getContextPath() + "/index.jsp");

	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
