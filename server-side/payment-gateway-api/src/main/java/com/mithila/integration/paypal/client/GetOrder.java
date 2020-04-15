package com.mithila.integration.paypal.client;

import java.io.IOException;

import com.paypal.http.HttpResponse;
import com.paypal.http.serializer.Json;
import org.json.JSONObject;


import com.paypal.orders.Order;
import com.paypal.orders.OrdersGetRequest;
import org.springframework.stereotype.Service;

/*
 *
 *1. Import the PayPal SDK client that was created in `Set up Server-Side SDK`.
 *This step extends the SDK client.  It's not mandatory to extend the client, you can also inject it.
 */
@Service
public class GetOrder extends PayPalClient {

    //2. Set up your server to receive a call from the client
    /**
     *Method to perform sample GET on an order
     *
     *@throws IOException Exceptions from the API, if any
     * @return
     */
    public String getOrder(String orderId) throws IOException {
        OrdersGetRequest request = new OrdersGetRequest(orderId);
        //3. Call PayPal to get the transaction
        HttpResponse<Order> response = client().execute(request);
        //4. Save the transaction in your database. Implement logic to save transaction to your database for future reference.
        System.out.println("Full response body:");
        System.out.println(new JSONObject(new Json().serialize(response.result())).toString(4));
        return new JSONObject(new Json().serialize(response.result())).toString(4);
    }

    /**
     *This driver method invokes the getOrder function with
     *order ID to retrieve order details.
     *
     *To get the correct order ID, this sample uses createOrder to create
     *a new order and then uses the newly-created order ID as a
     *parameter to getOrder.
     *
     *@param args
     *@throws IOException
     */
    public static void main(String[] args) throws IOException {
        new GetOrder().getOrder("74D22320LX009215F");
    }
}