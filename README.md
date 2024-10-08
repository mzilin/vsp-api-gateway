# Video Streaming Platform - API Gateway

The `API Gateway` is an integral component of the `Video Streaming Platform`, responsible for request routing and security.


## Technologies Used
This server utilises a comprehensive suite of technologies and dependencies, ensuring robust and scalable functionality:

- **Spring Boot** `3.2.5`:
  - **Actuator**: Monitors and manages the application, providing insights into runtime operations and health.
  - **Security**: Provides authentication and authorisation mechanisms for the application, helping to secure endpoints and manage user access effectively.
  - **Validation**: Ensures that incoming data meets the application's expectations, crucial for maintaining data integrity and proper functioning.
  - **Webflux**: Enables building scalable, non-blocking web applications using a reactive programming model.

- **Spring Cloud** `2023.0.1`:
  - **Gateway**: Acts as an intelligent, configurable router for API requests, directing traffic to various microservices.
  - **Config**: Manages externalised configuration, allowing applications to fetch their settings from a centralized source.
  - **Netflix Eureka Client**: Enables the microservice to register with a Eureka server for service discovery.

- **Java** `JDK 17`: Essential for secure, portable, high-performance software development.

- **Lombok**: Reduces boilerplate in Java code significantly, automating the generation of getters, setters, constructors, and other common methods.


### Dependency Management

- **Gradle**: A powerful build automation tool that streamlines the compilation, testing, and deployment processes for software projects.


### Containerization

- **Docker** (Optional): Automates OS-level virtualization on Windows and Linux.


## Requirements

To successfully set up and run the application, ensure you have the following installed:

- [Java JDK 17](https://www.oracle.com/uk/java/technologies/downloads/#java17)
- [Gradle](https://gradle.org/)
- [Docker](https://docs.docker.com/get-docker/) (optional)


## Installation

Follow these steps to get the microservice up and running:

1. Navigate into the app's directory
```shell
cd vsp-api-gateway
```

2. Clean and build the microservice

```shell
./gradlew clean build
```

3. Start the server

```shell
./gradlew bootRun
```


## License

This project is private and proprietary. Unauthorised copying, modification, distribution, or use of this software, via any medium, is strictly prohibited without explicit permission from the owner.


## Contact

For any questions or clarifications about the project, please reach out to the project owner via [www.mariuszilinskas.com](https://www.mariuszilinskas.com).

Marius Zilinskas

------

###### All rights are reserved. - Marius Zilinskas, 2024 to present