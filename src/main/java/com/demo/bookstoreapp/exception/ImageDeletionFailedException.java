package com.demo.bookstoreapp.exception;

public class ImageDeletionFailedException extends RuntimeException {

		public ImageDeletionFailedException ( String message ) {
				super(message);
		}

		public ImageDeletionFailedException ( String message, Throwable cause ) {
				super(message, cause);
		}
}
