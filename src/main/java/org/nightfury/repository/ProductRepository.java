package org.nightfury.repository;

import java.util.List;
import java.util.UUID;
import org.nightfury.entity.Product;

public interface ProductRepository {

    List<Product> findAll();

    void saveOrUpdate(Product product);

    Product findById(UUID id);

    void deleteById(UUID id);
}
