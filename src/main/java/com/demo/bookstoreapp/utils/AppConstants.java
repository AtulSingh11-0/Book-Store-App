package com.demo.bookstoreapp.utils;

public class AppConstants {

	// Constants for error messages
	public static final String BAD_REQUEST = "Bad request";
	public static final String NOT_FOUND = "Book with Id Not Found";
	public static final String INTERNAL_SERVER_ERROR = "Internal server error";
	public static final String NO_BOOKS_FOUND = "No books found";
	public static final String CONFLICT = "Conflict - Book with ISBN already exists";

	// Constants for success messages
	public static final String SUCCESS = "success";
	public static final String CREATED = "created";
	public static final String UPDATED = "updated";
	public static final String DELETED = "deleted";

	// Constants for Book and Image related messages
	public static final String BOOK_FOUND = "Book retrieved successfully";
	public static final String BOOK_NOT_FOUND = "Book not found";
	public static final String BOOK_CREATED = "Book created successfully";
	public static final String BOOK_UPDATED = "Book updated successfully";
	public static final String BOOK_DELETED = "Book deleted successfully";
	public static final String BOOK_NOT_FOUND_FOR_SEARCH = "No books found matching the search criteria";
	public static final String IMAGE_UPLOAD_ERROR = "Error uploading image";
	public static final String IMAGE_DELETE_ERROR = "Error deleting image";

	// Constants for Book and Image related paths
	public static final String DATE_FORMAT = "yyyy-MM-dd::HH:mm:ss";
	public static final String FILE_NAME_FORMAT = "%s_%s_%s";

	// private constructor to prevent instantiation
	private AppConstants() {
	}

}
