# Acciones para completar el ejercicio de GIT
## Primer Paso: Instalar Prerrequisitos
<p>En este primer paso se instalan todos los servicios frameworks y utilidades necesarios para el correcto funcionamiento del aplicativo</p>

* Instalar Eclipse IDE
* Instalar Servidor Web (Tompcat) dentro de Eclipse
* Instalar Maven

## Segundo Paso: Clonar repositorio e importar proyecto
<p>Para clonar el repositorio se puede realizar tanto por la consola como por Eclipse, yo lo he realizado mediante la vista de Git en Eclipse. Clicando en Clonar un repositorio de Git se te despliega una ventana en la que tendras que pegar la url del repositorio 
e identificarte con tu cuenta de Git, una vez realizado este paso ya tendrás clonado el repositorio remoto.</p>
<p>En segundo lugar, con el repositorio clonado, clicamos en este y seleccionamos Importar Proyectos, de nuevo se nos abrirá un desplegable y habrá que importar, en este caso, el unico proyecto disponible "practicas-sm-2024"</p>

# Apartados del ejercicio
## 00 - Desplegar base de datos y preparar aplicativo
<p>Antes de realizar los distintos apartados del ejercicio es necesario desplegar una base de datos. Tras una reunión se decidió que yo desplegara una base de datos MySQL en Azure. Como extra para facilitar el acceso a la base de datos desarrollé un pequeño
componente de acceso a datos y una excepción personalizada para unificar los errores que puedan suceder en el componente</p>
