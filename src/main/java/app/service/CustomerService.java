package app.service;

import app.domain.Customer;
import app.domain.Product;

import java.util.List;

public interface CustomerService {

    Customer save(Customer customer);

    List<Customer> getAllActiveCustomer();

    Customer getCustomerById(long id);

    void updateCustomer(Customer customer);

    void deleteCustomerById(long id);

    void deleteByName(String name);

    void restoreCustomer(long id);

    long getActiveCustomerCount(long customerId);

    double getCustomerCartTotalCost(long customerId);

    double getCustomerCartAveragePrice(long customerId);

    boolean addProductToCustomerCart(long customerId, long productId);

    boolean removeProductFromCustomerCart(long customerId, long productId);

    boolean clearCustomerCart(long customerId);

    List<Product> getCustomerCartProduct(long customerId);
}
