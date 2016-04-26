# mbta-prediction
A project of EC500 (cloud computing), this is a web application that aims to provide accurate prediction to users of Massacheustes Bay Transportation Authority train network. The application will predict estimated time of arrival and commute afer taking into account the various effecting factors.  

In branch RestAPI, the file messenger-2 is the Restful API.
It can be opened in Eclipse luna IDE and our java SE version is 1.7.0_79.
By extracting the file as  a .war file, we can deploy it on AWS Elastic Beanstalk, the Configuration is 64bit Amazon Linux 2015.09 v2.0.8 running Tomcat 8 Java 8. It provides us the URL to get access to our database.

In branch backend, the file MBTAProj is the backend and database program.
It can be opened in Eclipse luna IDE and our java SE version is 1.7.0_79.
By extracting the file as a .jar file, we can ran it on EC2.
Firstly getting the MBTAkeypair.pem file from AWS, then the steps are
1. scp -i MBTAkeypair.pem ~/Desktop/database.jar ec2-user@52.35.245.109:~
2. ssh -i MBTAkeypair.pem ec2-user@ec2-52-35-245-109.us-west-2.compute.amazonaws.com 
3. sudo yum install java
4. nohup java -jar database.jar & > /dev/null &
We can also check the daily log at the same directry.



