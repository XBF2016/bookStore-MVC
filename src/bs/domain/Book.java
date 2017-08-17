package bs.domain;

public class Book {
	// #图书表
	// create table books(
	// id varchar(100) primary key,
	// bookName varchar(100) not null,
	// author varchar(50) not null,
	// price float ,
	// imagePath varchar(255),
	// categoryId varchar(100) ,
	//				
	// foreign key (categoryId) references category(id)
	// );

	// 与表名对应
	public static final String MAP_TABLE_NAME = "books";

	private String id;
	private String bookName;
	private String author;
	private double price;
	private String imagePath;
	private String categoryId;

	@Override
	public String toString() {
		return "Books [author=" + author + ", bookName=" + bookName
				+ ", categoryId=" + categoryId + ", id=" + id + ", price="
				+ price + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	// 自动生成的根据图书id比较相等的方法
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Book other = (Book) obj;
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

	public synchronized String getBookName() {
		return bookName;
	}

	public synchronized void setBookName(String bookName) {
		this.bookName = bookName;
	}

	public synchronized String getAuthor() {
		return author;
	}

	public synchronized void setAuthor(String author) {
		this.author = author;
	}

	public synchronized double getPrice() {
		return price;
	}

	public synchronized void setPrice(double price) {
		this.price = price;
	}

	public synchronized String getImagePath() {
		return imagePath;
	}

	public synchronized void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public synchronized String getCategoryId() {
		return categoryId;
	}

	public synchronized void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}
}
