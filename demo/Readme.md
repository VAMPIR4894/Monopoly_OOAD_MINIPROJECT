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
```java
public class CaptchaAuthenticationProxy implements AuthenticationService {
    private final AuthenticationService authService;
    private final CaptchaService captchaService;

    @Override
    public boolean authenticate(LoginRequest request) {
        // First validate CAPTCHA
        if (!captchaService.validateCaptcha(request.getCaptchaToken(), 
                                          request.getCaptchaInput())) {
            throw new InvalidCaptchaException();
        }
        // Then proceed with actual authentication
        return authService.authenticate(request);
    }
}
```

#### 2. Singleton Pattern (Database Connection)
```java
@Component
public class DatabaseConnectionManager {
    private static DatabaseConnectionManager instance;
    private Connection connection;
    
    private DatabaseConnectionManager() {
        // Private constructor to prevent instantiation
    }
    
    public static synchronized DatabaseConnectionManager getInstance() {
        if (instance == null) {
            instance = new DatabaseConnectionManager();
        }
        return instance;
    }
    
    public Connection getConnection() {
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/demo_db",
                "username",
                "password"
            );
        }
        return connection;
    }
}
```

#### 3. Prototype Pattern (User Sessions)
```java
public class UserSession implements Cloneable {
    private String sessionId;
    private User user;
    private Date lastActivity;
    
    @Override
    public UserSession clone() {
        try {
            UserSession clone = (UserSession) super.clone();
            // Deep copy of user object
            clone.user = new User(this.user);
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }
}

// Usage in Session Manager
public class SessionManager {
    public UserSession createNewSession(User user) {
        UserSession prototype = new UserSession();
        prototype.setUser(user);
        return prototype.clone();
    }
}
```

#### 4. Factory Pattern (User Creation)
```java
public class UserFactory {
    public User createUser(String type, String username, String password) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setRole(UserRole.valueOf(type.toUpperCase()));
        return user;
    }
}
```

#### 5. Observer Pattern (Authentication Events)
```java
@Component
public class AuthenticationEventListener {
    @EventListener
    public void handleAuthenticationSuccess(AuthenticationSuccessEvent event) {
        // Handle successful login
        updateLoginStats(event.getAuthentication());
        notifySecurityAdmin(event);
    }
}
```

### Spring-Specific Design Patterns

1. **Dependency Injection**
   - Constructor-based injection
   - Setter-based injection
   - Field-based injection

2. **Bean Scopes**
   - Singleton (default)
   - Prototype
   - Request
   - Session
   - Application

3. **AOP (Aspect-Oriented Programming)**
   - Logging aspects
   - Transaction management
   - Security aspects

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