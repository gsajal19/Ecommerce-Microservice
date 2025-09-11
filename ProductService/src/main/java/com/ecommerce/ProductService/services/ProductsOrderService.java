package com.ecommerce.ProductService.services;

import com.ecommerce.ProductService.dto.ProductQuantity;
import com.ecommerce.ProductService.entities.ProductsOrder;
import com.ecommerce.ProductService.repository.ProductOrderRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductsOrderService {

    @Autowired
    private ProductInventoryService productInventoryService;

    @Autowired
    private ProductOrderRespository productOrderRespository;



    public void SaveProductsWithOrderId(List<ProductQuantity> items,String orderId){
        List<ProductsOrder> orders = new ArrayList<>();
        for(ProductQuantity item : items){
            ProductsOrder order = new ProductsOrder();
            order.setOrderId(orderId);
            order.setQuantity(item.getQuantity());
            order.setProductId(item.getProductId());
            order.setPrice(productInventoryService.getProductPrice(item.getProductId()));
            orders.add(order);
        }
        productOrderRespository.saveAll(orders);
    }
}
