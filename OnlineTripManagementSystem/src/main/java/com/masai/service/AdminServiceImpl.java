package com.masai.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.masai.exceptions.CustomerException;
import com.masai.models.CurrentUserSession;
import com.masai.models.Customer;
import com.masai.repository.CustomerDAO;
import com.masai.repository.SessionDAO;

@Service
public class AdminServiceImpl implements AdminService {
	
	@Autowired
	private CustomerDAO customerD;
	
	
	
	@Autowired
	private SessionDAO sessionD;
	
	
	
	@Override
	public Customer addCustomer(Customer cust) throws CustomerException {
		
		Customer existCustomer = customerD.findByEmail(cust.getEmail());
		
		if(existCustomer!=null) {
			throw new CustomerException("Customer already exists");
		}
		
		Customer new_cust = customerD.save(cust);
		
		return customerD.save(new_cust);
		
	}
	

	@Override
	public Customer updateCustomer(Customer cust) throws CustomerException {
		
		Customer existCustomer = customerD.findByEmail(cust.getEmail());
		
		if(existCustomer!=null) {
			Customer new_cust = customerD.save(cust);
			
			return customerD.save(new_cust);
		}
		else
		{
			return null;
		}
		
		
		
	}
	

	@Override
	public Customer removeCustomer(Integer custId) throws CustomerException {
		
		Optional<Customer> custOpt = customerD.findById(custId);
		
		if(custOpt!=null) {
			customerD.delete(custOpt.get());
			
			return custOpt.get();
		}
		else {
			throw new CustomerException("Invalid customer ID");
		}
					
	}
	

	@Override
	public Customer viewCustomer(Integer custId) throws CustomerException {
		
		Optional<Customer> custOpt = customerD.findById(custId);
		
		if(custOpt.isPresent())
			return custOpt.get();
		
		else
			throw new CustomerException("Customer is not found with given customerId "+custId);
	}

	@Override
	public List<Customer> viewAllCustomer(String location) throws CustomerException {
		
		List<Customer> customers = customerD.viewAllCustomer(location);
		
		if(customers.isEmpty())
			throw new CustomerException("Customer is not found with given location: "+location);
		
		else {
			List<Customer> customerList = new ArrayList<>(customers);
			
			return customerList;
		}
	}

	
	


}
