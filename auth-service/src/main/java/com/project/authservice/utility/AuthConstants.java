package com.project.authservice.utility;

public class AuthConstants {
    public static final long AUTH_CACHE_DEFAULT_EXPIRY_TIME_IN_MINUTES = 86400000;
    public static final long AUTH_JWT_EXPIRY_TIME_IN_MILLIS = 600000;
    public static final String AUTH_CACHE_NAMESPACE = "authCache";
    public static final String AUTH_CACHE_WITH_EXPIRY_NAMESPACE = "authCacheWithExpiry";
    public static final String AUTH_JWT_ROLE_CLAIM = "role";
}
