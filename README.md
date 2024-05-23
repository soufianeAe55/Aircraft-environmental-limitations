# Aircraft performance API Testing Guide

The Environmental Limitations Model provides the maximum and minimum temperature deviations under which the aircraft can be operated, as a function of geopotential pressure altitude.
Two different environmental envelopes are provided, depending on the type of operation: the first one is to be used in flight (climb, cruise, approach), and the second one for landing and take-off, where more restrictive limitations are in place.

# User Story
As a user who uses the aircraft performance API, I want to retrieve the temperature deviation flight envelope data of an aircraft model via an API service, so that I can have an aircraft model temperature deviation flight envelope available to set the proper limits during a simulation.

# Getting Started

To try the performance API, follow these steps:

 - Clone the code source from: *https://github.com/soufianeAe55/Aircraft-environmental-limitations*
 - To facilitate the testing process, I have deployed the app on OneRender server, you will find the URL below : <br />
  *https://soufiane-images-latest.onrender.com/swagger-ui/index.html*
 - You can test the API directly from the swagger, you will find also a full documentation of api there.
 - **Note: Due to the inactivity, the requests may be delayed for 50 seconds (it's a free server-.-)**

## Project Structure

I developed the api on several layers that are explained below, and I wrote units tests for all the class that contains some logic with (Junit and mockito), I handled exceptions globally in the layer of exception to return a comprehensive responses to the client:

 - **src/main/java -** Contains the application's source code.
 - **api -** Contains classes application's REST API.
 - **business -** Contains the application's business logic.
 - **config -** Contains configuration files for the application.
 - **dto -** Contains data transfer objects (DTOs) used by the application.
 - **exception -** Contains the application's custom exception classes.
 - **Model -** Contains dataset model used in the application.
 - **utils -** Contains utility classes used by the application.
 - **validation -** Contains validation logic for the requests received by the application.
 - **src/test/java -** Contains the application's unit tests.

## Running the application

Navigate to the project directory.
Run: mvn spring-boot:run
This will start the application on port 8080 by default.

### Happy Testing! I hope you like the application and enjoy exploring its features. 