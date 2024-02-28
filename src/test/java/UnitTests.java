import com.woowup.controllers.AlertController;
import com.woowup.controllers.ThemeController;
import com.woowup.controllers.UsersController;
import com.woowup.entities.*;
import com.woowup.exceptions.AlertNotFoundException;
import com.woowup.exceptions.SameStateException;
import com.woowup.exceptions.UserNotFoundException;
import com.woowup.util.AlertFactory;
import org.junit.jupiter.api.*;
import org.springframework.test.annotation.Rollback;


import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

//Anotación para ordenación de metodos
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UnitTests {

    User user1,user2,user3;

    Theme theme1, theme2;

    Alert alert1, alert2, alert3, alert4, alert5;


    //Inicializamos variables para la ejecución de los test

    @BeforeEach
    public void setUp(){

        user1 = new User("exampleuser");

        user2 = new User("exampleuser2");

        user3 = new User("exampleuser3");

        theme1 = Theme.builder().descripcion("Futbol").observerSet(new HashSet<>()).build();

        theme2 = Theme.builder().descripcion("Moda").observerSet(new HashSet<>()).build();


        //Acá podriamos hacer uso de patrón builder tambien para una mejor legibilidad

        alert1 = AlertFactory.getAlert(EnumTipoAlerta.INFORMATIVA);
        alert1.setExpired(false);
        alert1.setTheme(theme1);
        alert1.setDescription("I1");
        alert1.setUsers(new HashMap<>());

        alert2 = AlertFactory.getAlert(EnumTipoAlerta.INFORMATIVA);
        alert2.setExpired(false);
        alert2.setTheme(theme2);
        alert2.setDescription("I2");
        alert2.setUsers(new HashMap<>());

        alert3 = AlertFactory.getAlert(EnumTipoAlerta.URGENTE);
        alert3.setExpired(false);
        alert3.setTheme(theme1);
        alert3.setDescription("U1");
        alert3.setUsers(new HashMap<>());

        alert4 = AlertFactory.getAlert(EnumTipoAlerta.URGENTE);
        alert4.setExpired(false);
        alert4.setTheme(theme2);
        alert4.setDescription("U2");
        alert4.setUsers(new HashMap<>());

        alert5 = AlertFactory.getAlert(EnumTipoAlerta.INFORMATIVA);
        alert5.setExpired(false);
        alert5.setTheme(theme1);
        alert5.setDescription("I3");
        alert5.setUsers(new HashMap<>());


    }

    //REQ 1. Se pueden registrar usuarios que recibirán alertas.

    @Test
    @Order(1)
    public void addUser(){

        //Añadimos dos usuarios

        UsersController.addUser(user1);

        UsersController.addUser(user2);

        //La cantidad de usuarios debe ser 2 para la lista estatica

        assertEquals(2,UsersController.users.size());
    }

    //REQ 2. Se pueden registrar temas sobre los cuales se enviarán alertas.

    @Test
    @Order(2)
    public void addTheme(){

        //Añadimos dos temas

        ThemeController.addTema(theme1);

        ThemeController.addTema(theme2);

        //La cantidad de temas debe ser 2 para la lista estatica

        assertEquals(2,ThemeController.themes.size());
    }

    //REQ 3. Los usuarios pueden optar sobre cuales temas quieren recibir alertas.

    @Test
    @Order(3)
    public void subscribeUserToTheme(){

        //Subscribimos al usuario 1 y al usuario 2 al tema 1

        ThemeController.subscribeUser(theme1,user1);

        ThemeController.subscribeUser(theme1,user2);

        //El tema 1 debe tener dos observadores, en este caso, los usuarios que se subscribieron

        assertEquals(2,theme1.getObserverSet().size());


    }
    //REQ 4. Se puede enviar una alerta sobre un tema y lo reciben todos los usuarios que han optado recibir alertas de ese tema.

    @Test
    @Order(4)
    public void sendAlertToAllUsers() throws UserNotFoundException {

        //Subscribimos los usuarios 1 y 2 al tema 1

        ThemeController.subscribeUser(theme1,user1);

        ThemeController.subscribeUser(theme1,user2);

        //Enviamos la alerta a los usuarios subscriptos

        AlertController.sendAlertToAllUsers(alert1);

        //Se espera que los usuarios vinculados a la alerta sean 2

        assertEquals(2,alert1.getUsers().size());

    }

    //REQ 5. Se puede enviar una alerta sobre un tema a un usuario específico, solo lo recibe ese único usuario.

    @Test
    @Order(5)
    public void sendAlertToOneUser() throws UserNotFoundException {

        //Subscribimos a dos usuarios a un tema especifico (theme1)

        ThemeController.subscribeUser(theme1,user1);

        ThemeController.subscribeUser(theme1,user2);

        //Enviamos la alerta 1 al usuario user1

        AlertController.sendAlertToUser(alert1,user1);

        //El usuario user1 debe haber recibido la alerta

        assertEquals(1,user1.getAlerts().size());

        //El usuario user2 no debe tener alertas recibidas

        assertEquals(0,user2.getAlerts().size());



    }

    //REQ 8. Un usuario puede marcar una alerta como leída.

    @Test
    @Order(6)
    public void setAlertAsRead() throws SameStateException, UserNotFoundException {


        //Subscribimos a el usuario 1 al tema 1

        ThemeController.subscribeUser(theme1,user1);

        //Emviamos la alerta al usuario

        AlertController.sendAlertToUser(alert1,user1);

        //Esperamos que en principio la alerta este en estado no leida

        assertEquals(StateAlertNoLeida.class,alert1.getUsers().get(user1).getClass());

        //Cambiamos el estado de la alerta a leida para ese usuario

        alert1.changeState(new StateAlertLeida(alert1),user1);

        //Esperamos que ahora el estado sea leida mediante comparación de clases que representan el estado

        assertEquals(StateAlertLeida.class,alert1.getUsers().get(user1).getClass());




    }

    //REQ 9. Se pueden obtener todas las alertas no expiradas de un usuario que aún no ha leído.


    @Test
    @Order(7)

    public void getAlertsNotExpiredNotRead() throws UserNotFoundException, AlertNotFoundException {

        //Subscribimos al usuario 1 al tema 1

        ThemeController.subscribeUser(theme1,user1);

        //Subscribimos al usuario 1 al tema 2

        ThemeController.subscribeUser(theme2,user1);

        //Enviamos la alerta del tema 1 al usuario 1, la cual vence en 7 dias (estado no leída)

        AlertController.sendAlertToUser(alert1,user1);

        //Enviamos la alerta del tema 2 al usuario 1, la cual suponemos que venció (estado no leída)

        alert2.setExpired(true);

        AlertController.sendAlertToUser(alert2,user1);

        //El resultado debe ser 1, dado que solo debe traernos aquellas no vencidas ni leídas

        assertEquals(1,AlertController.getAlertsNotExpiredNotRead(user1).size());



    }

    //REQ 10. Se pueden obtener todas las alertas no expiradas para un tema. Se informa para cada alerta si es para todos los usuarios o para uno específico.

    @Test
    @Order(8)
    public void getAlertsNotExpiredTheme() throws UserNotFoundException, AlertNotFoundException {

        //Subscribimos al usuario 1 al tema 1

        ThemeController.subscribeUser(theme1,user1);

        //Subscribimos al usuario 2 al tema 1

        ThemeController.subscribeUser(theme1,user2);

        //Subscribimos al usuario 3 al tema 1

        ThemeController.subscribeUser(theme1,user3);

        //Enviamos la alerta 1 que contiene el tema 1 a todos los usuarios

        AlertController.sendAlertToAllUsers(alert1);

        //Como las tres alertas para ese tema no están expiradas debe retornar 3 de size

        assertEquals(3,AlertController.getAlertsNotExpiredTheme(theme1).size());

        //Subscribimos al usuario 1 al tema 2

        ThemeController.subscribeUser(theme2,user1);

        //Como la alerta para ese tema está expirada, debe retornar 0 de size

        assertEquals(0,AlertController.getAlertsNotExpiredTheme(theme2).size());



    }



    //REQ 11. Tanto para el punto 9 como el 10, el ordenamiento de las alertas es el siguiente.....

    @Test
    @Order(9)
    public void ordenationMethod() throws UserNotFoundException, AlertNotFoundException {


        //Limpiamos el array para evitar repeticiones

        AlertController.alerts.clear();

        //Subscribimos al usuario 1 a los dos temas para hacer la prueba (dado que vamos a mandar 5 alerts y algunas contienen un tema u otro)

        ThemeController.subscribeUser(theme1,user1);

        ThemeController.subscribeUser(theme2,user1);


        //ALERT1 INFORMATIVA (I1)
        //ALERT2 INFORMATIVA (I2)
        //ALERT3 URGENTE (U1)
        //ALERT4 URGENTE (U2)
        //ALERT5 INFORMATIVA (I3)


        //Enviamos I1 al usuario 1

        AlertController.sendAlertToUser(alert1,user1);

        //Enviamos I2 al usuario 1

        AlertController.sendAlertToUser(alert2,user1);

        //Enviamos U1 al usuario 1

        AlertController.sendAlertToUser(alert3,user1);

        //Enviamos U2 al usuario 1

        AlertController.sendAlertToUser(alert4,user1);

        //Enviamos I3 al usuario 1

        AlertController.sendAlertToUser(alert5,user1);

        List<Alert> alerts = AlertController.getAlerts();



        assertEquals("U2",alerts.get(0).getDescription());
        assertEquals("U1",alerts.get(1).getDescription());
        assertEquals("I1",alerts.get(2).getDescription());
        assertEquals("I2",alerts.get(3).getDescription());
        assertEquals("I3",alerts.get(4).getDescription());



    }



}
