package com.woowup.entities;


public class StateAlertLeida extends AlertState {

    //Llamamos al constructor de la clase padre
    public StateAlertLeida(Alert alert) {
        super(alert);
    }


    //Sobrecargamos el metodo onRead para modificar el comportamiento de lectura

    @Override
    public String onRead() {
        return "-- Alerta ya leida -- \n " +
                "Descripción" + this.alert.getDescription();

    }


}
