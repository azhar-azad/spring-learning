package com.azad.onlinecourse.common.generics;

import com.azad.onlinecourse.common.exception.ApiError;

public interface ResponseModel {

    String getUid();
    ApiError getError();
}
