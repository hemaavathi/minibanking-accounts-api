package com.minibanking.account.api.model;

import lombok.Data;

@Data
public class CustomerApiResponse {

	private Long id;
	private String name;
	private String dob;
	private String address;
	private String mobile;
	private String email;
	private String kycType;
	private String kycNo;

}
