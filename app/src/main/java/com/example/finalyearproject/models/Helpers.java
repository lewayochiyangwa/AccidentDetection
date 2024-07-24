package com.example.finalyearproject.models;


public  class Helpers {
    public static String connection() throws Exception {
       // String connection = "http://192.168.100.2:5000/";
        String connection = " https://b6a9-41-60-108-79.ngrok-free.app/";
        return connection;
    }

    public static String validTwilioNumber() throws Exception {
        String validPhone = "+12512572467";
        return validPhone;
    }

}