package bs.web.filter;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import bs.domain.User;
import bs.system.AuthPath;

public class AuthFilter implements Filter {

	private List<AuthPath> authPaths;

	public void init(FilterConfig filterConfig) throws ServletException {
		authPaths = new ArrayList<AuthPath>();
		String xmlFile = AuthFilter.class.getResource("/authPath.xml")
				.getFile();

		try {
			FileInputStream input = new FileInputStream(xmlFile);
			// 1.获取xml解析器
			XmlPullParser parser = XmlPullParserFactory.newInstance()
					.newPullParser();
			parser.setInput(input, "utf-8");

			int type = parser.getEventType();// 获取当前的事件的类型
			while (type != XmlPullParser.END_DOCUMENT) { // 需要让pull解析器解析到文件的末尾
				if (type == XmlPullParser.START_TAG) {
					if ("authPath".equals(parser.getName())) {
						// parser.getAttributeValue(namespace, name);
						String path = parser.getAttributeValue("", "path");
						String action = parser.getAttributeValue("", "action");
						if (path == null || path.trim().length() == 0
								|| action == null
								|| action.trim().length() == 0) {
							throw new RuntimeException(
									"服务器错误 : authPath 配置文件错误(属性值不能为空)");
						}
						authPaths.add(new AuthPath(path, action));
					}
				}
				type = parser.next();
			}
			input.close();
		} catch (Exception e) {
			throw new RuntimeException("服务器错误 : " + e);
		}
	}

	public void doFilter(ServletRequest req, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		String path = request.getRequestURI();

		// 请求路径不是以Servlet结尾的,直接放行
		if (!path.endsWith("Servlet")) {
			chain.doFilter(request, response);
			return;
		}

		String action = request.getParameter("action");

		for (AuthPath authPath : authPaths) {
			if (path.endsWith(authPath.getPath())
					&& authPath.getAction().equals(action)) {
				User user = (User) request.getSession().getAttribute("user");
				if (user != null) {
					if (user.isAdmin()) {
						chain.doFilter(req, response);
					}
				}
				// 执行到此处说明权限不足
				response.setContentType("text/plain;charset=utf-8");
				response.getWriter().print("权限不足");
				return;
			}
		}
		chain.doFilter(req, response);
	}

	public void destroy() {

	}

}
