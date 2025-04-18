# Spring Boot Web Application Project

A comprehensive web application project demonstrating modern Spring Boot development practices, security implementations, and design patterns. This application provides a secure and user-friendly platform with a responsive design and robust security features.

## Project Structure

```
.
├── demo/                 # Main Spring Boot application
│   ├── src/             # Source code
│   │   ├── main/
│   │   │   ├── java/
│   │   │   │   └── com/example/demo/
│   │   │   │       ├── controller/    # Request handlers
│   │   │   │       ├── model/         # Data models
│   │   │   │       ├── repository/    # Database operations
│   │   │   │       ├── service/       # Business logic
│   │   │   │       └── DemoApplication.java
│   │   │   └── resources/
│   │   │       ├── static/           # Static resources
│   │   │       │   ├── css/         # Stylesheets
│   │   │       │   ├── images/      # Image assets
│   │   │       │   └── js/          # JavaScript files
│   │   │       └── templates/       # HTML templates
│   │   └── test/                    # Test files
│   ├── pom.xml          # Maven configuration
│   ├── mvnw             # Maven wrapper
│   ├── mvnw.cmd         # Maven wrapper for Windows
│   └── .mvn/            # Maven wrapper configuration
├── .vscode/            # VS Code configuration
├── .dist/              # Distribution files
└── Readme.md           # Project documentation
```

## Features

### Authentication System
- Secure user login with email and password
- User registration with email verification
- Session management and persistence
- Password encryption and secure storage

### Security Features
- CAPTCHA verification for login attempts
- Refreshable CAPTCHA system
- Server-side validation
- Protection against automated attacks
- Secure session handling

### User Interface
- Modern, responsive design
- Clean and intuitive layout
- Mobile-friendly interface
- Smooth transitions and animations
- Custom styling with Poppins font
- Interactive form validation

### Database Integration
- MySQL database for data persistence
- JPA/Hibernate for object-relational mapping
- Efficient data management
- Secure data storage

## Tech Stack

### Backend
- Spring Boot 3.4.4
- Spring Security for authentication
- Spring Data JPA for database operations
- MySQL Database
- Thymeleaf Template Engine
- Java 17

### Frontend
- HTML5 for structure
- CSS3 for styling
- JavaScript for interactivity
- Modern UI components
- Responsive design framework

## Design Principles and Patterns

### Design Principles

#### 1. SOLID Principles

##### Single Responsibility Principle (SRP)
**Implementation**: Each class has a single responsibility
- `UserService` (`src/main/java/com/example/demo/service/UserService.java`): Handles user operations
- `GameService` (`src/main/java/com/example/demo/service/GameService.java`): Manages game operations
- `CaptchaService` (`src/main/java/com/example/demo/service/CaptchaService.java`): Handles CAPTCHA generation and validation

**Explanation**: The Single Responsibility Principle states that a class should have only one reason to change, meaning it should have only one job or responsibility. In our application, this is demonstrated through specialized service classes. The `UserService` focuses solely on user-related operations like registration and authentication, while the `GameService` handles game-specific logic such as creating games and managing players. The `CaptchaService` is dedicated to CAPTCHA generation and validation, ensuring security without mixing concerns. This separation of concerns makes the code more maintainable, testable, and easier to understand. Each service can be modified independently without affecting others, reducing the risk of unintended side effects and making the system more robust.

##### Open/Closed Principle (OCP)
**Implementation**: Classes are open for extension but closed for modification
- `AuthenticationService` (`src/main/java/com/example/demo/service/AuthenticationService.java`): Interface for authentication
- `RealAuthenticationService` (`src/main/java/com/example/demo/service/RealAuthenticationService.java`): Implementation of authentication
- `UserRepository` (`src/main/java/com/example/demo/repository/UserRepository.java`): Interface for user data access

**Explanation**: The Open/Closed Principle emphasizes that software entities should be open for extension but closed for modification. In our application, this is achieved through interfaces and abstract classes. The `AuthenticationService` interface defines the contract for authentication, while `RealAuthenticationService` provides the concrete implementation. This design allows us to add new authentication methods (like social login or biometric authentication) by creating new implementations of the interface without modifying existing code. Similarly, the `UserRepository` interface abstracts data access operations, allowing different database implementations without changing the service layer. This approach promotes code reuse, reduces regression risks, and makes the system more adaptable to future requirements.

##### Liskov Substitution Principle (LSP)
**Implementation**: Subtypes are substitutable for their base types
- `AuthenticationProxy` (`src/main/java/com/example/demo/service/AuthenticationProxy.java`): Proxy implementation of authentication
- `UserServiceProxy` (`src/main/java/com/example/demo/service/UserServiceProxy.java`): Proxy implementation of user service

**Explanation**: The Liskov Substitution Principle ensures that objects of a superclass can be replaced with objects of its subclasses without breaking the application. In our system, this is demonstrated through the proxy pattern implementations. The `AuthenticationProxy` extends the base `AuthenticationService` while adding CAPTCHA verification, but maintains the same contract and behavior expectations. Similarly, `UserServiceProxy` extends `UserService` functionality while preserving the original interface. This principle ensures that derived classes don't violate the expectations set by their base classes, maintaining consistency and reliability throughout the application. It allows for safe extension of functionality while preserving the original behavior.

##### Interface Segregation Principle (ISP)
**Implementation**: Clients aren't forced to depend on unused methods
- `UserRepository` (`src/main/java/com/example/demo/repository/UserRepository.java`): User-specific database operations
- `GameRepository` (`src/main/java/com/example/demo/repository/GameRepository.java`): Game-specific database operations
- `GameWinnerRepository` (`src/main/java/com/example/demo/repository/GameWinnerRepository.java`): Winner-specific database operations

**Explanation**: The Interface Segregation Principle states that clients should not be forced to depend on interfaces they don't use. In our application, this is achieved through specialized repository interfaces. Instead of having one large repository interface with all possible database operations, we have separate interfaces for different entities. The `UserRepository` focuses on user-related queries, `GameRepository` handles game-specific operations, and `GameWinnerRepository` manages winner records. This separation ensures that each service only depends on the methods it actually needs, reducing coupling and making the code more maintainable. It also makes it easier to implement new features without affecting existing functionality.

##### Dependency Inversion Principle (DIP)
**Implementation**: High-level modules depend on abstractions
- `UserService` (`src/main/java/com/example/demo/service/UserService.java`): Depends on `UserRepository` interface
- `GameService` (`src/main/java/com/example/demo/service/GameService.java`): Depends on `GameRepository` interface
- `AuthenticationProxy` (`src/main/java/com/example/demo/service/AuthenticationProxy.java`): Depends on `AuthenticationService` interface

**Explanation**: The Dependency Inversion Principle states that high-level modules should not depend on low-level modules, but both should depend on abstractions. In our application, this is implemented through interface-based design. Services like `UserService` and `GameService` depend on repository interfaces rather than concrete implementations. This abstraction allows for easy substitution of implementations, such as switching between different database technologies or testing with mock repositories. The `AuthenticationProxy` depends on the `AuthenticationService` interface, enabling different authentication strategies without modifying the proxy. This principle promotes loose coupling, making the system more flexible and easier to test and maintain.

#### 2. Other Key Principles

##### DRY (Don't Repeat Yourself)
**Implementation**: Shared utilities for common operations
- `CaptchaService` (`src/main/java/com/example/demo/service/CaptchaService.java`): Centralized CAPTCHA handling
- `GameService` (`src/main/java/com/example/demo/service/GameService.java`): Reusable game management logic
- `UserService` (`src/main/java/com/example/demo/service/UserService.java`): Centralized user management

**Explanation**: The DRY principle emphasizes that every piece of knowledge should have a single, unambiguous representation in a system. In our application, this is demonstrated through centralized services that handle specific functionality. The `CaptchaService` encapsulates all CAPTCHA-related logic, preventing duplication across different parts of the application. The `GameService` centralizes game management operations, ensuring consistent behavior and reducing the risk of inconsistencies. The `UserService` provides a single point of truth for user-related operations. This approach reduces code duplication, makes maintenance easier, and ensures that changes only need to be made in one place. It also helps prevent bugs that can occur when similar logic is implemented in multiple places.

##### KISS (Keep It Simple, Stupid)
**Implementation**: Simple, focused classes
- `User` (`src/main/java/com/example/demo/model/User.java`): Simple user entity
- `Game` (`src/main/java/com/example/demo/model/Game.java`): Simple game entity
- `GameWinner` (`src/main/java/com/example/demo/model/GameWinner.java`): Simple winner entity

**Explanation**: The KISS principle advocates for simplicity in design and implementation. In our application, this is reflected in our entity classes and their relationships. The `User` class maintains a simple structure with essential properties and clear relationships. The `Game` class focuses on core game functionality without unnecessary complexity. The `GameWinner` class represents a straightforward relationship between games and winners. This simplicity makes the code easier to understand, maintain, and extend. It reduces the likelihood of bugs and makes the system more reliable. By avoiding over-engineering and keeping classes focused on their primary responsibilities, we create a more maintainable and robust application.

##### YAGNI (You Aren't Gonna Need It)
**Implementation**: Only essential features implemented
- `GameWinnerService` (`src/main/java/com/example/demo/service/GameWinnerService.java`): Focused winner recording
- `UserService` (`src/main/java/com/example/demo/service/UserService.java`): Essential user operations
- `GameService` (`src/main/java/com/example/demo/service/GameService.java`): Essential game operations

**Explanation**: The YAGNI principle encourages developers to implement only the features that are actually needed, avoiding speculative development. In our application, this is demonstrated through focused service implementations. The `GameWinnerService` provides only the necessary functionality for recording and retrieving winners. The `UserService` implements essential user management operations without unnecessary features. The `GameService` focuses on core game functionality. This approach prevents over-engineering and keeps the codebase lean and maintainable. It also reduces development time and complexity, allowing for faster iterations and easier maintenance. By implementing only what is needed, we create a more efficient and focused application.

### Design Patterns

#### 1. Proxy Pattern
**Purpose**: Control access to login functionality
**Implementation**:
- `AuthenticationProxy` (`src/main/java/com/example/demo/service/AuthenticationProxy.java`): Adds CAPTCHA verification to authentication
- `UserServiceProxy` (`src/main/java/com/example/demo/service/UserServiceProxy.java`): Adds CAPTCHA verification to user operations

**Explanation**: The Proxy Pattern provides a surrogate or placeholder for another object to control access to it. In our application, we use this pattern to add security features to existing services. The `AuthenticationProxy` wraps the real authentication service and adds CAPTCHA verification before allowing authentication attempts. Similarly, the `UserServiceProxy` extends the base user service with additional security checks. This pattern allows us to add functionality without modifying the original classes, following the Open/Closed Principle. It also provides a way to control access to sensitive operations and add cross-cutting concerns like security and logging. The proxy pattern helps maintain separation of concerns while adding necessary functionality.

#### 2. Factory Pattern
**Purpose**: Encapsulate user object creation
**Implementation**:
- `User` (`src/main/java/com/example/demo/model/User.java`): Entity class with factory methods
- `Game` (`src/main/java/com/example/demo/model/Game.java`): Entity class with factory methods

**Explanation**: The Factory Pattern provides an interface for creating objects while allowing subclasses to alter the type of objects that will be created. In our application, this is implemented through entity classes with factory methods. The `User` class provides methods for creating different types of users with specific roles and permissions. The `Game` class offers factory methods for creating games with different configurations. This pattern encapsulates the object creation logic, making it easier to modify how objects are created without changing the client code. It also provides a centralized place for object creation, ensuring consistency and reducing duplication. The factory pattern helps manage object creation complexity and provides flexibility in how objects are instantiated.

#### 3. Singleton Pattern
**Purpose**: Ensure single instance of critical components
**Implementation**:
- `CaptchaService` (`src/main/java/com/example/demo/service/CaptchaService.java`): Single instance for CAPTCHA management
- `GameService` (`src/main/java/com/example/demo/service/GameService.java`): Single instance for game management

**Explanation**: The Singleton Pattern ensures that a class has only one instance and provides a global point of access to it. In our application, this is implemented through Spring's singleton scope. The `CaptchaService` is designed as a singleton to maintain a single source of truth for CAPTCHA tokens and their validation. The `GameService` is also a singleton to ensure consistent game state management across the application. This pattern is particularly useful for services that manage shared resources or maintain application-wide state. It helps prevent resource contention and ensures consistent behavior throughout the application. The singleton pattern is implemented using Spring's dependency injection, which handles the lifecycle and scoping of these services.

#### 4. Repository Pattern
**Purpose**: Abstract data access layer
**Implementation**:
- `UserRepository` (`src/main/java/com/example/demo/repository/UserRepository.java`): User data access
- `GameRepository` (`src/main/java/com/example/demo/repository/GameRepository.java`): Game data access
- `GameWinnerRepository` (`src/main/java/com/example/demo/repository/GameWinnerRepository.java`): Winner data access

**Explanation**: The Repository Pattern provides an abstraction of data access, separating the business logic from the data access logic. In our application, this is implemented through Spring Data JPA repositories. Each repository interface (`UserRepository`, `GameRepository`, `GameWinnerRepository`) defines the data access operations for its respective entity. This pattern encapsulates the details of data access, making it easier to change the underlying storage mechanism without affecting the business logic. It also provides a consistent interface for data operations, making the code more maintainable and testable. The repository pattern helps maintain separation of concerns and makes the application more flexible to changes in data storage requirements.

#### 5. Prototype Pattern
**Purpose**: Create new objects by copying existing ones
**Implementation**:
- `Game` (`src/main/java/com/example/demo/model/Game.java`): Cloneable game instances
- `User` (`src/main/java/com/example/demo/model/User.java`): Cloneable user instances

**Explanation**: The Prototype Pattern allows objects to be created by copying existing objects, rather than creating new instances from scratch. In our application, this is implemented through the `Cloneable` interface in our entity classes. The `Game` and `User` classes implement cloning functionality to create new instances based on existing ones. This pattern is particularly useful when object creation is expensive or when we need to create objects that are similar to existing ones. It provides a way to create new objects while maintaining their state and relationships. The prototype pattern helps improve performance and provides flexibility in object creation.

### Spring-Specific Design Patterns

1. **Dependency Injection**
   - Constructor-based injection in `UserService` (`src/main/java/com/example/demo/service/UserService.java`)
   - Field-based injection in `GameService` (`src/main/java/com/example/demo/service/GameService.java`)
   - Setter-based injection in `AuthenticationProxy` (`src/main/java/com/example/demo/service/AuthenticationProxy.java`)

**Explanation**: Dependency Injection is a design pattern that implements Inversion of Control for resolving dependencies. In our application, we use Spring's DI container to manage object dependencies. Constructor-based injection is used in `UserService` to ensure required dependencies are provided at creation time. Field-based injection in `GameService` provides a convenient way to inject optional dependencies. Setter-based injection in `AuthenticationProxy` allows for flexible dependency configuration. This pattern promotes loose coupling, makes the code more testable, and simplifies dependency management. It also helps maintain separation of concerns and makes the application more modular.

2. **Bean Scopes**
   - Singleton scope in `CaptchaService` (`src/main/java/com/example/demo/service/CaptchaService.java`)
   - Prototype scope in `Game` (`src/main/java/com/example/demo/model/Game.java`)

**Explanation**: Bean Scopes define the lifecycle and visibility of Spring-managed beans. In our application, we use different scopes for different components. The `CaptchaService` uses singleton scope to maintain a single instance throughout the application lifecycle. The `Game` class uses prototype scope to create a new instance for each request. This pattern helps manage object lifecycle and resource usage efficiently. It also provides flexibility in how objects are created and shared across the application. Bean scopes help optimize resource usage and ensure proper object lifecycle management.

3. **AOP (Aspect-Oriented Programming)**
   - Transaction management in `GameService` (`src/main/java/com/example/demo/service/GameService.java`)
   - Logging in `GameWinnerService` (`src/main/java/com/example/demo/service/GameWinnerService.java`)

**Explanation**: Aspect-Oriented Programming allows separation of cross-cutting concerns from the main business logic. In our application, we use AOP for transaction management and logging. The `GameService` uses `@Transactional` annotations to manage database transactions. The `GameWinnerService` implements logging aspects to track game outcomes. This pattern helps maintain separation of concerns and reduces code duplication for cross-cutting concerns. It also makes the code more maintainable and easier to understand. AOP provides a clean way to implement features that span multiple components of the application.

### Architectural Patterns

1. **MVC (Model-View-Controller)**
- Controllers: `src/main/java/com/example/demo/controller/`
- Models: `src/main/java/com/example/demo/model/`
- Views: `src/main/resources/templates/`

**Explanation**: The Model-View-Controller pattern separates an application into three main components. In our application, controllers handle HTTP requests and delegate to services, models represent the data and business logic, and views render the user interface. This separation of concerns makes the application more maintainable and easier to test. It also allows for independent development of different components and provides flexibility in how the application is presented to users. The MVC pattern helps organize code in a logical way and promotes clean separation of responsibilities.

2. **Layered Architecture**
   - Presentation Layer: `src/main/java/com/example/demo/controller/`
   - Business Layer: `src/main/java/com/example/demo/service/`
   - Data Access Layer: `src/main/java/com/example/demo/repository/`
   - Domain Layer: `src/main/java/com/example/demo/model/`

**Explanation**: Layered Architecture organizes the application into distinct layers, each with specific responsibilities. In our application, the presentation layer handles user interaction, the business layer implements core logic, the data access layer manages persistence, and the domain layer defines the business entities. This organization promotes separation of concerns and makes the application more maintainable. It also provides clear boundaries between different aspects of the application and makes it easier to modify or replace individual layers. The layered architecture helps manage complexity and provides a clear structure for the application.

3. **Dependency Injection**
   - Spring's DI container configuration in `src/main/java/com/example/demo/DemoApplication.java`
   - Service layer dependencies in `src/main/java/com/example/demo/service/`

**Explanation**: Dependency Injection is a fundamental architectural pattern in Spring applications. In our application, the DI container is configured in the main application class, and services declare their dependencies through constructor or field injection. This pattern promotes loose coupling and makes the application more testable and maintainable. It also simplifies configuration and makes it easier to manage object lifecycles. Dependency Injection helps create a flexible and modular architecture that can adapt to changing requirements.

## Getting Started

### Prerequisites
- Java Development Kit (JDK) 17 or higher
- MySQL Server 8.0 or higher
- Maven 3.6+ for dependency management
- Modern web browser (Chrome, Firefox, Safari, or Edge)

### Setup Instructions

1. Clone the repository:
```bash
git clone [repository-url]
cd demo
```

2. Database Configuration:
   - Create a new MySQL database:
   ```sql
   CREATE DATABASE demo_db;
   ```
   
   - Configure database connection in `src/main/resources/application.properties`:
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/demo_db
   spring.datasource.username=your_username
   spring.datasource.password=your_password
   spring.jpa.hibernate.ddl-auto=update
   ```

3. Build and Run:
```bash
# Build the project
./mvnw clean install

# Run the application
./mvnw spring-boot:run
```

4. Access the Application:
   - Open your web browser
   - Navigate to `http://localhost:8080`
   - You should see the login/register page

## Development

### Running Tests
```bash
./mvnw test
```

### Development Mode
```bash
./mvnw spring-boot:run -Dspring-boot.run.profiles=dev
```

## Troubleshooting

### Common Issues
1. Database Connection
   - Verify MySQL is running
   - Check connection credentials
   - Ensure database exists

2. Build Failures
   - Verify Java version
   - Check Maven installation
   - Clear Maven cache if needed

3. Runtime Errors
   - Check application logs
   - Verify port availability
   - Ensure all services are running

## Contributing

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request