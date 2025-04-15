# Spring Boot Web Application

A modern web application with authentication, registration, and CAPTCHA verification. This application provides a secure and user-friendly platform with a responsive design and robust security features.

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

## Architecture & Design Patterns

The application follows a structured architecture with various design patterns to ensure maintainability, scalability, and security.

### Core Design Patterns

#### 1. Proxy Pattern (CAPTCHA Implementation)
**Implementation Location**: `src/main/java/com/example/demo/security/CaptchaAuthenticationProxy.java`
**Purpose**: Controls access to login functionality by adding a CAPTCHA validation layer
**Benefits**: 
- Adds security layer before authentication
- Separates CAPTCHA validation from core authentication
- Enables easy modification of CAPTCHA implementation
```java
public class CaptchaAuthenticationProxy implements AuthenticationService {
    public boolean authenticate(LoginRequest request) {
        if (!captchaService.validateCaptcha(request.getCaptchaToken(), request.getCaptchaInput())) {
            throw new InvalidCaptchaException();
        }
        return authService.authenticate(request);
    }
}
```

#### 2. Factory Pattern (User Creation)
**Implementation Location**: `src/main/java/com/example/demo/service/UserFactory.java`
**Purpose**: Encapsulates user object creation logic
**Benefits**:
- Standardizes user creation process
- Ensures proper initialization of user fields
- Makes user type modifications easier
```java
public class UserFactory {
    public User createUser(String type, String username, String password) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        return user;
    }
}
```

#### 3. Observer Pattern (Authentication Events)
**Implementation Location**: `src/main/java/com/example/demo/security/AuthenticationEventListener.java`
**Purpose**: Handles post-authentication actions and notifications
**Benefits**:
- Decouples authentication from related actions
- Enables easy addition of new post-auth behaviors
- Improves system maintainability
```java
@Component
public class AuthenticationEventListener {
    @EventListener
    public void handleAuthenticationSuccess(AuthenticationSuccessEvent event) {
        updateLoginStats(event.getAuthentication());
    }
}
```

#### 4. Singleton Pattern (Database Connection)
**Implementation Location**: `src/main/java/com/example/demo/config/DatabaseConfig.java`
**Purpose**: Ensures single instance of database connection pool
**Benefits**:
- Prevents multiple connection pools
- Efficient resource management
- Single point of access to database
```java
@Configuration
public class DatabaseConfig {
    @Bean
    @Scope("singleton")
    public DataSource dataSource() {
        return new HikariDataSource(config);
    }
}
```

#### 5. Prototype Pattern (User Sessions)
**Implementation Location**: 
- `src/main/java/com/example/demo/model/UserSession.java`
- `src/main/java/com/example/demo/service/SessionService.java`
**Purpose**: Creates unique session instances for each user
**Benefits**:
- Independent session management
- Easy session cleanup
- Flexible session customization
```java
@Component
@Scope("prototype")
public class UserSession {
    public UserSession createNewSession(User user) {
        return new UserSession(user, UUID.randomUUID().toString());
    }
}
```

### Additional Pattern Implementations

#### 6. Repository Pattern
**Implementation Location**: `src/main/java/com/example/demo/repository/UserRepository.java`
```java
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    boolean existsByUsername(String username);
}
```

#### 7. Strategy Pattern
**Implementation Location**: `src/main/java/com/example/demo/security/PasswordEncoderStrategy.java`
```java
public interface PasswordEncoderStrategy {
    String encode(String rawPassword);
    boolean matches(String rawPassword, String encodedPassword);
}

@Component
public class BCryptPasswordEncoderStrategy implements PasswordEncoderStrategy {
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    
    @Override
    public String encode(String rawPassword) {
        return encoder.encode(rawPassword);
    }
    
    @Override
    public boolean matches(String rawPassword, String encodedPassword) {
        return encoder.matches(rawPassword, encodedPassword);
    }
}
```

### Design Principles

The project follows several key design principles to ensure maintainability, scalability, and code quality.

#### 1. SOLID Principles

##### Single Responsibility Principle (SRP)
**Purpose**: Each class should have only one reason to change
**Implementation**: 
- `UserService` handles user operations
- `EmailService` handles email sending
- `ValidationService` handles input validation
**Benefits**: Easier maintenance, testing, and understanding

##### Open/Closed Principle (OCP)
**Purpose**: Software entities should be open for extension but closed for modification
**Implementation**: 
- `PasswordEncoderStrategy` interface
- `AuthenticationService` interface
- `UserRepository` interface
**Benefits**: Easy to add new features without modifying existing code

##### Liskov Substitution Principle (LSP)
**Purpose**: Subtypes must be substitutable for their base types
**Implementation**:
- `DatabaseAuthenticationService` and `LDAPAuthenticationService` both implement `AuthenticationService`
- `BCryptPasswordEncoder` and `Argon2PasswordEncoder` both implement `PasswordEncoder`
**Benefits**: Interchangeable components, flexible system

##### Interface Segregation Principle (ISP)
**Purpose**: Clients shouldn't be forced to depend on methods they don't use
**Implementation**:
- Separate `UserReadService` and `UserWriteService` interfaces
- `ReadOnlyRepository` and `WriteOnlyRepository` interfaces
**Benefits**: Cleaner dependencies, better maintainability

##### Dependency Inversion Principle (DIP)
**Purpose**: High-level modules should not depend on low-level modules
**Implementation**:
- `UserService` depends on `UserRepository` interface
- `AuthenticationService` depends on `PasswordEncoder` interface
**Benefits**: Loose coupling, easier testing

#### 2. DRY (Don't Repeat Yourself)
**Purpose**: Avoid code duplication
**Implementation**:
- `ValidationUtils` for shared validation logic
- `SecurityUtils` for common security operations
- `DateUtils` for date formatting
**Benefits**: Easier maintenance, consistent behavior

#### 3. KISS (Keep It Simple, Stupid)
**Purpose**: Keep code simple and straightforward
**Implementation**:
- Clear method names
- Small, focused classes
- Minimal dependencies
**Benefits**: Easier to understand and maintain

#### 4. YAGNI (You Aren't Gonna Need It)
**Purpose**: Don't add functionality until it's necessary
**Implementation**:
- Minimal `UserService` with essential methods
- Focused repository interfaces
- Simple validation rules
**Benefits**: Cleaner code, faster development

#### 5. Law of Demeter (LoD)
**Purpose**: Objects should only talk to their immediate friends
**Implementation**:
- `OrderService` uses `UserService` without accessing User internals
- `AuthenticationService` uses `PasswordEncoder` without knowing implementation
**Benefits**: Reduced coupling, better encapsulation

#### 6. Interface Segregation
**Purpose**: Keep interfaces small and focused
**Implementation**:
- Separate `ReadOnlyRepository` and `WriteOnlyRepository`
- `UserReadService` and `UserWriteService` interfaces
**Benefits**: Cleaner dependencies, better maintainability

#### 7. Composition Over Inheritance
**Purpose**: Favor object composition over class inheritance
**Implementation**:
- `SecureUserService` composes `UserService` and `SecurityService`
- `CachingUserService` composes `UserService` and `CacheService`
**Benefits**: More flexible, easier to maintain

### Architectural Layers

1. **Presentation Layer**
   - Controllers handle HTTP requests
   - View templates (Thymeleaf)
   - REST endpoints

2. **Business Layer**
   - Service implementations
   - Business logic
   - Transaction management

3. **Data Access Layer**
   - JPA repositories
   - Database operations
   - Data mapping

### Security Architecture

1. **Authentication Flow**
   ```
   Request → CAPTCHA Proxy → Authentication Provider → User Details Service
   ```

2. **Session Management**
   - Secure session handling
   - Token-based authentication
   - Session timeout management

## Prerequisites

Before you begin, ensure you have the following installed:
- Java Development Kit (JDK) 17 or higher
- MySQL Server 8.0 or higher
- Maven 3.6+ for dependency management
- Modern web browser (Chrome, Firefox, Safari, or Edge)
- Git for version control

## Setup Instructions

### 1. Clone the Repository
```bash
git clone [repository-url]
cd demo
```

### 2. Database Configuration
1. Create a new MySQL database:
```sql
CREATE DATABASE demo_db;
```

2. Configure database connection in `src/main/resources/application.properties`:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/demo_db
spring.datasource.username=your_username
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=update
```

### 3. Build the Project
```bash
./mvnw clean install
```

### 4. Run the Application
```bash
./mvnw spring-boot:run
```

### 5. Access the Application
- Open your web browser
- Navigate to `http://localhost:8080`
- You should see the login/register page

## Project Structure
```
demo/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/example/demo/
│   │   │       ├── controller/    # Request handlers
│   │   │       ├── model/         # Data models
│   │   │       ├── repository/    # Database operations
│   │   │       ├── service/       # Business logic
│   │   │       └── DemoApplication.java
│   │   └── resources/
│   │       ├── static/           # Static resources
│   │       │   ├── css/         # Stylesheets
│   │       │   ├── images/      # Image assets
│   │       │   └── js/          # JavaScript files
│   │       └── templates/       # HTML templates
│   └── test/                    # Test files
├── pom.xml                      # Dependencies
└── README.md                    # Documentation
```

## Key Features in Detail

### Login System
- Email-based authentication
- Password validation and encryption
- Remember-me functionality
- Password reset capability

### Registration Process
- User data validation
- Email verification
- Secure password requirements
- Duplicate account prevention

### CAPTCHA Implementation
- Random code generation
- Visual distortion for security
- Refresh capability
- Server-side verification

### Security Measures
- Password hashing
- Session timeout
- CSRF protection
- XSS prevention
- SQL injection protection

## Development

### Building from Source
```bash
git clone [repository-url]
cd demo
./mvnw clean install
```

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

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## Support

For support and questions:
- Open an issue in the repository
- Contact the development team
- Check documentation

## Contributing

1. Fork the repository
2. Create a feature branch
3. Commit your changes
4. Push to the branch
5. Create a Pull Request

## Acknowledgments

- Spring Boot team for the framework
- MySQL community
- All contributors and maintainers