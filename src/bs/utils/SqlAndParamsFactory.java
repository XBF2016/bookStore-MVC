package bs.utils;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

public class SqlAndParamsFactory {
	// 工厂类，用于为各种查询方法生成SqlAndParams对象

	public static <T> SqlAndParams forStatsCount(Class<T> clazz,
			Conditions conditions) {
		StringBuilder sql = new StringBuilder();
		Object[] params = null;

		try {
			sql.append("select count(*) from ").append(
					clazz.getDeclaredField("MAP_TABLE_NAME").get(null));

			Map<String, Object> conditionMap = conditions.getConditionMap();
			List<Boolean> needDimList = conditions.getNeedDimList();

			if (conditionMap != null && conditionMap.size() > 0) {
				sql.append(" where ");

				Set<Entry<String, Object>> entrySet = conditionMap.entrySet();
				int i = 0;
				// 因为有多少查询条件就会有多少?占位符
				params = new Object[entrySet.size()];

				for (Entry<String, Object> entry : entrySet) {
					String key = entry.getKey();
					Object value = entry.getValue();
					boolean needDim = needDimList.get(i);

					sql.append(key)
							.append(needDim ? " like ? and " : "=? and ");

					if (needDim) {
						params[i] = "%" + value + "%";
					} else {
						params[i] = value;
					}

					i++;
				}

				sql.delete(sql.length() - 4, sql.length());
			}

			return new SqlAndParams(sql.toString(), params);

		} catch (Exception e) {
			throw new RuntimeException("服务器错误 : " + e);
		}
	}

	public static <T> SqlAndParams forInsert(T t) {
		// 先获得泛型的类型类
		Class<T> clazz = (Class<T>) t.getClass();
		// 参数列表
		Object[] params = null;
		// 拼接sql语句
		StringBuilder sql = new StringBuilder();
		sql.append("insert into ");
		// 拿到表名
		try {
			// 通过反射拿到类型类对象的字段的值
			String tableName = (String) clazz
					.getDeclaredField("MAP_TABLE_NAME").get(null);
			sql.append(tableName).append("(");
			// 通过内省获得所有字段的名称和值
			BeanInfo beanInfo = Introspector.getBeanInfo(clazz);
			PropertyDescriptor[] propertyDescriptors = beanInfo
					.getPropertyDescriptors();
			// 参数列表
			params = new Object[propertyDescriptors.length - 1];
			// 占位符
			StringBuilder p = new StringBuilder();
			p.append(" values(");
			int i = 0;
			for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
				// 字段名称
				String fieldName = propertyDescriptor.getName();
				// 排除class字段
				if ("class".equals(fieldName)) {
					continue;
				}
				Object fieldValue = propertyDescriptor.getReadMethod()
						.invoke(t);
				sql.append(fieldName).append(",");
				// 拼接占位符
				p.append("?,");
				// 拿到参数的值
				params[i++] = fieldValue;
			}
			// 修饰sql
			sql.deleteCharAt(sql.length() - 1);
			sql.append(")");
			// 修饰占位符
			p.deleteCharAt(p.length() - 1);
			p.append(")");
			sql.append(p);

		} catch (Exception e) {
			throw new RuntimeException("服务器出错：" + e);
		}

		return new SqlAndParams(sql.toString(), params);
	}

	public static <T> SqlAndParams forDelete(T t) {
		// delete from xxx where id=?
		// sql语句
		StringBuilder sql = new StringBuilder();
		sql.append("delete from ");
		// 类型类对象
		Class<T> clazz = (Class<T>) t.getClass();
		// 参数列表
		Object[] params = new Object[1];
		String idValue = null;
		try {
			// 表名通过反射拿到
			String tableName = (String) clazz
					.getDeclaredField("MAP_TABLE_NAME").get(null);
			sql.append(tableName).append(" where id=?");

			// 通过内省拿到参数的值
			BeanInfo beanInfo = Introspector.getBeanInfo(clazz);
			PropertyDescriptor[] propertyDescriptors = beanInfo
					.getPropertyDescriptors();
			for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
				// 拿到字段名
				String fieldName = propertyDescriptor.getName();
				// 遇到id字段就拿到其值并退出循环
				if ("id".equals(fieldName)) {
					idValue = (String) propertyDescriptor.getReadMethod()
							.invoke(t);
				}
			}
			// 给参数赋值
			params[0] = idValue;
		} catch (Exception e) {
			throw new RuntimeException("服务器错误：" + e);
		}

		return new SqlAndParams(sql.toString(), params);
	}

	public static <T> SqlAndParams forUpdate(T t) {
		// update xxx set x=?,... where id=?
		// 拿到对象的类型类对象
		Class<T> clazz = (Class<T>) t.getClass();
		// sql语句
		StringBuilder sql = new StringBuilder();
		sql.append("update ");
		// 参数列表
		Object[] params = null;

		try {
			// 拿到表名
			String tableName = (String) clazz
					.getDeclaredField("MAP_TABLE_NAME").get(null);
			sql.append(tableName).append(" set ");
			// 通过内省获得字段名和值
			BeanInfo beanInfo = Introspector.getBeanInfo(clazz);
			PropertyDescriptor[] propertyDescriptors = beanInfo
					.getPropertyDescriptors();
			// 创建参数对象
			params = new Object[propertyDescriptors.length - 1];
			// 单独拿到id的值
			String idValue = null;
			int i = 0;
			for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
				// 获得字段名
				String fieldName = propertyDescriptor.getName();
				// 排除class字段
				if ("class".equals(fieldName)) {
					continue;
				}
				// 如果遇到id字段就拿到其值并continue
				if ("id".equals(fieldName)) {
					idValue = (String) propertyDescriptor.getReadMethod()
							.invoke(t);
					continue;
				}
				// 获取字段的值
				Object fieldValue = propertyDescriptor.getReadMethod()
						.invoke(t);
				sql.append(fieldName).append("=?,");
				params[i++] = fieldValue;
			}
			sql.deleteCharAt(sql.length() - 1);
			sql.append(" where id=?");
			// 添加上id的值
			params[i] = idValue;
			System.out.println(sql.toString());
		} catch (Exception e) {
			throw new RuntimeException("服务器出错：" + e);
		}

		return new SqlAndParams(sql.toString(), params);
	}

	public static <T> SqlAndParams forQuery(Class clazz, Conditions conditions) {
		// 条件查询不同于更新删除插入操作，后者是已知的，前者是未知的，但主线都是拼接sql语句
		StringBuilder sql = new StringBuilder();
		sql.append("select * from ");
		String tableName = null;
		Map<String, Object> conditionsMap = conditions.getConditionMap();
		List<Boolean> needDimList = conditions.getNeedDimList();
		// 占位符的值
		Object[] params = null;
		// 拿到表名
		try {
			tableName = (String) clazz.getDeclaredField("MAP_TABLE_NAME").get(
					null);
			// 拼接表名
			sql.append(tableName);
			// 拼接条件的键值对
			// 首先要确保map不为空，因为null对象无法调用函数
			if (conditionsMap != null && conditionsMap.size() > 0) {
				// where要在确定了有条件的时候才拼接
				sql.append(" where ");
				// map对象无法得到key所以要拿到entry
				Set<Entry<String, Object>> entrySet = conditionsMap.entrySet();
				// 遍历条件键值对
				int i = 0;
				params = new Object[needDimList.size()];
				boolean needDim;
				for (Entry<String, Object> entry : entrySet) {
					String keyString = entry.getKey();
					Object valueObject = entry.getValue();
					// 顺便把标记也拿出
					needDim = needDimList.get(i);
					// 拼接key和占位符
					sql.append(keyString).append(
							needDim ? " like ? and " : "= ? and ");
					// 将占位符对应的值放入
					params[i] = needDim ? ("%" + valueObject + "%")
							: valueObject;
					i++;
				}
				// 把sql语句最后的and删除
				sql.delete(sql.length() - 4, sql.length());

			}

		} catch (Exception e) {
			throw new RuntimeException("服务器出错：" + e);
		}

		return new SqlAndParams(sql.toString(), params);
	}
}
