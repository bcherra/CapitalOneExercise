package com.test.capitalone.apiaccess.login;

import com.capitalone.service.client.PostRequestClient;
import com.test.capitalone.api.authentication.ServerAPIEndpoints;
/*
 * Sends POST request to the Login API.
 */
public class LoginClient {

	String loginUrl = ServerAPIEndpoints.LOGIN_URL;
	PostRequestClient httpClient;
	/*
	 * @input the URL for the login API.
	 * If none is passed the default provided in the ServerAPIEndpoints will be used.
	 * This constructor can be used to override default Urls.
	 */
	public LoginClient(String loginUrl) {
		this.loginUrl = loginUrl;
	}
	/*
	 * 
	 */
	public LoginClient() {
		this.loginUrl = ServerAPIEndpoints.LOGIN_URL;
	}
	/*
	 * Login to the server API system.
	 */
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
