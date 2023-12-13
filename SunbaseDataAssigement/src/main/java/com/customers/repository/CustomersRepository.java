package com.customers.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.customers.model.Customers;

@Repository
public interface CustomersRepository extends JpaRepository<Customers, Long> {

}
