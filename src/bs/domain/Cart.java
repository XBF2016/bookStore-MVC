package bs.domain;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

//购物车类
public class Cart {

	@Override
	public String toString() {
		return "Cart [cartItems=" + cartItemList + "]";
	}

	public synchronized List<CartItem> getCartItemList() {
		return cartItemList;
	}

	// 一个购物车有很多购物车项
	List<CartItem> cartItemList = new ArrayList<CartItem>();

	public synchronized List<CartItem> getCartItems() {
		return cartItemList;
	}

	// 购买
	public void buy(Book books) {
		// 先看看购物车中是否已经存在该图书
		for (CartItem cartItem : cartItemList) {
			// 判断新添加的图书与原来的图书是否相等
			if (cartItem.getBook().equals(books)) {
				// 如果相等就使数量+1
				cartItem.setBookCount(cartItem.getBookCount() + 1);
				return;
			}
		}
		// 到这里说明购物车中不存在该图书
		CartItem cartItem = new CartItem();
		cartItem.setBook(books);
		cartItem.setBookCount(1);
		// 添加
		cartItemList.add(cartItem);
	}

	// 删除（根据图书的id）
	public void delete(String bookId) {
		// 先看看购物车中是否存在该图书
		Iterator<CartItem> iterator = cartItemList.iterator();
		while (iterator.hasNext()) {
			if (iterator.next().getBook().getId().equals(bookId)) {
				// 如果有就删除
				iterator.remove();
				return;
			}
		}
	}

	// 更改数量
	public void update(String bookId, int bookCount) {
		// 数量的合法性检查
		if (bookCount < 1) {
			return;
		}
		// 看看购物车中是否存在该图书
		Iterator<CartItem> iterator = cartItemList.iterator();
		while (iterator.hasNext()) {
			CartItem cartItem = iterator.next();
			if (cartItem.getBook().getId().equals(bookId)) {
				// 如果有就更新
				cartItem.setBookCount(bookCount);
				return;
			}
		}
	}

	// 购物车里的一个项
	public class CartItem {

		// 有两个成员，一个是Books对象，一个是数量
		private Book book;
		private int bookCount;

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + getOuterType().hashCode();
			result = prime * result + ((book == null) ? 0 : book.hashCode());
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
			CartItem other = (CartItem) obj;
			if (!getOuterType().equals(other.getOuterType()))
				return false;
			if (book == null) {
				if (other.book != null)
					return false;
			} else if (!book.equals(other.book))
				return false;
			return true;
		}

		@Override
		public String toString() {
			return "CartItem [bookCount=" + bookCount + ", book=" + book + "]";
		}

		public synchronized Book getBook() {
			return book;
		}

		public synchronized void setBook(Book book) {
			this.book = book;
		}

		public synchronized int getBookCount() {
			return bookCount;
		}

		public synchronized void setBookCount(int bookCount) {
			this.bookCount = bookCount;
		}

		private Cart getOuterType() {
			return Cart.this;
		}

	}

}
