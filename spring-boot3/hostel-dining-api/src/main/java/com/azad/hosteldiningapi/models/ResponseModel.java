package com.azad.hosteldiningapi.models;

import com.azad.hosteldiningapi.common.exceptions.ApiError;

public interface ResponseModel {

    String getUid();
    ApiError getError();
}
