package com.minibanking.account.api.model;

import java.util.Map;

import lombok.Data;

@Data
public class ApiResponse {

	private Map<String, ?> data;
}
