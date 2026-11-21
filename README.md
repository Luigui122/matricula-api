# matricula-api

Proyecto de automatización de pruebas para el proceso de matrícula de CIBERTEC, desarrollado para el curso de Pruebas de Software utilizando BDD con Gherkin, Cucumber, Serenity BDD, Screenplay y Rest Assured.

Se implementaron tres escenarios automatizados para comprobar el funcionamiento de la cotización, el registro y pago de matrículas, y la validación de reglas de negocio.

## Pruebas automatizadas

### 1. Cotización de curso

Se realizó una prueba para verificar que un alumno pueda consultar el costo de un curso antes de matricularse.

**Datos utilizados:**
- Alumno: `i202610003`
- Curso: `TS5514`

**Resultado obtenido:**

| Campo | Resultado |
|---|---:|
| Créditos | 4 |
| Subtotal | S/ 480 |
| Descuento | S/ 0 |
| Total | S/ 480 |

Se comprobó que la API calcule y devuelva correctamente el importe de la cotización.

### 2. Matrícula válida y pago mediante YAPE

Se automatizó el proceso de matrícula y pago de un curso. Primero se registra la matrícula y luego se utiliza el ID generado por la API para realizar el pago mediante YAPE. Finalmente, se consulta el pago para verificar que se haya registrado correctamente.

**Datos utilizados:**
- Alumno: `i202610003`
- Curso: `TS5514`
- Método de pago: `YAPE`

**Resultados obtenidos:**

| Validación | Resultado |
|---|---|
| Registro de matrícula | HTTP 201 |
| Estado inicial | REGISTRADA |
| Pago de matrícula | HTTP 200 |
| Estado final | PAGADA |
| Monto pagado | S/ 480 |
| Número de operación | OP-000016 |

Se verificó que la matrícula cambie a estado `PAGADA`, que se registre la fecha del pago y que el pago esté asociado a la matrícula creada.

### 3. Rechazo de matrícula por carrera diferente

Se realizó una prueba negativa para comprobar que un alumno no pueda matricularse en un curso que pertenece a otra carrera.

**Datos utilizados:**
- Alumno: `i202610003`
- Curso: `AD5101`

**Resultado obtenido:**

| Validación | Resultado |
|---|---|
| Código HTTP | 422 |
| Código de error | CURSO_DE_OTRA_CARRERA |
| Resultado | Matrícula rechazada |

Se comprobó que la API rechace correctamente la solicitud y devuelva el código de error esperado.

## Ejecución de las pruebas

Para ejecutar los tres escenarios automatizados en Windows:

```powershell
.\mvnw.cmd test -Dtest=CucumberTestSuite
```

## Resultado general

Las tres pruebas automatizadas finalizaron correctamente.

| Indicador | Resultado |
|---|---:|
| Pruebas ejecutadas | 3 |
| Fallidas | 0 |
| Errores | 0 |
| Omitidas | 0 |
| Resultado | BUILD SUCCESS |

## Evidencias

Las capturas de los resultados se encuentran en la carpeta `evidencias/`:


1`01-ejecucion-todos-los-tests.png`

---

**CIBERTEC - Curso de Pruebas de Software**  
**Pregunta 1: Automatización de pruebas con BDD**