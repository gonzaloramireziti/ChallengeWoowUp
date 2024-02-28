package com.woowup.entities;

import com.woowup.controllers.AlertController;
import com.woowup.util.Observer;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


import java.util.HashSet;


//Implementa interfaz para patrón de diseño Observer (Usuario es la clase que observa)


@Getter
@Setter
@AllArgsConstructor
public class User implements Observer {


    private String username;


    private HashSet<Alert> alerts = new HashSet<>();


    //Constructor donde inicializamos el objeto con el username y la lista de alertas vacia
    public User(String username) {
        this.username = username;
    }



    //Sobrecargamos el metodo update para ejecutar la acción correspondiente cuando el observable cambia
    @Override
    public void update(Alert alert) {

        AlertController.alerts.add(alert);

        //Invocamos el metodo onRead de alertState que imprime la alerta



    }

}
