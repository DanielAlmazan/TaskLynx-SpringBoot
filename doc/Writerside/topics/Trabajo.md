# Trabajo

## GET /api/trabajos
> Returns all the tasks in the database.  
> If there are no tasks, it will return an empty list.

**Successful response example**

```json
{
  "result": [
    {
      "codTrabajo": "J001",
      "categoria": "Mecánica",
      "descripcion": "Reparación de tuberías",
      "fecIni": "2024-01-01",
      "fecFin": null,
      "tiempo": null,
      "idTrabajador": null,
      "prioridad": 2
    },
    {
      "codTrabajo": "J002",
      "categoria": "Fontanería",
      "descripcion": "Reparación de tuberías",
      "fecIni": "2022-01-02",
      "fecFin": null,
      "tiempo": 5.0,
      "idTrabajador": {
        "idTrabajador": "T002",
        "dni": "23456789B",
        "nombre": "Ana",
        "apellidos": "Gómez",
        "especialidad": "Fontanería",
        "contraseña": "contraseña2",
        "email": "ana.gomez@example.com"
      },
      "prioridad": 2
    },
    {
      "codTrabajo": "J003",
      "categoria": "Electricidad",
      "descripcion": "Instalación de luces",
      "fecIni": "2022-01-03",
      "fecFin": null,
      "tiempo": 8.0,
      "idTrabajador": {
        "idTrabajador": "T003",
        "dni": "34567890C",
        "nombre": "Carlos",
        "apellidos": "Martínez",
        "especialidad": "Electricidad",
        "contraseña": "contraseña3",
        "email": "carlos.martinez@example.com"
      },
      "prioridad": 3
    }
  ],
  "error": false
}
```

### Empty list response example
```json
{
  "result": [],
  "error": false
}
```

***

## GET /api/trabajos/:id
> Returns a task by its ID.  
> If the task does not exist, it will return an error message.

**Successful response example:**
>/api/trabajos/J001
```json
{
  "result": {
    "codTrabajo": "J001",
    "categoria": "Mecánica",
    "descripcion": "Reparación de tuberías",
    "fecIni": "2024-01-01",
    "fecFin": null,
    "tiempo": null,
    "idTrabajador": null,
    "prioridad": 2
  },
  "error": false
}
```

**Non-existent worker response example:**
>/api/trabajos/J025
```json
{
  "errorMessage": "El trabajo con ID 'J025' no existe en la base de datos",
  "error": true
}
```

***

## POST /api/trabajos
> Create a new task in the database and returns the task created.  
> If there are any errors, it will return an error message with the list of errors.  
> If the worker already exists, it will return an error message.
> 
> All fields are required except:
> - fecFin: task completion date
> - tiempo: duration of the task
> - idTrabajador: worker assigned

**Successful body example**
```json
{
  "codTrabajo": "J007",
  "categoria": "Pintura",
  "descripcion": "Pintar las paredes",
  "fecIni": "2024-01-09",
  "prioridad": 4
}
```

**Response** 
```json
{
  "result": {
    "codTrabajo": "J007",
    "categoria": "Pintura",
    "descripcion": "Pintar las paredes",
    "fecIni": "2024-01-09",
    "fecFin": null,
    "tiempo": null,
    "idTrabajador": null,
    "prioridad": 4
  },
  "error": false
}
```

**Other successful body example**
```json
{
  "codTrabajo": "J008",
  "categoria": "Pintura",
  "descripcion": "Pintar las paredes",
  "fecIni": "2024-01-09",
  "fecFin": "2024-01-11",
  "tiempo": 8.5,
  "idTrabajador": {
    "idTrabajador": "T001",
    "dni": "12345678A",
    "nombre": "Pepe",
    "apellidos": "Gomez",
    "especialidad": "Fontanería",
    "contraseña": "123",
    "email": "wefwefesadf.com"
  },
  "prioridad": 4
}
```

**Response**
```json
{
  "result": {
    "codTrabajo": "J008",
    "categoria": "Pintura",
    "descripcion": "Pintar las paredes",
    "fecIni": "2024-01-09",
    "fecFin": "2024-01-11",
    "tiempo": 8.5,
    "idTrabajador": {
      "idTrabajador": "T001",
      "dni": "12345678A",
      "nombre": "Pepe",
      "apellidos": "García",
      "especialidad": "Fontaneria",
      "contraseña": "123",
      "email": "wefwefewf@ergerg.com"
    },
    "prioridad": 4
  },
  "error": false
}
```

**Error body example**
```json
{
  "codTrabajo": "J009",
  "fecIni": "2024-01-09",
  "prioridad": 4
}
```

**Response**
```json
{
  "errorsList": [
    "La categoría no puede estar vacía",
    "La descripción no puede estar vacía"
  ],
  "error": true
}
```

**Other error body example**
```json
{
  "codTrabajo": "J008",
  "categoria": "Pintura",
  "descripcion": "Pintar las paredes",
  "fecIni": "2024-01-09",
  "prioridad": 4
}
```

**Response**
```json
{
  "errorsList": [
    "El trabajo con id 'J008' ya existe en la base de datos"
  ],
  "error": true
}
```

***

## PUT /api/trabajos/:id
> Update a task by its ID and returns the task updated.  
> If the worker does not exist, it will return an error message.  
> If any of the fields are empty or invalid, it will return an error message with the list of errors.

**Successful body example, we add an assigned worker**
> /api/trabajos/J007
```json
{
  "codTrabajo": "J007",
  "categoria": "Pintura",
  "descripcion": "Pintar las paredes",
  "fecIni": "2024-01-09",
  "prioridad": 4,
  "idTrabajador": {
    "idTrabajador": "T001",
    "dni": "12345678A",
    "nombre": "Pepe",
    "apellidos": "García",
    "especialidad": "Fontaneria",
    "contraseña": "123",
    "email": "wefwefewf@ergerg.com"
  }
}
```

**Response**
```json
{
  "result": {
    "codTrabajo": "J007",
    "categoria": "Pintura",
    "descripcion": "Pintar las paredes",
    "fecIni": "2024-01-09",
    "fecFin": null,
    "tiempo": null,
    "idTrabajador": {
      "idTrabajador": "T001",
      "dni": "12345678A",
      "nombre": "Pepe",
      "apellidos": "García",
      "especialidad": "Fontaneria",
      "contraseña": "123",
      "email": "wefwefewf@ergerg.com"
    },
    "prioridad": 4
  },
  "error": false
}
```

**Error body example**
> /api/trabajos/J020
```json
{
  "codTrabajo": "J020",
  "categoria": "Pintura",
  "descripcion": "Pintar las paredes",
  "fecIni": "2024-01-09",
  "prioridad": 4
}
```

**Response**
```json
{
  "errorsList": [
    "No se pudo editar, el trabajador con ID 'J020' no existe en la base de datos"
  ],
  "error": true
}
```

***

## DELETE /api/trabajos/:id
> Delete a task by its ID. Returns a response without errors.  
> If the task does not exist, it will return an error message.

**Successfull response example**
> /api/trabajos/J008
```json
{
  "error": false
}
```

**Non-existent worker response example**
> /api/trabajos/J020
```json
{
  "errorMessage": "El trabajo con id 'J020' no existe en la base de datos",
  "error": true
}
```