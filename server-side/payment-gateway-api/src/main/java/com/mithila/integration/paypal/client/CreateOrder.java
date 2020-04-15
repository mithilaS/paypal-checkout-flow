package com.mithila.integration.paypal.client;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import com.mithila.integration.paypal.model.*;
import com.paypal.core.PayPalHttpClient;
import com.paypal.http.HttpResponse;
import com.paypal.orders.*;
import com.paypal.orders.Order;
import com.paypal.orders.ShippingDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

/*
 *
 *1. Import the PayPal SDK client that was created in `Set up Server-Side SDK`.
 *This step extends the SDK client. It's not mandatory to extend the client, you can also inject it.
 */
@Service
public class CreateOrder extends PayPalClient {

    @Autowired
    PayPalHttpClient paypalClient;

    //2. Set up your server to receive a call from the client
    /**
     *Method to create order
     *
     *@param debug true = print response data
     *@return HttpResponse<Order> response received from API
     *@throws IOException Exceptions from API if any
     */
    public HttpResponse<Order> build(CheckoutDetails checkout, boolean debug) throws IOException {
        OrdersCreateRequest request = new OrdersCreateRequest();
        request.prefer("return=representation");
        request.requestBody(buildRequestBody(checkout));
        //3. Call PayPal to set up a transaction
        HttpResponse<Order> response = paypalClient.execute(request);
        if (debug) {
            if (response.statusCode() == 201) {
                System.out.println("Status Code: " + response.statusCode());
                System.out.println("Status: " + response.result().status());
                System.out.println("Order ID: " + response.result().id());
                System.out.println("Intent: " + response.result().checkoutPaymentIntent() );
                System.out.println("Links: ");
                for (LinkDescription link : response.result().links()) {
                    System.out.println("\t" + link.rel() + ": " + link.href() + "\tCall Type: " + link.method());
                }
                System.out.println("Total Amount: " + response.result().purchaseUnits().get(0).amountWithBreakdown().currencyCode()
                        + " " + response.result().purchaseUnits().get(0).amountWithBreakdown().value());
            }
        }
        return response;
    }

    /**
     *Method to generate sample create order body with CAPTURE intent
     *
     *@return OrderRequest with created order request
     */
    private OrderRequest buildRequestBody(CheckoutDetails checkout) {
        OrderRequest orderRequest = new OrderRequest();
        orderRequest.checkoutPaymentIntent("CAPTURE");

        ApplicationContext applicationContext = new ApplicationContext().brandName("COUCHLANE INC").landingPage("BILLING")
                .shippingPreference("SET_PROVIDED_ADDRESS");
        orderRequest.applicationContext(applicationContext);

        Purchase_units purchase_units = checkout.getPurchase_units();
        Amount amount = purchase_units.getAmount();
        List<Items> items = purchase_units.getItems();
        List<PurchaseUnitRequest> purchaseUnitRequests = new ArrayList<PurchaseUnitRequest>();
        Item_total item_total = amount.getBreakdown().getItem_total();
        List<Item> itemList = new ArrayList<>();
        for(Items item : items){
            Item it = new Item().name(item.getName()).description(item.getDescription())
                    .unitAmount(new Money().currencyCode(item.getCurrency_code()).value(item.getValue()))
                    .tax(new Money().currencyCode(item.getCurrency_code()).value(item.getTax()))
                    .quantity(item.getQuantity());
            itemList.add(it);
        }
        Random random = new Random();

        PurchaseUnitRequest purchaseUnitRequest = new PurchaseUnitRequest().referenceId("REF-" +  String.valueOf(random.nextInt(1000)))
                .amountWithBreakdown(new AmountWithBreakdown().currencyCode(amount.getCurrency_code()).value(amount.getValue())

                        .amountBreakdown(new AmountBreakdown().itemTotal(new Money().currencyCode(item_total.getCurrency_code()).value(item_total.getValue()))
                                .shipping(new Money().currencyCode(item_total.getCurrency_code()).value(item_total.getShippingtotal()))
                                .taxTotal(new Money().currencyCode(item_total.getCurrency_code()).value(item_total.getTaxtotal()))
                                .shippingDiscount(new Money().currencyCode(item_total.getCurrency_code()).value(item_total.getDiscount()))))
                .items(itemList)
                .shippingDetail(new ShippingDetail().name(new Name().fullName("Mithi Doe"))
                        .addressPortable(new AddressPortable().addressLine1("123 Townsend St").addressLine2("Floor 6")
                                .adminArea2("San Francisco").adminArea1("CA").postalCode("94107").countryCode("US")));
        purchaseUnitRequests.add(purchaseUnitRequest);
        orderRequest.purchaseUnits(purchaseUnitRequests);
        return orderRequest;
    }

    /**
     *This driver function invokes the createOrder function to create
     *a sample order.
     */
    public static void main(String args[]) {
        try {
            HttpResponse<Order> response = new CreateOrder().build(new CheckoutDetails() , true);
            Order result = response.result();
            String id = result.id();
            GetOrder getorder =  new GetOrder();
            getorder.getOrder(id);



        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}