
package pe.edu.cibertec.matricula_api.screenplay.questions;

import net.serenitybdd.rest.SerenityRest;
import net.serenitybdd.screenplay.Question;

public class ElCodigoDeError {

    public static Question<String> valor() {

        return actor -> {

            if (SerenityRest.lastResponse() == null) {
                throw new AssertionError(
                        "No existe una respuesta HTTP para consultar"
                );
            }

            String codigo = SerenityRest.lastResponse()
                    .jsonPath()
                    .getString("codigo");

            if (codigo == null || codigo.isBlank()) {
                throw new AssertionError(
                        "La respuesta no contiene el campo codigo"
                );
            }

            return codigo;
        };
    }
}
