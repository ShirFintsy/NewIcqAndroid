package com.example.newicqandroid.api;

import java.io.Serializable;

public class ApiTupleResponse implements Serializable {

    private int Item1;

    private String Item2;

    public ApiTupleResponse(int item1, String item2) {
        this.Item1 = item1;
        this.Item2 = item2;
    }

    public int getItem1() {
        return Item1;
    }

    public void setItem1(int item1) {
        this.Item1 = item1;
    }

    public String getItem2() {
        return Item2;
    }

    public void setItem2(String item2) {
        this.Item2 = item2;
    }
}
