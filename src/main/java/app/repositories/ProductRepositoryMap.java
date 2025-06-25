package app.repositories;

import app.domain.Product;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ProductRepositoryMap implements ProductRepository{

    private final Map<Long, Product> database = new HashMap<>();
    private long currentId = 0;

    @Override
    public Product save(Product product) {
        product.setId(++currentId);
        database.put(currentId, product);
        return product;
    }

    @Override
    public List<Product> findAll() {
        return new ArrayList<>(database.values());
    }

    @Override
    public Product findById(Long id) {
        return database.get(id);
    }

    @Override
    public boolean deleteById(Long id) {
        Product oldProduct = findById(id);

        if (oldProduct == null){
            ;return false;
        }
        oldProduct.setActive(false);
        return true;
    }

    @Override
    public Product updateById(Product product) {
        Long id = product.getId();
        double newPrice = product.getPrice();
        String newName = product.getName();

        Product oldProduct = findById(id);

        if (oldProduct != null){
            oldProduct.setName(newName);
            oldProduct.setPrice(newPrice);
        }
        return oldProduct;
    }
}
