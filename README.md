# Contenido del repositorio

* **AMSound ->** Proyecto de Android Studio (Aplicación Movil)
* **CAD ->** Componente de acceso a datos desarrollado en NetBeans para la aplicación. (No se ejecuta, se utiliza como libreria)
* **ClienteC ->** Cliente de comunicaciones desarrollado en NetBeans para la aplicación (No se ejecuta, está integrado en la aplicación)
* **POJOS ->** Pojos que manejarán todos los componentes de la aplicación, desarrollado en NetBeans (No se ejecuta, se utiliza como libreria)
* **ServidorC ->** Servidor de comunicaciones necesario para atender las peticiones de los clientes y acceder a la base de datos (Debe estar corriendo en todo momento para el correcto funcionamiento de la aplicación)
* **CreaciónBD.sql ->** Script de creación de la base de datos (Oracle)

## Importante
<p>Para ejecutar el aplicativo correctamente antes se debera montar la base de datos oracle y ejecutar el servidor de comunicaciones para que pueda atender peticiones y acceder a los datos. Ademas es muy importante que tanto en el componente de Servidor y Cliente
de comunicaciones se configure correctemente las direcciones IP</p>

* **Componente de Servidor de comunicaciones ->** Se debe confugurar la IP donde se aloja la base de datos (Proyecto ServidorC >>> Clase ManejadorPeticiones.java >>> Variable HOST)
* **Componente de Servidor de comunicaciones ->** Se deben configurar las credenciales del Sistema gestor de base de datos en la clase ManejadorPeticiones.java (Variables USUARIO_BD y CONTRASENA_BD)
* **Clase ClienteC dentro de la apliación ->** Se debe configurar la IP donde se esta ejecutando el servidor de comunicaciones (Variable IP_SERVIDOR)
