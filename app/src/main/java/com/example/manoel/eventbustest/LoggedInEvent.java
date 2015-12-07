package com.example.manoel.eventbustest;

/**
 * Created by manoel on 07/12/15.
 */
public class LoggedInEvent {
    private String data;

    public LoggedInEvent(String data){
        this.data = data;
    }

    public String getData(){
        return data;
    }
}
