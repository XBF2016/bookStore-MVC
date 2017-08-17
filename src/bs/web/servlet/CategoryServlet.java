package bs.web.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bs.domain.Category;
import bs.service.CommonService;
import bs.utils.Utils;

//图书类别的增删改查操作
public class CategoryServlet extends HttpServlet {

	// 业务对象
	CommonService commonService = new CommonService();

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 首先判断操作类型
		String action = request.getParameter("action");
		if ("add".equals(action)) {
			add(request, response);
		} else if ("delete".equals(action)) {
			delete(request, response);
		} else if ("update".equals(action)) {
			update(request, response);
		} else if ("list".equals(action)) {
			list(request, response);
		}

	}

	// 查询方法
	private void list(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		List<Category> categoryList = commonService.queryAll(Category.class);

		request.setAttribute("categoryList", categoryList);

		// 不可以重定向，因为有request域对象存入
		request.getRequestDispatcher("/manager/categoryList.jsp").forward(
				request, response);
	}

	// 更新方法
	private void update(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 获取id
		String id = request.getParameter("id");
		// 获取新的类名
		String categoryName = request.getParameter("categoryName");
		// 合法性判断
		if (Utils.hasEmpty(id, categoryName)) {
			request.setAttribute("message", "类名不能为空");
			list(request, response);
			return;
		}

		Category category = new Category();
		category.setId(id);
		category.setCategoryName(categoryName);

		commonService.update(category);

		// 更新数据字典
		List<Category> categoryList = (List<Category>) request.getSession()
				.getServletContext().getAttribute("categoryList");
		if (categoryList != null) {
			for (Category category2 : categoryList) {
				if (category2.equals(category)) {
					// 如果找到就把新的给旧的
					category2.setCategoryName(category.getCategoryName());
					break;
				}
			}
		}
		request.getSession().getServletContext().setAttribute("categoryList",
				categoryList);

		request.setAttribute("message", "修改图书分类：" + categoryName + "成功");
		list(request, response);
	}

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

		// 检查完后进行删除
		Category category = new Category();
		category.setId(id);
		commonService.delete(category);

		// 更新数据字典
		List<Category> categoryList = (List<Category>) request.getSession()
				.getServletContext().getAttribute("categoryList");
		if (categoryList != null) {
			categoryList.remove(category);
		}
		request.getSession().getServletContext().setAttribute("categoryList",
				categoryList);

		request.setAttribute("message", "删除图书分类成功");
		list(request, response);
	}

	// 图书类别的添加方法
	private void add(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 获取类名名称
		String categoryName = request.getParameter("categoryName");
		// 合法性检查
		// 非空检查
		if (Utils.hasEmpty(categoryName)) {
			request.setAttribute("message", "图书类别不能为空");
			request.getRequestDispatcher("").forward(request, response);
			return;
		}
		// 唯一性判断
		if (!commonService.checkUnique(Category.class, "categoryName",
				categoryName)) {
			request.setAttribute("message", "图书类别已经存在");
			request.getRequestDispatcher("").forward(request, response);
			return;
		}

		// 检查完后进行添加
		Category category = new Category();
		category.setCategoryName(categoryName);
		category.setId(UUID.randomUUID().toString());
		commonService.add(category);

		// 更新数据字典
		List<Category> categoryList = (List<Category>) request.getSession()
				.getServletContext().getAttribute("categoryList");
		if (categoryList != null) {
			categoryList.add(category);
		} else {
			categoryList = new ArrayList<Category>();
			categoryList.add(category);
		}
		request.getSession().getServletContext().setAttribute("categoryList",
				categoryList);

		// 添加完后跳转
		request.setAttribute("message", "添加图书分类:" + categoryName + "成功");
		request.getRequestDispatcher("/manager/categoryAdd.jsp").forward(
				request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
