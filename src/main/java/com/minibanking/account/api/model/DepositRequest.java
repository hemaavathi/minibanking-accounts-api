package com.minibanking.account.api.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DepositRequest {
    @NotBlank(message = "ToAccountNo cannot be blank")
    private Long toAccountNo;
    private String toAccountName;
    @NotBlank(message = "Amount cannot be blank")
    private Long amount;

}
