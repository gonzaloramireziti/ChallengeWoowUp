package com.woowup.util;


import com.woowup.entities.Alert;
import com.woowup.entities.AlertInformativa;
import com.woowup.entities.AlertUrgente;
import com.woowup.entities.EnumTipoAlerta;


//Patron de diseño Factory Method para la creación de distintos tipos de alerta
public class AlertFactory {



    //Retornamos el alert correspondiente a la key indicada como parametro
    public static Alert getAlert(EnumTipoAlerta enumTipoAlerta){

        if(enumTipoAlerta.equals(EnumTipoAlerta.INFORMATIVA)){
            return new AlertInformativa();
        }else if(enumTipoAlerta.equals(EnumTipoAlerta.URGENTE)){
            return new AlertUrgente();
        }

        throw new IllegalArgumentException("Tipo de enum para alerta incorrecto");

    }


}
