package com.food.model;

import java.sql.Timestamp;

import com.tap.enums.PaymentMethod;
import com.tap.enums.PaymentStatus;

public class Payment {
    private int paymentId;
    private int orderId;
    private int userId;

    private PaymentMethod paymentMethod;
    private PaymentStatus paymentStatus;
    private Timestamp paymentDate;  // Added paymentDate

    public Payment() {
    }

    public Payment(int paymentId, int orderId, int userId,  PaymentMethod paymentMethod, PaymentStatus paymentStatus, Timestamp paymentDate) {
        super();
        this.paymentId = paymentId;
        this.orderId = orderId;
        this.userId = userId;
      
        this.paymentMethod = paymentMethod;
        this.paymentStatus = paymentStatus;
        this.paymentDate = paymentDate;  // Initialize paymentDate
    }

    public int getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(int paymentId) {
        this.paymentId = paymentId;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

  

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public Timestamp getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Timestamp paymentDate) {
        this.paymentDate = paymentDate;
    }

    @Override
    public String toString() {
        return "Payment [paymentId=" + paymentId + ", orderId=" + orderId + ", userId=" + userId 
                + ", paymentMethod=" + paymentMethod + ", paymentStatus=" + paymentStatus + ", paymentDate=" + paymentDate + "]";
    }
}
