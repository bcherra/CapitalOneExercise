package com.test.capitalone.api.authentication;

/*
 * This class encapsulate API log-in credentials. Essentially, this information should be loaded from configurable resource.
 * uid and token are updated when user logs on. apiToken is the main key used to authenticate the client application and session with the server
 */
public class APILoginCredentials {
	double uid = 1110590645;
	String token = "5AC1A19B3F4BC97696E31F62A6CE8882";
	String apiToken = "AppTokenForInterview";

	public APILoginCredentials(double uid, String authToken, String apiToken) {
		init(uid, authToken, apiToken);
	}
	public APILoginCredentials(String uid, String authToken, String apiToken) {
		double d;
		try {
			d = Double.parseDouble(uid);
			
		} catch(NumberFormatException exp) {
			d = 0;
		}
		init(d, authToken, apiToken);
	}
	public APILoginCredentials() {
		//default
	};
	private void init(double uid, String authToken, String apiToken) {
		this.uid = uid;
		this.token = authToken;
		this.apiToken = apiToken;
	}
	public double getUid() {
		return uid;
	}
	public String getToken() {
		return token;
	}
	public String getApiToken() {
		return apiToken;
	}
	public void setUid(double uid) {
		this.uid = uid;
	}
	public void setToken(String token) {
		this.token = token;
	}
}
