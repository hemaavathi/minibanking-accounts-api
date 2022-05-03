package com.minibanking.account.api.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WithdrawRequest {
    @NotBlank(message = "fromAccountNo cannot be blank")
    private Long fromAccountNo;
    @NotBlank(message = "FromCustomerId cannot be blank")
    private Long fromCustomerId;
    @NotBlank(message = "Amount cannot be blank")
    private Long amount;
}
