package bs.dao;

import java.sql.SQLException;

import bs.domain.User;

public class CategoryDao {
	// public int insert(Category category) throws SQLException {
	// // 查询语句
	// String sql = "insert into category(id,categoryName) values(?,?)";
	// // 参数列表
	// Object[] params = new Object[] { category.getId(),
	// category.getCategoryName() };
	// return JDBCUtils.updateOrInsertOrDelete(sql, params);
	// }

	public static void main(String[] args) throws SQLException {
		// Category category = new Category();
		// category.setId("0004");
		// category.setCategoryName("C#");

		User user = new User();
		user.setId("0001");
		user.setUserName("xbf");
		user.setPassword("123456");
		user.setAdmin(false);

		CommonDao commonDao = new CommonDao();
		// commonDao.insert(user);
		// commonDao.update(user);
		commonDao.delete(user);
	}
}
