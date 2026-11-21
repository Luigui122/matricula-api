
package pe.edu.cibertec.matricula_api.screenplay.questions;

import net.serenitybdd.rest.SerenityRest;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Pattern;

public class LaRespuesta {

    public static Question<Integer> codigoDeEstado() {
        return Question.about("el codigo de estado")
                .answeredBy(actor -> SerenityRest.lastResponse().statusCode());
    }

    public static Question<Double> decimal(String campo) {
        return actor -> SerenityRest.lastResponse()
                .jsonPath()
                .getDouble(campo);
    }

    public static Question<String> campo(String ruta) {
        return Question.about("el campo " + ruta)
                .answeredBy(actor ->
                        SerenityRest.lastResponse().jsonPath().getString(ruta));
    }

    public static Question<Integer> entero(String ruta) {
        return Question.about("el campo entero " + ruta)
                .answeredBy(actor ->
                        SerenityRest.lastResponse().jsonPath().getInt(ruta));
    }

    public static Question<Boolean> campoTieneValor(String ruta) {
        return Question.about("el campo " + ruta + " tiene valor")
                .answeredBy(actor -> {
                    String valor = SerenityRest.lastResponse()
                            .jsonPath().getString(ruta);

                    return valor != null && !valor.isBlank();
                });
    }

    public static Question<Integer> cantidadDeElementos() {
        return Question.about("la cantidad de elementos")
                .answeredBy(actor -> {
                    List<?> elementos = SerenityRest.lastResponse()
                            .jsonPath().getList("$");

                    return elementos == null ? 0 : elementos.size();
                });
    }

    // Comprueba que existe un pago asociado a la matrícula creada.
    public static Question<Boolean> pagoCorrespondeMatricula() {
        return Question.about("el pago corresponde a la matrícula")
                .answeredBy(actor -> pagoDeLaMatricula(actor) != null);
    }

    // Comprueba el método de pago de esa misma matrícula.
    public static Question<Boolean> pagoTieneMetodo(String metodo) {
        return Question.about("el método de pago es " + metodo)
                .answeredBy(actor -> {
                    Map<String, Object> pago = pagoDeLaMatricula(actor);

                    return pago != null &&
                            Objects.equals(
                                    String.valueOf(pago.get("metodo")),
                                    metodo
                            );
                });
    }

    // Comprueba el formato del número de operación.
    // Ejemplo: OP-000000 se convierte en OP-\d{6}.
    public static Question<Boolean> numeroOperacionTieneFormato(String formato) {
        return Question.about("el número de operación tiene formato " + formato)
                .answeredBy(actor -> {
                    Map<String, Object> pago = pagoDeLaMatricula(actor);

                    if (pago == null || pago.get("numeroOperacion") == null) {
                        return false;
                    }

                    StringBuilder regex = new StringBuilder();

                    for (char caracter : formato.toCharArray()) {
                        if (caracter == '0') {
                            regex.append("\\d");
                        } else {
                            regex.append(
                                    Pattern.quote(String.valueOf(caracter))
                            );
                        }
                    }

                    String numero = String.valueOf(
                            pago.get("numeroOperacion")
                    );

                    return numero.matches(regex.toString());
                });
    }

    private static Map<String, Object> pagoDeLaMatricula(Actor actor) {

        Number idMatricula = actor.recall("idMatricula");

        if (idMatricula == null || SerenityRest.lastResponse() == null) {
            return null;
        }

        List<Map<String, Object>> pagos =
                SerenityRest.lastResponse().jsonPath().getList("$");

        if (pagos == null) {
            return null;
        }

        return pagos.stream()
                .filter(pago ->
                        pago.get("idMatricula") instanceof Number
                )
                .filter(pago ->
                        ((Number) pago.get("idMatricula")).longValue()
                                == idMatricula.longValue()
                )
                .findFirst()
                .orElse(null);
    }
}
