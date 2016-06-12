package com.fibonacci.api;

/**
 * Created by aslan on 11/06/16.
 */
abstract class GenericAPIResponse {

    private int status = 200;
    private String message = "SUCCESS";

    public int getStatus() {
        return this.status;
    }

    public Object getMessage() {
        return this.message;
    }

}
