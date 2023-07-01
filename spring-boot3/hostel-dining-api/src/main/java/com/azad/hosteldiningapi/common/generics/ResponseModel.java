package com.azad.hosteldiningapi.common.generics;

import com.azad.hosteldiningapi.common.exceptions.ApiError;

public interface ResponseModel {

    String getUid();
    ApiError getError();
}
