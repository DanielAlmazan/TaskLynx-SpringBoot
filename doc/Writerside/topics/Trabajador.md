# Trabajador

## GET /api/trabajadores
> Returns all the workers in the database.  
> If there are no workers, it will return an empty list.

**Successful response example**
```json
{
  "result": [
    {
      "idTrabajador": "T003",
      "dni": "34567890C",
      "nombre": "Carlos",
      "apellidos": "Martínez",
      "especialidad": "Electricidad",
      "contraseña": "contraseña3",
      "email": "carlos.martinez@example.com"
    },
    {
      "idTrabajador": "T001",
      "dni": "12345678A",
      "nombre": "Juan",
      "apellidos": "Pérez",
      "especialidad": "Carpintería",
      "contraseña": "contraseña1",
      "email": "juan.perez@example.com"
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

## GET /api/trabajadores/:id
> Returns a worker by its ID.  
> If the worker does not exist, it will return an error message.

**Successful response example**
```json
{
  "result": {
    "idTrabajador": "T003",
    "dni": "34567890C",
    "nombre": "Carlos",
    "apellidos": "Martínez",
    "especialidad": "Electricidad",
    "contraseña": "contraseña3",
    "email": "carlos.martinez@example.com"
  },
  "error": false
}
```

**Non-existent worker response example**
```json
{
  "errorMessage": "El trabajador con ID: 'asdtg' no existe en la base de datos",
  "error": true
}
```

***

## POST /api/trabajadores
> Create a new worker in the database. Returns the worker created.  
> If there are any errors, it will return an error message with the list of errors.  
> If the worker already exists, it will return an error message.

**Successful body example**
```json
{
  "idTrabajador": "T001",
  "dni": "12345678A",
  "nombre": "Pepe",
  "apellidos": "García",
  "especialidad": "Fontanería",
  "contraseña": "123",
  "email": "wefwefewf@ergerg.com"
}
```

**Successful response example**
```json
{
  "result": {
    "idTrabajador": "T001",
    "dni": "12345678A",
    "nombre": "Pepe",
    "apellidos": "García",
    "especialidad": "Fontanería",
    "contraseña": "123",
    "email": "wefwefewf@ergerg.com"
  },
  "error": false
}
```

**Error body example**
```json
{
  "idTrabajador": "T001",
  "dni": "",
  "nombre": "Pepe",
  "apellidos": "García",
  "especialidad": "Fontanería",
  "contraseña": "123",
  "email": "holaHolita"
}
```

**Error response example**
```json
{
  "errorsList": [
    "El campo DNI no puede estar vacío",
    "El email no es válido"
  ],
  "error": true
}
```

**Existing worker response example**
```json
{
  "errorsList": "El trabajador ya existe",
  "error": true
}
```

***

## PUT /api/trabajadores/:id
> Update a worker by its ID. Returns the worker updated.  
> If the worker does not exist, it will return an error message.  
> If any of the fields are empty or invalid, it will return an error message with the list of errors.

**Successful body example**
```json
{
    "idTrabajador": "T001",
    "dni": "12345678A",
    "nombre": "Pepe",
    "apellidos": "Gomez",
    "especialidad": "Fontanería",
    "contraseña": "123",
    "email": "wefwefewf@ergerg.com"
}
```

**Successful response example**
```json
{
  "result": {
    "idTrabajador": "T001",
    "dni": "12345678A",
    "nombre": "Pepe",
    "apellidos": "Gomez",
    "especialidad": "Fontanería",
    "contraseña": "123",
    "email": "wefwefewf@ergerg.com"
  },
  "error": false
}
```

**Error body example**
```json
{
  "idTrabajador": "T001",
  "dni": "",
  "nombre": "Pepe",
  "apellidos": "García",
  "especialidad": "Fontanería",
  "contraseña": "123",
  "email": "holaHolita"
}
```

**Error response example**
```json
{
  "errorsList": [
    "El campo DNI no puede estar vacío",
    "El email no es válido"
  ],
  "error": true
}
```

**Non-existent worker body example**
```json
{
    "idTrabajador": "T999",
    "dni": "12344321T",
    "nombre": "José",
    "apellidos": "Jimenez",
    "especialidad": "Ebanistería",
    "contraseña": "123",
    "email": "jose@jimenez.com"
}
```

**Non-existent worker response example**
```json
{
  "errorsList": [
    "No se pudo editar, el trabajador con ID 'T999' no existe en la base de datos"
  ],
  "error": true
}
```

***

## DELETE /api/trabajadores/:id
> Delete a worker by its ID. Returns a response without errors.  
> If the worker does not exist, it will return an error message.

**Successful response example**
```json
{
  "error": false
}
```

**Non-existent worker response example**
```json
{
  "errorMessage": "El trabajador con ID: 'asdtg' no existe en la base de datos",
  "error": true
}
```