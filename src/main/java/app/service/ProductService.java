package app.service;

import app.domain.Product;

import java.util.List;

public interface ProductService {

    Product save(Product product);

    List<Product> getAllisActiveProduct();

    Product getProductById(long id);

    void  updateProduct(Product product);

    void deleteProductById(long id);

    void deleteByName(String name);

    void restoreProduct(long id);

    long getActiveProductTotalCount();

    double getActiveProductTotalCost();

    double getAverageActiveProductPrice();
}
