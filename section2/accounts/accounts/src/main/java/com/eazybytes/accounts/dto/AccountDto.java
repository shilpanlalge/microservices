package com.eazybytes.accounts.dto;


import org.springframework.stereotype.Component;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;

@Schema(
		name="Accounts",
		description="Schema to hold Account information"
		)
@Component
public class AccountDto {

	@Schema(
			description="Account Number of EasyBank account", example="1234567890"
			)
	@NotEmpty(message="AccountNumber can not be a null or empty")
	@Pattern(regexp = "(^$|[0-9]{10})", message="Account number must be 10 digits")
	private Long customerAccount;
	
	@Schema(
			description="AccountType of Easybank", example="savings"
			)
	@NotEmpty(message="AccountType can not be a null or empty")
	private String accountType;
	
	@Schema(
			description="EasyBank branch address", example="Honnavar"
			)
	@NotEmpty(message="BranchAddress can not be a null or empty")
	private String branchAddress;

	public Long getCustomerAccount() {
		return customerAccount;
	}

	public void setCustomerAccount(Long customerAccount) {
		this.customerAccount = customerAccount;
	}

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	public String getBranchAddress() {
		return branchAddress;
	}

	public void setBranchAddress(String branchAddress) {
		this.branchAddress = branchAddress;
	}
	
	
}
