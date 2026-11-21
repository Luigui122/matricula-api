
package pe.edu.cibertec.matricula_api.screenplay.task;

import net.serenitybdd.rest.SerenityRest;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.rest.interactions.Post;

import static net.serenitybdd.screenplay.Tasks.instrumented;

public class CerrarSesion implements Task {

    public static CerrarSesion actual() {
        return instrumented(CerrarSesion.class);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {

        String accessToken = actor.recall("accessToken");

        if (accessToken == null || accessToken.isBlank()) {
            throw new AssertionError(
                    "No existe un accessToken en la memoria del Actor"
            );
        }

        actor.attemptsTo(
                Post.to("/api/auth/logout").with(request ->
                        request.header(
                                "Authorization",
                                "Bearer " + accessToken
                        )
                )
        );

        int status = SerenityRest.lastResponse().statusCode();

        if (status != 204) {
            throw new AssertionError(
                    "Error al cerrar sesión. HTTP esperado: 204, recibido: "
                            + status
            );
        }
    }
}
