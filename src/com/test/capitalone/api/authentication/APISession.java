package com.test.capitalone.api.authentication;

import com.google.gson.annotations.SerializedName;

/*
 * A Singleton Class to represent and encapsulate session information required for requests to the API.
 */
public final class APISession {
	private double uid = 0;
	private String token = "";
	@SerializedName("api-token")
	private String apiToken;
	@SerializedName("json-strict-mode")
	private boolean jsonStrictMode = false;
	@SerializedName("json-verbose-response")
	private boolean jsonVerboseResponse = false;
	
	
	public APISession(double uid, String token, String apiToken, boolean jsonStrictMode, boolean jsonVerboseResponse) {
		init(uid, token, apiToken, jsonStrictMode, jsonVerboseResponse);
	}
	public APISession(String uid, String token, String apiToken) {
		double d;
		try {
			d = Double.parseDouble(uid);
			
		} catch(NumberFormatException exp) {
			d = 0;
		}
		init( d, token, apiToken, false, false);
	}
	public APISession(double uid, String authToken, String apiToken) {
		
		init( uid, authToken, apiToken, false, false);
	}
	
	public APISession(String apiToken) {
		
		init( 0, "", apiToken, false, false);
	}
	
	
	private void init(double uid, String token, String apiToken, boolean jsm, boolean jvr) {
		
		this.uid = uid;
		this.token = token;
		this.apiToken = apiToken;
		this.jsonStrictMode = jsm;
		this.jsonVerboseResponse = jvr;
	}
	public double getUid() {
		return uid;
	}
	public String getToken() {
		return token;
	}
	public String getApitoken() {
		return apiToken;
	}
	public boolean isJsonStrictMode() {
		return jsonStrictMode;
	}
	public boolean isJsonVerboseResponse() {
		return jsonVerboseResponse;
	}
	public void setUid(double uid) {
		this.uid = uid;
	}
	public void setUid(String uid) {
		double d;
		try {
			d = Double.parseDouble(uid);
			
		} catch(NumberFormatException exp) {
			d = 0;
		}
		this.uid = d;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public void setApiToken(String apiToken) {
		this.apiToken = apiToken;
	}
	public void setJsonStrictMode(boolean jsonStrictMode) {
		this.jsonStrictMode = jsonStrictMode;
	}
	public void setJsonVerboseResponse(boolean jsonVerboseResponse) {
		this.jsonVerboseResponse = jsonVerboseResponse;
	}
	
	
	
}
