package com.capitalone.service.client;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import com.test.capitalone.api.authentication.ServerAPIEndpoints;
/*
 * Generic simple POST implementation for the Server APIs. Since the session information is part of the POST request .. no additional 
 * authentication headers are necessary and since only JSON data is being posted for all cases, only application/json mimetype is set.
 */
public class PostRequestClient {
	String requestUrl;
	
	public PostRequestClient(String url) {
		this.requestUrl = url;
	}
	
	public String postRequest(String data) throws Exception{
		URL obj = new URL(requestUrl);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		con.setRequestMethod("POST");
		
		con.setDoOutput(true);
		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		wr.writeBytes(data);
		wr.flush();
		wr.close();
		
		int responseCode = con.getResponseCode();
		
		//TODO this logging is only to show the the data transmission to user and can be taken out, if needed.
		
		System.out.println("\nSending 'POST' request to URL : " + this.requestUrl);
		
		System.out.println("\n Posting data :: " + data);
		
		System.out.println("Response Code : " + responseCode);

		BufferedReader in = new BufferedReader(
		        new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
		System.out.println("Response Data from Service :\n " + response.toString());
		
		return response.toString();
	}
}
