package app.repositories;

import app.domain.Customer;

import java.util.List;

public interface CustomerRepository {

    Customer save(Customer customer);
    List<Customer> findAll();
    Customer findById(Long id);

    Customer update(Customer customer);

    boolean deleteById(Long id);



}
