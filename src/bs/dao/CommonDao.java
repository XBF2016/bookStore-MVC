package bs.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import bs.domain.Category;
import bs.utils.Conditions;
import bs.utils.JDBCUtils;
import bs.utils.Page;
import bs.utils.SqlAndParams;
import bs.utils.SqlAndParamsFactory;

public class CommonDao implements Dao {

	public static void main(String[] args) throws SQLException {
		// Conditions conditions = new Conditions();
		// conditions.addConditions("username", "ad", true);
		CommonDao commonDao = new CommonDao();
		// List list = commonDao.query(User.class, conditions);
		// System.out.println(list);
		// System.out.println(commonDao.queryById(User.class, "001"));
		System.out.println(commonDao.queryAll(Category.class));
	}

	// 利用query实现queryAll，它返回一个List
	public <T> List<T> queryAll(Class<T> clazz) throws SQLException {
		// 虽然不需要条件，打算还是要声明Conditions对象，否则会query方法中会出现使用null错误
		Conditions conditions = new Conditions();
		List<T> list = query(clazz, conditions);
		if (list != null || list.size() > 0) {
			return list;
		}
		return null;
	}

	// 利用query实现queryAll，它返回一个List
	public <T> List<T> queryAll(Connection connection, Class<T> clazz)
			throws SQLException {
		// 虽然不需要条件，打算还是要声明Conditions对象，否则会query方法中会出现使用null错误
		Conditions conditions = new Conditions();
		List<T> list = query(connection, clazz, conditions);
		if (list != null || list.size() > 0) {
			return list;
		}
		return null;
	}

	// 利用query实现queryById，它只需返回一个对象
	public <T> T queryById(Class<T> clazz, Object idValue) throws SQLException {
		Conditions conditions = new Conditions();
		conditions.addConditions("id", idValue, false);
		try {
			List<T> list = query(clazz, conditions);
			// 如果查询的结果不为null或空就返回第一个元素
			if (list != null || list.size() > 0) {
				return list.get(0);
			}
		} catch (Exception e) {
			throw new RuntimeException("服务器出错：" + e);
		}
		return null;
	}

	// 利用query实现queryById，它只需返回一个对象
	public <T> T queryById(Connection connection, Class<T> clazz, Object idValue)
			throws SQLException {
		Conditions conditions = new Conditions();
		conditions.addConditions("id", idValue, false);
		try {
			List<T> list = query(connection, clazz, conditions);
			// 如果查询的结果不为null或空就返回第一个元素
			if (list != null || list.size() > 0) {
				return list.get(0);
			}
		} catch (Exception e) {
			throw new RuntimeException("服务器出错：" + e);
		}
		return null;
	}

	// 通用条件查询方法
	public <T> List<T> query(Class<T> clazz, Conditions conditions)
			throws SQLException {
		SqlAndParams sqlAndParams = SqlAndParamsFactory.forQuery(clazz,
				conditions);
		return JDBCUtils.query(sqlAndParams.getSql(), sqlAndParams.getParams(),
				clazz);
	}

	// 通用条件查询方法
	public <T> List<T> query(Connection connection, Class<T> clazz,
			Conditions conditions) throws SQLException {
		SqlAndParams sqlAndParams = SqlAndParamsFactory.forQuery(clazz,
				conditions);
		return JDBCUtils.query(connection, sqlAndParams.getSql(), sqlAndParams
				.getParams(), clazz);
	}

	// 通用update方法
	public <T> int update(T t) throws SQLException {
		SqlAndParams sqlAndParams = SqlAndParamsFactory.forUpdate(t);
		return JDBCUtils.updateOrInsertOrDelete(sqlAndParams.getSql(),
				sqlAndParams.getParams());

	}

	// 通用update方法
	public <T> int update(Connection connection, T t) throws SQLException {
		SqlAndParams sqlAndParams = SqlAndParamsFactory.forUpdate(t);
		return JDBCUtils.updateOrInsertOrDelete(connection, sqlAndParams
				.getSql(), sqlAndParams.getParams());

	}

	// 通用insert方法
	public <T> int insert(T t) throws SQLException {
		SqlAndParams sqlAndParams = SqlAndParamsFactory.forInsert(t);
		return JDBCUtils.updateOrInsertOrDelete(sqlAndParams.getSql(),
				sqlAndParams.getParams());
	}

	// 通用insert方法
	public <T> int insert(Connection connection, T t) throws SQLException {
		SqlAndParams sqlAndParams = SqlAndParamsFactory.forInsert(t);
		return JDBCUtils.updateOrInsertOrDelete(connection, sqlAndParams
				.getSql(), sqlAndParams.getParams());
	}

	// 通用delete方法
	public <T> int delete(T t) throws SQLException {
		SqlAndParams sqlAndParams = SqlAndParamsFactory.forDelete(t);
		return JDBCUtils.updateOrInsertOrDelete(sqlAndParams.getSql(),
				sqlAndParams.getParams());
	}

	// 通用delete方法
	public <T> int delete(Connection connection, T t) throws SQLException {
		SqlAndParams sqlAndParams = SqlAndParamsFactory.forDelete(t);
		return JDBCUtils.updateOrInsertOrDelete(connection, sqlAndParams
				.getSql(), sqlAndParams.getParams());
	}

	// 分页查询
	public <T> void page(Class<T> clazz, Page<T> page) throws SQLException {
		// select * from books limit 0,4
		SqlAndParams sqlAndParams = SqlAndParamsFactory.forQuery(clazz,
				new Conditions());
		String sql = sqlAndParams.getSql() + " limit "
				+ ((page.getPageIndex() - 1) * page.getPageSize()) + ","
				+ page.getPageSize();
		List<T> items = JDBCUtils.query(sql, sqlAndParams.getParams(), clazz);
		page.setItems(items);

		SqlAndParams sqlAndParams2 = SqlAndParamsFactory.forStatsCount(clazz,
				new Conditions());
		long totalSize = JDBCUtils.queryForStatsCount(sqlAndParams2.getSql(),
				sqlAndParams2.getParams(), clazz);
		long totalPage = (totalSize % page.getPageSize()) == 0 ? (totalSize / page
				.getPageSize())
				: (totalSize / page.getPageSize() + 1);

		page.setTotalPage((int) totalPage);
		page.setTotalSize((int) totalSize);
	}

	public <T> void pageQuery(Class<T> clazz, Page<T> page,
			Conditions conditions) throws SQLException {

		SqlAndParams sqlAndParams2 = SqlAndParamsFactory.forStatsCount(clazz,
				conditions);

		long totalSize = JDBCUtils.queryForStatsCount(sqlAndParams2.getSql(),
				sqlAndParams2.getParams(), clazz);
		long totalPage = (totalSize % page.getPageSize()) == 0 ? (totalSize / page
				.getPageSize())
				: (totalSize / page.getPageSize() + 1);

		page.setTotalPage((int) totalPage);
		page.setTotalSize((int) totalSize);

		SqlAndParams sqlAndParams = SqlAndParamsFactory.forQuery(clazz,
				conditions);

		String sql = sqlAndParams.getSql();
		sql += " limit " + ((page.getPageIndex() - 1) * page.getPageSize())
				+ "," + page.getPageSize();

		List<T> items = JDBCUtils.query(sql, sqlAndParams.getParams(), clazz);

		page.setItems(items);
	}

	public <T> int insertPart(T bean) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}
}
