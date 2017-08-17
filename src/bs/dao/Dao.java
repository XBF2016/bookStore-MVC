package bs.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import bs.utils.Conditions;
import bs.utils.Page;

public interface Dao {
	<T> List<T> queryAll(Connection conn, Class<T> clazz) throws SQLException;

	<T> List<T> queryAll(Class<T> clazz) throws SQLException;

	<T> T queryById(Connection conn, Class<T> clazz, Object idValue)
			throws SQLException;

	<T> T queryById(Class<T> clazz, Object idValue) throws SQLException;

	<T> List<T> query(Connection conn, Class<T> clazz, Conditions conditions)
			throws SQLException;

	<T> List<T> query(Class<T> clazz, Conditions conditions)
			throws SQLException;

	<T> int insert(T bean) throws SQLException;
	
	<T> int insertPart(T bean) throws SQLException;

	<T> int insert(Connection conn, T bean) throws SQLException;

	<T> int update(T bean) throws SQLException;

	<T> int update(Connection conn, T bean) throws SQLException;

	<T> int delete(T bean) throws SQLException;

	<T> int delete(Connection conn, T bean) throws SQLException;

	<T> void page(Class<T> clazz, Page<T> page) throws SQLException;

	<T> void pageQuery(Class<T> clazz, Page<T> page, Conditions conditions)
			throws SQLException;

}
