# NT Consult Technical Challenge

This project is a solution for the technical challenge proposed by NT Consult. It uses Kotlin, Spring Boot, PostgreSQL, Kafka, Swagger, Test containers and Gradle.

## How to Run the Project

1. Clone the repository: `git clone https://github.com/kauepalota/sicredi-tech-challenge.git`
2. Ensure you have Docker, Docker Compose, and at least JDK 17 installed on your machine.
3. Build the Docker image by running the `./build-docker.sh` script. This script compiles the project and creates a Docker image for the application.
4. Ensure you have the `.env` file at the root of the project. This file contains the necessary environment variables to run the application.
5. Run the project by executing the `./run-docker.sh` script. This script starts the application inside a Docker container.

## Bonus Tasks

### 1. Integration with External Systems

The endpoint to check if the user can vote is not up.

### 2. Messaging and Queues

I used Kafka to solve the problem. 
The application is publishing a message to a topic when a session ends. 
The message is consumed by a consumer that prints the message to the console.

I chose Kafka because it is easy to implement, and it is a good solution for this problem.

### 3. API Versioning

I used versioning in the URL.

#### Pros:
- It is easy to understand
- It is easy to implement

#### Cons:
- It changes the endpoint in each version
