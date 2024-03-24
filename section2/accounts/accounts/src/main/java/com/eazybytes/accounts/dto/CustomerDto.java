package com.eazybytes.accounts.dto;


import org.springframework.stereotype.Component;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Schema(
		name="Customer",
		description="Schema to hold Customer and Account information"
		)
@Component
public class CustomerDto {

	@Schema(
			description="Name of the customer", example="Easy Byte"
			)
	@NotEmpty(message="Name can not a null or empty")
	@Size(min=5, max=30, message="The lengthof the customer name should be between 5 and 30")
	private String name;
	
	@Schema(
			description="Email address of the customer", example="navi@easybyte.com"
			)
	@Email(message="Email address should be a valid value")
	@NotEmpty(message="Email address can not be a null or empty")
	private String email;
	
	@Schema(
			description="Mobile Number of the customer", example="9832436474"
			)
	@Pattern(regexp = "(^$|[0-9]{10})", message="Mobile number must be 10 digits")
	private String mobileNumber;
	
	@Schema(
			description="Account details of the customer", example="Easy Byte"
			)
	private AccountDto accountDto;

	public AccountDto getAccountDto() {
		return accountDto;
	}

	public void setAccountDto(AccountDto accountDto) {
		this.accountDto = accountDto;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}
	
	
}
