# TaskLynx-SpringBoot
SpringBoot project for 'Acceso a Datos' (Data Access) subject

erDiagram
    TRABAJADOR ||--|{ TRABAJO : tiene
    TRABAJADOR {
        string id_trabajador
        string dni
        -- otros campos --
    }
    TRABAJO {
        string cod_trabajo
        string categoria
        -- otros campos --
    }
