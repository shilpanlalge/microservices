package com.eazybytes.accounts.service.Impl;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eazybytes.accounts.cexception.CustomerAlreadyExitsException;
import com.eazybytes.accounts.cexception.ResourceNotFoundException;
import com.eazybytes.accounts.constants.AccountConstants;
import com.eazybytes.accounts.dto.AccountDto;
import com.eazybytes.accounts.dto.CustomerDto;
import com.eazybytes.accounts.entity.Accounts;
import com.eazybytes.accounts.entity.Customer;
import com.eazybytes.accounts.mapper.AccountsMapper;
import com.eazybytes.accounts.mapper.CustomerMapper;
import com.eazybytes.accounts.repository.AccountRepository;
import com.eazybytes.accounts.repository.CustomerRepository;
import com.eazybytes.accounts.service.IAccountsService;

@Service
public class AccountServiceImpl implements IAccountsService{

	@Autowired
	private AccountRepository accountRepository;
	@Autowired
	private CustomerRepository customerRepository;
	
	
	// @param customerDto -customerDto Object
	@Override
	public void createAccount(CustomerDto customerDto) {
		Customer customer = CustomerMapper.mapToCustomer(customerDto, new Customer());
		Optional<Customer> optionalCustomer = customerRepository.findByMobileNumber(customerDto.getMobileNumber());
		if(optionalCustomer.isPresent()) {
			throw new CustomerAlreadyExitsException("Customer already registered with given mobileNumber"
					+customerDto.getMobileNumber());
		}
//		customer.setCreatedAt(LocalDateTime.now());
//		customer.setCreatedBy("Anonymous");
		Customer savedCustomer = customerRepository.save(customer);
		accountRepository.save(CreateNewAccount(savedCustomer));
	}

	// @param customer -Customer Object
	// @return the new account details
	
	private Accounts CreateNewAccount(Customer customer) {
		Accounts newAccount= new Accounts();
		newAccount.setCustomerId(customer.getCustomerId());
		long randomAccnumber=1000000000L+new Random().nextInt(900000000);
		
		newAccount.setCustomerAccount(randomAccnumber);
		newAccount.setAccountType(AccountConstants.SAVING);
		newAccount.setBranchAddress(AccountConstants.ADDRESS);
//		newAccount.setCreatedAt(LocalDateTime.now());
//		newAccount.setCreatedBy("Anonymous");
		return newAccount;
	}

	@Override
	public CustomerDto fetchAccount(String mobileNumber) {
		Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
		()-> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber)
		);
		Accounts accounts = accountRepository.findByCustomerId(customer.getCustomerId()).orElseThrow(
				()-> new ResourceNotFoundException("Account", "customerId", customer.getCustomerId().toString())
				);
		CustomerDto customerDto = CustomerMapper.mapToCustomerDto(customer, new CustomerDto());
		customerDto.setAccountDto(AccountsMapper.mapToAccountDto(accounts, new AccountDto()));
		return customerDto;
	}

	@Override
	public boolean updateAccount(CustomerDto customerDto) {
		boolean isUpdated=false;
		AccountDto accountDto=customerDto.getAccountDto();
		if(accountDto!=null) {
			Accounts accounts = accountRepository.findById(accountDto.getCustomerAccount()).orElseThrow(
					()-> new ResourceNotFoundException("Account", "AccountNumber", accountDto.getCustomerAccount().toString())
					);
			AccountsMapper.mapToAccounts(accountDto, accounts);
			accounts=accountRepository.save(accounts);
			
			Long customerId=accounts.getCustomerId();
			Customer customer = customerRepository.findById(customerId).orElseThrow(
					()-> new ResourceNotFoundException("Customer", "CustomerId", customerId.toString())
					);
			CustomerMapper.mapToCustomer(customerDto, customer);
			customerRepository.save(customer);
		 isUpdated = true;
		}
		return isUpdated;
	}

	@Override
	public boolean deleteAccount(String mobileNumber) {
      Customer customer=customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
    		  ()-> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber)
    		  );
		accountRepository.deleteByCustomerId(customer.getCustomerId());
		customerRepository.deleteById(customer.getCustomerId());
		return true;
	}

}

