package com.app.template.model.res;

import java.util.List;

public class ResAuth {
    private String date;
    private String message;
    private List<ResDetailAuth> data;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<ResDetailAuth> getData() {
        return data;
    }

    public void setData(List<ResDetailAuth> data) {
        this.data = data;
    }
}
