package com.kalangga.rhscdn.base;


import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class BaseResponse<T> {

    @JsonIgnore
    private int httpCode;
    
    @JsonIgnore
    private Boolean isSuccess =true;

    @JsonProperty("status_code")
    private String statusCode;

    @JsonProperty("status_message")
    private String statusMessage;

    private T data;

    public void sendNotFound(T aData, String message) {
        this.httpCode = 404;
        this.statusCode = "404";
        this.statusMessage = message == null ? "Data Not Found" :message;
        this.data = aData;
        this.isSuccess = false;
    }

    public void sendValidationError() {
        this.httpCode = 400;
        this.statusCode = "400";
        this.statusMessage = "Request Validation Error";
        this.isSuccess = false;
    }

    public void sendSuccess(T aData, String message) {
        this.httpCode = 200;
        this.statusCode = "200";
        this.statusMessage = message == null ? "Success" :message;;
        this.data = aData;
    }
    
    public void sendCreated(T aData) {
        this.httpCode = 201;
        this.statusCode = "201";
        this.statusMessage = "Data Creation Succeeded";
        this.data = aData;
    }

    public void sendBadRequest(T aData,String message) {
        this.httpCode = 400;
        this.statusCode= "400";
        this.statusMessage = message == null ? "Bad Request" :message;
        this.data = aData;
        this.isSuccess = false;
    }
  
}
