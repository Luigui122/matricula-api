
# language: es

Característica: Seguridad de la sesión
  Como usuario de la API de matrícula
  Quiero que se controle la autenticación y autorización
  Para proteger mis datos y mi sesión

  @seguridad @refresh
  Escenario: Rotación y reutilización del refresh token
    Dado que el alumno "i202610002" inicia sesión correctamente
    Cuando renueva el refresh token original
    Entonces el código de respuesta es 200
    Cuando reutiliza el refresh token original
    Entonces el código de respuesta es 401
    Y el código de error es "REFRESH_REUTILIZADO"
    Cuando consulta su perfil con el access token renovado
    Entonces el código de respuesta es 401
    Y el código de error es "TOKEN_INVALIDO"

  @seguridad @logout
  Escenario: Cerrar sesión e invalidar el access token
    Dado que el alumno "i202610002" inicia sesión correctamente
    Cuando cierra su sesión
    Entonces el código de respuesta es 204
    Cuando consulta su perfil con el mismo access token
    Entonces el código de respuesta es 401
    Y el código de error es "TOKEN_INVALIDO"

  @seguridad @bloqueo
  Escenario: Bloquear la cuenta después de cinco intentos fallidos
    Cuando el usuario "i202610004" realiza cuatro intentos de inicio de sesión con contraseña incorrecta
    Entonces el código de respuesta es 401
    Y el código de error es "CREDENCIALES_INVALIDAS"
    Cuando realiza el quinto intento de inicio de sesión con contraseña incorrecta
    Entonces el código de respuesta es 423
    Y el código de error es "CUENTA_BLOQUEADA"
    Cuando intenta iniciar sesión con la contraseña correcta
    Entonces el código de respuesta es 423
    Y el código de error es "CUENTA_BLOQUEADA"

  @seguridad @autorizacion
  Escenario: Alumno intenta acceder a un recurso exclusivo de administrador
    Dado que el alumno "i202610002" inicia sesión correctamente
    Cuando consulta la lista de alumnos
    Entonces el código de respuesta es 403
    Y el código de error es "ACCESO_DENEGADO"
