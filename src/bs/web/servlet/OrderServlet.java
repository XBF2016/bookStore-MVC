package bs.web.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.map.HashedMap;

import bs.domain.Address;
import bs.domain.Cart;
import bs.domain.Order;
import bs.domain.OrderItem;
import bs.domain.User;
import bs.domain.Cart.CartItem;
import bs.service.CommonService;
import bs.service.OrderService;
import bs.utils.Conditions;
import bs.utils.Utils;

//处理订单信息
public class OrderServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private CommonService commonService = new CommonService();
	private OrderService orderService = new OrderService();

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 首先获取操作类型
		String action = request.getParameter("action");
		// 判断
		if ("create".equals(action)) {
			// 来自购物车页面的结算功能，应该吧购物车项生成一个订单页面并展示该用户的地址
			create(request, response);
		} else if ("confirm".equals(action)) {
			// 来自订单页面的确认订单功能，最终应该将Order与OrderItem和一起存入数据库
			confirm(request, response);
		} else if ("updateStatus".equals(action)) {
			updateStatus(request, response);
		} else if ("list".equals(action)) {
			list(request, response);
		}

	}

	// 查看
	private void list(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 查看所有订单，每个订单又有订单项和地址信息
		List<Order> orderList = null;
		Map<String, List<OrderItem>> order_OrderItemMap = null;
		Map<String, String> order_AddressMap = null;
		// 查询数据库
		orderList = commonService.queryAll(Order.class);

		// 判断是否有订单，有的话再进行下一步操作
		if (orderList != null) {
			order_OrderItemMap = new HashedMap();
			order_AddressMap = new HashedMap();
			// 遍历订单，对每个订单找出其所有的订单项
			for (Order order : orderList) {
				// 添加地址
				Address address = commonService.queryById(Address.class, order
						.getAddressId());
				order_AddressMap.put(order.getId(), address.toString());

				// 添加订单项
				Conditions conditions = new Conditions();
				conditions.addConditions("orderId", order.getId(), false);
				List<OrderItem> orderItems = commonService.queryByCondition(
						OrderItem.class, conditions);
				order_OrderItemMap.put(order.getId(), orderItems);
			}
			request.setAttribute("orderList", orderList);
			request.setAttribute("order_OrderItemMap", order_OrderItemMap);
			request.setAttribute("order_AddressMap", order_AddressMap);
		}

		User user = (User) request.getSession().getAttribute("user");
		if (user.isAdmin()) {
			request.getRequestDispatcher("/manager/orderList.jsp").forward(
					request, response);
		} else {
			request.getRequestDispatcher("/client/orderList.jsp").forward(
					request, response);
		}

		// List<Orders> orderList = null;
		// Map<String, List<OrderItem>> orderItemsMap = null;
		// Map<String, String> addressMap = null;
		//
		// // 获取所有订单
		// orderList = commonService.queryAll(Orders.class);
		//
		// if (orderList != null) {
		//
		// orderItemsMap = new HashMap<String, List<OrderItem>>();
		// addressMap = new HashMap<String, String>();
		//
		// // 遍历订单
		// for (Orders orders : orderList) {
		//
		// // 拿到订单的地址
		// Address address = commonService.queryById(Address.class, orders
		// .getAddressId());
		// addressMap.put(orders.getId(), address.toString());
		//
		// // 查询该订单所有的订单项
		// Conditions conditions = new Conditions();
		// conditions.addConditions("orderId", orders.getId(), false);
		//
		// List<OrderItem> orderItems = commonService.queryByCondition(
		// OrderItem.class, conditions);
		//
		// orderItemsMap.put(orders.getId(), orderItems);
		// }
		//
		// request.setAttribute("orderList", orderList);
		// request.setAttribute("orderItemsMap", orderItemsMap);
		// request.setAttribute("addressMap", addressMap);
		// }
		//
		// User user = (User) request.getSession().getAttribute("user");
		// if (user.isAdmin()) {
		// request.getRequestDispatcher("/manager/orderList.jsp").forward(
		// request, response);
		// } else {
		// request.getRequestDispatcher("/client/orderList.jsp").forward(
		// request, response);
		// }
	}

	// 更改
	private void updateStatus(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		String orderId = request.getParameter("orderId");
		Order orders = commonService.queryById(Order.class, orderId);
		orders.setStatus("发货");

		commonService.update(orders);
		list(request, response);
	}

	// 确认订单
	private void confirm(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// 确认之前还要确认收货地址
		String addressId = request.getParameter("addressId");

		if (Utils.hasEmpty(addressId)) {
			request.setAttribute("message", "请选择收货地址");
			request.getRequestDispatcher("").forward(request, response);
			return;
		}

		User user = (User) request.getSession().getAttribute("user");

		// 先判断是否登录
		if (user == null) {
			request.setAttribute("message", "请先登录");
			request.getRequestDispatcher("/login.jsp").forward(request,
					response);
			return;
		}

		// 确认地址后进行订单的生成
		Order orders = new Order();
		orders.setId(UUID.randomUUID().toString());
		orders.setAddressId(addressId);
		orders.setOrderNumber(Utils.createOrderNumber());
		orders.setStatus("未发货");
		orders.setUserId(user.getId());

		// 存放所有的订单项
		List<OrderItem> orderItemList = new ArrayList<OrderItem>();
		// 拿到购物车对象
		Cart cart = (Cart) request.getSession().getAttribute("cart");
		List<CartItem> cartItemList = cart.getCartItems();
		if (cartItemList == null || cartItemList.size() == 0) {
			request.setAttribute("message", "你的购物车空空如也");
			request.getRequestDispatcher("/index.jsp").forward(request,
					response);
		}
		// 遍历购物车项
		double totalPrice = 0;
		for (CartItem cartItem : cartItemList) {
			OrderItem orderItem = new OrderItem();
			orderItem.setBookCount(cartItem.getBookCount());
			orderItem.setBookId(cartItem.getBook().getId());
			orderItem.setBookName(cartItem.getBook().getBookName());
			orderItem.setId(UUID.randomUUID().toString());
			orderItem.setOrderId(orders.getId());
			orderItem.setTotalPrice(orderItem.getBookCount()
					* cartItem.getBook().getPrice());
			totalPrice += orderItem.getTotalPrice();
			orderItemList.add(orderItem);
		}
		orders.setTotalPrice(totalPrice);

		// 现在订单和订单项好了可以存入数据库了的，但是问题来了，两者必须要么一起存入要么不存入，但是没有可以一起存入的方法，
		// 所以要新创一个OrderService类继承CommonService
		orderService.addOrder(orders, orderItemList);

		list(request, response);

	}

	// 结算生成订单
	private void create(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 购物车项生成一个订单页面，由于购物车在session中，故不需处理
		// 展示该用户的地址
		User user = (User) request.getSession().getAttribute("user");

		// 先判断是否登录
		if (user == null) {
			request.setAttribute("message", "请先登录");
			request.getRequestDispatcher("/login.jsp").forward(request,
					response);
			return;
		}

		Conditions conditions = new Conditions();
		conditions.addConditions("userId", user.getId(), false);
		List<Address> addressList = commonService.queryByCondition(
				Address.class, conditions);
		// 将地址数据存入request
		request.setAttribute("addressList", addressList);
		request.getRequestDispatcher("/client/orderCreate.jsp").forward(
				request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
