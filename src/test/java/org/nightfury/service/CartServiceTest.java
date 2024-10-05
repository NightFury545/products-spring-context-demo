package org.nightfury.service;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.nightfury.config.ApplicationConfig;
import org.nightfury.entity.Cart;
import org.nightfury.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@SpringJUnitConfig(ApplicationConfig.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CartServiceTest {

    @Autowired
    private CartService cartService;

    @Autowired
    private ProductService productService;

    @Autowired
    private ApplicationContext context;

    @Test
    @Order(1)
    @DisplayName("CartService bean exists in the Spring Context")
    public void cartServiceBeanExistsInContext() {
        assertThat(cartService).isNotNull();
    }

    @Test
    @Order(2)
    @DisplayName("Get new cart from Spring Context Successfully")
    public void getNewCartFromContextSuccessfully() {
        Cart cart = context.getBean(Cart.class);

        assertThat(cart).isNotNull();
    }

    @Test
    @Order(3)
    @DisplayName("Each request for Cart creates a new instance")
    public void newCartIsCreatedEachTimeRequested() {
        Cart cart1 = context.getBean(Cart.class);
        Cart cart2 = context.getBean(Cart.class);

        assertThat(cart1).isNotEqualTo(cart2);
    }

    @Test
    @Order(4)
    @DisplayName("Add product to Cart successfully")
    public void productIsAddedToCart() {
        Cart cart = context.getBean(Cart.class);
        Product product = productService.getProductList().get(0);

        cartService.addProduct(cart, product, 5);

        assertThat(cart.getCartMap()).isNotEmpty();
    }

    @Test
    @Order(5)
    @DisplayName("Remove product from Cart successfully")
    public void productIsRemovedFromCart() {
        Cart cart = context.getBean(Cart.class);
        Product product = productService.getProductList().get(0);
        cartService.addProduct(cart, product, 5);

        cartService.delProduct(cart, product, 5);

        assertThat(cart.getCartMap()).isEmpty();
    }

    @Test
    @Order(6)
    @DisplayName("Calculate total quantity and sum of products in Cart")
    public void totalQuantityAndSumIsCalculatedCorrectly() {
        Cart cart = context.getBean(Cart.class);
        Product product1 = productService.getProductList().get(1);
        Product product2 = productService.getProductList().get(2);

        cartService.addProduct(cart, product1, 3);
        cartService.addProduct(cart, product2, 7);

        double expectedSum = product1.getPrice() * 3 + product2.getPrice() * 7;
        double actualSum = cartService.getSum(cart).doubleValue();

        assertThat(actualSum).isEqualTo(expectedSum);
    }
}
