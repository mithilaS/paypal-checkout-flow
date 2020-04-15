package com.mithila.integration.paypal.model;

import com.paypal.orders.Order;

public class CompleteOrderResponse {


    private String status;
    private Order order;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    @Override
    public String toString() {
        return "CompleteOrderResponse{" +
                "status='" + status + '\'' +
                ", order=" + order +
                '}';
    }
}
