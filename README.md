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
Contiene los o el propietario de la propiedad.

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

  - `POST /usuarios/login`: Permite al usuario autenticarse.
      - **RUTA PÚBLICA** Todas las peticiones a este endpoint deben permitirse
      - **Entrada**: JSON con `username` y `password`.
      - **Salida**: Token de la sesión si las credenciales son válidas.
      - **Excepciones**:
        - Bad Request Exception (Error 400): Los campos username y password no pueden estar vacios
        - Invalid Password Exception (Error 401): La contraseña introducida es incorrecta
        - Username not found (Error 404): No existe ningun usuario asociado a ese username
  - `GET /usuarios/{id}`: Permite obtener información de un usuario a traves de su id.
      - **RUTA PRIVADA** Todas las peticiones a este endpoint se permiten si eres admin o si eres user solo se le permite acceder al usuario que este asociado a su id
      - **Entrada**: Parametro `id`.
      - **Salida**: Objeto UserDTO.
      - **Excepciones**:
        - Bad Request Exception (Error 400): El campo id no puede estar vacío.
        - Illegal number exception (Error 400): La id debe de ser un número.
        - Username not found (Error 404): No existe ningun usuario asociado a esa id.
