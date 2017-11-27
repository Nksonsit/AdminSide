package com.myapp.adminside.model;

import java.util.List;

/**
 * Created by ishan on 27-11-2017.
 */

public class BaseResponse<T> {
    private int Status;
    private String Message;
    private List<T> Data;

    public List<T> getData() {
        return Data;
    }

    public void setData(List<T> data) {
        Data = data;
    }

    public int getStatus() {
        return Status;
    }

    public void setStatus(int status) {
        Status = status;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }
}
