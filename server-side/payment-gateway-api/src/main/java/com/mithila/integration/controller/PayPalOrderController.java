package com.mithila.integration.controller;

import com.mithila.integration.paypal.model.CheckoutDetails;
import com.mithila.integration.paypal.model.CompleteOrderResponse;
import com.mithila.integration.paypal.model.PaymentDetails;
import com.mithila.integration.paypal.client.CaptureOrder;
import com.mithila.integration.paypal.client.CreateOrder;
import com.mithila.integration.paypal.client.GetOrder;
import com.paypal.http.HttpResponse;
import com.paypal.http.exceptions.SerializeException;
import com.paypal.orders.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


@CrossOrigin(origins = {"http://localhost:4200","http://localhost:8080" , "https://localhost:8443"
        ,"http://40.117.143.140:8080" , "https://40.117.143.140:8443"
        ,"https://couchlane.eastus.cloudapp.azure.com:8443",
        "http://couchlane.eastus.cloudapp.azure.com:8080" ,  }, maxAge = 3600  ,allowedHeaders = "*")
@RestController
@RequestMapping(value = "/order")
public class PayPalOrderController {

    @Autowired
    CreateOrder createOrder;

    @Autowired
    CaptureOrder captureOrder;

    @Autowired
    GetOrder getOrder;


    @PostMapping(path="/createPayment")
    public    Map<String, Object> createPayment(@RequestBody CheckoutDetails checkoutDetails){
        System.out.println(" in create payment");
        Map<String, Object> payment = new HashMap<>();
        HttpResponse<Order> order =null;
        try {
           order = createOrder.build(checkoutDetails, true);
           payment.put("status", "success");
           payment.put("orderId" , order.result().id());
           payment.put("redirect_url"  , order.result().links().get(0).href());
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(payment);
        System.out.println(" ************* create order complete ************");
        return payment;

    }

    @PostMapping(path="/completePayment")
    public Map<String, Object> completePayment(@RequestBody PaymentDetails paymentDetails) throws SerializeException {
        System.out.println(" in complete payment ");
        Map<String, Object> payment = new HashMap<>();

        HttpResponse<Order> stringObjectMap = null;
        try {
            stringObjectMap = captureOrder.captureOrder(paymentDetails.getOrderId() ,true );
        } catch (IOException e) {
            e.printStackTrace();
        }catch(Exception e){
            e.printStackTrace();
        }
        System.out.println(stringObjectMap);
        System.out.println("**********************************");
        Order order =stringObjectMap.result();
        payment.put("status" , "success");
        payment.put("orderId" , order.id());
        payment.put("orderStatus" , order.status());
       payment.put("paidFor1",true);
        CompleteOrderResponse cr = new CompleteOrderResponse();
        cr.setOrder(order);
        cr.setStatus(String.valueOf(stringObjectMap.statusCode()));
        return payment;
    }

    @GetMapping(path="/getOrder")
    public String getOrderDetails(@RequestBody String orderId){
        try {
            String order = getOrder.getOrder(orderId);
            return order;
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }
}
