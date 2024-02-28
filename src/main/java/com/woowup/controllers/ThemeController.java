package com.woowup.controllers;


import com.woowup.entities.Theme;
import com.woowup.entities.User;

import java.util.HashSet;

public class ThemeController {


    //Referencia estatica para los temas
    public static HashSet<Theme> themes = new HashSet<>();

    //Permite añadir un nuevo tema
    public static void addTema(Theme theme){
        themes.add(theme);
    }


    //Permite subscribir un usuario a un tema especifico
    public static void subscribeUser(Theme theme, User user) {
        theme.getObserverSet().add(user);
    }


    //Permite subscribir a todos los usuarios a un tema especifico
    public static void subscribeAllUser(Theme theme){

        UsersController.users.forEach(theme::addObserver);


    }

}
