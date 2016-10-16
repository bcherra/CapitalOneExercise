package com.test.capitalone.app;


import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.test.capitalone.api.authentication.APILoginCredentials;
import com.test.capitalone.api.authentication.APISession;
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
	/*
	 * The application, on start will prompt user for userId and Password.
	 * Authenticate with the API server using APIToken and entered userid/password.
	 * A session with API is established and maintained for the length of the application use.
	 * User can enter following commands to interact with the API layer.
	 * 1. getAllTransactions.
	 * 2. exit 
	 */
	public static void main(String[] args) {
		
		ProcessCommands command = new ProcessCommands();
		try {
			
			command.establishSession();
			
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		System.out.println("Eneter any of the following Commands.");
		System.out.println(" 1. getAllTransactions");
		System.out.println(" 2. exit");
		String commandLine = "";
		String whatCommand = "";
		int spaceIndex = 0;
		//Read user commands and take action as per entered command.
		while(!(commandLine = command.scanner.nextLine()).equals("exit")) {
			spaceIndex = commandLine.indexOf(' ');
			if(spaceIndex > 0)
				whatCommand = commandLine.substring(0, spaceIndex);
			else
				whatCommand = commandLine;
			System.out.println("What Command " + whatCommand);
			switch (whatCommand) {
				case "getAllTransactions" : {
					command.doAllTransactionsCommand(commandLine);
					break;
				}
				case "exit": {
					return;
				}
			}
		}
	}	
	/*
	 * Log-in to the OAuth API.
	 */
	public void establishSession() throws Exception{
		LoginResponse res = doLogin();
		if(res.error.equals("no-error")) {
			_session.setToken(res.token);
			_session.setUid(res.uid);
		} else {
			//close with error.
			System.out.println("\nUnable to establish User session with Server");
			//TODO: Create Application specific Exception mechanism.
			throw new Exception("Unable to Login");
		}
	}
	private LoginResponse doLogin() {
		LoginRequest lReq = getLoginCredentials();
		LoginClient client = new LoginClient();
		return client.doLogin(lReq);
	}
	/*
	 * Read login credentials from the console.
	 */
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
	/*
	 * Implement getAllTransactions API and apply calculations.
	 * The calculations are applied as per user entered commands. 
	 * This method is called when user enter "getAllTransactions" command.
	 * @input command -- user command
	 */
	public void doAllTransactionsCommand(String command) {
		
		TransactionsRequest req = new TransactionsRequest();
		req.args = _session;
		GetTransactionsClient client = new GetTransactionsClient();
		TransactionsResponse trnResp = client.getTransactions(req);
		if (trnResp.error.equals("no-error")) {
			//TODO: This print is only for testing purposes and can be removed.
			System.out.println(trnResp.toJson());
		}
		List<String> filters = new ArrayList<>();
		int index = 0; //0 is fine we are looking for switch after the command.
		int len = command.length();
		int nextSpace = 0;
		while(index < len && (index = command.indexOf("--",index)) > 0) {
			nextSpace = index;
			while(nextSpace < len && (command.charAt(nextSpace++)) != ' ') {
				
			}
			
			System.out.println("Found command switch " + command.substring(index, nextSpace));
			filters.add(command.substring(index, nextSpace));
			index = nextSpace;
		}
		AllTransactionCalculations cal = new AllTransactionCalculations(trnResp.transactions, filters);
		cal.calculateAverage();
	}

}
