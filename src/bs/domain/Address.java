package bs.domain;

public class Address {
	// #地址表
	// create table address(
	// id varchar(100) primary key,
	// location varchar(255) not null,
	// phone varchar(11) not null,
	// userId varchar(100) not null,
	// userName varchar(100) not null,
	//			
	// foreign key (userId) references user(id)
	// );

	// 与表名对应
	public static final String MAP_TABLE_NAME = "address";

	private String id;
	private String location;
	private String phone;
	private String userId;
	private String userName;

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
		Address other = (Address) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Address [location=" + location + ", phone=" + phone
				+ ", userName=" + userName + "]";
	}

	public synchronized String getId() {
		return id;
	}

	public synchronized void setId(String id) {
		this.id = id;
	}

	public synchronized String getLocation() {
		return location;
	}

	public synchronized void setLocation(String location) {
		this.location = location;
	}

	public synchronized String getPhone() {
		return phone;
	}

	public synchronized void setPhone(String phone) {
		this.phone = phone;
	}

	public synchronized String getUserId() {
		return userId;
	}

	public synchronized void setUserId(String userId) {
		this.userId = userId;
	}

	public synchronized String getUserName() {
		return userName;
	}

	public synchronized void setUserName(String userName) {
		this.userName = userName;
	}
}
