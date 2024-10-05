package org.nightfury.service.impl;

import java.math.BigDecimal;
import java.util.UUID;
import org.nightfury.entity.Cart;
import org.nightfury.entity.Product;
import org.nightfury.repository.ProductRepository;
import org.nightfury.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartServiceImpl implements CartService {

    private final ProductRepository productRepository;

    @Autowired
    public CartServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Cart getNewCart() {
        return new Cart();
    }

    @Override
    public void addProduct(Cart cart, Product product, Integer quantity) {
        cart.addProduct(product, quantity);
    }

    @Override
    public void addProduct(Cart cart, UUID prodId, Integer quantity) {
        Product product = productRepository.findById(prodId);
        cart.addProduct(product, quantity);
    }

    @Override
    public void delProduct(Cart cart, Product product, Integer quantity) {
        cart.delProduct(product, quantity);
    }

    @Override
    public BigDecimal getSum(Cart cart) {
        return BigDecimal.valueOf(cart.getSum());
    }

    @Override
    public void printCart(Cart cart) {
        System.out.println(cart.toString());
    }

    @Override
    public int getProductQuantity(Cart cart, Product product) {
       return cart.getCartMap().get(product);
    }

    @Override
    public int getProductQuantity(Cart cart, UUID prodId) {
        Product product = productRepository.findById(prodId);
        return cart.getCartMap().get(product);
    }
}
