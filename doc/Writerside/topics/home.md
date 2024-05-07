![TaskLynx-API-oval-logo.svg](TaskLynx-API-oval-logo.svg){width=300}

# TaskLynx – API – Home

## Introducción

TaskLynx es un sistema compuesto por TaskLynx Api, TaskLynx Business y TaskLynx Mobile.  
TaskLynx Api es una API REST que permite la comunicación entre TaskLynx Business y TaskLynx Mobile.

## Tecnologías

- Java 21
- Spring Boot 2.5.4
- Apache Maven 3.9.6
- PostgreSQL


## Diagrama Entidad-Relación

> **Importante**
> 
> El campo `contrasenya`, en realidad es `contraseña`. Se ha cambiado únicamente 
> en el diagrama por motivos de renderizado de caracteres no UTF-8.
> 
{style=warning}

```mermaid
erDiagram
    TRABAJADOR ||--|{ TRABAJO: tiene
    TRABAJADOR {
        varchar(5) id_trabajador
        varchar(9) dni
        varchar(100) nombre
        varchar(100) apellidos
        varchar(50) especialidad
        varchar(50) contrasenya
        varchar(150) email
    }
    TRABAJO {
        varchar(5) cod_trabajo
        varchar(50) categoria
        varchar(500) descripcion
        date fec_ini
        date fec_fin
        numeric tiempo
        numeric prioridad
        varchar(5) id_trabajador
    }
```


