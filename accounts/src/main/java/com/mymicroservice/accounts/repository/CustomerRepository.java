package com.mymicroservice.accounts.repository;

import com.mymicroservice.accounts.entity.Customer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerRepository extends CrudRepository<Customer,Long> {

    Optional<Customer> findByMobileNumber(String mobileNumber);
}
