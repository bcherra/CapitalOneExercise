package com.test.capitalone.apiaccess.alltransactions;

import com.google.gson.Gson;

public class TransactionsResponse {
	public String error;
	public Transaction[] transactions;
	
	public static TransactionsResponse parseJson(String json) {
		Gson gson = new Gson();
		return gson.fromJson(json,TransactionsResponse.class);
	}
	public String toJson() {
		Gson gson = new Gson();
		return gson.toJson(this);
	}
}
