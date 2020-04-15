package com.mithila.integration.paypal.model;

public class Amount {
   private String currency_code;
   private String value;
   Breakdown BreakdownObject;


   // Getter Methods

   public String getCurrency_code() {
       return currency_code;
   }

   public String getValue() {
       return value;
   }

   public Breakdown getBreakdown() {
       return BreakdownObject;
   }

   // Setter Methods

   public void setCurrency_code(String currency_code) {
       this.currency_code = currency_code;
   }

   public void setValue(String value) {
       this.value = value;
   }

   public void setBreakdown(Breakdown breakdownObject) {
       this.BreakdownObject = breakdownObject;
   }
}
