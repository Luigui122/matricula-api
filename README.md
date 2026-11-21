# Pregunta 1: Pruebas automatizadas de matrícula

Se realizaron pruebas automatizadas utilizando **Cucumber, Gherkin, Serenity BDD, Screenplay y Rest Assured**, con el objetivo de verificar el correcto funcionamiento de los procesos de cotización, registro de matrícula, pago y validación de matrículas duplicadas.

## Escenario 1: Cotizar curso

**Objetivo:** verificar que el sistema calcule correctamente los créditos, subtotal, descuento y total de la cotización.

**Resultado obtenido:**

```json
{
  "codigoAlumno": "i202610002",
  "cursos": [
    "DM5512"
  ],
  "creditos": 4,
  "subtotal": 480,
  "descuento": 10,
  "total": 432
}
```

**Validación:** se verifica que el subtotal sea 480, se aplique un descuento del 10 % y el total calculado sea 432.

## Escenario 2: Registrar matrícula

**Objetivo:** verificar que la matrícula se registre correctamente y que el monto corresponda al resultado de la cotización.

**Resultado obtenido:**

```json
{
  "id": 22,
  "codigoAlumno": "i202610002",
  "codigoCurso": "DM5512",
  "periodo": "2026-2",
  "horario": "MARTES 08:00-12:00",
  "creditos": 4,
  "monto": 432,
  "estado": "REGISTRADA",
  "nota": null,
  "condicion": null,
  "fechaRegistro": "2026-11-21T00:44:22.8144487",
  "fechaPago": null
}
```

**Validación:** se comprueba que la matrícula se registre con un monto de 432 y estado `REGISTRADA`.

## Escenario 3: Pagar matrícula

**Objetivo:** verificar que el pago se procese correctamente y que el estado de la matrícula cambie a `PAGADA`.

**Resultado obtenido:**

```json
{
  "id": 22,
  "codigoAlumno": "i202610002",
  "codigoCurso": "DM5512",
  "periodo": "2026-2",
  "horario": "MARTES 08:00-12:00",
  "creditos": 4,
  "monto": 432,
  "estado": "PAGADA",
  "nota": null,
  "condicion": null,
  "fechaRegistro": "2026-11-21T00:44:22.814449",
  "fechaPago": "2026-11-21T00:48:13.2353246"
}
```

**Validación:** se verifica que el pago se procese correctamente y que la matrícula cambie al estado `PAGADA`.

## Escenario 4: Verificar pago

**Objetivo:** comprobar que el pago quede registrado correctamente, verificando el método de pago y el monto correspondiente.

**Resultado obtenido:**

```json
{
  "id": 17,
  "numeroOperacion": "OP-000017",
  "idMatricula": 22,
  "codigoAlumno": "i202610002",
  "codigoCurso": "DM5512",
  "periodo": "2026-2",
  "metodo": "TRANSFERENCIA",
  "monto": 432,
  "fecha": "2026-11-21T00:48:13.235325"
}
```

**Validación:** se comprueba que el pago esté registrado mediante `TRANSFERENCIA`, por un monto de 432, asociado a la matrícula con ID 22.

## Escenario 5: Matrícula rechazada

**Objetivo:** verificar que el sistema rechace una matrícula duplicada cuando el alumno ya se encuentra matriculado en el curso `PS5511`.

**Resultado obtenido:**

```json
{
  "detail": "El alumno ya está matriculado en PS5511",
  "instance": "/api/matriculas",
  "status": 409,
  "title": "Conflict",
  "codigo": "MATRICULA_DUPLICADA"
}
```

**Validación:** se comprueba que el sistema rechace la operación con HTTP `409 Conflict` y devuelva el código de error `MATRICULA_DUPLICADA`.

## Ejecución de las pruebas automatizadas

Para ejecutar la suite de pruebas correspondiente a la Pregunta 1, se utiliza el siguiente comando en la terminal de PowerShell:

```powershell
.\mvnw.cmd test -Dtest=CucumberTestSuite
```

**Resultado esperado:** ejecución de los escenarios automatizados mediante Serenity BDD y Cucumber, verificando los procesos de cotización, registro de matrícula, pago, consulta del pago y rechazo de matrícula duplicada.

## Evidencias

Las capturas de pantalla de los resultados de ejecución se encuentran en la carpeta `evidencias/`.

**Archivo de evidencia:**

```text
evidencias/
└── 01-ejecucion-todos-los-tests.png
```

## Conclusión

Las pruebas automatizadas permiten verificar el correcto funcionamiento de los procesos principales de matrícula, desde la cotización del curso hasta el registro y confirmación del pago.

También permiten comprobar que el sistema aplique sus reglas de negocio correctamente, evitando el registro de matrículas duplicadas. La integración de Cucumber, Gherkin, Serenity BDD, Screenplay y Rest Assured facilita la organización, ejecución y validación de los escenarios de prueba.
---

## 2. Escenario 1: Rotación del refresh token

**Objetivo:** comprobar que un refresh token solo pueda utilizarse una vez y que su reutilización cierre las sesiones del usuario.

**Usuario:** `i202610002` – Luis Ramos.

### Paso 1. Iniciar sesión

**Endpoint:**

`POST /api/auth/login`

**Request:**

```json
{
  "username": "i202610002",
  "password": "Cibertec2026"
}
```

**Resultado:** HTTP `200 OK`.

La API devuelve `accessToken` y `refreshToken`.

### Paso 2. Renovar los tokens

**Endpoint:**

`POST /api/auth/refresh`

Se envía el `refreshToken` obtenido durante el inicio de sesión.

**Resultado:** HTTP `200 OK`.

**Response:**

```json
{
  "accessToken": "<nuevo_accessToken>",
  "refreshToken": "<nuevo_refreshToken>",
  "tipo": "Bearer",
  "expiraEn": 900,
  "username": "i202610002",
  "rol": "ALUMNO"
}
```

### Paso 3. Reutilizar el refresh token anterior

Se vuelve a enviar el refresh token original.

**Resultado:** HTTP `401 Unauthorized`.

**Response:**

```json
{
  "detail": "El refresh token ya fue usado; por seguridad se cerraron todas las sesiones del usuario",
  "instance": "/api/auth/refresh",
  "status": 401,
  "title": "Unauthorized",
  "codigo": "REFRESH_REUTILIZADO"
}
```

### Paso 4. Comprobar la invalidación del access token

**Endpoint:**

`GET /api/auth/perfil`

Se utiliza el access token generado durante la renovación anterior.

**Resultado:** HTTP `401 Unauthorized`.

**Response:**

```json
{
  "detail": "El token es inválido, venció o su sesión fue cerrada",
  "instance": "/api/auth/perfil",
  "status": 401,
  "title": "Unauthorized",
  "codigo": "TOKEN_INVALIDO"
}
```

**Conclusión:** la API detecta la reutilización del refresh token, cierra las sesiones del usuario e invalida también el access token generado durante la renovación.

---

## 3. Escenario 2: Cierre de sesión

**Objetivo:** comprobar que, después de cerrar sesión, el access token utilizado deje de ser válido.

**Usuario:** `i202610002` – Luis Ramos.

### Paso 1. Iniciar sesión

**Endpoint:**

`POST /api/auth/login`

**Request:**

```json
{
  "username": "i202610002",
  "password": "Cibertec2026"
}
```

**Resultado:** HTTP `200 OK`.

Se obtiene un nuevo access token.

### Paso 2. Cerrar sesión

**Endpoint:**

`POST /api/auth/logout`

Se envía el access token mediante el encabezado Authorization.

```http
Authorization: Bearer <accessToken>
```

**Resultado:** HTTP `204 No Content`.

### Paso 3. Consultar el perfil después del logout

**Endpoint:**

`GET /api/auth/perfil`

Se utiliza el mismo access token con el que se cerró sesión.

**Resultado:** HTTP `401 Unauthorized`.

**Response:**

```json
{
  "detail": "El token es inválido, venció o su sesión fue cerrada",
  "instance": "/api/auth/perfil",
  "status": 401,
  "title": "Unauthorized",
  "codigo": "TOKEN_INVALIDO"
}
```

**Conclusión:** después del cierre de sesión, la API invalida el token e impide acceder al perfil del usuario.

---

## 4. Escenario 3: Bloqueo de cuenta por intentos fallidos

**Objetivo:** comprobar que la cuenta se bloquee después de cinco intentos fallidos y que no permita iniciar sesión incluso utilizando la contraseña correcta durante el periodo de bloqueo.

**Usuario exclusivo para esta prueba:** `i202610004`.

### Paso 1. Realizar cuatro intentos fallidos

**Endpoint:**

`POST /api/auth/login`

**Request:**

```json
{
  "username": "i202610004",
  "password": "ClaveIncorrecta123"
}
```

**Resultado de los primeros cuatro intentos:** HTTP `401 Unauthorized`.

**Response:**

```json
{
  "detail": "Usuario o contraseña incorrectos",
  "instance": "/api/auth/login",
  "status": 401,
  "title": "Unauthorized",
  "codigo": "CREDENCIALES_INVALIDAS"
}
```

### Paso 2. Quinto intento fallido

Se vuelve a enviar la misma petición.

**Request:**

```json
{
  "username": "i202610004",
  "password": "ClaveIncorrecta123"
}
```

**Resultado:** HTTP `423 Locked`.

**Response:**

```json
{
  "detail": "Cuenta bloqueada por intentos fallidos hasta las HH:MM",
  "instance": "/api/auth/login",
  "status": 423,
  "title": "Locked",
  "codigo": "CUENTA_BLOQUEADA"
}
```

### Paso 3. Intentar ingresar con la contraseña correcta

Se intenta iniciar sesión nuevamente utilizando la contraseña correcta.

**Request:**

```json
{
  "username": "i202610004",
  "password": "Cibertec2026"
}
```

**Resultado:** HTTP `423 Locked`.

**Response:**

```json
{
  "detail": "Cuenta bloqueada por intentos fallidos hasta las HH:MM",
  "instance": "/api/auth/login",
  "status": 423,
  "title": "Locked",
  "codigo": "CUENTA_BLOQUEADA"
}
```

### Resumen de resultados

| Intento | HTTP | Código |
|---|---:|---|
| 1 | 401 | `CREDENCIALES_INVALIDAS` |
| 2 | 401 | `CREDENCIALES_INVALIDAS` |
| 3 | 401 | `CREDENCIALES_INVALIDAS` |
| 4 | 401 | `CREDENCIALES_INVALIDAS` |
| 5 | 423 | `CUENTA_BLOQUEADA` |
| Contraseña correcta durante el bloqueo