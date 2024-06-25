package com.demo.bookstoreapp.exception;

public class ImageNotSavedException extends Throwable {
  public ImageNotSavedException ( String cause ) {
    super("Failed to save image file " + cause);
  }
}
