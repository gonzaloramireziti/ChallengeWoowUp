package com.woowup.controllers;


import com.woowup.entities.User;

import java.util.HashSet;


public class UsersController {

    //Utilizamos hashset para evitar usuarios repetidos en tiempo de ejecución
    public static HashSet<User> users = new HashSet<>();

    //Añade un usuario al hashset
    public static void addUser(User user){

        users.add(user);

    }



}
