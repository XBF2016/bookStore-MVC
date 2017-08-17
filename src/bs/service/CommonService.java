package bs.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import bs.dao.CommonDao;
import bs.dao.Dao;
import bs.utils.Conditions;
import bs.utils.Page;

public class CommonService {

	private Dao dao = null;

	public CommonService() {
		dao = new CommonDao();
	}

	// 为了拓展性，提供了可接受其他Dao的构造函数
	public CommonService(Dao dao) {
		this.dao = dao;
	}

	// 提供一个检查是否唯一的方法
	public <T> boolean checkUnique(Class<T> clazz, String key, Object value) {
		Conditions conditions = new Conditions();
		conditions.addConditions(key, value, false);
		List<T> list = (List<T>) queryByCondition(clazz, conditions);
		// 如果找到了就说明不唯一
		if (list != null && list.size() > 0) {
			return false;
		}
		return true;
	}

	// 增
	public <T> int add(T t) {
		try {
			return dao.insert(t);
		} catch (SQLException e) {
			throw new RuntimeException("服务器错误：" + e);
		}
	}

	public <T> int add(Connection connection, T t) {
		try {
			return dao.insert(connection, t);
		} catch (SQLException e) {
			throw new RuntimeException("服务器错误：" + e);
		}
	}

	// 删
	public <T> int delete(T t) {
		try {
			return dao.delete(t);
		} catch (SQLException e) {
			throw new RuntimeException("服务器错误：" + e);
		}
	}

	public <T> int delete(Connection connection, T t) {
		try {
			return dao.delete(connection, t);
		} catch (SQLException e) {
			throw new RuntimeException("服务器错误：" + e);
		}
	}

	// 改
	public <T> int update(T t) {
		try {
			return dao.update(t);
		} catch (SQLException e) {
			throw new RuntimeException("服务器错误：" + e);
		}
	}

	public <T> int update(Connection connection, T t) {
		try {
			return dao.update(connection, t);
		} catch (SQLException e) {
			throw new RuntimeException("服务器错误：" + e);
		}
	}

	// 条件查询
	public <T> List<T> queryByCondition(Class<T> clazz, Conditions conditions) {
		try {
			return dao.query(clazz, conditions);
		} catch (SQLException e) {
			throw new RuntimeException("服务器错误：" + e);
		}
	}

	public <T> List<T> queryByCondition(Connection connection, Class<T> clazz,
			Conditions conditions) {
		try {
			return dao.query(connection, clazz, conditions);
		} catch (SQLException e) {
			throw new RuntimeException("服务器错误：" + e);
		}
	}

	// ID查询
	public <T> T queryById(Class<T> clazz, Object idValue) {
		try {
			return dao.queryById(clazz, idValue);
		} catch (SQLException e) {
			throw new RuntimeException("服务器错误：" + e);
		}
	}

	// ID查询
	public <T> T queryById(Connection connection, Class<T> clazz, Object idValue) {
		try {
			return dao.queryById(connection, clazz, idValue);
		} catch (SQLException e) {
			throw new RuntimeException("服务器错误：" + e);
		}
	}

	// 查询全部
	public <T> List<T> queryAll(Class<T> clazz) {
		try {
			return dao.queryAll(clazz);
		} catch (SQLException e) {
			throw new RuntimeException("服务器错误：" + e);
		}
	}

	// 查询全部
	public <T> List<T> queryAll(Connection connection, Class<T> clazz) {
		try {
			return dao.queryAll(connection, clazz);
		} catch (SQLException e) {
			throw new RuntimeException("服务器错误：" + e);
		}
	}

	public <T> void page(Class<T> clazz, Page<T> page) {
		try {
			dao.page(clazz, page);
		} catch (Exception e) {
			throw new RuntimeException("服务器错误 : " + e);
		}

	}

	public <T> void pageQuery(Class<T> clazz, Page<T> page,
			Conditions conditions) {
		try {
			dao.pageQuery(clazz, page, conditions);
		} catch (Exception e) {
			throw new RuntimeException("服务器错误 : " + e);
		}

	}
}
