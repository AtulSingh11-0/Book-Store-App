# Bookstore Application

A Spring Boot application for managing a bookstore. The application allows for the creation, retrieval, updating, and deletion of book records. It also supports searching books by title or ISBN with pagination and sorting.

## Table of Contents

- [Overview](#overview)
- [Features](#features)
- [Technologies](#technologies)
- [Setup and Installation](#setup-and-installation)
- [Usage](#usage)
- [API Endpoints](#api-endpoints)
- [Example Responses](#example-responses)
- [Exception Handling](#exception-handling)
- [Swagger Documentation](#swagger-documentation)

## Overview

This application is built using Spring Boot and MongoDB. It provides a RESTful API for managing books in a bookstore.

## Features

- Create, retrieve, update, and delete book records
- Search books by title or ISBN
- Pagination and sorting of book records
- Validation for book data
- Custom exception handling

## Technologies

- Java 17
- Spring Boot
- MongoDB
- Lombok
- JPA
- Maven

## Setup and Installation

### Prerequisites

- Java 17
- Maven
- MongoDB

### Installation Steps

1. Clone the repository:
    ```sh
    git clone https://github.com/AtulSingh11-0/Book-Store-App.git
    cd bookstore-app
    ```

2. Install the dependencies:
    ```sh
    mvn install
    ```

3. Configure MongoDB in `application.properties`:
    ```properties
    spring.data.mongodb.uri=mongodb://localhost:27017/bookstore
    ```

4. Run the application:
    ```sh
    mvn spring-boot:run
    ```

5. Access the API documentation: [Swagger UI](http://localhost:6969/swagger-ui.html)

## Usage

Once the application is running, you can interact with the API using tools like Postman or Curl.

## API Endpoints

### Book Endpoints

- **Create a new book**
    ```http
    POST /api/v1/books/
    ```
    **Request Body (form-data):**
    ```form-data
      Content-Type: multipart/form-data

      title: Example Title
      author: Example Author
      summary: Example Summary
      publishYear: 2023
      isbn: 1234567890123
      image: [Upload your image file here]
    ```

- **Update a book**
    ```http
    PUT /api/v1/books/{id}
    ```
    **Request Body (form-data):**
    ```form-data
      Content-Type: multipart/form-data

      title: Example Title
      author: Example Author
      summary: Example Summary
      publishYear: 2023
      isbn: 1234567890123
      image: [Upload your image file here]
    ```

- **Delete a book**
    ```http
    DELETE /api/v1/books/{id}
    ```

- **Get a book by ID**
    ```http
    GET /api/v1/books/{id}
    ```

- **Get all books**
    ```http
    GET /api/v1/books?pageNo=0&pageSize=10&sortBy=id&order=asc
    ```

- **Search books by title**
    ```http
    GET /api/v1/books/search-by-title?title=example&pageNo=0&pageSize=10&sortBy=id&order=asc
    ```

- **Search books by ISBN**
    ```http
    GET /api/v1/books/search-by-isbn?isbn=1234567890123&pageNo=0&pageSize=10&sortBy=id&order=asc
    ```

## Example Responses

**Request:**

- **Create a new book**
    ```http
    POST /api/v1/books/
    ```
    **Response Body:**
    ```json
    {
      "status": "created",
      "statusCode": 201,
      "message": "Book created successfully",
      "data": {
        "id": "667beadd7c1711725f248a0a",
        "title": "The Martian",
        "author": "Andy Weir",
        "summary": "A science fiction novel about an astronaut stranded on Mars and his struggle to survive.",
        "publishYear": 2011,
        "isbn": "978055341800",
        "image": {
            "id": "667beadd7c1711725f248a09",
            "publicId": "bookstoreapp/<filename>_<date::yyyy-MM-dd>::<time::HH:mm:ss>",
            "url": "<image_url>/bookstoreapp/<filename>_<date::yyyy-MM-dd>::<time::HH:mm:ss>.png",
            "createdDate": "2024-06-26T10:18:05.733+00:00",
            "lastModifiedDate": "2024-06-26T10:18:05.733+00:00"
        },
        "createdDate": "2024-06-26T10:18:05.751+00:00",
        "lastModifiedDate": "2024-06-26T10:18:05.751+00:00"
      }
    }
    ```

- **Get a book by ID**
    ```http
    GET /api/v1/books/667bdf28d608bd246873c834
    ```
    **Response Body:**
    ```json
    {
      "status": "success",
      "statusCode": 200,
      "message": "Book retrieved successfully",
      "data": {
        "id": "667bdf28d608bd246873c834",
        "title": "The Martian",
        "author": "Andy Weir",
        "summary": "A science fiction novel about an astronaut stranded on Mars and his struggle to survive.",
        "publishYear": 2011,
        "isbn": "978055341800",
        "image": {
            "id": "667bdf28d608bd246873c833",
            "publicId": "bookstoreapp/<filename>_<date::yyyy-MM-dd>::<time::HH:mm:ss>",
            "url": "<image_url>/bookstoreapp/<filename>_<date::yyyy-MM-dd>::<time::HH:mm:ss>.png",
            "createdDate": "2024-06-26T09:28:08.030+00:00",
            "lastModifiedDate": "2024-06-26T09:28:08.030+00:00"
        },
        "createdDate": "2024-06-26T09:28:08.075+00:00",
        "lastModifiedDate": "2024-06-26T09:28:08.075+00:00"
      }
    }
    ```

- **Delete a book by ID**
    ```http
    DELETE /api/v1/books/667bdf28d608bd246873c834
    ```
    **Response Body:**
    ```json
    {
      "status": "deleted",
      "statusCode": 200,
      "message": "Book deleted successfully",
      "data": true
    }
    ```

## Exception Handling

Custom exception handling is implemented to manage errors gracefully. The following exceptions are handled:

- `BookWithIdNotFoundException`: Thrown when a book with a specific ID is not found.
- `BookWithIsbnAlreadyExistsException`: Thrown when trying to create a book with an ISBN that already exists.
- Generic exceptions for other unhandled errors.

Example of an error response:
```json
{
    "status": "error",
    "statusCode": 404,
    "message": [
        "Book not found with id 66794326337c184a7988b0"
    ]
}
```

## Swagger Documentation

Explore the API using Swagger UI:

- **Swagger UI URL:** [http://localhost:6969/swagger-ui.html](http://localhost:6969/swagger-ui.html)

Swagger UI provides a graphical interface for exploring the endpoints, making it easier to understand and interact with the API.
