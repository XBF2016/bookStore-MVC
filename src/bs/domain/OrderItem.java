package bs.domain;

public class OrderItem {
	// #订单项
	// create table orderItem(
	// id varchar(100) primary key,
	// bookId varchar(100) not null,
	// bookName varchar(50),#为了编程的方便,其实是冗余数据
	// bookCount int,
	// totalPrice double,
	//			
	// orderId varchar(100) not null,
	//			
	// foreign key (bookId) references books(id),
	// foreign key (orderId) references orders(id)
	// );

	// 与表名对应
	public static final String MAP_TABLE_NAME = "orderItem";

	private String id;
	private String bookId;
	private String bookName;
	private int bookCount;
	private double totalPrice;
	private String orderId;

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
		OrderItem other = (OrderItem) obj;
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

	public synchronized String getBookId() {
		return bookId;
	}

	public synchronized void setBookId(String bookId) {
		this.bookId = bookId;
	}

	public synchronized String getBookName() {
		return bookName;
	}

	public synchronized void setBookName(String bookName) {
		this.bookName = bookName;
	}

	public synchronized int getBookCount() {
		return bookCount;
	}

	public synchronized void setBookCount(int bookCount) {
		this.bookCount = bookCount;
	}

	public synchronized double getTotalPrice() {
		return totalPrice;
	}

	public synchronized void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public synchronized String getOrderId() {
		return orderId;
	}

	public synchronized void setOrderId(String orderId) {
		this.orderId = orderId;
	}
}
