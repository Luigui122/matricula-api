## Pregunta 1: Pruebas automatizadas de matrícula

Se realizaron tres escenarios de pruebas automatizadas utilizando Cucumber, Gherkin, Serenity BDD, Screenplay y Rest Assured.

### Escenario 1: Cotizar curso

Se verifica la cotización del curso, los créditos, el subtotal, el descuento y el total.

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

### Escenario 2: Registrar matrícula

Se verifica que la matrícula se registre correctamente con el monto correspondiente a la cotización.

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

### Escenario 3: Pagar matrícula

Se verifica que el pago se procese correctamente y que el estado cambie a PAGADA.

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

### Escenario 4: Verificar pago

Se verifica que el pago esté registrado con el método TRANSFERENCIA y el monto correspondiente.

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

### Escenario 5: Matrícula rechazada

Se verifica que el sistema rechace la matrícula duplicada cuando el alumno ya se encuentra matriculado en el curso PS5511.

```json
{
  "detail": "El alumno ya está matriculado en PS5511",
  "instance": "/api/matriculas",
  "status": 409,
  "title": "Conflict",
  "codigo": "MATRICULA_DUPLICADA"
}
```

### Ejecución de pruebas

Para ejecutar los escenarios automatizados:

```powershell
.\mvnw.cmd test -Dtest=CucumberTestSuite
```

**Resultado:** Los tres escenarios se ejecutan mediante Serenity BDD y Cucumber, incluyendo la cotización, matrícula y pago, y el rechazo por matrícula duplicada.

## Evidencias

Las capturas de los resultados se encuentran en la carpeta `evidencias/`:

- `01-ejecucion-todos-los-tests.png`