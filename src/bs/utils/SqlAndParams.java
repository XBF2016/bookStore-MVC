package bs.utils;

public class SqlAndParams {
	// 带有sql语句和占位符值数组的新对象，解决了只能返回一个值的问题
	private String sql;
	private Object[] params;

	// 构造函数用于存储sql语句和占位符值数组
	public SqlAndParams(String sql, Object[] params) {
		super();
		this.sql = sql;
		this.params = params;
	}

	public synchronized String getSql() {
		return sql;
	}

	public synchronized void setSql(String sql) {
		this.sql = sql;
	}

	public synchronized Object[] getParams() {
		return params;
	}

	public synchronized void setParams(Object[] params) {
		this.params = params;
	}

}
