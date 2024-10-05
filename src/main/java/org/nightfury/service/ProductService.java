package org.nightfury.service;

import java.util.List;
import java.util.UUID;
import org.nightfury.entity.Product;

public interface ProductService {

    List<Product> getProductList();

    void saveOrUpdate(Product product);

    Product getProductById(UUID id);

    void deleteById(UUID id);
}
