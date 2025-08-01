# Spring Cloud Project

A Spring Boot 3 application with Spring Cloud integration, built with Maven and Java 17, featuring comprehensive JUnit tests.

## Project Overview

This project demonstrates a modern Spring Boot 3 application that leverages Spring Cloud for microservices architecture patterns. It includes:

- **Spring Boot 3.2.1** with Java 17
- **Spring Cloud 2023.0.0** (latest release train)
- **Maven** build system
- **JUnit 5** comprehensive test suite
- **Spring Cloud OpenFeign** for service-to-service communication
- **Resilience4j Circuit Breaker** for fault tolerance
- **Spring Boot Actuator** for monitoring and health checks
- **Eureka Client** configuration (ready for service discovery)

## Features

### Core Components

1. **User Management API**
   - RESTful endpoints for CRUD operations
   - In-memory user storage with sample data
   - Comprehensive validation and error handling

2. **External API Integration**
   - Feign client for external service calls
   - Circuit breaker pattern implementation
   - Fallback mechanisms for resilience

3. **Health Monitoring**
   - Spring Boot Actuator endpoints
   - Custom health checks
   - Metrics and monitoring ready

### API Endpoints

#### User Management
- `GET /api/users` - Get all users
- `GET /api/users/{id}` - Get user by ID
- `POST /api/users` - Create new user
- `PUT /api/users/{id}` - Update existing user
- `DELETE /api/users/{id}` - Delete user
- `GET /api/users/health` - Health check

#### External API Integration
- `GET /api/external/posts/{id}` - Get external post (with circuit breaker)
- `GET /api/external/users/{id}` - Get external user (with circuit breaker)

#### Monitoring
- `GET /actuator/health` - Application health status
- `GET /actuator/info` - Application information
- `GET /actuator/metrics` - Application metrics

## Technology Stack

- **Java 17** - Latest LTS version
- **Spring Boot 3.2.1** - Latest stable release
- **Spring Cloud 2023.0.0** - Latest release train
- **Spring Web** - RESTful web services
- **Spring Cloud OpenFeign** - Declarative REST clients
- **Spring Cloud Config** - Externalized configuration
- **Resilience4j** - Circuit breaker and fault tolerance
- **Spring Boot Actuator** - Production-ready features
- **JUnit 5** - Modern testing framework
- **Mockito** - Mocking framework for tests
- **Maven** - Build and dependency management

## Project Structure

```
src/
├── main/
│   ├── java/com/javaninja/
│   │   ├── SpringCloudApplication.java     # Main application class
│   │   ├── controller/
│   │   │   ├── UserController.java         # User REST controller
│   │   │   └── ExternalController.java     # External API controller
│   │   ├── service/
│   │   │   ├── UserService.java            # User business logic
│   │   │   └── ExternalService.java        # External API service with circuit breaker
│   │   ├── client/
│   │   │   └── ExternalApiClient.java      # Feign client interface
│   │   └── model/
│   │       └── User.java                   # User domain model
│   └── resources/
│       ├── application.yml                 # Main configuration
│       └── bootstrap.yml                   # Bootstrap configuration
└── test/
    ├── java/com/javaninja/
    │   ├── SpringCloudApplicationTests.java # Integration tests
    │   ├── controller/
    │   │   ├── UserControllerTest.java      # Controller unit tests
    │   │   └── ExternalControllerTest.java  # External controller tests
    │   └── service/
    │       ├── UserServiceTest.java         # Service unit tests
    │       └── ExternalServiceTest.java     # External service tests
    └── resources/
        └── application-test.yml             # Test configuration
```

## Prerequisites

- **Java 17** or higher
- **Maven 3.6+**
- Internet connection (for external API integration)

## Getting Started

### 1. Clone and Build

```bash
# Navigate to project directory
cd spring-cloud-project

# Clean and compile
mvn clean compile

# Run tests
mvn test

# Package the application
mvn clean package
```

### 2. Run the Application

```bash
# Run with Maven
mvn spring-boot:run

# Or run the JAR file
java -jar target/spring-cloud-project-1.0.0-SNAPSHOT.jar
```

The application will start on `http://localhost:8080`

### 3. Test the APIs

```bash
# Get all users
curl http://localhost:8080/api/users

# Get user by ID
curl http://localhost:8080/api/users/1

# Create new user
curl -X POST http://localhost:8080/api/users \
  -H "Content-Type: application/json" \
  -d '{"name":"Alice Brown","email":"alice.brown@example.com"}'

# Health check
curl http://localhost:8080/actuator/health

# Test external API with circuit breaker
curl http://localhost:8080/api/external/posts/1
```

## Configuration

### Application Properties

The application uses YAML configuration files:

- `application.yml` - Main application configuration
- `bootstrap.yml` - Bootstrap configuration for Spring Cloud
- `application-test.yml` - Test environment configuration

### Key Configuration Options

```yaml
server:
  port: 8080                    # Application port

spring:
  application:
    name: spring-cloud-project  # Service name

eureka:
  client:
    service-url:
      defaultZone: http://localhost:8761/eureka/  # Eureka server URL

management:
  endpoints:
    web:
      exposure:
        include: health,info,metrics  # Exposed actuator endpoints
```

## Testing

The project includes comprehensive test coverage:

### Test Types

1. **Unit Tests**
   - Service layer tests with business logic validation
   - Isolated component testing with mocks

2. **Integration Tests**
   - Controller tests with MockMvc
   - Full application context loading tests

3. **Circuit Breaker Tests**
   - Fallback mechanism validation
   - External service failure scenarios

### Running Tests

```bash
# Run all tests
mvn test

# Run specific test class
mvn test -Dtest=UserServiceTest

# Run tests with coverage
mvn test jacoco:report
```

### Test Coverage

- **UserService**: Complete CRUD operations testing
- **UserController**: REST API endpoint testing
- **ExternalService**: Circuit breaker and fallback testing
- **ExternalController**: External API integration testing
- **Application Context**: Spring Boot application loading

## Spring Cloud Features

### 1. Service Discovery (Eureka Client)
- Ready for Eureka server integration
- Automatic service registration
- Load balancing support

### 2. Circuit Breaker (Resilience4j)
- Fault tolerance for external service calls
- Automatic fallback mechanisms
- Configurable failure thresholds

### 3. Declarative REST Clients (OpenFeign)
- Type-safe HTTP clients
- Automatic request/response mapping
- Integration with load balancing

### 4. Configuration Management
- Externalized configuration support
- Profile-based configuration
- Spring Cloud Config ready

## Production Considerations

### 1. External Dependencies
- Configure actual Eureka server URL
- Set up external service endpoints
- Configure database connections (if needed)

### 2. Security
- Add Spring Security for authentication/authorization
- Implement API key management
- Configure HTTPS/TLS

### 3. Monitoring
- Set up centralized logging
- Configure metrics collection
- Implement distributed tracing

### 4. Deployment
- Configure environment-specific properties
- Set up health checks for container orchestration
- Configure resource limits and scaling

## Development

### IDE Setup
- Import as Maven project in IntelliJ IDEA or Eclipse
- Enable annotation processing
- Configure Java 17 as project SDK

### Adding New Features
1. Create service classes in `service` package
2. Add REST controllers in `controller` package
3. Define models in `model` package
4. Add corresponding tests in `test` directory

### Best Practices
- Follow Spring Boot conventions
- Write comprehensive tests for new features
- Use proper HTTP status codes
- Implement proper error handling
- Document API changes

## Troubleshooting

### Common Issues

1. **Java Version Mismatch**
   ```bash
   # Check Java version
   java -version
   
   # Set JAVA_HOME if needed
   export JAVA_HOME=/usr/lib/jvm/java-17-openjdk-amd64
   ```

2. **Port Already in Use**
   ```bash
   # Change port in application.yml
   server:
     port: 8081
   ```

3. **External Service Unavailable**
   - Circuit breaker will activate fallback responses
   - Check logs for detailed error information

## License

This project is created for demonstration purposes and follows standard Spring Boot project structure and best practices.

## Support

For questions or issues:
1. Check the Spring Boot documentation
2. Review Spring Cloud documentation
3. Examine test cases for usage examples
4. Check application logs for detailed error information

