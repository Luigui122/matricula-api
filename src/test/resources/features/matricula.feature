
# language: es

Característica: Matrícula de cursos
  Como alumno de CIBERTEC
  Quiero matricularme en mis cursos
  Para llevarlos durante el ciclo académico

  Antecedentes:
    Dado que el alumno "i202610003" inicia sesión correctamente

  Escenario: Cotizar el curso TS5514
    Cuando consulta la cotización del curso "TS5514" para el alumno "i202610003"
    Entonces el código de respuesta es 200
    Y la cotización contiene 4 créditos
    Y el subtotal es 480
    Y el descuento es 0
    Y el total es 480

  Escenario: Matrícula válida y pago mediante YAPE
    Cuando se matricula con el codigo "i202610003" en el curso "TS5514"
    Entonces el código de respuesta es 201
    Y el monto de la matrícula es 480
    Y el estado de la matrícula es "REGISTRADA"
    Y la respuesta cumple el esquema "matricula.json"
    Cuando paga la matrícula mediante "YAPE"
    Entonces el código de respuesta es 200
    Y el estado de la matrícula es "PAGADA"
    Y la fecha de pago está registrada
    Y consulta los pagos de la API
    Y verifica que el pago corresponde a la matrícula
    Y el método de pago es "YAPE"
    Y el número de operación tiene el formato "OP-000000"

  Escenario: Matrícula rechazada por curso de otra carrera
    Cuando se matricula con el codigo "i202610003" en el curso "AD5101"
    Entonces el código de respuesta es 422
    Y el código de error es "CURSO_DE_OTRA_CARRERA"
    Y la respuesta cumple el esquema "error.json"
