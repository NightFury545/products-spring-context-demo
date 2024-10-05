package org.nightfury.service.impl;

import java.util.List;
import java.util.UUID;
import org.nightfury.entity.Product;
import org.nightfury.repository.ProductRepository;
import org.nightfury.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<Product> getProductList() {
        return productRepository.findAll();
    }

    @Override
    public void saveOrUpdate(Product product) {
        productRepository.saveOrUpdate(product);
    }

    @Override
    public Product getProductById(UUID id) {
        return productRepository.findById(id);
    }

    @Override
    public void deleteById(UUID id) {
        productRepository.deleteById(id);
    }
}
