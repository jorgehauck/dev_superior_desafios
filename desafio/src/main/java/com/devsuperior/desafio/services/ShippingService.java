package com.devsuperior.desafio.services;

import com.devsuperior.desafio.entities.Order;
import org.springframework.stereotype.Service;

@Service
public class ShippingService {

    public double shipment(Order order) {
        if (order.getBasic() < 100) {
            return 20.00;
        }

        if (order.getBasic() <= 200) {
            return 12.00;
        }
        return 0.0;
    }
}
