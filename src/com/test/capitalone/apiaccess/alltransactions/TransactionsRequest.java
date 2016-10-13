package com.test.capitalone.apiaccess.alltransactions;

import com.google.gson.Gson;
import com.test.capitalone.api.authentication.APISession;

public class TransactionsRequest {
	public APISession args;
	
	public String toJson() {
		Gson gson = new Gson();
		return gson.toJson(this);
	}

}
