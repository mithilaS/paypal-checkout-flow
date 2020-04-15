package com.mithila.integration.paypal.model;

public class Item_total {
        private String currency_code;
        private String value;
        private String taxtotal;
        private String shippingtotal;
        private String discount;


        // Getter Methods

        public String getCurrency_code() {
            return currency_code;
        }

        public String getValue() {
            return value;
        }

        public String getTaxtotal() {
            return taxtotal;
        }

        public String getShippingtotal() {
            return shippingtotal;
        }

        public String getDiscount() {
            return discount;
        }

        // Setter Methods

        public void setCurrency_code(String currency_code) {
            this.currency_code = currency_code;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public void setTaxtotal(String taxtotal) {
            this.taxtotal = taxtotal;
        }

        public void setShippingtotal(String shippingtotal) {
            this.shippingtotal = shippingtotal;
        }

        public void setDiscount(String discount) {
            this.discount = discount;
        }
    }
