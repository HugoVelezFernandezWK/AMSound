# Acciones para completar el ejercicio de GIT
## Primer Paso: Instalar Prerrequisitos
<p>En este primer paso se instalan todos los servicios frameworks y utilidades necesarios para el correcto funcionamiento del aplicativo</p>

* Instalar Eclipse IDE
* Instalar Servidor Web (Tompcat) dentro de Eclipse
* Instalar Maven

## Segundo Paso: Clonar repositorio e importar proyecto
<p>Para clonar el repositorio se puede realizar tanto por la consola como por Eclipse, yo lo he realizado mediante la vista de Git en Eclipse. Clicando en Clonar un repositorio de Git se te despliega una ventana en la que tendras que pegar la url del repositorio 
e identificarte con tu cuenta de Git, una vez realizado este paso ya tendrás clonado el repositorio remoto.</p>
<p>En segundo lugar, con el repositorio clonado, clicamos en este y seleccionamos Importar Proyectos, de nuevo se nos abrirá un desplegable y habrá que importar, en este caso, el unico proyecto disponible "practicas-sm-2024".</p>

# Apartados del ejercicio
## 00 - Desplegar base de datos y preparar aplicativo
<p>Antes de realizar los distintos apartados del ejercicio es necesario desplegar una base de datos. Tras una reunión se decidió que yo desplegara una base de datos MySQL en Azure. Como extra para facilitar el acceso a la base de datos desarrollé un pequeño
componente de acceso a datos y una excepción personalizada para unificar los errores que puedan suceder en el componente.</p>

## 02 - Permitir la visualización de errores en el panel
<p>Para este apartado es necesario poder visualizar los errores que se produzcan en la ejecución de aplicativo, para eso tuve que modificar el controlador para capturar las excepciones, crear un metodo que añada los datos de la excepción al JSON que contiene los
datos que posteriormente se muestra en el panel, y por ultimo, crear una apartado en el panel donde se mostrarán los errores en caso de que se produzcan. De esta forma se trasportan los errores a la capa de presentación y aporta información adicional al usuario.</p>

## 08 - Permitir cargar archivos a la base de datos
<p>Para este apartado tuve que crear una nueva tabla que permita almacenar archivos (BLOB), posteriormente hice un pequeño apartado en el panel principal que permita al usuario seleccionar un archivo, a partir del archivo seleccionado se mandan los datos al controlador
y este accede a un metodo que inserta a la base de datos un nuevo registro con el archivo seleccionado.</p>

* **Nota:** En web es imposible conocer la ruta de un archivo por motivos de seguridad por lo que he optado por, desde javascript, leer el contenido del archivo y su nombre, y ya desde el controlador en java he creado un objeto File con los datos del archivo seleccionado.
