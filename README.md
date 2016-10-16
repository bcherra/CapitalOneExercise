# CapitalOneExercise

#Design and Implementation:
The project is implemented as console based java client application and built using jdk 1.8. 
The application provide following features:
  1. Login using email and password. (use interview@levelmoney.com and password2 to test)
  2. Implemented getAllTransactions, it caluclates the average amounts and print the results.
  3. Implemented getAllTransactions --ignore-donuts, it filters out results to exclude Dunkin Donuts and Krispt Kereme            merchant transactions.'
  4. Implemented getAllTransactions --ignore-cc-payment, filter out transactions based on the creteria defined in                requirments. The logic used assume that transactions are in order of time. If this is not true the ArrayList can be          sorted by implementing Comparable interface.
 #How to build
 To build clone the branch and use mvn command to build the solution. The build jar is an executable jar.

#How to run:
To run use the following command: (note this jar is built using jdk1.8)
java -jar CapitalOneExercise-1.jar 


