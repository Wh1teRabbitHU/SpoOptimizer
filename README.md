# Optimizer

It's a micro service application using spring boot. It has an endpoint that calculates and optimizes the ideal amount
of skilled workers based on the capacity of the given rooms and the skill levels capacity size.

## How to run the web server?

### From IDE:

I'm using Intellij IDEA for developing java applications. With this application you only need to open the 
project, run a gradle build, finding the Application java class, (hu.tamasruszka.spo.optimizer.Application) and starting the main method.

### From command line:

Depending on the host's OS, you need to use one of the gradle wrapper files. ``gradlew`` is for iOS or Linux and 
``gradlew.bat`` is for Windows. The following command starts the web server: (OSX example)

```shell script
./gradlew clean bootRun
```

### Running tests

- From IDEA locate the ``CleanerControllerTest`` class and run the tests from class level (small play button next to the class keyword)
- From command line: (OSX example)
```shell script
./gradlew clean test
```

## What's the used URL scheme

This application has only one endpoint. The spring boot server using the 8080 port, so the url looks like this:
``http://[MACHINE_ADDRESS]:8080/[ENDPOINT_URL]``

### API description

|                  | Cleaner - Optimization                                                                                                                                                                                                                       |
|------------------|----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| Description      | This endpoint calculate the optimal amount of workers for cleaning rooms at our clients.                                                                                                                                                     |
| URL              | /cleaner/optimization                                                                                                                                                                                                                        |
| Method           | POST                                                                                                                                                                                                                                         |
| URL Params       | not used                                                                                                                                                                                                                                           |
| Data params      | { <br> "rooms": [int[], required], // Every rooms capacity <br>  "senior": [int, required], // Senior worker's capacity <br>  "junior": [int, required] // Junior worker's capacity <br> } <br><br> Example: <br><br> { <br>  "rooms": [24, 28], <br>  "senior": 11, <br>  "junior": 6 <br> } |
| Success response | [ { <br> "senior": [int] // Optimized number of senior workers for the given room <br> "junior": [int] // Optimized number of junior workers for the given room <br> } ] <br><br> Example: <br><br>  [ { <br> senior: 2, <br> junior: 1 <br> }, <br> { <br> senior: 2, <br> junior: 1 <br> } ] |
| Error response   | { <br> "status": [int], // The HttpStatus code <br> "message": "Not Found" // The HttpStatus reason phrase <br> } |