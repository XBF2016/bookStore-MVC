package bs.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import bs.domain.OrderItem;
import bs.domain.Order;
import bs.utils.JDBCUtils;

public class OrderService extends CommonService {

	// 要自己控制事务
	public void addOrder(Order orders, List<OrderItem> orderItemList) {
		// 要自己控制事务就要使用Connection
		Connection connection = null;

		try {
			connection = JDBCUtils.getConnection();
			// 设置为不自动提交
			connection.setAutoCommit(false);
			add(connection, orders);
			for (OrderItem orderItem : orderItemList) {
				add(connection, orderItem);
			}

			connection.commit();
		} catch (SQLException e) {
			if (connection != null) {
				try {
					connection.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
			throw new RuntimeException("服务器错误：" + e);
		} finally {
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}

	}
}
