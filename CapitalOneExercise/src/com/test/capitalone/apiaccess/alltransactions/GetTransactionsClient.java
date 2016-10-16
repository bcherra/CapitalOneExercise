package com.test.capitalone.apiaccess.alltransactions;

import com.capitalone.service.client.PostRequestClient;
import com.test.capitalone.api.authentication.ServerAPIEndpoints;

public class GetTransactionsClient {
	String getTransactionsAPIUrl = ServerAPIEndpoints.GET_ALL_TRANSACTIONS;
	PostRequestClient httpClient;
	
	public GetTransactionsClient(String url) {
		this.getTransactionsAPIUrl = url;
	}
	public GetTransactionsClient() {
		this(ServerAPIEndpoints.GET_ALL_TRANSACTIONS);
	}
	
	public TransactionsResponse getTransactions(TransactionsRequest req) {
		
		if(httpClient == null) {
			httpClient = new PostRequestClient(this.getTransactionsAPIUrl);
			
		}
		try {
			String response = httpClient.postRequest(req.toJson());
			return TransactionsResponse.parseJson(response);
			
		} catch (Exception e) {
			
			e.printStackTrace();
			return null;
		}
	}
}
