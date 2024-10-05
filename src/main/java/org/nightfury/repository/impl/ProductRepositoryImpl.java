package org.nightfury.repository.impl;

import com.github.javafaker.Faker;
import jakarta.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.function.Supplier;
import org.nightfury.entity.Product;
import org.nightfury.repository.ProductRepository;
import org.springframework.stereotype.Repository;

@Repository
public class ProductRepositoryImpl implements ProductRepository {

    private final Map<Long, Product> productMap = new HashMap<>();

    private final Faker faker = new Faker();

    @PostConstruct
    public void fillRepositoryWithProducts() {
        for (int i = 0; i < 5; i++) {
            this.productMap.put((long) faker.number().numberBetween(9, 999),
                new Product(UUID.randomUUID(), faker.commerce().productName(),
                    Double.parseDouble(faker.commerce().price().replace(',', '.'))));
        }
    }

    @Override
    public List<Product> findAll() {
        final List<Product> productList = new ArrayList<>();
        productMap.forEach((key, value) -> productList.add(value));
        return productList;
    }

    @Override
    public void saveOrUpdate(Product product) {
        if (!this.productMap.containsValue(product)) {
            this.productMap.put((long) faker.number().numberBetween(9, 999), product);
        } else {
            this.productMap.forEach((quantity, currentProduct) -> {
                if (currentProduct.getId().equals(product.getId())) {
                    this.productMap.put(quantity, product);
                }
            });
        }
    }

    @Override
    public Product findById(UUID id) {
        return productMap.entrySet()
            .stream()
            .filter(longProductEntry -> longProductEntry.getValue().getId().equals(id))
            .findFirst()
            .orElseThrow(() -> new NoSuchElementException("No such element with ID: " + id))
            .getValue();
    }

    @Override
    public void deleteById(UUID id) {
        this.productMap.entrySet()
            .removeIf(longProductEntry -> longProductEntry.getValue().getId().equals(id));
    }
}
