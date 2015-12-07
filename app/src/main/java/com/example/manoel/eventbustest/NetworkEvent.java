package com.example.manoel.eventbustest;

/**
 * Created by manoel on 07/12/15.
 */
public class NetworkEvent {
    private String data;

    public NetworkEvent(String data){
        this.data = data;
    }

    public String getData(){
        return data;
    }
}
