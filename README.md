# forexapp
Forex Test App

Source code for Forex Test app is available in the GitHub repo below.
https://github.com/saravanan-ramanathan/forexapp

Executable JAR File
-------------------
Because of size limitations, executable jar(forexapp-0.0.1-SNAPSHOT.jar) file for forex app is uploaded in Google drive 
and it is available in the location below.
https://drive.google.com/open?id=1v1oFBVZukhWLQXDGOKIN0nwdPtgNQ5Ji

Prerequisites for running Forex App
-----------------------------------
Ensure Java 8 is installed and it is available in the path. Run the below command for verification and it should display the java version.
cmd> java -version

Running the application
-----------------------
Once the JAR is downloaded, run the jar with the command below.
cmd> java -jar forexapp-0.0.1-SNAPSHOT.jar

Application should startup and it should be up and running on port 8080.
http://localhost:8080/api/v1

Assumptions
-----------
1) Forex App is considered to perform trading only on GBP/USD currency.
2) Forex price is read from the configuration file and it is a fixed value(1.2610).
3) Forex order matching(BID vs ASK) is performed on the below economical attributes.
	> OrderType - BID/ASK
	> Price
	> Amount
	> Currency
4) UserId validation(not to match the same user BID/ASK for the same currency) is not performed for any BID/ASK. This is left pending.
5) Duplicate order validation considering userId, orderType, currency, price and amount is not performed. This is left pending.
6) Forex order assumes the below status.
	> NEW - New forex order registered in the system and eligible for matching.
	> CANCEL - Forex order that has been cancelled.
	> MATCHED - Forex orders that are matched.

Design Considerations
---------------------
1) Forex app was developed as a Spring boot web application that exposes end points for taking input/output.
2) In memory database is used for storing forex orders. Once restarted, the existing data will get erased.
3) Forex app can run as a stand alone service and can be hosted in any run time environment.
4) Forex app was developed using Test Driven Development(TDD) and there are various test cases added for each functionality.
5) Forex app uses N-Tier architecture(Client, Server[Business Logic], Data store).

RESTFul API
-----------
The below end points can be invoked from Postman for handling Forex orders. Sample payloads provided in the next section.

POST
----
For Creating new forex order
http://localhost:8080/api/v1/registerorder

For cancelling a forex order
http://localhost:8080/api/v1/cancelorder

For matching the forex orders(BID vs ASK)
http://localhost:8080/api/v1/matchorders


GET
---
For getting all unmatched orders
http://localhost:8080/api/v1/unmatchedorders

For getting all matched trades
http://localhost:8080/api/v1/matchedtrades

For getting all cancelled orders
http://localhost:8080/api/v1/cancelledorders


Sample payloads(For testing purpose)
------------------------------------
Price(1.2610) is read from a configuration file and it will be added automatically to the order.

{
	"userId": "Jon", 
	"orderType": "BID", 
	"currency": "GBP/USD", 
	"amount": 5555
}

{
	"userId": "Mark", 
	"orderType": "ASK", 
	"currency": "GBP/USD", 
	"amount": 5555
}

{
	"userId": "Ash", 
	"orderType": "BID", 
	"currency": "GBP/USD", 
	"amount": 4444
}

{
	"userId": "Steve", 
	"orderType": "ASK", 
	"currency": "GBP/USD", 
	"amount": 4443
}
