package com.project.mausam.utility.errorhandling;

public class CacheDataNotFoundException extends RuntimeException {
  public CacheDataNotFoundException() {
	  super("Cached data not found.");
}
}
