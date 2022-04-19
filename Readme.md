# Ticket Booking

## Overview

This is the online movie ticket booking platform to caters both B2B (theatre partners) and B2C (end customers) clients
for managing shows and booking ticket for the shows respectively.

Currently, it's implemented with below functionalities:

1. Browse theatres currently running the show (movie selected) in the town, including show timing by a chosen date. We have implemented `Search` in `ShowController.java` for the same.
2. Theatres can create, update, and delete shows for the day. We have implemented crud operations in `ShowController.java`.

## Steps to Setup and Run

**1. Clone the repository**

```bash
git clone https://github.com/rajeshdave/TicketBooking.git
```

**2. How to Run**

You may package the application in the form of a jar and then run the jar file like so -

```bash
mvn clean package
java -jar target/TicketBooking-0.0.1-SNAPSHOT.jar
```

That's it! The application can be accessed at `http://localhost:4000/show/search?city=Pune&movie=RRR&date=2022-04-05`.

## Usage Details

### Swagger Documentation

After starting application, access swagger UI at below:
`http://localhost:4000/swagger-ui/index.html`

Access swagger api docs at below:
`http://localhost:4000/v3/api-docs/`

### Browse Running Shows

```
curl --location --request 
GET 'localhost:4000/show/search?city=Pune&movie=RRR&date=2022-04-05'
```

### Create Show

```
curl --location --request POST 'localhost:4000/show/' \
--header 'Content-Type: application/json' \
--data-raw '{
    "fromTime": "2022-04-05T16:00:00",
    "toTime": "2022-04-05T18:30:00",
    "theatreScreenId": 1,
    "movieId": 1
}'
```

### Get Show By Show Id

```
curl --location --request GET 'localhost:4000/show/25'
```

### Update Show

```
curl --location --request PUT 'localhost:4000/show/25' \
--header 'Content-Type: application/json' \
--data-raw '{
    "fromTime": "2022-04-05T16:30:00",
    "toTime": "2022-04-05T18:30:00",
    "theatreScreenId": 1,
    "movieId": 1
}'
```

### Delete Show

```
curl --location --request DELETE 'localhost:4000/show/25'
```

## Code and Testcase Details

This is Spring boot application using Spring Boot v2.6.6.

* `TicketBookingApplication.java` is Spring boot application class.
* `ShowController.java` is REST controller to handle all requests.
* `ShowService.java` is service layer to interact with JPA Repository.
* `ShowRepository.java` is the JPA Repository to interact with Database.
* `ExceptionHandlerController.java` is for handling all exceptions. `ResourceNotFoundException` is custom exception.
  Further, `ErrorCodes` and `ErrorResponse` to have standard common Error codes and Error Responses for teh Application.
* `MDCFilter.java` is to add unique Request id in MDC Context for each of the REST request.

Below are test cases added to test the code:

* `TicketBookingApplicationIT.java` for doing end to end integration tests by using RestTemplate. This cover testcases
  for both successful and exception scenarios.
* `ShowControllerTest.java` is to add Junit testcases for ShowController.

## Motivations

Below are motivations behind given design of application:

* Clean code
* Single Responsibility by layered design. Like  `ShowController`, `ShowService`, `ShowRepository` etc.
* Isolation by use of Interfaces.
* Global Exception handling with REST standard error response.
* Unit and integration Test cases with nearly 100% Code coverage.
* No issue based on Sonar analysis.
* For more details please refer javadoc of each class.

> “It is not enough for code to work.”
> ― Robert C. Martin, Clean Code: A Handbook of Agile Software Craftsmanship