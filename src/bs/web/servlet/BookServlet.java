package bs.web.servlet;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bs.domain.Book;
import bs.domain.User;
import bs.service.CommonService;
import bs.utils.Conditions;
import bs.utils.Page;
import bs.utils.Utils;
import bs.utils.WebUtils;

public class BookServlet extends HttpServlet {
	// 图书管理

	private CommonService commonService = new CommonService();

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 先获取操作类型
		String action = request.getParameter("action");
		if ("add".equals(action)) {
			add(request, response);
		} else if ("delete".equals(action)) {
			delete(request, response);
		} else if ("update".equals(action)) {
			update(request, response);
		} else if ("list".equals(action)) {
			list(request, response);
		} else if ("detail".equals(action)) {
			detail(request, response);
		} else if ("query".equals(action)) {
			query(request, response);
		} else if ("page".equals(action)) {
			page(request, response);
		} else if ("pageQuery".equals(action)) {
			pageQuery(request, response);
		}
	}

	//
	private void pageQuery(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String bookName = request.getParameter("bookName");
		String categoryId = request.getParameter("categoryId");
		String author = request.getParameter("author");

		String pageIndexStr = request.getParameter("pageIndex");
		String pageSizeStr = request.getParameter("pageSize");

		int pageIndex = 1;
		int pageSize = 4;

		try {
			pageIndex = Integer.parseInt(pageIndexStr);
		} catch (Exception e) {
		}
		try {
			pageSize = Integer.parseInt(pageSizeStr);
		} catch (Exception e) {
		}

		Conditions conditions = new Conditions();
		conditions.addConditions("bookName", bookName, true);
		conditions.addConditions("categoryId", categoryId, false);
		conditions.addConditions("author", author, true);

		Page<Book> page = new Page<Book>(pageIndex, pageSize);

		commonService.pageQuery(Book.class, page, conditions);

		request.setAttribute("bookList", page.getItems());
		request.setAttribute("beanPage", page);
		request.setAttribute("queryString", conditions.createURLQueryString());
		request.setAttribute("bookName", bookName);
		request.setAttribute("categoryId", categoryId);
		request.setAttribute("author", author);

		request.getRequestDispatcher("/client/index.jsp").forward(request,
				response);
	}

	// 分页查询
	private void page(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 获取当前的页码和页面大小
		String pageIndexString = request.getParameter("pageIndex");
		String pageSizeString = request.getParameter("pageSize");

		int pageIndex = 1;
		int pageSize = 4;

		try {
			pageIndex = Integer.parseInt(pageIndexString);
		} catch (Exception e) {
		}
		try {
			pageSize = Integer.parseInt(pageSizeString);
		} catch (Exception e) {
		}

		// 创建Page对象并设置属性值
		Page<Book> page = new Page<Book>(pageIndex, pageSize);
		// 将分页查询的结果List添加
		commonService.page(Book.class, page);
		request.setAttribute("bookList", page.getItems());
		request.setAttribute("beanPage", page);

		request.getRequestDispatcher("/client/index.jsp").forward(request,
				response);

	}

	// 首页的图书搜索方法
	private void query(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// 首先获取参数
		String bookName = request.getParameter("bookName");
		String categoryId = request.getParameter("categoryId");
		String author = request.getParameter("author");

		// 添加条件
		Conditions conditions = new Conditions();
		conditions.addConditions("bookName", bookName, true);
		conditions.addConditions("categoryId", categoryId, false);
		conditions.addConditions("author", author, true);

		List<Book> bookList = commonService.queryByCondition(Book.class,
				conditions);

		// 存入web域
		request.setAttribute("bookList", bookList);
		// 为了能够支持回显，把之前的参数也存入web域
		request.setAttribute("bookName", bookName);
		request.setAttribute("categoryId", categoryId);
		request.setAttribute("author", author);

		request.getRequestDispatcher("/client/index.jsp").forward(request,
				response);

	}

	// 查询详情
	private void detail(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 获取图书id
		String id = request.getParameter("id");
		// 根据id查询
		Book book = commonService.queryById(Book.class, id);
		// 把结果存入request中
		request.setAttribute("book", book);
		System.out.println(book);
		//
		request.getRequestDispatcher("manager/bookDetail.jsp").forward(request,
				response);
	}

	// 查询方法
	private void list(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		List<Book> booksList = commonService.queryAll(Book.class);

		request.setAttribute("bookList", booksList);

		// 判断用户类型
		User user = (User) request.getSession().getAttribute("user");
		if (user != null && user.isAdmin()) {
			// 不可以重定向，因为有request域对象存入
			request.getRequestDispatcher("/manager/bookList.jsp").forward(
					request, response);
		}
		request.getRequestDispatcher("/client/index.jsp").forward(request,
				response);

	}

	// 更新方法
	private void update(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 获取id
		String id = request.getParameter("id");
		String author = request.getParameter("author");
		String priceStr = request.getParameter("price");
		String categoryId = request.getParameter("categoryId");
		String bookName = request.getParameter("bookName");
		// 合法性判断
		if (Utils.hasEmpty(id, author, priceStr, categoryId, bookName)) {
			request.setAttribute("message", "图书信息不能出现空");
			request.getRequestDispatcher("").forward(request, response);
			return;
		}
		if (commonService.checkUnique(Book.class, "id", id)) {
			request.setAttribute("message", "该图书不存在");
			request.getRequestDispatcher("").forward(request, response);
			return;
		}

		// 之所以不创建新的是因为图片路径不能变
		Book book = commonService.queryById(Book.class, id);
		book.setAuthor(author);
		book.setBookName(bookName);
		book.setCategoryId(categoryId);
		book.setId(id);
		// 将字符串的价格转换为double的价格
		book.setPrice(Double.parseDouble(priceStr));

		commonService.update(book);

		request.setAttribute("message", "修改图书成功");
		detail(request, response);
	}

	// 删除方法
	private void delete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 获取id
		String id = request.getParameter("id");
		// 进行合法性检查
		// 非空检查
		if (Utils.hasEmpty(id)) {
			request.setAttribute("message", "id不能为空");
			request.getRequestDispatcher("").forward(request, response);
			return;
		}
		// 唯一性判断
		if (commonService.checkUnique(Book.class, "id", id)) {
			request.setAttribute("message", "该图书不存在");
			request.getRequestDispatcher("").forward(request, response);
			return;
		}

		// 检查完后进行删除
		// 还要把服务器的图片文件删除
		Book books = commonService.queryById(Book.class, id);
		String imagePath = books.getImagePath();
		imagePath = request.getSession().getServletContext().getRealPath(
				imagePath);
		java.io.File file = new java.io.File(imagePath);
		if (file.exists()) {
			file.delete();
		}
		commonService.delete(books);
		request.setAttribute("message", "删除图书成功");
		list(request, response);
	}

	// 图书的添加操作
	private void add(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		// 先进行文件上传
		Book book = WebUtils.upload(request, Book.class);
		// 然后还要添加id
		book.setId(UUID.randomUUID().toString());
		// 添加
		commonService.add(book);
		request.setAttribute("message", "添加图书:" + book.getBookName() + "成功");
		request.getRequestDispatcher("/manager/bookAdd.jsp").forward(request,
				response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
