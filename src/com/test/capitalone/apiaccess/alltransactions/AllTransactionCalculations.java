package com.test.capitalone.apiaccess.alltransactions;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AllTransactionCalculations {

	Transaction[] _transactions;
	
	public AllTransactionCalculations(Transaction[] t) {
		_transactions = t;
	}
	public void calculateAverage() {
		List<Transaction> transactions = Arrays.asList(_transactions);
		
		Map<String, Amounts> monthlyData = new HashMap<>();
		
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
		
		Amounts resultsOfAverages = getAverage(monthlyData);
		
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
	private class Amounts {
		public double amountSpent;
		public double amountEarned;
		public Amounts() {}
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
			return "{\n \"AmountSpent\": \"" + decimalFormat.format(amountSpent) + "\",\n" + " \"amountEarned\":" + "\"" + decimalFormat.format(amountEarned) + "\""+ "\n}";
		}
	}
}
