package com.mithila.integration.paypal.client;

import com.paypal.core.PayPalEnvironment;
import com.paypal.core.PayPalHttpClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PayPalClient {


    @Value( "${paypal.client.id}")
    String clientId;

    @Value( "${paypal.client.secret}")
    String clientSecret;


    /**
     *Set up the PayPal Java SDK environment with PayPal access credentials.
     *This sample uses SandboxEnvironment. In production, use LiveEnvironment.
     */
    private PayPalEnvironment environment = new PayPalEnvironment.Sandbox(
            "AX_2m61Rw3UCoDKjLqDn5cf-biKnIGOL1drQLswc18DReieyg5X6AIh-b0fgUsxAAcqgm_o4q9G2Fc-C",
            "ED96xAq8PL9RjbSOJckwbhq5Y0BsHR_fALuXX9gKJQBdQt2llBHZcn1Ap0McB3vSMfa6rgyZwDKkBxFj");

    /**
     *PayPal HTTP client instance with environment that has access
     *credentials context. Use to invoke PayPal APIs.
     */
    PayPalHttpClient client = new PayPalHttpClient(environment);

    /**
     *Method to get client object
     *
     *@return PayPalHttpClient client
     */
    public PayPalHttpClient client() {
        return this.client;
    }
}