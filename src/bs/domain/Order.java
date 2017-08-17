package bs.domain;

public class Order {
	// #订单
	// create table orders(
	// id varchar(100) primary key,
	// orderNumber varchar(50) not null unique,#订单号
	// totalPrice double ,
	// status varchar(10),
	// userId varchar(100) not null,
	// addressId varchar(100) not null,
	//			
	// foreign key (userId) references user(id),
	// foreign key (addressId) references address(id)
	// );

	// 与表名对应
	public static final String MAP_TABLE_NAME = "orders";

	private String id;
	private String orderNumber;
	private double totalPrice;
	private String status;
	private String userId;
	private String addressId;

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
		Order other = (Order) obj;
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

	public synchronized String getOrderNumber() {
		return orderNumber;
	}

	public synchronized void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	public synchronized double getTotalPrice() {
		return totalPrice;
	}

	public synchronized void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public synchronized String getStatus() {
		return status;
	}

	public synchronized void setStatus(String status) {
		this.status = status;
	}

	public synchronized String getUserId() {
		return userId;
	}

	public synchronized void setUserId(String userId) {
		this.userId = userId;
	}

	public synchronized String getAddressId() {
		return addressId;
	}

	public synchronized void setAddressId(String addressId) {
		this.addressId = addressId;
	}
}
