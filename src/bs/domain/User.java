package bs.domain;

public class User {
	// #用户表
	// create table user(
	// id varchar(100) primary key,
	// userName varchar(20) not null unique,
	// password varchar(100) not null,
	// admin boolean default false
	// );

	// 与表名对应
	public static final String MAP_TABLE_NAME = "user";

	private String id;
	private String userName;
	private String password;
	private boolean admin;

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public synchronized String getId() {
		return id;
	}

	public synchronized void setId(String id) {
		this.id = id;
	}

	public synchronized String getUserName() {
		return userName;
	}

	public synchronized void setUserName(String userName) {
		this.userName = userName;
	}

	public synchronized String getPassword() {
		return password;
	}

	public synchronized boolean isAdmin() {
		return admin;
	}

	public synchronized void setAdmin(boolean admin) {
		this.admin = admin;
	}

	public synchronized void setPassword(String password) {
		this.password = password;
	}

}
