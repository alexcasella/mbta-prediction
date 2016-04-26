## Visualzing MBTA Performance
A project of EC500 (cloud computing), this is a web application that aims to provide accurate prediction to users of Massacheustes Bay Transportation Authority train network. The application will predict estimated time of arrival and commute after taking into account the various effecting factors.  

Demo website:
[http://129.10.3.103:3000/#/home](http://129.10.3.103:3000/#/home)

### Setting the environment:

#### NodeJS
1. [Install](https://nodejs.org/en/download/) the Node.JS binaries or the installer. 
2. If the install was successful the Node.JS CLI should be available to you. Check by firing up the terminal
  `node --version` should give the installed version. 
3. If the install was successful you should now have the **npm** which is Node's package manager. `npm --version` should confirm this. 
4. Clone this repo.
5. Navigate to the root folder in the terminal and type in `npm install`
6. This should install all the depencies required to run the application. 
7. There should now be a node_modules folder in your root directory. 
8. If all the steps were successful you should now be able to launch the application.
9. In the root directory type in `node server.js`, fire up a browser and hit `http://localhost:3000/`

#### Java
1. The project is built using JDK 1.7, you will have to download and install it.
2. Make sure the JAVA_HOME variable points to the JDK 1.7 binary.
3. Download and install Apache tomcat 7. 

### Running the Project:
1. To run the front end server check branch ui-angular (below).
2. To run the backend server check branch RestAPI (below).
3. To run the pipeline check check branch pipeline (below).

### Branch: Master
This branch contains the progress of the project uptill Sprint 3, the first version of Boston Now. For the latest files please use Branch ui-angular !

 * app.js for the The NodeJS front end server. 
 * index.html for the User interface.
 * messenger.war for the Jersey Java api 
 * Supporting scripts to parse and format data.


### Branch: ui-angular
In branch ui-angular, you can fine the latest UI. Do not use the UI files from Master! To run UI.
 * Make sure you have Node.js downloaded on your machine. 
 * cd to folder, run command     node server.js
 * On browser go to        localhost:3000
 

### Branch: RestAPI, the file messenger-2 is the Restful API.
RestAPI is used to recieve user request from the front end server, and use the information to query the database and send it back to dront end.
It can be opened in Eclipse luna IDE and our java SE version is 1.7.0_79.
By extracting the file as  a .war file, we can deploy it on AWS Elastic Beanstalk, the Configuration is 64bit Amazon Linux 2015.09 v2.0.8 running Tomcat 8 Java 8. It provides us the URL to get access to our database.
The alerts and predictions can be accessed by the following urls with different extentions:
 * Time prediction:
  http://messenger-env.us-west-2.elasticbeanstalk.com/webapi/AllPrediction/LineColor/StartStopid/EndStopid
 * Time prediction over an hour:
  http://messenger-env.us-west-2.elasticbeanstalk.com/webapi/AllPrediction/LineColor/StartStopid/EndStopid/Time
 * Alert Header:
  http://messenger-env.us-west-2.elasticbeanstalk.com/webapi/Allalerts/headers/StartStopid/EndStopid
 * Alert Detail:
  http://messenger-env.us-west-2.elasticbeanstalk.com/webapi/Allalerts/detail/StartStopid/EndStopid
 * Path suggestion:
  http://messenger-env.us-west-2.elasticbeanstalk.com/webapi/Allalerts/StartStopid/EndStopid



### Branch: backend, the file MBTAProj is the backend and database program.
It can be opened in Eclipse luna IDE and our java SE version is 1.7.0_79.
By extracting the file as a .jar file, we can ran it on EC2.
Firstly getting the MBTAkeypair.pem file from AWS, then the steps are
* scp -i MBTAkeypair.pem ~/Desktop/database.jar ec2-user@52.35.245.109:~
* ssh -i MBTAkeypair.pem ec2-user@ec2-52-35-245-109.us-west-2.compute.amazonaws.com 
* sudo yum install java
* nohup java -jar database.jar & > /dev/null &
We can also check the daily log at the same directry.


### Branch: pipeline
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
