package com.azad.bazaar.models.responses;

import java.util.List;
import java.util.Map;

public class ApiResponse {

    private Boolean success;
    private String message;

    private Map<String, List<?>> data;

    public ApiResponse() {
    }

    public ApiResponse(Boolean success, String message, Map<String, List<?>> data) {
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

    public Map<String, List<?>> getData() {
        return data;
    }

    public void setData(Map<String, List<?>> data) {
        this.data = data;
    }
}
