package com.test.capitalone.apiaccess.login;

import com.google.gson.Gson;
import com.test.capitalone.api.authentication.APISession;

public class LoginRequest {

	public String email;
	public String password;
	public APISession args;
	
	public LoginRequest(){}
	
	public String toJson() {
		Gson gson = new Gson();
		return gson.toJson(this);
	}
}
