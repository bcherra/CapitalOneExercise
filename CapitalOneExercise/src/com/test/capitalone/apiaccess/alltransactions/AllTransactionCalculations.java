package com.test.capitalone.apiaccess.alltransactions;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/*
 * This class encapsulates the Transactions data returned by the getAllTransactions end-point. Any calculations that need to be performed
 * on this data should be implemented here.
 */
public class AllTransactionCalculations {

	Transaction[] _transactions;
	
	List<String> filters;
	
	public AllTransactionCalculations(Transaction[] t) {
		this._transactions = t;
	}
	public AllTransactionCalculations(Transaction[] t, List<String> filters) {
		this._transactions = t;
		this.filters = filters;
	}
	/*
	 * Calculates the averages and prints the results.
	 * TODO: may be it will be better for this method to return the results, so that these could be accessed
	 * for further processing or customized printing.
	 */
	public void calculateAverage() {
		List<Transaction> transactions = applyFilters();
		
		Map<String, Amounts> monthlyData = new HashMap<>();
		// Organize/aggregate data month wise.
		transactions.stream().forEach(e -> {
			String yearMonth = e.getYearMonth();
			
			if(monthlyData.containsKey(yearMonth)) {
				monthlyData.get(yearMonth).addAmount(e.getAmount());
			} else {
				Amounts a = new Amounts();
				a.addAmount(e.getAmount());
				monthlyData.put(yearMonth, a);
			}
		});
		//From month wise data, calculate averages.
		Amounts resultsOfAverages = getAverage(monthlyData);
		
		//TODO: Take the printing of the results out from here into a separate private method.
		
		monthlyData.forEach((k, v) -> {
			System.out.println("Data For Month of " + k + " : ");
			System.out.println(v.toPrettyString());
		});
		
		System.out.println("Average of all the months: " + resultsOfAverages.toPrettyString());
	}
	private Amounts getAverage(Map<String, Amounts> monthlyData) {
		Amounts totalAmountsObj = new Amounts();
		int size = monthlyData.size();
		Collection<Amounts> allAmounts = monthlyData.values();
		allAmounts.stream().forEach(e -> {
			totalAmountsObj.addToAmountSpent(e.getAmountSpent());
			totalAmountsObj.addToAmountEarned(e.getAmountEarned());
			}
		);
		//We can use Amounts object to store the average amounts.
		Amounts averageAmountsObj = new Amounts();
		averageAmountsObj.addToAmountSpent(totalAmountsObj.getAmountSpent()/size);
		averageAmountsObj.addToAmountEarned(totalAmountsObj.getAmountEarned()/size);
		return averageAmountsObj;
	}
	private List<Transaction> applyFilters() {
		List<Transaction> _temp = Arrays.asList(this._transactions);
		System.out.println("Filters :: " + filters.size());
		for (String filter: filters) {
			switch (filter)  {
			case "--ignore-donuts" : 
				_temp = applyIgnoreDonuts(_temp);
				break;
			
			case "--ignore-cc-payments": 
				_temp = applyIgnorePaymentTransactions(_temp);
				break;
			}
		};
		return _temp;
	}
	private List<Transaction> applyIgnoreDonuts(List<Transaction> t) {
		System.out.println("Before Filter " + t.size());
		List<Transaction> _temp = new ArrayList<>();
		Set<String> ignoreList = new HashSet<>();
			ignoreList.add("Krispy Kreme Donuts");
			ignoreList.add("DUNKIN #336784");
			ignoreList.add("Dunkin #336784");
		 t.forEach( e  -> {
			if (!ignoreList.contains(e.getMerchant())) {
				_temp.add(e);
			}
		});
		 System.out.println("Before Filter " + _temp.size());	
		return _temp;	
	}
	/*
	 * 
	 */
	private List<Transaction> applyIgnorePaymentTransactions(List<Transaction> transactions) {
		List<Transaction> _temp = new ArrayList<>();
		Map<String, Transaction> byAmount = new HashMap<>();
		Set<String> tobeIgnoredIds = new HashSet<>();
		List<Transaction> ignored = new ArrayList<>();	
		//THE ASSUMPTION MADE WITH THIS SOLUTION IS THAT TRANSACTIONS ARE ORDERED LIST.
		//IF THAT IS NOT TRUE, THEN WE WOULD NEED TO ORDER THE TRANSACIONS BY TRANSACTION TIME or other factor that
		//will ensure the transaction are in order of time.
		for( Transaction e: transactions) {
			if (byAmount.containsKey(Double.toString(e.getAbsoluteAmount()))) {
				Transaction t = byAmount.get(Double.toString(e.getAbsoluteAmount()));
				if(isPayment(t,e)) {
					tobeIgnoredIds.add(e.getTransactionId());
					tobeIgnoredIds.add(t.getTransactionId());
					//The key must be removed to avoid one credit amount matching with multiple
					//debit amounts.
					
					byAmount.remove(Double.toString(e.getAbsoluteAmount()));
				}
				
			}else {
				byAmount.put(Double.toString(e.getAmount()), e);
			}
		};
		for (Transaction _t : transactions) {
			if(!tobeIgnoredIds.contains(_t.getTransactionId())) {
				_temp.add(_t);
			}	else {
				ignored.add(_t);
			}
		}
		ignored.stream().forEach(e -> {
			System.out.println("Ignoring : " + e.toJson());
		});
		return _temp;	
	}
	private boolean isPayment(Transaction t1, Transaction t2) {
		return ((t1.getAmount() + t2.getAmount()) == 0) && isTransactionTimeWithInDay(t1, t2);
	}
	private boolean isTransactionTimeWithInDay(Transaction t1, Transaction t2) {
		try {
			Date d1 = t1.getTransactionTimeAsDate();
			Date d2 = t2.getTransactionTimeAsDate();
			return (d1.getTime() - d2.getTime()) <= 24*60*60*1000;
		} catch(ParseException e) {
			e.printStackTrace();
			return false;
		}
		
	}
	/*
	 * Inner class to hold calculated or accumulated results.
	 * TODO: consider moving it out in case we need external objects to access calculated results.
	 * Or it can be declared as a static class to be made accessible by external classes.
	 */
	private class Amounts {
		private double amountSpent;
		private double amountEarned;
		private Amounts() {}
		
		public void addAmount(double amount) {
			if(amount < 0) {
				addToAmountSpent(Math.abs(amount)); 
			} else {
				addToAmountEarned(Math.abs(amount));
			}
		}
		public void addToAmountSpent(double a) {
			amountSpent += a;
		}
		public void addToAmountEarned(double a) {
			amountEarned += a;
		}
		public double getAmountSpent() {
			return amountSpent;
		}
		public double getAmountEarned() {
			return amountEarned;
		}
		public String toPrettyString() {
			String pattern = "$###,###.00";
			DecimalFormat decimalFormat = new DecimalFormat(pattern);
			return "{\n \"amountSpent\": \"" + decimalFormat.format(amountSpent) + "\",\n" + " \"amountEarned\":" + "\"" + decimalFormat.format(amountEarned) + "\""+ "\n}";
		}
	}
}
