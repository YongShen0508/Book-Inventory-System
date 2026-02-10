# Postman API Request Guide - Book Inventory System

## Base URL

```
http://localhost:8080
```

---

## 📚 Book Controller API Endpoints

### 1. Create Book

**Method:** `POST`  
**URL:** `http://localhost:8080/books/createBook`  
**Headers:**

```
Content-Type: application/json
```

**Body (raw JSON):**

```json
{
  "title": "The Great Gatsby",
  "author": "F. Scott Fitzgerald",
  "genre": "Classic",
  "publicationDate": "1925-04-10",
  "price": 10.99,
  "stockQuantity": 50
}
```

**Success Response (200):**

```json
{
  "status": "success",
  "message": "Book created successfully",
  "data": {
    "id": 1,
    "title": "The Great Gatsby",
    "author": "F. Scott Fitzgerald",
    "genre": "Classic",
    "publicationDate": "1925-04-10",
    "price": 10.99,
    "stockQuantity": 50,
    "createdAt": "2026-02-10T10:30:00",
    "updatedAt": "2026-02-10T10:30:00"
  }
}
```

---

### 2. Get All Books

**Method:** `GET`  
**URL:** `http://localhost:8080/books`  
**Query Parameters:**

- `pageSize` (optional, default: 200)
- `offset` (optional, default: 0)

**Example URLs:**

```
http://localhost:8080/books
http://localhost:8080/books?pageSize=10&offset=0
http://localhost:8080/books?pageSize=20&offset=20
```

**Success Response (200):**

```json
{
  "status": "success",
  "message": "Books retrieved successfully",
  "data": [
    {
      "id": 1,
      "title": "The Great Gatsby",
      "author": "F. Scott Fitzgerald",
      "genre": "Classic",
      "publicationDate": "1925-04-10",
      "price": 10.99,
      "stockQuantity": 50,
      "createdAt": "2026-02-10T10:30:00",
      "updatedAt": "2026-02-10T10:30:00"
    },
    {
      "id": 2,
      "title": "1984",
      "author": "George Orwell",
      "genre": "Dystopian",
      "publicationDate": "1949-06-08",
      "price": 8.99,
      "stockQuantity": 40,
      "createdAt": "2026-02-10T10:31:00",
      "updatedAt": "2026-02-10T10:31:00"
    }
  ]
}
```

---

### 3. Get Book by ID

**Method:** `GET`  
**URL:** `http://localhost:8080/books/{id}`

**Example:**

```
http://localhost:8080/books/1
```

**Success Response (200):**

```json
{
  "status": "success",
  "message": "Book retrieved successfully",
  "data": {
    "id": 1,
    "title": "The Great Gatsby",
    "author": "F. Scott Fitzgerald",
    "genre": "Classic",
    "publicationDate": "1925-04-10",
    "price": 10.99,
    "stockQuantity": 50,
    "createdAt": "2026-02-10T10:30:00",
    "updatedAt": "2026-02-10T10:30:00"
  }
}
```

---

### 4. Update Book

**Method:** `PUT`  
**URL:** `http://localhost:8080/books`  
**Headers:**

```
Content-Type: application/json
```

**Body (raw JSON):**

```json
{
  "id": 1,
  "title": "The Great Gatsby - Updated",
  "author": "F. Scott Fitzgerald",
  "genre": "Classic Fiction",
  "publicationDate": "1925-04-10",
  "price": 12.99,
  "stockQuantity": 45
}
```

**Success Response (200):**

```json
{
  "status": "success",
  "message": "Book updated successfully",
  "data": null
}
```

---

### 5. Delete Book

**Method:** `DELETE`  
**URL:** `http://localhost:8080/books`  
**Headers:**

```
Content-Type: application/json
```

**Body (raw JSON):**

```json
{
  "id": 1
}
```

**Success Response (200):**

```json
{
  "status": "success",
  "message": "Book deleted successfully",
  "data": null
}
```

---

### 6. Search Books by Keyword

**Method:** `GET`  
**URL:** `http://localhost:8080/books/searchByKeyword`  
**Query Parameters:**

- `keyword` (required) - Search in title, author, or genre
- `pageSize` (optional, default: 200)
- `offset` (optional, default: 0)

**Example URLs:**

```
http://localhost:8080/books/searchByKeyword?keyword=Gatsby
http://localhost:8080/books/searchByKeyword?keyword=Orwell&pageSize=10&offset=0
http://localhost:8080/books/searchByKeyword?keyword=Classic
```

**Success Response (200):**

```json
{
  "status": "success",
  "message": "Books retrieved successfully",
  "data": [
    {
      "id": 1,
      "title": "The Great Gatsby",
      "author": "F. Scott Fitzgerald",
      "genre": "Classic",
      "publicationDate": "1925-04-10",
      "price": 10.99,
      "stockQuantity": 50,
      "createdAt": "2026-02-10T10:30:00",
      "updatedAt": "2026-02-10T10:30:00"
    }
  ]
}
```

---

### 7. Search Books by Criteria

**Method:** `GET`  
**URL:** `http://localhost:8080/books/searchByCriteria`  
**Query Parameters:**

- `title` (optional)
- `author` (optional)
- `genre` (optional)
- `minPrice` (optional)
- `maxPrice` (optional)
- `publicationDateStart` (optional, format: yyyy-MM-dd)
- `publicationDateEnd` (optional, format: yyyy-MM-dd)
- `pageSize` (optional, default: 200)
- `offset` (optional, default: 0)

**Example URLs:**

```
http://localhost:8080/books/searchByCriteria?author=Orwell
http://localhost:8080/books/searchByCriteria?genre=Classic&minPrice=5.00&maxPrice=15.00
http://localhost:8080/books/searchByCriteria?title=Great&author=Fitzgerald
http://localhost:8080/books/searchByCriteria?publicationDateStart=1900-01-01&publicationDateEnd=1950-12-31
http://localhost:8080/books/searchByCriteria?minPrice=10.00&pageSize=10&offset=0
```

**Success Response (200):**

```json
{
  "status": "success",
  "message": "Books retrieved successfully",
  "data": [
    {
      "id": 3,
      "title": "1984",
      "author": "George Orwell",
      "genre": "Dystopian",
      "publicationDate": "1949-06-08",
      "price": 8.99,
      "stockQuantity": 40,
      "createdAt": "2026-02-10T10:30:00",
      "updatedAt": "2026-02-10T10:30:00"
    },
    {
      "id": 11,
      "title": "Animal Farm",
      "author": "George Orwell",
      "genre": "Political Fiction",
      "publicationDate": "1945-08-17",
      "price": 7.49,
      "stockQuantity": 60,
      "createdAt": "2026-02-10T10:30:00",
      "updatedAt": "2026-02-10T10:30:00"
    }
  ]
}
```

---

## 🛒 Order Controller API Endpoints

### 1. Create Order

**Method:** `POST`  
**URL:** `http://localhost:8080/orders/createOrder`  
**Headers:**

```
Content-Type: application/json
```

**Body (raw JSON):**

```json
{
  "customerId": 1,
  "orderDate": "2026-02-10T10:30:00",
  "items": [
    {
      "bookId": 1,
      "quantity": 2
    },
    {
      "bookId": 3,
      "quantity": 1
    }
  ]
}
```

**Success Response (200):**

```json
{
  "status": "success",
  "message": "Order created successfully",
  "data": {
    "id": 1,
    "customerId": 1,
    "customerName": "John Doe",
    "orderDate": "2026-02-10T10:30:00",
    "status": "PENDING",
    "totalAmount": 30.97,
    "orderAt": "2026-02-10T10:30:00",
    "expiresAt": "2026-02-17T10:30:00",
    "items": [
      {
        "id": 1,
        "bookId": 1,
        "quantity": 2,
        "price": 10.99,
        "subtotal": 21.98
      },
      {
        "id": 2,
        "bookId": 3,
        "quantity": 1,
        "price": 8.99,
        "subtotal": 8.99
      }
    ]
  }
}
```

---

### 2. Get Order by ID

**Method:** `GET`  
**URL:** `http://localhost:8080/orders/order/{orderId}`

**Example:**

```
http://localhost:8080/orders/order/1
```

**Success Response (200):**

```json
{
  "status": "success",
  "message": "Order retrieved successfully",
  "data": {
    "id": 1,
    "customerId": 1,
    "customerName": "John Doe",
    "orderDate": "2026-02-10T10:30:00",
    "status": "PENDING",
    "totalAmount": 30.97,
    "orderAt": "2026-02-10T10:30:00",
    "expiresAt": "2026-02-17T10:30:00",
    "items": [
      {
        "id": 1,
        "bookId": 1,
        "quantity": 2,
        "price": 10.99,
        "subtotal": 21.98
      }
    ]
  }
}
```

---

### 3. Get Orders by Customer ID

**Method:** `GET`  
**URL:** `http://localhost:8080/orders/customer/{customerId}`

**Example:**

```
http://localhost:8080/orders/customer/1
```

**Success Response (200):**

```json
{
  "status": "success",
  "message": "Orders retrieved successfully",
  "data": [
    {
      "id": 1,
      "customerId": 1,
      "orderDate": "2026-02-10T10:30:00",
      "status": "PENDING",
      "totalAmount": 30.97,
      "items": [...]
    },
    {
      "id": 9,
      "customerId": 1,
      "orderDate": "2026-01-17T15:50:00",
      "status": "PENDING",
      "totalAmount": 44.97,
      "items": [...]
    }
  ]
}
```

---

### 4. Update Order Status

**Method:** `PUT`  
**URL:** `http://localhost:8080/orders/updateOrderStatus`  
**Headers:**

```
Content-Type: application/json
```

**Body (raw JSON):**

```json
{
  "id": 1,
  "status": "CONFIRMED"
}
```

**Valid Status Values:**

- `PENDING`
- `CONFIRMED`
- `SHIPPED`
- `DELIVERED`
- `COMPLETED`
- `CANCELLED`
- `EXPIRED`

**Success Response (200):**

```json
{
  "status": "success",
  "message": "Order status updated successfully",
  "data": null
}
```

---

### 5. Delete Order

**Method:** `DELETE`  
**URL:** `http://localhost:8080/orders/deleteOrder`  
**Headers:**

```
Content-Type: application/json
```

**Body (raw JSON):**

```json
{
  "id": 1
}
```

**Success Response (200):**

```json
{
  "status": "success",
  "message": "Order deleted successfully",
  "data": null
}
```

---

## 👤 Customer Controller API Endpoints

### 1. Create Customer

**Method:** `POST`  
**URL:** `http://localhost:8080/customers/createCustomer`  
**Headers:**

```
Content-Type: application/json
```

**Body (raw JSON):**

```json
{
  "name": "John Doe",
  "email": "john.doe@email.com",
  "phone": "555-0101",
  "address": "123 Main St, New York, NY 10001"
}
```

**Success Response (200):**

```json
{
  "status": "success",
  "message": "Customer created successfully",
  "data": {
    "id": 1,
    "name": "John Doe",
    "email": "john.doe@email.com",
    "phone": "555-0101",
    "address": "123 Main St, New York, NY 10001",
    "createdAt": "2026-02-10T10:30:00",
    "updatedAt": "2026-02-10T10:30:00"
  }
}
```

---

## 📋 Common Response Structures

### Success Response

```json
{
  "status": "success",
  "message": "Operation completed successfully",
  "data": { /* response data */ }
}
```

### Error Response

```json
{
  "status": "error",
  "message": "Error description",
  "errorCode": "ERROR_CODE",
  "timestamp": "2026-02-10T10:30:00"
}
```

---

## 🔧 Postman Collection Setup

### Setting Up Environment Variables

1. Create a new Environment in Postman named "Book Inventory - Local"
2. Add these variables:

| Variable   | Initial Value         | Current Value         |
|------------|-----------------------|-----------------------|
| baseUrl    | http://localhost:8080 | http://localhost:8080 |
| bookId     | 1                     | 1                     |
| orderId    | 1                     | 1                     |
| customerId | 1                     | 1                     |

### Using Variables in Requests

Replace hardcoded values with variables:

```
{{baseUrl}}/books/{{bookId}}
{{baseUrl}}/orders/order/{{orderId}}
{{baseUrl}}/customers/{{customerId}}
```

---

## 🧪 Testing Scenarios

### Scenario 1: Complete Book Management Flow

```
1. POST /books/createBook - Create a new book
2. GET /books - Verify book appears in list
3. GET /books/{id} - Get specific book details
4. PUT /books - Update book information
5. GET /books/{id} - Verify updates
6. DELETE /books - Delete the book
```

### Scenario 2: Order Creation Flow

```
1. GET /books - Check available books and stock
2. GET /customers - Get customer ID
3. POST /orders/createOrder - Create order with items
4. GET /orders/order/{orderId} - Verify order created
5. PUT /orders/updateOrderStatus - Update to CONFIRMED
6. PUT /orders/updateOrderStatus - Update to SHIPPED
7. PUT /orders/updateOrderStatus - Update to DELIVERED
8. PUT /orders/updateOrderStatus - Update to COMPLETED
```

### Scenario 3: Search and Filter

```
1. GET /books/searchByKeyword?keyword=Orwell
2. GET /books/searchByCriteria?genre=Classic&minPrice=5.00&maxPrice=15.00
3. GET /books/searchByCriteria?publicationDateStart=1900-01-01&publicationDateEnd=1950-12-31
```

---

## 🚨 Common Error Responses

### 404 Not Found

```json
{
  "status": "error",
  "message": "Book with ID 999 does not exist",
  "errorCode": "RECORD_NOT_FOUND",
  "timestamp": "2026-02-10T10:30:00"
}
```

### 400 Bad Request (Insufficient Stock)

```json
{
  "status": "error",
  "message": "Insufficient stock for book ID 1. Available: 5, Requested: 10",
  "errorCode": "INSUFFICIENT_STOCK",
  "timestamp": "2026-02-10T10:30:00"
}
```

### 400 Bad Request (Validation Error)

```json
{
  "status": "error",
  "message": "Validation failed",
  "errorCode": "VALIDATION_ERROR",
  "timestamp": "2026-02-10T10:30:00"
}
```

---

## 💡 Tips for Postman Testing

1. **Save Responses**: Use Postman's "Save Response" to compare before/after states
2. **Use Tests**: Add automatic validation in the "Tests" tab
3. **Chain Requests**: Use response data to set environment variables for next request
4. **Collections**: Organize related requests into folders
5. **Pre-request Scripts**: Set up dynamic data (timestamps, random values)

### Example Test Script

```javascript
// In the "Tests" tab of your request
pm.test("Status code is 200", function () {
    pm.response.to.have.status(200);
});

pm.test("Response has success status", function () {
    var jsonData = pm.response.json();
    pm.expect(jsonData.status).to.eql("success");
});

// Save book ID for next request
var jsonData = pm.response.json();
if (jsonData.data && jsonData.data.id) {
    pm.environment.set("bookId", jsonData.data.id);
}
```

---

## 📚 Additional Resources

- Base URL: `http://localhost:8080`
- API Prefix: `/books`, `/orders`, `/customers`
- Content-Type: `application/json` (for POST/PUT)
- Default Pagination: pageSize=200, offset=0

**Note:** Ensure your Spring Boot application is running on port 8080 before testing these endpoints.

