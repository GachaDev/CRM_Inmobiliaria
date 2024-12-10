# CRM Inmobiliaria

## a. Nombre del proyecto
**CRM Inmobiliaria**

## b. Idea del proyecto
La idea es hacer un CRM que se encargue de la organización de una inmobiliaria la cual tenga varios vendedores. Luego todas las propiedades que se gestionen salgan automaticamente en la app web.

## c. Justificación del proyecto
En el sector inmobiliario, la gestión eficiente de propiedades y la organización del equipo de ventas son aspectos críticos para el éxito empresarial. Las inmobiliarias suelen enfrentar desafíos como la falta de integración de información, la dificultad para coordinar equipos y la actualización manual de los datos en múltiples plataformas. Por ello, surge la necesidad de implementar una solución tecnológica que optimice estos procesos.

Este proyecto tiene como objetivo desarrollar un CRM (Customer Relationship Management) específico para el sector inmobiliario. La plataforma estará diseñada para centralizar la gestión de propiedades, coordinar las actividades de los vendedores y automatizar la publicación de propiedades en una aplicación web.

## d. Descripción detallada de las tablas

### 1. Usuarios
Representa a los vendedores de la inmobiliaria

| Campo            | Tipo     | Restricciones           |
|-------------------|----------|-------------------------|
| **id**           | Long     | PRIMARY KEY             |
| **username**       | String   | UNIQUE              |
| **password**   | String   |                 |
| **rol**          | String   |                 |
| **fecha_registro** | Date    |                 |
| **id_propietario** | LONG    | NULLABLE                |

---

### 2. Propiedad
Contiene los datos de la propiedad a vender.

| Campo               | Tipo     | Restricciones           |
|----------------------|----------|-------------------------|
| **id**              | Long     | PRIMARY KEY             |
| **id_propietario**          | Long   | FOREIGN KEY  (Propietarios)               |
| **direccion**     | String   |                         |
| **precio**          | Double   | NULLABLE                |
| **vendida** | Boolean    |                |
| **oculta** | Boolean    |                |
| **id_usuario**  | Long      | FOREIGN KEY (Usuarios)  |

---

### 3. Propietario
Contiene los propietarios de las propiedades.

| Campo            | Tipo     | Restricciones               |
|-------------------|----------|-----------------------------|
| **id**           | Long     | PRIMARY KEY                 |
| **nombre** | String     |                     |
| **apellidos**    | String     |                     |
| **telefono**   | String      |      |
| **genero**  | String      | NULLABLE     |
| **casado**  | Boolean      | NULLABLE     |
| **n_hijos**  | Integer      | NULLABLE     |

## Diagrama E/R

https://drive.google.com/file/d/1ZGk58pfa-kD9k4RlAZvzYUrDvaCLaHE6/view?usp=sharing

## Endpoints

### 1. Usuarios

  - `GET /usuarios/`: Permite obtener la información de todos los usuarios.
    - **RUTA PRIVADA** Todas las peticiones a este endpoint se permiten si eres admin.
    - **Entrada**: N/A.
    - **Salida**: Lista con todos los objetos UserDTO de los usuarios.
    - **Excepciones**:
      - Unauthorized (Error 403): No tienes suficientes permisos.
      - Internal Server Error (Error 500): Error interno de la base de datos.
  - `GET /usuarios/{id}`: Permite obtener información de un usuario a traves de su id.
    - **RUTA PRIVADA** Todas las peticiones a este endpoint se permiten si eres admin o se le permite acceder al usuario que este asociado a la id solicitada.
    - **Entrada**: Parametro `id`.
    - **Salida**: Objeto UserDTO.
    - **Excepciones**:
      - Bad Request Exception (Error 400): El campo id no puede estar vacío.
      - Illegal number exception (Error 400): La id debe de ser un número.
      - Unauthorized (Error 403): No tienes suficientes permisos.
      - Username not found (Error 404): No existe ningun usuario asociado a esa id.
      - Internal Server Error (Error 500): Error interno de la base de datos.
  - `POST /usuarios/`: Permite crear un usuario.
    - **RUTA PÚBLICA** Todas las peticiones a este endpoint deben permitirse.
    - **Entrada**: JSON con `username` y `password`.
    - **Salida**: Objeto UserDTO.
    - **Excepciones**:
      - Bad Request Exception (Error 400): Los campos username y password no pueden estar vacios.
      - Internal Server Error (Error 500): Error interno de la base de datos.
  - `POST /usuarios/login`: Permite al usuario autenticarse.
    - **RUTA PÚBLICA** Todas las peticiones a este endpoint deben permitirse.
    - **Entrada**: JSON con `username` y `password`.
    - **Salida**: Token de la sesión si las credenciales son válidas.
    - **Excepciones**:
      - Bad Request Exception (Error 400): Los campos username y password no pueden estar vacios.
      - Invalid Password Exception (Error 401): La contraseña introducida es incorrecta.
      - Username not found (Error 404): No existe ningun usuario asociado a ese username.
      - Internal Server Error (Error 500): Error interno de la base de datos.
  - `PUT /usuarios/{username}`: Permite actualizar el username o password de un usuario.
    - **RUTA PRIVADA** Todas las peticiones a este endpoint se permiten si eres admin o se le permite acceder al usuario que este asociado al username pasado por parametro.
    - **Entrada**: Parametro `username` y JSON UserDTO con la nueva información del usuario.
    - **Salida**: Nuevo objeto UserDTO.
    - **Excepciones**:
      - Bad Request Exception (Error 400): El campo username y los campos del objeto user no pueden estar vacios.
      - Unauthorized (Error 403): No tienes suficientes permisos.
      - Username not found (Error 404): No existe ningun usuario asociado a ese username.
      - Internal Server Error (Error 500): Error interno de la base de datos.
  - `DELETE /usuarios/{username}`: Permite al administrador eliminar un usuario por su username.
    - **RUTA PRIVADA** Las peticiones se permiten si eres administrador.
    - **Entrada**: Parametro `username`.
    - **Salida**: N/A.
    - **Excepciones**:
      - Bad Request Exception (Error 400): El campo username no puede estar vacío.
      - Unauthorized (Error 403): No tienes suficientes permisos.
      - Username not found (Error 404): No existe ningun usuario asociado a ese username
      - Internal Server Error (Error 500): Error interno de la base de datos.

### 2. Propiedad

  - `GET /propiedades/`: Permite obtener la información de todas las propiedades.
    - **RUTA PRIVADA** Todas las peticiones a este endpoint se permiten si eres admin.
    - **Entrada**: N/A.
    - **Salida**: Lista con todos los objetos PropiedadDTO de todas las propiedades.
    - **Excepciones**:
      - Unauthorized (Error 403): No tienes suficientes permisos.
      - Internal Server Error (Error 500): Error interno de la base de datos.
  - `GET /propiedades/{id}`: Permite obtener información de una propiedad a traves de su id.
    - **RUTA PRIVADA** Todas las peticiones a este endpoint se permiten si eres admin o se le permite acceder al usuario que este asociado a la propiedad solicitada.
    - **Entrada**: Parametro `id`.
    - **Salida**: Objeto PropiedadDTO.
    - **Excepciones**:
      - Bad Request Exception (Error 400): El campo id no puede estar vacío.
      - Illegal number exception (Error 400): La id debe de ser un número.
      - Unauthorized (Error 403): No tienes suficientes permisos.
      - Propiedad not found (Error 404): No existe ninguna propiedad asociada a esa id.
      - Internal Server Error (Error 500): Error interno de la base de datos.
  - `POST /propiedades/`: Permite crear una propiedad.
    - **RUTA PRIVADA** Todas las peticiones a este endpoint deben permitirse si eres administrador.
    - **Entrada**: JSON con `id_propietario`, `direccion`, `precio` y `oculta`.
    - **Salida**: Objeto PropiedadDTO.
    - **Excepciones**:
      - Bad Request Exception (Error 400): Los campos `id_propietario`, `direccion`, `precio` y `oculta` no pueden estar vacios.
      - Illegal number exception (Error 400): La id del propietario y el precio deben de ser un número.
      - Propietario Not Found Exception (Error 404): No hay ningun propietario asociado a esa id.
      - Internal Server Error (Error 500): Error interno de la base de datos.
  - `PUT /propiedades/{id}`: Permite actualizar la propiedad.
    - **RUTA PRIVADA** Todas las peticiones a este endpoint se permiten si eres admin.
    - **Entrada**: Id de la propiedad por parametro y JSON PropiedadDTO con la nueva información de la propiedad.
    - **Salida**: Nuevo objeto PropiedadDTO.
    - **Excepciones**:
      - Bad Request Exception (Error 400): Los campos del objeto propiedad o la id no pueden estar vacios.
      - Illegal number exception (Error 400): La id, la id del propietario y el precio deben de ser un número.
      - Unauthorized (Error 403): No tienes suficientes permisos.
      - Propiedad not found (Error 404): No existe ninguna propiedad con esa id.
      - Internal Server Error (Error 500): Error interno de la base de datos.
  - `DELETE /propiedades/{id}`: Permite al administrador eliminar una propiedad por su id.
    - **RUTA PRIVADA** Las peticiones se permiten si eres administrador.
    - **Entrada**: Parametro `id`.
    - **Salida**: N/A.
    - **Excepciones**:
      - Bad Request Exception (Error 400): El campo id no puede estar vacío.
      - Illegal number exception (Error 400): La id debe de ser un número.
      - Unauthorized (Error 403): No tienes suficientes permisos.
      - Propiedad not found (Error 404): No existe ninguna propiedad asociada a esa id.
      - Internal Server Error (Error 500): Error interno de la base de datos.

### 3. Propietario

  - `GET /propietarios/`: Permite obtener la información de todos los propietarios.
    - **RUTA PRIVADA** Todas las peticiones a este endpoint se permiten si eres admin.
    - **Entrada**: N/A.
    - **Salida**: Lista con todos los objetos PropietarioDTO de todos los propietarios.
    - **Excepciones**:
      - Unauthorized (Error 403): No tienes suficientes permisos.
      - Internal Server Error (Error 500): Error interno de la base de datos.
  - `GET /propietarios/{id}`: Permite obtener información de una propietario a traves de su id.
    - **RUTA PRIVADA** Todas las peticiones a este endpoint se permiten si eres admin.
    - **Entrada**: Parametro `id`.
    - **Salida**: Objeto PropietarioDTO.
    - **Excepciones**:
      - Bad Request Exception (Error 400): El campo id no puede estar vacío.
      - Illegal number exception (Error 400): La id debe de ser un número.
      - Unauthorized (Error 403): No tienes suficientes permisos.
      - Propietario not found (Error 404): No existe ningun propietario asociado a esa id.
      - Internal Server Error (Error 500): Error interno de la base de datos.
  - `POST /propietarios/`: Permite crear un propietario.
    - **RUTA PRIVADA** Todas las peticiones a este endpoint deben permitirse si eres administrador.
    - **Entrada**: JSON con `nombre`, `apellidos`, `telefono`, `genero`, `casado` y `n_hijos`.
    - **Salida**: Objeto PropietarioDTO.
    - **Excepciones**:
      - Bad Request Exception (Error 400): Los campos `nombre`, `apellidos`, `telefono`, `genero`, `casado` y `n_hijos` no pueden estar vacios.
      - Illegal number exception (Error 400): n_hijos debe de ser un número.
      - Internal Server Error (Error 500): Error interno de la base de datos.
  - `PUT /propietarios/{id}`: Permite actualizar el propietario.
    - **RUTA PRIVADA** Todas las peticiones a este endpoint se permiten si eres admin.
    - **Entrada**: Id del propietario por parametro y JSON PropietarioDTO con la nueva información del propietario.
    - **Salida**: Nuevo objeto PropietarioDTO.
    - **Excepciones**:
      - Bad Request Exception (Error 400): Los campos del objeto propietario o la id no pueden estar vacios.
      - Illegal number exception (Error 400): La id y n_hijos deben de ser un número.
      - Unauthorized (Error 403): No tienes suficientes permisos.
      - Propietario not found (Error 404): No existe ningun propietario con esa id.
      - Internal Server Error (Error 500): Error interno de la base de datos.
  - `DELETE /propietarios/{id}`: Permite al administrador eliminar un propietario por su id.
    - **RUTA PRIVADA** Las peticiones se permiten si eres administrador.
    - **Entrada**: Parametro `id`.
    - **Salida**: N/A.
    - **Excepciones**:
      - Bad Request Exception (Error 400): El campo id no puede estar vacío.
      - Illegal number exception (Error 400): La id debe de ser un número.
      - Unauthorized (Error 403): No tienes suficientes permisos.
      - Propietario not found (Error 404): No existe ningun propietario asociada a esa id.
      - Internal Server Error (Error 500): Error interno de la base de datos.
