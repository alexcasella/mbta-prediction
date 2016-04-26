Author: Alex Casella
Date: Spring 2016



------------- To Run -------------

1. Make sure you have Node.js downloaded on your machine. 
2. cd to folder, run command     node server.js
3. On browser go to        localhost:3000


------------- Architecture and goals -------------

- I am solely responsible for the User Interface and the front end server. The front end server takes user requests from the UI and makes RESTful API calls to the backend and returns JSON results to the UI. It links together various parts of this project. The UI is written in AngularJS and the front end server is written in Node.js.

- My goal for the UI is to make it simple, easy to use, and beautiful at the same time. I used Bootstrap, Font Awesome, and Responsive CSS to help with the styling. 


------------- Accomplishments -------------

- There were several accomplishments that took place throughout the project. 

- During the first two sprints, we used traditional HTML, CSS, and Javascript. However, I decided to take on a challenge and completely revampt the front end to AngularJS because of its two way data binding properties and MVC ease-of-use. It is also the hot technology in the industry and I wanted to refresh my Angular knowledge. 

- The UI provides two services, service 1 predicts current MBTA arrival time. Service 2 predicts MBTA arrival times in the future. Service 1 asks the users for the MBTA color, if they choose the Green line, then a dropdown menu for different Green lines will pop up. It also asks for the direction of travel. Based on these selections, possible starting stops are populated. Then user chooses chooses whether he will transfer during travel. If not, then the same stops are populated for End Stop selection. If yes, then he chooses line and direction again and a differnt set of stops are populated based on his selection. In addition, service 3 asks for time of day of travel and populates times (with 30 minute intervals) based on time of day selection. 

- Depending on the line selection, a different map would pop up to give users a better visual of the stops. 

- A cool feature is bookmarkable URL. The URL is dynamic based on user selection and can be saved to populate custom stop selections on page load for convenience. 

- Node.js server makes 3 separate API calls to the backend for Alert Header, Alert Detail, and Prediction. 

- Bootstrap is used for better display of parsed JSON response data from the backend. 

- In total, I wrote over 1200 lines of code for the UI and the front end server. 


------------- What I learned -------------

- I refreshed my AngularJS and Node.js skills. A ton of little tricks. 

- Learned how to do server to server communication. 

- Learned how to draw architecture so different parts of the system can be scaled separately. Our mentor told us that many applications in the industry use this kind of architecture. 

- Learned how a modern day cloud appliation works. 

- Learned how to better work with teammates.


------------- Future work -------------

- Periodic API calls to the backend so data keeps refreshing without user input

- Interactive map for stop selection

- More client and server side validation
