## Pregunta 1: Pruebas automatizadas de matrícula

Se realizaron cinco escenarios de pruebas automatizadas utilizando Cucumber, Gherkin, Serenity BDD, Screenplay y Rest Assured.

### Escenario 1: Cotizar curso

Se verifica la cotización del curso, los créditos, el subtotal, el descuento y el total.

```json
{
  "codigoAlumno": "i202610003",
  "cursos": ["TS5514"],
  "creditos": 4,
  "subtotal": 480,
  "descuento": 0,
  "total": 480
}
```

### Escenario 2: Registrar matrícula

Se verifica que la matrícula se registre correctamente.

```json
{
  "id": 21,
  "codigoAlumno": "i202610003",
  "codigoCurso": "TS5514",
  "periodo": "2026-2",
  "horario": "JUEVES 08:00-12:00",
  "creditos": 4,
  "monto": 480,
  "estado": "REGISTRADA",
  "nota": null,
  "condicion": null,
  "fechaRegistro": "2026-11-21T00:43:08.5145514",
  "fechaPago": null
}
```

### Escenario 3: Pagar matrícula

Se verifica que el pago se procese correctamente y que el estado cambie a PAGADA.

```json
{
  "id": 21,
  "codigoAlumno": "i202610003",
  "codigoCurso": "TS5514",
  "periodo": "2026-2",
  "horario": "JUEVES 08:00-12:00",
  "creditos": 4,
  "monto": 480,
  "estado": "PAGADA",
  "nota": null,
  "condicion": null,
  "fechaRegistro": "2026-11-21T00:43:08.514551",
  "fechaPago": "2026-11-21T00:47:22.9929753"
}
```

### Escenario 4: Verificar pago

Se verifica que el pago esté registrado con el método YAPE y el monto correspondiente.

```json
{
  "id": 16,
  "numeroOperacion": "OP-000016",
  "idMatricula": 21,
  "codigoAlumno": "i202610003",
  "codigoCurso": "TS5514",
  "periodo": "2026-2",
  "metodo": "YAPE",
  "monto": 480,
  "fecha": "2026-11-21T00:47:22.992975"
}
```

### Escenario 5: Matrícula rechazada

Se verifica que el sistema rechace la matrícula cuando el curso pertenece a otra carrera.

```json
{
  "detail": "El curso AD5101 pertenece a la carrera AE",
  "instance": "/api/matriculas",
  "status": 422,
  "title": "Unprocessable Content",
  "codigo": "CURSO_DE_OTRA_CARRERA"
}
```

### Ejecución de pruebas

Para ejecutar los escenarios automatizados:

```powershell
.\mvnw.cmd test -Dtest=CucumberTestSuite
```

**Resultado:** Los cinco escenarios se ejecutan mediante Serenity BDD y Cucumber.

## **_Evidencias_**

Las capturas de los resultados se encuentran en la carpeta evidencias/:


01-ejecucion-todos-los-tests.png