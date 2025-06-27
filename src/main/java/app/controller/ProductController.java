package app.controller;

import app.domain.Product;
import app.service.ProductService;

import java.util.List;

public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    public Product save(String name, double price){
       Product product = new Product(name,  price);
       return productService.save(product);
    }
    public List<Product> getAll(){
        return productService.getAllisActiveProduct();
    }
    public Product getById(long id){
        return productService.getProductById(id);
    }
    public void update(Long id, String name, double price){
        Product product = new Product(id, name, price);
         productService.updateProduct(product);
    }
    public void deleteById(Long id){
        productService.deleteProductById(id);
    }
    public void deleteByName(String name){
        productService.deleteByName(name);
    }
    public  void restoreById(Long id){
        productService.restoreProduct(id);
    }
    public long getActiveProductsTotalCount(){
        return productService.getActiveProductTotalCount();
    }
    public long getActiveProductsTotalCost(){
        return (long) productService.getActiveProductTotalCost();
    }
    public double getActiveProductsAveragePrice(){
        return productService.getAverageActiveProductPrice();
    }

}
