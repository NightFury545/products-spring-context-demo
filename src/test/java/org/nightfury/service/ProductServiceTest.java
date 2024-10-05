package org.nightfury.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.fail;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import org.assertj.core.api.AbstractThrowableAssert;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.nightfury.config.ApplicationConfig;
import org.nightfury.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@SpringJUnitConfig(ApplicationConfig.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ProductServiceTest {

    @Autowired
    private ProductService productService;

    @Autowired
    private ApplicationContext context;

    @Test
    @Order(1)
    @DisplayName("ProductService bean exists in the Spring Context")
    public void cartServiceBeanExistsInContext() {
        assertThat(productService).isNotNull();
    }

    @Test
    @Order(2)
    @DisplayName("Get the same ProductService Bean from Spring Context Successfully")
    public void getSameProductServiceBeanFromContextSuccessfully() {
        ProductService productService1 = context.getBean(ProductService.class);
        ProductService productService2 = context.getBean(ProductService.class);

        assertThat(productService1).isEqualTo(productService2);
    }

    @Test
    @Order(3)
    @DisplayName("Save a product successfully")
    public void saveProductSuccessfully() {
        Product product = new Product(UUID.randomUUID(), "Test Product", 55.95);
        productService.saveOrUpdate(product);

        Product savedProduct = productService.getProductById(product.getId());
        assertThat(savedProduct).isNotNull();
        assertThat(savedProduct.getName()).isEqualTo("Test Product");
        assertThat(savedProduct.getPrice()).isEqualTo(55.95);
    }

    @Test
    @Order(4)
    @DisplayName("Update a product successfully")
    public void updateProductSuccessfully() {
        Product product = productService.getProductList().get(0);
        double actualPrice = product.getPrice();
        double expectedPrice = 25.55;
        double updatedPrice;

        product.setPrice(expectedPrice);
        productService.saveOrUpdate(product);
        updatedPrice = productService.getProductList().get(0).getPrice();

        assertThat(updatedPrice).isNotEqualTo(actualPrice);
        assertThat(updatedPrice).isEqualTo(expectedPrice);
    }

    @Test
    @Order(5)
    @DisplayName("Get product list successfully")
    public void getProductListSuccessfully() {
        List<Product>  products = productService.getProductList();
        assertThat(products).isNotNull();
        assertThat(products).isNotEmpty();
    }

    @Test
    @Order(6)
    @DisplayName("Get a product by ID successfully")
    public void getProductByIdSuccessfully() {
        UUID uuid = productService.getProductList().get(1).getId();
        Product product = productService.getProductById(uuid);

        assertThat(product).isNotNull();
    }

    @Test
    @Order(7)
    @DisplayName("Delete a product by ID successfully")
    public void deleteProductByIdSuccessfully() {
        Product product = productService.getProductList().get(3);
        productService.deleteById(product.getId());
        assertThatThrownBy(() -> productService.getProductById(product.getId()))
            .isInstanceOf(NoSuchElementException.class)
            .hasMessageContaining("No such element with ID: " + product.getId());
    }
    
}
