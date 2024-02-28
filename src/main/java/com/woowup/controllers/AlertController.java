package com.woowup.controllers;

import com.woowup.entities.Alert;
import com.woowup.entities.StateAlertNoLeida;
import com.woowup.entities.Theme;
import com.woowup.entities.User;
import com.woowup.exceptions.AlertNotFoundException;
import com.woowup.exceptions.UserNotFoundException;
import com.woowup.util.ListOrdenation;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;


public class AlertController {


    //Referencia estatica de los alerts en tiempo de ejecución
    public static ArrayList<Alert> alerts = new ArrayList<>();


    //Envia una alerta a un usuario especifico
    public static void sendAlertToUser(Alert alert, User user) throws UserNotFoundException {


        //El usuario debe estar subscripto al tópico en cuestión, sino largamos una excepción

       if(alert.getTheme().getObserverSet().stream().noneMatch(u -> u.getUsername().equals(user.getUsername()))){
           throw new UserNotFoundException("Usuario no encontrado para ese tópico");

       }

        //Añadimos la alerta al usuario especifico con el estado no leida

        alert.getUsers().put(user,new StateAlertNoLeida(alert));

        //Añadimos la alerta a la lista de alertas del usuario

        user.getAlerts().add(alert);

        //Notificamos al usuario

        alert.getTheme().notifyObserver(user,alert);

    }

    //Enviamos una alerta a todos los usuarios
    public static void sendAlertToAllUsers(Alert alert) throws UserNotFoundException {


        //Obtenemos todos los usuarios que están seteados como observadores

        Set<User> usersObservers = alert.getTheme().getObserverSet();

        //Ningun usuario se encuentra escuchando el tópico de la alerta, lanzamos una excepción

        if(usersObservers.isEmpty()){
            throw new UserNotFoundException("No se encontraron usuarios subscriptos a ese tópico");
        }

        //Agregamos cada usuario al alert con el estado no leída y guardamos el alert para el usuario

        usersObservers.forEach(user -> {

        alert.getUsers().put(user,new StateAlertNoLeida(alert));

        user.getAlerts().add(alert);

        });



        //Notificamos a los usuarios sobre la nueva alerta

        alert.getTheme().notifyObservers(alert);






    }

    //Obtenemos todas las alertas utilizando el método de ordenación
    public static List<Alert> getAlerts(){


        return ListOrdenation.orderList(alerts.stream().toList()).stream()
           .toList();

    }

    //Retorna todas las alertas no expiradas y no leidas del usuario
    public static List<Alert> getAlertsNotExpiredTheme(Theme theme) throws AlertNotFoundException {

        //Utilizamos el metódo de ordenación creado y luego Java Stream API para filtrar el array por alertas no expiradas para un tema

        List<Alert> alertsFoundOrdened = ListOrdenation.orderList(alerts.stream().toList()).stream()
                .filter(alert -> alert.getTheme().equals(theme) && !alert.isExpired()).toList();

        if(!alerts.isEmpty()){

            //Recorremos cada alerta e imprimimos el informe

            for(Alert alert: alertsFoundOrdened){

                String informarAlerta = (alert.getUsers().size() == 1) ? "La alerta " + alert.getDescription() + "fue enviada para un solo usuario"
                        : "La alerta "+alert.getDescription() + " fue enviada para todos los usuarios";

                System.out.println(informarAlerta);

            }

            return alertsFoundOrdened;
        }else{
            throw new AlertNotFoundException("No se hallaron alertas para este usuario");
        }


    }

    //Retorna todas las alertas no expiradas y no leidas para un usuario en especifico
    public static List<Alert> getAlertsNotExpiredNotRead(User user) throws AlertNotFoundException {

        //Utilizamos Java Stream API para filtrar el array por alertas no expiradas y no leidas
        //Nota: utilizo .equals para comparar si la alerta para el usuario en cuestión tiene el estado no leida (contiene esa clase)

        List<Alert> alertsFoundOrdened = ListOrdenation.orderList(user.getAlerts().stream().toList()).stream().
                filter(a -> !a.isExpired() && a.getUsers().get(user).getClass().equals(StateAlertNoLeida.class)).toList();

        //Array vacio como respuesta, lanzamos excepción

        if(alertsFoundOrdened.isEmpty()){
            throw new AlertNotFoundException("No se hallaron alertas para ese tema");
        }


        return alertsFoundOrdened;


    }



}
