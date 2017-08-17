package bs.utils;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class JDBCUtils {
	// 1 提供数据库连接池
	// 2 提供数据库连接
	// 3 执行update/insert/delete sql语句

	private static DataSource dataSource = null;
	static {
		dataSource = new ComboPooledDataSource();
	}

	// 提供数据库连接池
	public static DataSource getDataSource() {
		return dataSource;
	}

	// 提供数据库连接
	public static Connection getConnection() {
		try {
			return dataSource.getConnection();
		} catch (SQLException e) {
			throw new RuntimeException("服务器错误：" + e);
		}
	}

	// 执行update/insert/delete sql语句
	public static int updateOrInsertOrDelete(String sql, Object[] params)
			throws SQLException {
		QueryRunner queryRunner = new QueryRunner(dataSource);
		return queryRunner.update(sql, params);
	}

	// 执行update/insert/delete sql语句（可控版本）
	public static int updateOrInsertOrDelete(Connection connection, String sql,
			Object[] params) throws SQLException {
		QueryRunner queryRunner = new QueryRunner(dataSource);
		return queryRunner.update(connection, sql, params);
	}

	// 执行查询 , 根据id查询 , 查询全部 ,条件查询, 条件分页查询
	public static <T> List<T> query(String sql, Object[] params,
			Class<T> beanClazz) throws SQLException {
		QueryRunner queryRunner = new QueryRunner(dataSource);
		return queryRunner
				.query(sql, new BeanListHandler<T>(beanClazz), params);
	}

	// 执行查询 , 根据id查询 , 查询全部 ,条件查询, 条件分页查询
	public static <T> List<T> query(Connection connection, String sql,
			Object[] params, Class<T> beanClazz) throws SQLException {
		QueryRunner queryRunner = new QueryRunner(dataSource);
		return queryRunner.query(connection, sql, new BeanListHandler<T>(
				beanClazz), params);
	}

	public static <T> Long queryForStatsCount(String sql, Object[] params,
			Class<T> beanClazz) throws SQLException {
		QueryRunner queryRunner = new QueryRunner(dataSource);
		return queryRunner.query(sql, new ScalarHandler<Long>(), params);
	}

	// 测试工具类
	// public static void main(String[] args) throws SQLException {
	// System.out.println(getDataSource());
	// System.out.println(getConnection());
	// Object[] params = { "0002", "IT" };
	// updateOrInsertOrDelete(
	// "insert into category(id,categoryName) values(?,?)", params);
	// Object[] params = { "IT" };
	// updateOrInsertOrDelete(
	// "update category set categoryName=? where id=0001 ", params);
	// Object[] params = { "0001" };
	// updateOrInsertOrDelete("delete from category where id=?", params);
	// Object[] params = new Object[] {};
	// List<Category> list = query("select * from category where id=0001",
	// params, Category.class);
	// for (Category category : list) {
	// System.out.println(category.getCategoryName());
	// }
	// }
}
