package com.customers.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.customers.model.Customers;
import com.customers.service.CustomersService;


@Controller
public class CustomersController {

	@Autowired
	private CustomersService customersService;
	
	// display list of customers
	@GetMapping("/")
	public String viewHomePage(Model model) {
		return findPaginated(1, "firstName", "asc", model);		
	}
	
	@GetMapping("/showNewCustomersForm")
	public String showNewEmployeeForm(Model model) {
		// create model attribute to bind form data
		Customers customers = new Customers();
		model.addAttribute("customers", customers);
		return "new_customers";
	}
	
	@PostMapping("/saveCustomers")
	public String saveCustomers(@ModelAttribute("customers") Customers customers) {
		// save customers to database
		customersService.saveCustomers(customers);
		return "redirect:/";
	}
	
	@GetMapping("/showFormForUpdate/{id}")
	public String showFormForUpdate(@PathVariable ( value = "id") long id, Model model) {
		
		// get Customers from the service
		Customers customers = customersService.getCustomersById(id);
		
		// set customers as a model attribute to pre-populate the form
		model.addAttribute("customers", customers);
		return "update_customers";
	}
	
	@GetMapping("/deleteCustomers/{id}")
	public String deleteCustomers(@PathVariable (value = "id") long id) {
		
		// call delete customers method 
		this.customersService.deleteCustomersById(id);
		return "redirect:/";
	}
	
	
	@GetMapping("/page/{pageNo}")
	public String findPaginated(@PathVariable (value = "pageNo") int pageNo, 
			@RequestParam("sortField") String sortField,
			@RequestParam("sortDir") String sortDir,
			Model model) {
		int pageSize = 5;
		
		Page<Customers> page = customersService.findPaginated(pageNo, pageSize, sortField, sortDir);
		List<Customers> listCustomers = page.getContent();
		
		model.addAttribute("currentPage", pageNo);
		model.addAttribute("totalPages", page.getTotalPages());
		model.addAttribute("totalItems", page.getTotalElements());
		
		model.addAttribute("sortField", sortField);
		model.addAttribute("sortDir", sortDir);
		model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
		
		model.addAttribute("listCustomers", listCustomers);
		return "index";
	}
}

