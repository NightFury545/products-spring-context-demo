package org.nightfury.entity;

import java.util.HashMap;
import java.util.Map;
import lombok.Getter;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Getter
@Component
@Scope("prototype")
public class Cart {
    private final Map<Product, Integer> cartMap = new HashMap<>();

    public void addProduct(Product product, Integer quantity) {
        this.cartMap.put(product, quantity);
    }

    public void delProduct(Product product, Integer quantity) {
        this.cartMap.remove(product, quantity);
    }

    public double getSum() {
        return this.cartMap.entrySet()
            .stream()
            .mapToDouble(entry -> entry.getKey().getPrice() * entry.getValue())
            .sum();
    }

    @Override
    public String toString() {
        return "Cart{" +
            "cartMap=" + cartMap +
            '}';
    }
}
