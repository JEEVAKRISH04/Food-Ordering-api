//package com.food.servlet;
//
//import java.io.IOException;
//import java.sql.Connection;
//import java.sql.SQLException;
//
//import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;
//
//import org.apache.commons.math3.geometry.partitioning.BSPTreeVisitor.Order;
//
//import com.food.model.OrderItem;
//import com.food.model.Payment;
//import com.tapfoods.daoimpl.OrderDaoImpl;
//import com.tapfoods.daoimpl.OrderItemDaoImpl;
//import com.tapfoods.daoimpl.PaymentDaoImpl;
//import com.tapfoods.model.Cart;
//import com.tapfoods.model.CartItem;
//import com.tapfoods.model.User;
//import com.tapfoods.util.DBConnection;
//
//@WebServlet("/CheckoutServlet")
//public class CheckoutServlet extends HttpServlet {
//
//	private OrderDaoImpl orderDaoImpl;
//	private OrderItemDaoImpl orderItemDaoImpl;
//	private PaymentDaoImpl paymentDaoImpl;
//
//	public void init() {
//		orderDaoImpl = new OrderDaoImpl();
//		orderItemDaoImpl = new OrderItemDaoImpl();
//		paymentDaoImpl = new PaymentDaoImpl();
//	}
//
//	@Override
//	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//
//		HttpSession session = req.getSession();
//		Cart cart = (Cart) session.getAttribute("cart");
//		User user = (User) session.getAttribute("user");
//
//		if (cart != null && user != null) {
//
//			double totalPrice = 0;
//			for (CartItem item : cart.getItems().values()) {
//				totalPrice += item.getPrice() * item.getQuantity();
//			}
//
//			// Payment method from form
//			String paymentMethod = req.getParameter("payment");
//
//			// Insert Order
//			Order order = new Order();
//			order.setRestaurantId((int) session.getAttribute("restaurantId"));
//			order.setUserId(user.getUserId());
//			order.setModeOfPayment(paymentMethod);
//			order.setStatus("pending");
//
//			int generatedOrderId = 0;
//			try (Connection conn = DBConnection.getConnection()) {
//				conn.setAutoCommit(false); // Transaction Start
//
//				// Insert order, get generated id
//				generatedOrderId = orderDaoImpl.addOrder(order, conn);
//
//				// Insert each item into orderitem table
//				for (CartItem cartItem : cart.getItems().values()) {
//					OrderItem orderItem = new OrderItem();
//					orderItem.setOrderId(generatedOrderId);
//					orderItem.setMenuId(cartItem.getMenuId());
//					orderItem.setQuantity(cartItem.getQuantity());
//					orderItem.setPrice(cartItem.getPrice());
//					orderItem.setUserId(user.getUserId());
//					orderItem.setRestaurantId(order.getRestaurantId());
//					orderItem.setItemName(cartItem.getItemName());
//					orderItem.setTotalAmount(cartItem.getPrice() * cartItem.getQuantity());
//					orderItemDaoImpl.addOrderItem(orderItem, conn);
//				}
//
//				// Insert Payment
//				Payment payment = new Payment();
//				payment.setOrderId(generatedOrderId);
//				payment.setUserId(user.getUserId());
//				payment.setAmount(totalPrice);
//				payment.setPaymentMethod(paymentMethod);
//				payment.setPaymentStatus("Pending");
//				paymentDaoImpl.addPayment(payment, conn);
//
//				conn.commit(); // Commit transaction
//
//				session.removeAttribute("cart");
//				session.setAttribute("order", order);
//				session.setAttribute("totalPrice", totalPrice);
//
//			} catch (SQLException e) {
//				e.printStackTrace();
//				resp.sendRedirect("Error.jsp");
//				return;
//			}
//		}
//		resp.sendRedirect("Confirmation.jsp");
//	}
//}
