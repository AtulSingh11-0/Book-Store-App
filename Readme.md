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
    git clone https://github.com/yourusername/bookstore-app.git
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

## Usage

Once the application is running, you can interact with the API using tools like Postman or Curl.

## API Endpoints

### Book Endpoints

- **Create a new book**
    ```http
    POST /api/v1/books/
    ```
    **Request Body:**
    ```json
    {
      "title": "Example Title",
      "author": "Example Author",
      "summary": "Example Summary",
      "publishYear": 2023,
      "isbn": "1234567890123"
    }
    ```

- **Update a book**
    ```http
    PUT /api/v1/books/{id}
    ```
    **Request Body:**
    ```json
    {
      "title": "Updated Title",
      "author": "Updated Author",
      "summary": "Updated Summary",
      "publishYear": 2023,
      "isbn": "1234567890123"
    }
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
        "id": "60d5f9a0c2f4b146d4e29e3a",
        "title": "Example Title",
        "author": "Example Author",
        "summary": "Example Summary",
        "publishYear": 2023,
        "isbn": "1234567890123"
      }
    }
    ```

- **Get a book by ID**
    ```http
    GET /api/v1/books/60d5f9a0c2f4b146d4e29e3a
    ```
    **Response Body:**
    ```json
    {
      "status": "success",
      "statusCode": 200,
      "message": "Book retrieved successfully",
      "data": {
        "id": "60d5f9a0c2f4b146d4e29e3a",
        "title": "Example Title",
        "author": "Example Author",
        "summary": "Example Summary",
        "publishYear": 2023,
        "isbn": "1234567890123"
      }
    }
    ```

- **Get all books**
    ```http
    GET /api/v1/books?pageNo=0&pageSize=10&sortBy=id&order=asc
    ```
    **Response Body:**
    ```json
    {
      "status": "success",
      "statusCode": 200,
      "message": "Books retrieved successfully",
      "data": [
        {
          "id": "60d5f9a0c2f4b146d4e29e3a",
          "title": "Example Title",
          "author": "Example Author",
          "summary": "Example Summary",
          "publishYear": 2023,
          "isbn": "1234567890123"
        },
        {
          "id": "60d5f9a0c2f4b146d4e29e3b",
          "title": "Another Title",
          "author": "Another Author",
          "summary": "Another Summary",
          "publishYear": 2022,
          "isbn": "9876543210987"
        }
      ],
      "pagination": {
        "currentPage": 0,
        "currentItem": 2,
        "totalPages": 1,
        "totalItems": 2,
        "hasNext": false,
        "hasPrevious": false,
        "sort": "id: ASC",
        "sortBy": "id",
        "order": "asc"
      }
    }
    ```

- **Search books by title**
    ```http
    GET /api/v1/books/search-by-title?title=example&pageNo=0&pageSize=10&sortBy=id&order=asc
    ```
    **Response Body:**
    ```json
    {
      "status": "success",
      "statusCode": 200,
      "message": "Books retrieved successfully",
      "data": [
        {
          "id": "60d5f9a0c2f4b146d4e29e3a",
          "title": "Example Title",
          "author": "Example Author",
          "summary": "Example Summary",
          "publishYear": 2023,
          "isbn": "1234567890123"
        }
      ],
      "pagination": {
        "currentPage": 0,
        "currentItem": 1,
        "totalPages": 1,
        "totalItems": 1,
        "hasNext": false,
        "hasPrevious": false,
        "sort": "id: ASC",
        "sortBy": "id",
        "order": "asc"
      }
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
  "message": "Book not found with id 123"
}