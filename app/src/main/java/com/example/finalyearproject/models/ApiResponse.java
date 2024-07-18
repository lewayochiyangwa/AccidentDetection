package com.example.finalyearproject.models;

import com.google.gson.annotations.SerializedName;

public class ApiResponse {
    @SerializedName("success")
    private boolean success;

    @SerializedName("message")
    private String message;

    // Getters and setters
    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

