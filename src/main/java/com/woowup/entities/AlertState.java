package com.woowup.entities;


//Clase abstracta para state pattern
public abstract class AlertState {

    Alert alert;

    AlertState(Alert alert){
        this.alert = alert;
    }


    public abstract String onRead();



}
