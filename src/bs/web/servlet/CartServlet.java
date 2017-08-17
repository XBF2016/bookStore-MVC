package bs.web.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bs.domain.Book;
import bs.domain.Cart;
import bs.service.CommonService;

//购物车操作
public class CartServlet extends HttpServlet {

	private CommonService commonService = new CommonService();

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 先获取操作类型
		String action = request.getParameter("action");
		// 判断
		if ("add".equals(action)) {
			add(request, response);
		} else if ("update".equals(action)) {
			update(request, response);
		} else if ("delete".equals(action)) {
			delete(request, response);
		}
	}

	private void delete(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		// 首先要获取要更改的图书的id
		String bookId = request.getParameter("bookId");

		// 拿到购物车项，不用判断是否存在，因为是修改
		Cart cart = (Cart) request.getSession().getAttribute("cart");

		cart.delete(bookId);
		response
				.sendRedirect(request.getContextPath() + "/client/cartList.jsp");
	}

	// 更改数量
	private void update(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		// 首先要获取要更改的图书的id和数量
		String bookId = request.getParameter("bookId");
		String bookCountString = request.getParameter("bookCount");
		// 转换数据类型
		int bookCount = Integer.parseInt(bookCountString);

		// 拿到购物车项，不用判断是否存在，因为是修改
		Cart cart = (Cart) request.getSession().getAttribute("cart");

		cart.update(bookId, bookCount);
		response
				.sendRedirect(request.getContextPath() + "/client/cartList.jsp");
	}

	// 购物车的添加
	private void add(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		// 先获取要添加的图书的id
		String id = request.getParameter("id");
		// 合法性检查
		// if (Utils.hasEmpty(id)) {
		// request.
		// }
		// 通过id查询数据库获取Books对象
		Book books = commonService.queryById(Book.class, id);

		// 然后从session中获取购物车对象
		Cart cart = (Cart) request.getSession().getAttribute("cart");
		// 有可能还没有Cart对象，所以要进行判断
		if (cart == null) {
			cart = new Cart();
			// 设置
			request.getSession().setAttribute("cart", cart);
		}

		// 添加
		cart.buy(books);

		response
				.sendRedirect(request.getContextPath() + "/client/cartList.jsp");
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
