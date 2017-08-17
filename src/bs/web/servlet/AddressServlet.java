package bs.web.servlet;

import java.io.IOException;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bs.domain.Address;
import bs.domain.User;
import bs.service.CommonService;
import bs.utils.Utils;

//地址的添加
public class AddressServlet extends HttpServlet {

	private CommonService commonService = new CommonService();

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 先获取操作类型
		String action = request.getParameter("action");
		// 判断
		if ("add".equals(action)) {
			add(request, response);
		}

	}

	// 地址的添加方法
	private void add(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 获取各个参数
		String location = request.getParameter("location");
		String userName = request.getParameter("userName");
		String phone = request.getParameter("phone");
		// 合法性检查
		if (Utils.hasEmpty(location, userName, phone)) {
			request.setAttribute("message", "地址、收件人或者手机号不能为空 ");
			request.getRequestDispatcher("").forward(request, response);
			return;
		}
		// 手机号检查
		if (phone.length() != 11) {
			request.setAttribute("message", "手机号格式不正确");
			request.getRequestDispatcher("").forward(request, response);
			return;
		}

		// 从session中获取已经登录的用户
		User user = (User) request.getSession().getAttribute("user");

		//
		Address address = new Address();
		address.setId(UUID.randomUUID().toString());
		address.setLocation(location);
		address.setPhone(phone);
		address.setUserName(userName);
		address.setUserId(user.getId());

		//
		commonService.add(address);

		response.sendRedirect(request.getContextPath()
				+ "/OrderServlet?action=create");
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
