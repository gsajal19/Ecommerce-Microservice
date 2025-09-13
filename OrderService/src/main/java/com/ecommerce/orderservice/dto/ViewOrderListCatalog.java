package com.ecommerce.orderservice.dto;

import java.util.List;

public class ViewOrderListCatalog {
    private List<ProductDetails> products;
    private TotalCharges totalCharges;

    public ViewOrderListCatalog() {
    }

    public ViewOrderListCatalog(List<ProductDetails> products, TotalCharges totalCharges) {
        this.products = products;
        this.totalCharges = totalCharges;
    }

    public List<ProductDetails> getProducts() {
        return products;
    }

    public void setProducts(List<ProductDetails> products) {
        this.products = products;
    }

    public TotalCharges getTotalCharges() {
        return totalCharges;
    }

    public void setTotalCharges(TotalCharges totalCharges) {
        this.totalCharges = totalCharges;
    }

    @Override
    public String toString() {
        return "ViewOrderListCatalog{" +
                "products=" + products +
                ", totalCharges=" + totalCharges +
                '}';
    }
}
