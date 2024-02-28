package com.woowup.entities;

import com.woowup.exceptions.UserNotFoundException;
import com.woowup.util.Observable;
import com.woowup.util.Observer;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

//Implementa interfaz para patrón de diseño Observer (Tema es la clase observable)

@Getter
@Setter
@Builder
public class Theme implements Observable {

    private String descripcion;

    private Set<User> observerSet;


    //Subscribe al tópico
    @Override
    public void addObserver(User user) {

        observerSet.add(user);

    }

    @Override
    public void deleteObserver(User user) {

        observerSet.remove(user);

    }

    //Notifica a un observador especifico y ejecuta el metodo update pasandole la alerta como parametro
    public void notifyObserver(User user, Alert alert) throws UserNotFoundException {

        Optional<User> userFound = observerSet.stream().filter(u -> u.equals(user)).findFirst();

        if(userFound.isEmpty()){

            throw new UserNotFoundException("El usuario no existe.");

        }

        userFound.get().update(alert);

        alert.getUsers().put(userFound.get(),new StateAlertNoLeida(alert));


    }

    //Notifica a todos los observadores de un tema en concreto
    @Override
    public void notifyObservers(Alert alert) {

        for(Observer observer: observerSet){

            observer.update(alert);

        }

    }
}
