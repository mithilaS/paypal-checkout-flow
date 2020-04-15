package com.mithila.integration.paypal.client;


import java.io.IOException;

import com.paypal.http.HttpResponse;
import com.paypal.http.serializer.Json;
import org.json.JSONObject;


import com.paypal.orders.LinkDescription;
import com.paypal.orders.Order;
import com.paypal.orders.OrderActionRequest;
import com.paypal.orders.OrdersAuthorizeRequest;
import org.springframework.stereotype.Service;

/*
 *
 *1. Import the PayPal SDK client that was created in `Set up Server-Side SDK`.
 *This step extends the SDK client. It's not mandatory to extend the client, you can also inject it.
 */
@Service
public class AuthorizeOrder extends PayPalClient {

    //2. Set up your server to receive a call from the client
    /**
     *Method to authorize order after creation
     *
     *@param orderId Valid Approved Order ID from createOrder response
     *@param debug   true = print response data
     *@return HttpResponse<Order> response received from API
     *@throws IOException Exceptions from API if any
     */
    public HttpResponse<Order> authorizeOrder(String orderId, boolean debug) throws IOException {
        System.out.println("Capturing order .......................... order id : " + orderId);
        OrdersAuthorizeRequest request = new OrdersAuthorizeRequest(orderId);
        request.requestBody(buildRequestBody());
        // 3. Call PayPal to authorization an order
        HttpResponse<Order> response = client().execute(request);
        // 4. Save the authorization ID to your database. Implement logic to save the authorization to your database for future reference.
        if (debug) {
            System.out.println("Authorization Ids:");
            response.result().purchaseUnits()
                    .forEach(purchaseUnit -> purchaseUnit.payments()
                            .authorizations().stream()
                            .map(authorization -> authorization.id())
                            .forEach(System.out::println));
            System.out.println("Link Descriptions: ");
            for (LinkDescription link : response.result().links()) {
                System.out.println("\t" + link.rel() + ": " + link.href());
            }
            System.out.println("Full response body:");
            System.out.println(new JSONObject(new Json().serialize(response.result())).toString(4));
        }
        return response;
    }

    /**
     *Building empty request body.
     *
     *@return OrderActionRequest with empty body
     */
    private OrderActionRequest buildRequestBody() {
        return new OrderActionRequest();
    }

    /**
     *This driver function invokes the authorizeOrder function to
     *create an sample order.
     *
     *@param args
     */
    public static void main(String[] args) {
        try {
            new AuthorizeOrder().authorizeOrder(" REPLACE-WITH-VALID-APPROVED-ORDER-ID", true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}