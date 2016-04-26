# mbta-prediction
A project of EC500 (cloud computing), this is a web application that aims to provide accurate prediction to users of Massacheustes Bay Transportation Authority train network. The application will predict estimated time of arrival and commute afer taking into account the various effecting factors.  


## In branch ui-angular, you can fine the latest UI. Do not use the UI files from Master! To run UI. 
 +1. Make sure you have Node.js downloaded on your machine. 
 +2. cd to folder, run command     node server.js
 +3. On browser go to        localhost:3000
 

## In branch RestAPI, the file messenger-2 is the Restful API.
It can be opened in Eclipse luna IDE and our java SE version is 1.7.0_79.
By extracting the file as  a .war file, we can deploy it on AWS Elastic Beanstalk, the Configuration is 64bit Amazon Linux 2015.09 v2.0.8 running Tomcat 8 Java 8. It provides us the URL to get access to our database.

## In branch backend, the file MBTAProj is the backend and database program.
It can be opened in Eclipse luna IDE and our java SE version is 1.7.0_79.
By extracting the file as a .jar file, we can ran it on EC2.
Firstly getting the MBTAkeypair.pem file from AWS, then the steps are
1. scp -i MBTAkeypair.pem ~/Desktop/database.jar ec2-user@52.35.245.109:~
2. ssh -i MBTAkeypair.pem ec2-user@ec2-52-35-245-109.us-west-2.compute.amazonaws.com 
3. sudo yum install java
4. nohup java -jar database.jar & > /dev/null &
We can also check the daily log at the same directry.


## Data Pipeline
The source code for Data Pipeline component is located at ["pipeline" branch](https://github.com/sngpranay/mbta-prediction/tree/pipeline)
To run it locally, please follow the instructions provided in previous section to configure your Amazon DynamoDB credentials

* Pathfinding Algorithm
The source code for Pathfinding algorithm is located at [here](https://github.com/sngpranay/mbta-prediction/blob/pipeline/DynamoDB_Demo/src/com/mbta/graph/HeuristicSearch.java)
You can run it as stand alone program throuth [GraphTest.java](https://github.com/sngpranay/mbta-prediction/blob/pipeline/DynamoDB_Demo/src/com/mbta/test/GraphTest.java)

* Package structure: 
  com.mbta.api: contains the code for making MBTA API calls
  com.mbta.datastore: contains the code for DynamoDB operations
  com.mbta.dbmapper: contains the code for DynamoDB mapper class
  com.mbta.enitty: mapping json string to java object
  com.mbat.graph: contains the code for build graph data structure and heuristic search algorithm
  com.mbta.util: Several utility class for program processing
  com.mbat.test: contains the main method for standalone program.
