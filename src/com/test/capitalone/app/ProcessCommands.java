package com.test.capitalone.app;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

import com.google.gson.Gson;
import com.test.capitalone.api.authentication.APILoginCredentials;
import com.test.capitalone.api.authentication.APISession;
import com.test.capitalone.api.authentication.ServerAPIEndpoints;
import com.test.capitalone.apiaccess.alltransactions.AllTransactionCalculations;
import com.test.capitalone.apiaccess.alltransactions.GetTransactionsClient;
import com.test.capitalone.apiaccess.alltransactions.TransactionsRequest;
import com.test.capitalone.apiaccess.alltransactions.TransactionsResponse;
import com.test.capitalone.apiaccess.login.LoginClient;
import com.test.capitalone.apiaccess.login.LoginRequest;
import com.test.capitalone.apiaccess.login.LoginResponse;

public class ProcessCommands {
	private APISession _session = new APISession((new APILoginCredentials()).getApiToken());
	private Scanner scanner = new Scanner(System.in);
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ProcessCommands command = new ProcessCommands();
		try {
			
			command.establishSession();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Eneter any of the following Commands.");
		System.out.println("getAllTransactions");
		System.out.println("exit");
		String whatCommand = "";
		while(!(whatCommand = command.scanner.nextLine()).equals("exit")) {
			switch (whatCommand) {
			case "getAllTransactions" : {
				command.doAllTransactionsCommand("");
				break;
			}
			case "exit": {
				return;
			}
			}
		}
	}	
	
	public void establishSession() {
		LoginResponse res = doLogin();
		if(res.error.equals("no-error")) {
			_session.setToken(res.token);
			_session.setUid(res.uid);
		} else {
			//close with error.
			System.out.println("\nUnable to establish User session with Server");
		}
	}
	private LoginResponse doLogin() {
		LoginRequest lReq = getLoginCredentials();
		LoginClient client = new LoginClient();
		return client.doLogin(lReq);
	}
	private LoginRequest getLoginCredentials() {
		
		LoginRequest lReq = new LoginRequest();
		lReq.args = _session;
		System.out.print("User Email For LogIn:");
        lReq.email = (scanner.nextLine());
        System.out.println();
        System.out.print("Enter Password:");
        lReq.password = (scanner.nextLine());
        
		return lReq;
	}
	
	public void doAllTransactionsCommand(String command) {
		
		TransactionsRequest req = new TransactionsRequest();
		req.args = _session;
		GetTransactionsClient client = new GetTransactionsClient();
		TransactionsResponse trnResp = client.getTransactions(req);
		if (trnResp.error.equals("no-error")) {
			System.out.println(trnResp.toJson());
		}
		
		AllTransactionCalculations cal = new AllTransactionCalculations(trnResp.transactions);
		cal.calculateAverage();
	}

}
