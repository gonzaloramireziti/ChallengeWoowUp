package com.woowup.entities;


import com.woowup.exceptions.SameStateException;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;
import java.util.HashMap;

@Data
public class Alert {


    private String description;

    private boolean expired;

    private Theme theme;

    private HashMap<User, AlertState> users;



    //Actualizamos el estado de la alerta (leida, no leida)
    public void changeState(AlertState state,User user) throws SameStateException {


        if(this.users.get(user).getClass().equals(state.getClass())){

            throw new SameStateException("La alerta ya tiene ese estado.");

        }else{

            this.users.remove(user);

            this.users.put(user, state);
        }



    }


}
