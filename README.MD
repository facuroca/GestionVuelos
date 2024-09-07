# Proyecto de Gestión de Vuelos

Este proyecto es una aplicación de gestión de vuelos que permite administrar aviones, locaciones, tripulantes y vuelos. La aplicación incluye una interfaz gráfica para facilitar la interacción del usuario.

## Estructura del Proyecto

El proyecto está organizado en los siguientes paquetes:

- `controller`: Contiene los controladores para manejar la lógica de negocio.
  - `AvionesController`
  - `LocacionesController`
  - `TripulantesController`
  - `VuelosController`
- `view`: Contiene las clases relacionadas con la interfaz gráfica.
  - `MainGUI`
- `model`: Contiene las clases de modelo (si existen).

## Requisitos

- Java 8 o superior
- IDE como IntelliJ IDEA, Eclipse o Visual Studio Code

## Instalación

1. Clona el repositorio a tu máquina local:
    ```sh
    git clone https://github.com/facuroca/GestionVuelos.git
    ```
2. Abre el proyecto en tu IDE preferido.
3. Asegúrate de tener configurado el SDK de Java 8 o superior en tu IDE.

## Ejecución

Para ejecutar la aplicación, debes proporcionar un argumento en la línea de comandos. Este argumento puede ser cualquier cadena que necesite tu aplicación.

### Desde el IDE

1. Configura la clase principal `Principal` como la clase de inicio.
2. Proporciona el argumento necesario en la configuración de ejecución.
3. Ejecuta la aplicación.

### Desde la línea de comandos

1. Compila el proyecto:
    ```sh
    javac -d bin -sourcepath src src/Principal.java
    ```
2. Ejecuta la aplicación proporcionando el argumento necesario:
    ```sh
    java -cp bin Principal <argumento>
    ```

## Uso

La aplicación proporciona una interfaz gráfica (`MainGUI`) que permite al usuario interactuar con el sistema de gestión de vuelos. Los controladores (`AvionesController`, `LocacionesController`, `TripulantesController`, `VuelosController`) manejan la lógica de negocio y se comunican con la interfaz gráfica.

## Contribución

Si deseas contribuir a este proyecto, por favor sigue estos pasos:

1. Haz un fork del repositorio.
2. Crea una nueva rama (`git checkout -b feature/nueva-funcionalidad`).
3. Realiza tus cambios y haz commit (`git commit -am 'Agrega nueva funcionalidad'`).
4. Sube tus cambios a tu fork (`git push origin feature/nueva-funcionalidad`).
5. Abre un Pull Request en el repositorio original.

## Licencia

Este proyecto está licenciado bajo la Licencia MIT. Para más detalles, consulta el archivo `LICENSE`.

## Contacto

Para cualquier consulta o sugerencia, por favor contacta a [gas1nker@gmail.com](mailto:gas1nker@gmail.com).