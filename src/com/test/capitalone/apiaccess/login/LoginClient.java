package com.test.capitalone.apiaccess.login;

import com.capitalone.service.client.PostRequestClient;
import com.test.capitalone.api.authentication.ServerAPIEndpoints;

public class LoginClient {

	String loginUrl = ServerAPIEndpoints.LOGIN_URL;
	PostRequestClient httpClient;
	
	public LoginClient(String loginUrl) {
		this.loginUrl = loginUrl;
	}
	public LoginClient() {
		this.loginUrl = ServerAPIEndpoints.LOGIN_URL;
	}
	public LoginResponse doLogin(LoginRequest lReq) {
		if(httpClient == null) {
			httpClient = new PostRequestClient(loginUrl);
		}
		try {
			String response = httpClient.postRequest(lReq.toJson());
			LoginResponse lRes = LoginResponse.parseJson(response);
			
			return lRes;
		} catch (Exception e) {
			
			e.printStackTrace();
			return null;
		}
	}
}
