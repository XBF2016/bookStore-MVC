package bs.web.listener;

import java.util.List;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import bs.domain.Category;
import bs.service.CommonService;

public class InitListener implements ServletContextListener {

	public void contextDestroyed(ServletContextEvent sce) {

	}

	// 当服务器一启动就添加数据字典
	public void contextInitialized(ServletContextEvent sce) {

		CommonService commonService = new CommonService();
		// 查询所有的图书分类
		List<Category> categoryList = commonService.queryAll(Category.class);
		sce.getServletContext().setAttribute("categoryList", categoryList);
	}

}
