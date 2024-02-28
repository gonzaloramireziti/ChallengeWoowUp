# Sistema de Alertas

Este es un ejercicio que propone implementar un sistema de alertas simplificado, similar al que se encuentra en varias aplicaciones como Facebook. El sistema permite registrar usuarios, temas sobre los cuales se enviarán alertas, enviar alertas a todos los usuarios interesados en un tema específico o a un usuario en particular, marcar alertas como leídas, y obtener alertas no leídas para un usuario o para un tema.

## Requisitos del Sistema

1. **Registro de Usuarios:** Los usuarios pueden registrarse para recibir alertas.
2. **Registro de Temas:** Se pueden registrar temas sobre los cuales se enviarán alertas.
3. **Suscripción a Temas:** Los usuarios pueden optar por recibir alertas sobre temas específicos.
4. **Envío de Alertas a Todos los Usuarios:** Se puede enviar una alerta sobre un tema y todos los usuarios suscritos a ese tema la recibirán.
5. **Envío de Alertas a Usuario Específico:** Se puede enviar una alerta sobre un tema a un usuario específico, quien será el único receptor.
6. **Expiración de Alertas:** Las alertas pueden tener una fecha y hora de expiración.
7. **Tipos de Alertas:** Las alertas pueden ser informativas o urgentes.
8. **Marcar Alertas como Leídas:** Los usuarios pueden marcar las alertas como leídas.
9. **Obtener Alertas no Leídas de un Usuario:** Se pueden obtener todas las alertas no expiradas de un usuario que aún no ha sido leídas.
10. **Obtener Alertas no Expiradas para un Tema:** Se pueden obtener todas las alertas no expiradas para un tema, indicando si es para todos los usuarios suscritos o para un usuario específico.

## Implementación

El sistema se implementa en un lenguaje de programación orientado a objetos (se sugiere utilizar Python) y se incluyen pruebas unitarias para verificar su correcto funcionamiento.

### Clases Principales

1. **Usuario:** Representa a un usuario que puede recibir alertas.
2. **Tema:** Representa un tema sobre el cual se enviarán alertas.
3. **Alerta:** Modela una alerta que se puede enviar a uno o varios usuarios suscritos a un tema.
4. **AlertaInformativa:** Subclase de Alerta que representa una alerta informativa.
5. **AlertaUrgente:** Subclase de Alerta que representa una alerta urgente.

### Funcionalidades

- Registro de usuarios y temas.
- Suscripción de usuarios a temas.
- Envío de alertas a usuarios suscritos a un tema.
- Marcar alertas como leídas por parte de los usuarios.
- Obtener alertas no leídas para un usuario o para un tema, según el criterio de orden especificado.

### Tests Unitarios

Se incluyen pruebas unitarias para cada funcionalidad del sistema, asegurando su correcto funcionamiento en diferentes casos de uso.

## Ejecución del Código

Para ejecutar el código, sigue estos pasos:

1. Clona el repositorio en tu máquina local.
2. Ejecuta el script principal.
3. Observa la salida de las pruebas unitarias para verificar el funcionamiento del sistema.


*Nota: Este README proporciona una visión general del sistema y las instrucciones básicas para su ejecución. Para obtener detalles más técnicos sobre la implementación, consulta el código fuente y las pruebas unitarias adjuntas en el repositorio.*
