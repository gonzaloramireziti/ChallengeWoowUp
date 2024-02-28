package com.woowup.entities;


public class StateAlertNoLeida extends AlertState {


    //Llamamos al constructor de la clase padre
    public StateAlertNoLeida(Alert alert) {
        super(alert);
    }


    //Sobrecargamos el metodo onRead para modificar el comportamiento de lectura
    @Override
    public String onRead() {
        return "-- Alerta no leida -- \n " +
                "Descripción" + this.alert.getDescription();
    }
}
