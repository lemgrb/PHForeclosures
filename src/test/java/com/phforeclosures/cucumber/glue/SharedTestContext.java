package com.phforeclosures.cucumber.glue;

public class SharedTestContext {
    private static int port;
    private static int statusCode;
    private static String responseBody;
    
    public static void setPort(int port) {
        SharedTestContext.port = port;
    }
    
    public static int getPort() {
        return port;
    }
    
    public static void setStatusCode(int statusCode) {
        SharedTestContext.statusCode = statusCode;
    }
    
    public static int getStatusCode() {
        return statusCode;
    }
    
    public static void setResponseBody(String responseBody) {
        SharedTestContext.responseBody = responseBody;
    }
    
    public static String getResponseBody() {
        return responseBody;
    }
}