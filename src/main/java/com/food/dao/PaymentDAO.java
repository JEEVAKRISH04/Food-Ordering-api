package com.food.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import com.food.model.Payment;
import com.food.utility.UtilityClass;


public class PaymentDAO {
    private static final String INSERT_PAYMENT_SQL =
        "INSERT INTO payment (orderid, userid, paymentmethod, paymentstatus, paymentdate) VALUES (?, ?, ?, ?, ?)";

    /**
     * Adds a new payment record to the payment table using UtilityClass.
     *
     * @param payment - Payment object containing details.
     * @throws SQLException - If any SQL exception occurs.
     */
    public void addPayment(Payment payment) throws SQLException {
        Map<Integer, Object> paramMap = new HashMap<>();
        paramMap.put(1, payment.getOrderId()); // orderid
        paramMap.put(2, payment.getUserId()); // userid
        paramMap.put(3, payment.getPaymentMethod().toString()); // paymentmethod (enum to string)
        paramMap.put(4, payment.getPaymentStatus().toString()); // paymentstatus (enum to string)
        paramMap.put(5, payment.getPaymentDate()); // paymentdate

        UtilityClass.updateObject(INSERT_PAYMENT_SQL, paramMap);
    }
    public String getPaymentMethodByOrderId(int orderId) {
        String paymentMethod = null;
        String query = "SELECT paymentmethod FROM payment WHERE orderid = ?";
        
        try (Connection conn = UtilityClass.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setInt(1, orderId);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                paymentMethod = rs.getString("paymentmethod");
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return paymentMethod;
    }

}
