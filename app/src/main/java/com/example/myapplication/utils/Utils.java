package com.example.myapplication.utils;
import com.example.myapplication.data.User;

public class Utils {
    public static final String APIKEY = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6Iml4cnV6YmZxaW52b2txendnaHJwIiwicm9sZSI6ImFub24iLCJpYXQiOjE3Njk1NDQxOTcsImV4cCI6MjA4NTEyMDE5N30.j11KIVQwLTGc9N5c5ZUSnzGoeYeWmR_Gr44jmXVug78";

    public static final String CONTENT_TYPE = "application/json";

    public static User user = new User("", "");

    public static final String BASE_URL = "https://ixruzbfqinvokqzwghrp.supabase.co/auth/v1/";

    public static final String GRANT_TYPE = "password";

    public static String TOKEN = "";
}
