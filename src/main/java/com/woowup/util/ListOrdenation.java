package com.woowup.util;

import com.woowup.entities.Alert;
import com.woowup.entities.AlertInformativa;
import com.woowup.entities.AlertUrgente;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ListOrdenation {


    //Metodo de ordenación para cumplir con los requerimientos
    public static List<Alert> orderList(List<Alert> alertList){




        // Filtrar las alertas urgentes

        List<Alert> alertUrgentes = new ArrayList<>();
        List<Alert> alertInformativas = new ArrayList<>();


        for (Alert alert : alertList) {

            if (alert.getClass().equals(AlertUrgente.class)) {
                alertUrgentes.add(alert);
            } else if (alert.getClass().equals(AlertInformativa.class)) {
                alertInformativas.add(alert);
            }

        }


        // Invertir la lista de alertas urgentes
        Collections.reverse(alertUrgentes);

        // Concatenar las listas
        alertUrgentes.addAll(alertInformativas);





        return alertUrgentes;

    }


}
