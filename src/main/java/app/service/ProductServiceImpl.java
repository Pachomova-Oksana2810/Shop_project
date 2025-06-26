package app.service;

import app.domain.Product;
import app.exceptions.ProductNotFoundException;
import app.exceptions.ProductSaveException;
import app.exceptions.ProductUpdateException;
import app.repositories.ProductRepository;

import java.util.List;
import java.util.stream.Collectors;

public class ProductServiceImpl implements ProductService{

    private final ProductRepository repository;

    public ProductServiceImpl(ProductRepository repository) {
        this.repository = repository;
    }

    @Override
    public Product save(Product product) {
        if (product == null){
            throw new ProductSaveException("Product cannot be null");
        }
        String name = product.getName();
        if (name == null || name.trim().isEmpty() || name.length() < 3){
            throw new ProductSaveException("Product name should be at least 3 characters lang");

        }
        if (product.getPrice() <= 0){
            throw new ProductSaveException("Product price cannot be negative and zero");
        }
        product.setActive(true);
        return repository.save(product);
    }

    @Override
    public List<Product> getAllisActiveProduct() {
        return repository.findAll().stream()
                .filter(x -> x.isActive())
                .collect(Collectors.toList());
    }

    @Override
    public Product getProductById(long id) {
        Product product = repository.findById(id);
        if (product == null || !product.isActive()){
            throw new ProductNotFoundException("Product with id = " + id + "not found");
        }
        return product;
    }

    @Override
    public void updateProduct(Product product) {
        if (product ==null){
            throw new ProductUpdateException("Product cannot be null");
        }
        Long id = product.getId();
        if (id == null || id <0 ){
            throw new ProductUpdateException("Product id should be positive ");
        }
        String name = product.getName();
        if (name == null || name.trim().isEmpty() || name.length() < 3){
            throw new ProductUpdateException("Product name should be at least 3 characters lang");

        }
        if (product.getPrice() <= 0){
            throw new ProductUpdateException("Product price cannot be negative and zero");
        }
        repository.updateById(product);

    }

    @Override
    public void deleteProductById(long id) {
        getProductById(id).setActive(false);

    }

    @Override
    public void deleteByName(String name) {
        Product product = getAllisActiveProduct()
                .stream()
                .filter(product1 -> product1.getName().equals(name))
                .findFirst()
                .orElse(null);

        if (product  == null){
            throw new ProductNotFoundException("Product with name = " + name + "not found");
        }
        product.setActive(false);

    }

    @Override
    public void restoreProduct(long id) {
        getProductById(id).setActive(true);

    }

    @Override
    public long getActiveProductTotalCount() {
        return getAllisActiveProduct().size();
    }

    @Override
    public double getActiveProductTotalCost() {
        return getAllisActiveProduct().stream()
                .mapToDouble(p -> p.getPrice())
                .sum();
    }

    @Override
    public double getAverageActiveProductPrice() {
        return getAllisActiveProduct().stream()
                .mapToDouble(p -> p.getPrice())
                .average()
                .orElse(0);
    };

}







