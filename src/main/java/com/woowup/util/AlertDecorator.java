package com.woowup.util;

import com.woowup.entities.Alert;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

//Patron de diseño Decorator para añadir funcionalidad de fecha a la alerta
@Getter
@Setter
public class AlertDecorator extends Alert {

    private Alert alert;

    private LocalDateTime fechaHoraExpiracion;

    public void setFechaHoraExpiracion(LocalDateTime fechaHoraExpiracion) {

        //Si la fecha es anterior a la actual lanzamos una excepción

        if(fechaHoraExpiracion.isBefore(LocalDateTime.now())){
            throw new IllegalArgumentException("La fecha no puede ser anterior a la fecha actual");
        }

    }


}
