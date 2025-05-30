# APU Car Sales System (ACSS)

A comprehensive car sales management system built with Spring Boot for APU.

## Features

### Managing Staff Interface
- User Management (Staff & Salesmen)
  - Add, delete, search, and update staff and salesmen information
- Customer Management
  - Approve, delete, search, and update customer information
- Car Management
  - Add, delete, search, and update car information
- Analytics & Reporting
  - Payment analysis
  - Feedback analysis
  - Sales reports

### Salesmen Interface
- Car Sales Management
- Customer Interaction
- Sales Tracking

### Customer Interface
- Car Browsing
- Purchase Management
- Feedback Submission

## Technical Stack

- Java 17
- Spring Boot 3.2.3
- Spring Security with JWT
- Spring Data JPA
- H2 Database
- Maven
- Lombok

## Getting Started

### Prerequisites
- Java 17 or higher
- Maven 3.6 or higher

### Installation

1. Clone the repository:
```bash
git clone [repository-url]
```

2. Navigate to the project directory:
```bash
cd car-sales-system
```

3. Build the project:
```bash
mvn clean install
```

4. Run the application:
```bash
mvn spring-boot:run
```

The application will be available at `http://localhost:8080/api`

### Default Credentials

Managing Staff:
- Username: admin
- Password: admin123

## API Documentation

### Managing Staff Endpoints

#### User Management
- `POST /api/managing-staff/staff` - Add new staff
- `DELETE /api/managing-staff/users/{userId}` - Delete user
- `PUT /api/managing-staff/users` - Update user
- `GET /api/managing-staff/staff` - Get all staff
- `GET /api/managing-staff/salesmen` - Get all salesmen

#### Customer Management
- `POST /api/managing-staff/customers/{customerId}/approve` - Approve customer
- `GET /api/managing-staff/customers` - Get all customers

#### Car Management
- `POST /api/managing-staff/cars` - Add new car
- `DELETE /api/managing-staff/cars/{carId}` - Delete car
- `PUT /api/managing-staff/cars` - Update car
- `GET /api/managing-staff/cars` - Get all cars

#### Analytics & Reporting
- `GET /api/managing-staff/sales/by-date-range` - Get sales by date range
- `GET /api/managing-staff/sales/by-salesman/{salesmanId}` - Get sales by salesman
- `GET /api/managing-staff/feedback/unresponded` - Get unresponded feedback
- `POST /api/managing-staff/feedback/{feedbackId}/respond` - Respond to feedback

## Security

The application uses JWT (JSON Web Tokens) for authentication and authorization. All endpoints require appropriate authentication and role-based authorization.

## Database

The application uses H2 database with file persistence. The database console is available at `http://localhost:8080/api/h2-console` when running in development mode.

## Contributing

Please read CONTRIBUTING.md for details on our code of conduct and the process for submitting pull requests.

## License

This project is licensed under the MIT License - see the LICENSE.md file for details 