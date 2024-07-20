package com.demo.bookstoreapp.exception;

public class ImageUploadingFailedException extends RuntimeException {

		public ImageUploadingFailedException ( String message ) {
				super(message);
		}

		public ImageUploadingFailedException ( String message, Throwable cause ) {
				super(message, cause);
		}
}
