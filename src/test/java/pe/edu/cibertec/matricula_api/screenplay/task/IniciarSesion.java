
package pe.edu.cibertec.matricula_api.screenplay.task;

import io.restassured.http.ContentType;
import net.serenitybdd.rest.SerenityRest;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.rest.interactions.Post;

import java.util.Map;

import static net.serenitybdd.screenplay.Tasks.instrumented;

public class IniciarSesion implements Task {

    private final String username;
    private final String password;

    public IniciarSesion(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public static IniciarSesion conCredenciales(
            String username,
            String password
    ) {
        return instrumented(
                IniciarSesion.class,
                username,
                password
        );
    }

    @Override
    public <T extends Actor> void performAs(T actor) {

        actor.attemptsTo(
                Post.to("/api/auth/login").with(request ->
                        request
                                .contentType(ContentType.JSON)
                                .body(Map.of(
                                        "username", username,
                                        "password", password
                                ))
                )
        );

        int status = SerenityRest.lastResponse().statusCode();

        if (status == 200) {

            String accessToken = SerenityRest.lastResponse()
                    .jsonPath()
                    .getString("accessToken");

            String refreshToken = SerenityRest.lastResponse()
                    .jsonPath()
                    .getString("refreshToken");

            if (accessToken == null || accessToken.isBlank()) {
                throw new AssertionError(
                        "El inicio de sesión fue exitoso, pero no se recibió accessToken"
                );
            }

            if (refreshToken == null || refreshToken.isBlank()) {
                throw new AssertionError(
                        "El inicio de sesión fue exitoso, pero no se recibió refreshToken"
                );
            }

            actor.remember("accessToken", accessToken);
            actor.remember("refreshToken", refreshToken);
        }
    }
}
