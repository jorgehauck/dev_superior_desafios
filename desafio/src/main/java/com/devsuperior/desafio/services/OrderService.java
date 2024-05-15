package com.devsuperior.desafio.services;

import com.devsuperior.desafio.entities.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    @Autowired
    private ShippingService shippingService;

    public double total(Order order) {
        double desconto = (order.getBasic() * order.getDiscount() / 100);
        return (order.getBasic() - desconto) + shippingService.shipment(order);
    }
}
