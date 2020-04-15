package com.mithila.integration.paypal.model;

import java.util.ArrayList;
import java.util.List;

public class Purchase_units {
   Amount AmountObject;
   List< Items > items = new ArrayList< Items >();


  // Getter Methods

  public Amount getAmount() {
      return AmountObject;
  }

  // Setter Methods

  public void setAmount(Amount amountObject) {
      this.AmountObject = amountObject;
  }

   public List<Items> getItems() {
       return items;
   }

   public void setItems(List<Items> items) {
       this.items = items;
   }
}
