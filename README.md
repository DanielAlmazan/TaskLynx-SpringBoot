<!-- Links references -->

[MiguelColl]: https://github.com/MiguelColl

[LtVish]: https://github.com/LtVish

[DanielAlmazan]: https://github.com/DanielAlmazan

[HotelNest]: https://github.com/DanielAlmazan/hotel-nest
[TaskLynxFX]: https://github.com/DanielAlmazan/TaskLynx-JavaFX

<p align="center"><a href="https://iessanvicente.com/" target="_blank"><img src="https://img.shields.io/badge/IES_San_Vicente-DAM--DAW-gree" alt="DAM-DAW (Multiplatform Higher Degrees" /></a></p>

# TaskLynx-SpringBoot

> SpringBoot project for 'Acceso a Datos' (Data Access) subject


> [!IMPORTANT]  
> Field 'contrasenya' in TRABAJADOR is 'contraseña' due to exercise specifications.

> [!IMPORTANT]  
> This project is part of [Hotel Nest][HotelNest], [TaskLynx FX][TaskLynxFX] projects and other projects still in development.

> [!IMPORTANT]  
> The file application.properties is not included in the repository because authors have different configurations. However, the file is required to run the project.

### application.properties
```properties
spring.application.name=TaskLynx-SpringBoot
spring.datasource.url=jdbc:postgresql://localhost:5432/tasklynxDB?useSSL=false
spring.datasource.username=
spring.datasource.password=
spring.datasource.driver-class-name=org.postgresql.Driver
spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect
spring.jpa.hibernate.ddl-auto=none
logging.level.org.hibernate.SQL=debug
server.port=8443
server.ssl.key-store=classpath:TLKey.p12
server.ssl.key-store-password=
server.ssl.key-alias=TLKey
server.ssl.key-password=
server.ssl.key-store-type=pkcs12
```

## Entity Relationship Diagram for tasklynxDB
```mermaid
erDiagram
    TRABAJADOR ||--|{ TRABAJO: tiene
    TRABAJADOR {
        varchar id_trabajador
        varchar dni
        varchar nombre
        varchar apellidos
        varchar especialidad
        varchar contrasenya
        varchar email
    }
    TRABAJO {
        varchar cod_trabajo
        varchar categoria
        varchar descripcion
        date fecIni
        date fecFin
        numeric tiempo
        numeric prioridad
        varchar id_trabajador
    }
```
# Project requirements

## API Development

* [ ] Obtener trabajos pendientes (login para el trabajador en la app móvil)
    * Pasándole por parámetros el ID de usuario y la contraseña, si
      coinciden en la BD, devolverá una lista de todos los trabajos
      pendientes de ese trabajador (es decir, con el fec_fin a null)

* [ ] Obtener trabajos finalizados
    * Función similar al de pendientes, pero mostrando los trabajos
      finalizados por ese trabajador.

* [ ] Finalizar trabajo
    * Pasándole la fecha actual, actualizará ese trabajo en la tabla,
      poniéndole esa fecha en la columna fec_fin, de tal manera que
      se marque ese trabajo como finalizado

* [ ] Asignar trabajo
    * Pasándole el ID de un trabajador, se le asigna ese trabajo al
      trabajador, actualizando la columna clave ajena que hay en la
      tabla trabajo. Este método deberá comprobar que la categoría
      del trabajo y la especialidad del trabajador coinciden, y de no
      ser así devolverá un error

* [ ] Crear trabajo con trabajador asignado
    * Crear un trabajo con un ID de trabajador ya asignado, que se le
      pasará por parámetros

* [ ] Listar tareas sin asignar
    * Este método mostrará el detalle de todas las tareas sin asignar,
      mostrando toda la información de cada uno de ellos.

* [ ] Listar tareas sin realizar (de todos los trabajadores)
    * Mostrará el detalle de todas las tareas sin finalizar de todos los
      trabajadores.
* [ ] Listar tareas realizadas (de todos los trabajadores)
    * Devolverá el detalle de todas las tareas realizadas por todos los
      trabajadores.
* [ ] Listar tareas por trabajador entre fechas
    * Mostrará todas las tareas finalizadas por un trabajador
      concreto, entre dos fechas específicas.
* [ ] Listar tareas de un trabajador ordenadas por prioridad
    * Mostrará todas las tareas pendientes de un trabajador concreto,
      ordenadas por prioridad (De la 1 -urgente- a la 4 -menos
      urgente-)
* [ ] Listar tareas de un trabajador de una prioridad concreta
    * Mostrará las tareas pendientes de un trabajador, solo de la
      prioridad que se le pase en la petición (Por ejemplo, solo las de
      prioridad 1 para las más urgentes)


### Authors

#### TRABAJADOR CRUD: [Aitor Moreno Iborra][LtVish] <a href="https://github.com/LtVish"><img src="https://avatars.githubusercontent.com/u/119342012?v=4"  style="height: 40px; transform: translateY(7px); border-radius: 7px"></a>

#### TRABAJO CRUD: [Miguel Collado][MiguelColl] <a href="https://github.com/MiguelColl"><img src="https://avatars.githubusercontent.com/u/114687157?v=4" style="height: 30px; transform: translateY(7px); border-radius: 5px"></a>

#### API Development: [Daniel Enrique Almazán Sellés][DanielAlmazan] <a href="https://github.com/DanielAlmazan"><img src="https://avatars.githubusercontent.com/u/114589538?v=4" style="height: 40px; transform: translateY(7px); border-radius: 5px"></a>

