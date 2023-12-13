package com.customers.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.customers.model.Customers;



public interface CustomersService {
	List<Customers> getAllCustomers();
	void saveCustomers(Customers customers);
	Customers getCustomersById(long id);
	void deleteCustomersById(long id);
	Page<Customers> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection);

}
