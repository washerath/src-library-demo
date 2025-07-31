# Library Management System API

A Spring Boot REST API for managing a library system with books, borrowers, and loan operations.

## API Endpoints

### Base URL
```
http://localhost:8080/api/library
```

### 1. Book Management

#### Register a New Book
```bash
curl -X POST http://localhost:8080/api/library/books \
  -H "Content-Type: application/json" \
  -d '{
    "isbn": "978-0-7475-3269-9",
    "title": "Harry Potter and the Philosopher\'s Stone",
    "author": "J.K. Rowling"
  }'
```

#### List All Books
```bash
curl -X GET http://localhost:8080/api/library/books
```

### 2. Borrower Management

#### Register a New Borrower
```bash
curl -X POST http://localhost:8080/api/library/borrowers \
  -H "Content-Type: application/json" \
  -d '{
    "email": "john.doe@example.com",
    "name": "John Doe"
  }'
```

### 3. Loan Operations

#### Borrow a Book
```bash
curl -X POST http://localhost:8080/api/library/books/1/loans \
  -H "Content-Type: application/json" \
  -d '{
    "borrowerEmail": "john.doe@example.com"
  }'
```

#### Return a Book
```bash
curl -X DELETE http://localhost:8080/api/library/books/1/loans \
  -H "Content-Type: application/json" \
  -d '{
    "borrowerEmail": "john.doe@example.com"
  }'
```
## Assumptions


- **Borrower Identification**: Borrowers are uniquely identified by their email addresses.

- **Persistence**: The sample uses an in-memory H2 database for development and demonstration purposes.
- **Authentication/Authorization**: No authentication or authorization is implemented; all API endpoints are open.
- **Book Deletion**: There is no API for deleting books or borrowers in this sample.
- **API Structure**: The API is RESTful and uses standard HTTP status codes for responses.
