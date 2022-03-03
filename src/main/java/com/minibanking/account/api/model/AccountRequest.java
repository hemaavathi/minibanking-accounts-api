package com.minibanking.account.api.model;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountRequest {
	@NotBlank(message = "Account Type cannot be empty")
	private String acType;
	
	@NotBlank(message = "BSB cannot be empty")
	private String bsb;
	
	@NotBlank(message = "PayId cannot be empty")
	private String payId;
		
}
