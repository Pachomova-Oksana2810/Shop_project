package app.service;

import app.domain.Customer;
import app.domain.Product;
import app.exceptions.CustomerNotFoundException;
import app.exceptions.CustomerSaveException;
import app.exceptions.CustomerUpdateException;
import app.exceptions.ProductNotFoundException;
import app.repositories.CustomerRepository;
import app.repositories.ProductRepository;

import java.util.List;
import java.util.stream.Collectors;

public class CustomerServiceImpl implements CustomerService{

    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository, ProductRepository productRepository) {
        this.customerRepository = customerRepository;
        this.productRepository = productRepository;
    }

    @Override
    public Customer save(Customer customer) {
        if (customer == null){
            throw new CustomerSaveException("Customer cannot be null");
        }
        String name = customer.getName();
        if (name == null || name.trim().isEmpty() || name.length() < 2){
            throw new CustomerSaveException("Customer name must be at least 2 characters long");
        }
        customer.setActive(true);
        return customerRepository.save(customer);
    }

    @Override
    public List<Customer> getAllActiveCustomer() {
        return customerRepository.findAll().stream()
                .filter(customer -> customer.isActive())
                .collect(Collectors.toList());
    }

    @Override
    public Customer getCustomerById(long id) {
        Customer customer = customerRepository.findById(id);
        if (customer == null || !customer.isActive()){
            throw new CustomerNotFoundException("Customer with id = " + id + " not found");
        }
        return customer;
    }
    @Override
    public void updateCustomer(Customer customer){
        if (customer == null){
            throw new CustomerUpdateException("Customer cannot be null");
        }
        String name = customer.getName();
        if (name == null || name.trim().isEmpty() || name.length() < 2){
            throw new CustomerUpdateException("Customer name must be at least 2 characters long");
        }
        customerRepository.update(customer);
    }

    @Override
    public void deleteCustomerById(long id) {
        getCustomerById(id).setActive(false);

    }

    @Override
    public void deleteByName(String name) {
        Customer customer = getAllActiveCustomer().stream()
                .filter(c -> c.getName().equals(name))
                .findFirst()
                .orElseThrow(() -> new CustomerNotFoundException("Customer with name = " + name + " not found"));

        customer.setActive(false);

    }

    @Override
    public void restoreCustomer(long id) {
        Customer customer = customerRepository.findById(id);
        if (customer == null){
            throw new CustomerNotFoundException("Customer with id = " + id + " not found");
        }
        customer.setActive(true);
    }

    @Override
    public long getActiveCustomerCount(long customerId) {
        return getAllActiveCustomer().size();
    }

    @Override
    public double getCustomerCartTotalCost(long customerId) {
        Customer customer = getCustomerById(customerId);
        return  customer.getProducts().stream()
                .filter(p -> p.isActive())
                .mapToDouble(p -> p.getPrice())
                .sum();
    }

    @Override
    public double getCustomerCartAveragePrice(long customerId) {
        List<Product> cart = getCustomerById(customerId).getProducts()
                .stream()
                .filter(p -> p.isActive())
                .collect(Collectors.toList());
        return cart.stream()
                .mapToDouble(p -> p.getPrice())
                .average()
                .orElse(0.0);
    }

    @Override
    public boolean addProductToCustomerCart(long customerId, long productId) {
        Customer customer = getCustomerById(customerId);
        Product product = productRepository.findById(productId);

        if (product == null || !product.isActive()){
            throw new ProductNotFoundException("Product with id = " + productId + " not found or inactive");
        }
        customer.getProducts().add(product);
        return true;
    }

    @Override
    public boolean removeProductFromCustomerCart(long customerId, long productId) {
        Customer customer = getCustomerById(customerId);
        List<Product> cart = customer.getProducts();

        boolean removed = cart.removeIf(p -> p.getId() == productId);
        if (!removed) {
            throw new ProductNotFoundException("Product with id = " + productId + " not found in customer cart");
        }
        return true;
    }

    @Override
    public boolean clearCustomerCart(long customerId) {
        Customer customer = getCustomerById(customerId);
        customer.getProducts().clear();
        return true;
    }

    @Override
    public List<Product> getCustomerCartProduct(long customerId) {
        return getCustomerById(customerId).getProducts()
                .stream()
                .filter(p -> p.isActive())
                .collect(Collectors.toList());

    }
}
