package com.eazybytes.accounts.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.eazybytes.accounts.constants.AccountConstants;
import com.eazybytes.accounts.dto.CustomerDto;
import com.eazybytes.accounts.dto.ErrorResponseDto;
import com.eazybytes.accounts.dto.ResponseDto;
import com.eazybytes.accounts.service.IAccountsService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;

@Tag(
	name = "CRUD REST APIs for Accounts in EasyBank"	,
	description="CRUD REST APIs in EasyBank to CREATE, UPDATE, FETCH AND DELETE account details"
		)
@RestController
@RequestMapping(path = "/api", produces = {MediaType.APPLICATION_JSON_VALUE})
@Validated
public class AccountController {

	@Autowired
	private IAccountsService iAccountsService;
	
	@ApiResponse(
			responseCode="201",
			description="HTTP Status CREATED"
			)
	@Operation(
			summary="Create Account REST API",
			description="REST API to create new Customer & Account inside EasyBank"
			)
	@PostMapping("/create")
	public ResponseEntity<ResponseDto> createAccount(@Valid @RequestBody CustomerDto customerDto){
		 iAccountsService.createAccount(customerDto);
		return ResponseEntity
				.status(HttpStatus.CREATED)
				.body(new ResponseDto(AccountConstants.STATUS_201, AccountConstants.MESSAGE_201));
	}
	
	@ApiResponses({
		@ApiResponse(
				responseCode="200",
				description="HTTP Status OK"
				),
				@ApiResponse(	
						responseCode="500",
							description="HTTP Status Internal Server Error"
							)
	})
	
	@Operation(
			summary="Fetch Account Details REST API",
			description="REST API to fetch Customer & Account details based on a mobile number"
			)
	@GetMapping("/fetch")
	public ResponseEntity<CustomerDto> fetchAccountDetails(@RequestParam 
			@Pattern(regexp = "(^$|[0-9]{10})", message="Mobile number must be 10 digits")
			String mobileNumber){
		CustomerDto customerDto = iAccountsService.fetchAccount(mobileNumber);
		return ResponseEntity.status(HttpStatus.OK).body(customerDto);
	}
	
	
	@ApiResponses({
		@ApiResponse(	
		responseCode="200",
			description="HTTP Status OK"
			),
		@ApiResponse(	
				responseCode="417",
					description="Exception Failed"
					),
	@ApiResponse(
			responseCode="500",
			description="HTTP Status Internal Server Error",
			content=@Content(
					schema = @Schema(implementation = ErrorResponseDto.class)
					)
			)
	})
	@Operation(
			summary="Update Account REST API",
			description="REST API to update Customer & Account details based ona account number"
			)
	@PutMapping("/update")
	public ResponseEntity<ResponseDto> updateAccountDetails(@Valid @RequestBody CustomerDto customerDto){
		boolean isUpdated= iAccountsService.updateAccount(customerDto);
		if(isUpdated) {
			return ResponseEntity
					.status(HttpStatus.OK)
					.body(new ResponseDto(AccountConstants.STATUS_200, AccountConstants.MESSAGE_200));
		}else {
			return ResponseEntity
					.status(HttpStatus.EXPECTATION_FAILED)
					.body(new ResponseDto(AccountConstants.STATUS_417, AccountConstants.MESSAGE_417_UPDATE));
		}
	}
	
	
	@ApiResponses({
		@ApiResponse(	
		responseCode="200",
			description="HTTP Status OK"
			),
		@ApiResponse(	
				responseCode="417",
					description="Exception Failed"
					),
	@ApiResponse(
			responseCode="500",
			description="HTTP Status Internal Server Error"
			)
	})
	@Operation(
			summary="Delete Account & Customer details REST API",
			description="REST API to delete Customer & Account details based ona account number"
			)
	@DeleteMapping("/delete")
	public ResponseEntity<ResponseDto> deleteAccountDetails(@RequestParam 
			@Pattern(regexp = "(^$|[0-9]{10})", message="Mobile number must be 10 digits")
			String mobileNumber){
		boolean isDeleted = iAccountsService.deleteAccount(mobileNumber);
		if(isDeleted) {
			return ResponseEntity
					.status(HttpStatus.OK)
					.body(new ResponseDto(AccountConstants.STATUS_200, AccountConstants.MESSAGE_200));
		}else {
			return ResponseEntity
					.status(HttpStatus.EXPECTATION_FAILED)
					.body(new ResponseDto(AccountConstants.STATUS_417, AccountConstants.MESSAGE_417_DELETE));
		}
	}
	
	
}
