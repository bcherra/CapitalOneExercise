package com.test.capitalone.apiaccess.alltransactions;

/*
 * This class encapsulate data fields returned by getAllTransactions API. 
 * Each account holder transaction is represented by this class.
 */
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

public class Transaction {
	//Note: @SerializedName annotation is used by gson library to map fields to appropriate json fields.
	@SerializedName("transaction-id")
	String transactionId = "";	
	
	@SerializedName("account-id")
	String accountId = "";	
	
	@SerializedName("raw-merchant")
	String rawMerchant = "";	
	
	String merchant = "";
	
	@SerializedName("is-pending")
	boolean isPending	=	false;	
	
	@SerializedName("transaction-time")
	String transactionTime = "1970-01-01T00:00:00.000Z";	
	
	double amount = 0;	
	
	@SerializedName("previous-transaction-id")
	String previousTransactionId = ""; 
	
	String categorization = "";
	@SerializedName("memo-only-for-testing")
	
	String memo	= "" ;	
	
	@SerializedName("payee-name-only-for-testing")
	String payeeName =	"";
	
	@SerializedName("clear-date")
	double clearDate	= 	0;

	public String getTransactionId() {
		return transactionId;
	}

	public String getAccountId() {
		return accountId;
	}

	public String getRawMerchant() {
		return rawMerchant;
	}

	public String getMerchant() {
		return merchant;
	}

	public boolean isPending() {
		return isPending;
	}

	public String getTransactionTime() {
		return transactionTime;
	}

	public double getAmount() {
		return amount;
	}

	public String getPreviousTransactionId() {
		return previousTransactionId;
	}

	public String getCategorization() {
		return categorization;
	}

	public String getMemo() {
		return memo;
	}

	public String getPayeeName() {
		return payeeName;
	}

	public double getClearDate() {
		return clearDate;
	}
	/*
	 * Return the Year and month as yyyy-mm format, from the transaction time field of Transaction object.
	 */
	public String getYearMonth() {
		try {
			
			Date date = getTransactionTimeAsDate();
			Calendar cal = Calendar.getInstance();
		    cal.setTime(date);
		    int year = cal.get(Calendar.YEAR);
		    int month = cal.get(Calendar.MONTH);
		    
		    return year + "-" + month;
		} catch(ParseException e) {
			e.printStackTrace();
			return "";
		}
		
		
	}
	public Date getTransactionTimeAsDate() throws ParseException {
		//TODO yyyy-MM-dd'T'HH:mm:ss.SSS'Z' is the expected time-stamp format. In case additional formats are to be implemented.
		// we would need to write a routine to detect the format and then try parsing the value.
		
		SimpleDateFormat dFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
		return  dFormat.parse(getTransactionTime());
	}
	public double getAbsoluteAmount() {
		return Math.abs(getAmount());
	}
	public String toJson() {
		Gson g = new Gson();
		return g.toJson(this);
	}
}
