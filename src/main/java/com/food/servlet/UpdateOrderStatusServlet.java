//import java.io.IOException;
//import java.sql.Connection;
//
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import com.food.dao.OrderDAO;
//
//public class UpdateOrderStatusServlet extends HttpServlet {
//    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
//        int orderId = Integer.parseInt(request.getParameter("orderId"));
//        String status = request.getParameter("status");
//        try {
//            Connection conn = DBConnection.getConnection();
//            OrderDAO dao = new OrderDAO(conn);
//            dao.updateOrderStatus(orderId, status);
//            response.sendRedirect("DeliveryBoyOrdersServlet?deliveryBoyId=1"); // replace with session value
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//}
