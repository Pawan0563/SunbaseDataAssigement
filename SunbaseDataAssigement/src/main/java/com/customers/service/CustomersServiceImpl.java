package com.customers.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.customers.model.Customers;
import com.customers.repository.CustomersRepository;


@Service
public class CustomersServiceImpl implements CustomersService {

	@Autowired
	private CustomersRepository customersRepository;

	@Override
	public List<Customers> getAllCustomers() {
		return customersRepository.findAll();
	}

	@Override
	public void saveCustomers(Customers customers) {
		this.customersRepository.save(customers);
	}

	@Override
	public Customers getCustomersById(long id) {
		Optional<Customers> optional = customersRepository.findById(id);
		Customers customers = null;
		if (optional.isPresent()) {
			customers = optional.get();
		} else {
			throw new RuntimeException(" Employee not found for id :: " + id);
		}
		return customers;
	}

	@Override
	public void deleteCustomersById(long id) {
		this.customersRepository.deleteById(id);
	}

	@Override
	public Page<Customers> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection) {
		Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() :
			Sort.by(sortField).descending();
		
		Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
		return this.customersRepository.findAll(pageable);
	}
}