package com.test.capitalone.apiaccess.login;

import com.google.gson.Gson;

public class LoginResponse {

	public String error = "no-error";
	public String uid;// uid for the given email in request.
	public String token; //session/authentication token
	
	public LoginResponse(){}
	
	public static synchronized LoginResponse parseJson(String json) {
		Gson gson = new Gson();
		return gson.fromJson(json, LoginResponse.class);
	}
}
