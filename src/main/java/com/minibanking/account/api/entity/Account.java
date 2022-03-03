package com.minibanking.account.api.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.minibanking.account.api.model.AccountRequest;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonAutoDetect(fieldVisibility = Visibility.ANY)
@Entity
public class Account {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "account_generator")
	@SequenceGenerator(name="account_generator", sequenceName="account_no", initialValue=20000, allocationSize=1)
	@Column(name="acno", updatable = false, nullable = false)
	private Long acNo;

	@Column(name="actype")
	private String acType;
	
	private String bsb;
	
	@Column(name="payid")
	private String payId;
	
	@Column(name="customerid")
	private Long customerId;
	
	private Long balance;
	
	private String status;
	

	public Account(AccountRequest accountRequest) {
		this.acType = accountRequest.getAcType();
		this.bsb = accountRequest.getBsb();
		this.payId = accountRequest.getPayId();
	}

}
