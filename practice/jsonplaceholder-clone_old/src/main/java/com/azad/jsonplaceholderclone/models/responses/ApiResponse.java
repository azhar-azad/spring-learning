package com.azad.jsonplaceholderclone.models.responses;

import java.util.Map;

public class ApiResponse {

    private Boolean success;
    private String message;

    private Map<String, ?> data;

    public ApiResponse() {
    }

    public ApiResponse(Boolean success, String message, Map<String, ?> data) {
        this.success = success;
        this.message = message;
        this.data = data;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Map<String, ?> getData() {
        return data;
    }

    public void setData(Map<String, ?> data) {
        this.data = data;
    }
}
