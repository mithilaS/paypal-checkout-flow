package com.mithila.integration.config;

import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.OAuthTokenCredential;
import com.paypal.base.rest.PayPalRESTException;
import com.paypal.core.PayPalEnvironment;
import com.paypal.core.PayPalHttpClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class PayPalConfiguration {

    @Value( "${paypal.client.id}")
    String clientId;

    @Value( "${paypal.client.secret}")
    String clientSecret;

    @Bean
    PayPalEnvironment penvironment(){
        PayPalEnvironment penvironment = new PayPalEnvironment.Sandbox(clientId,clientSecret);
        return penvironment;
    }

    @Bean
    PayPalHttpClient paypalClient(){
        return  new PayPalHttpClient(penvironment());
    }

    @Bean
    OAuthTokenCredential oAuthTokenCredential()
    {
        System.out.println("id : " + clientId + "  secret: " + clientSecret);
        Map<String,String> configmap = new HashMap<>();
        configmap.put("mode" ,"sandbox");
        String mode = "sandbox";
        OAuthTokenCredential oAuthTokenCredential = new OAuthTokenCredential(clientId, clientSecret,configmap);
        System.out.println(" :expires in "  +  oAuthTokenCredential.expiresIn());
        return oAuthTokenCredential;
    }

    @Bean
    APIContext apiContext() throws PayPalRESTException {
        APIContext apiContext = new APIContext(clientId, clientSecret, "sandbox");
        Map<String,String> configmap = new HashMap<>();
        configmap.put("mode" ,"sandbox");
        apiContext.setConfigurationMap(configmap);
        return apiContext;
    }
}
