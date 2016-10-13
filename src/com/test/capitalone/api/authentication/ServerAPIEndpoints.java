package com.test.capitalone.api.authentication;

/*
 * This class should provide access to all API end-points
 * Essentially, these should be loaded from external configurable source. but for the our
 * purpose, the interface would provide static constants.
 */
public class ServerAPIEndpoints {
	public static String LOGIN_URL = "https://2016.api.levelmoney.com/api/v2/core/login";
	public static String GET_ALL_TRANSACTIONS = "https://2016.api.levelmoney.com/api/v2/core/get-all-transactions";
	

}
