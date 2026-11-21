
package pe.edu.cibertec.matricula_api.screenplay.task;

import io.restassured.http.ContentType;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.rest.interactions.Patch;

import java.util.Map;

public class PagarMatricula implements Task {

    private final String metodo;

    public PagarMatricula(String metodo) {
        this.metodo = metodo;
    }

    public static PagarMatricula conMetodo(String metodo) {
        return new PagarMatricula(metodo);
    }

    @Override
    @Step("{0} paga la matrícula mediante #metodo")
    public <T extends Actor> void performAs(T actor) {

        // Recuperar el ID obtenido al crear la matrícula
        Integer idMatricula = actor.recall("idMatricula");

        if (idMatricula == null) {
            throw new AssertionError(
                    "No se encontró el ID de matrícula. Primero debes matricular al alumno."
            );
        }

        // Recuperar el token del login
        String token = actor.recall("accessToken");

        actor.attemptsTo(
                Patch.to("/api/matriculas/{id}/pagar")
                        .with(req -> req
                                .header("Authorization", "Bearer " + token)
                                .pathParam("id", idMatricula)
                                .contentType(ContentType.JSON)
                                .body(Map.of("metodo", metodo))
                        )
        );
    }
}
