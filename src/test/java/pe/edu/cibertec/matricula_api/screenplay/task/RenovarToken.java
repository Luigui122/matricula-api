
package pe.edu.cibertec.matricula_api.screenplay.task;

import io.restassured.http.ContentType;
import net.serenitybdd.rest.SerenityRest;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.rest.interactions.Post;

import java.util.Map;

import static net.serenitybdd.screenplay.Tasks.instrumented;

public class RenovarToken implements Task {

    public static RenovarToken conRefreshTokenOriginal() {
        return instrumented(RenovarToken.class);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {

        String refreshTokenOriginal = actor.recall("refreshToken");

        if (refreshTokenOriginal == null || refreshTokenOriginal.isBlank()) {
            throw new AssertionError(
                    "No existe un refreshToken en la memoria del Actor"
            );
        }

        actor.remember("refreshTokenOriginal", refreshTokenOriginal);

        actor.attemptsTo(
                Post.to("/api/auth/refresh").with(request ->
                        request
                                .contentType(ContentType.JSON)
                                .body(Map.of(
                                        "refreshToken",
                                        refreshTokenOriginal
                                ))
                )
        );

        int status = SerenityRest.lastResponse().statusCode();

        if (status == 200) {

            String nuevoAccessToken = SerenityRest.lastResponse()
                    .jsonPath()
                    .getString("accessToken");

            String nuevoRefreshToken = SerenityRest.lastResponse()
                    .jsonPath()
                    .getString("refreshToken");

            if (nuevoAccessToken == null || nuevoAccessToken.isBlank()) {
                throw new AssertionError(
                        "La renovación no devolvió un nuevo accessToken"
                );
            }

            if (nuevoRefreshToken == null || nuevoRefreshToken.isBlank()) {
                throw new AssertionError(
                        "La renovación no devolvió un nuevo refreshToken"
                );
            }

            actor.remember("accessTokenRenovado", nuevoAccessToken);
            actor.remember("refreshTokenRenovado", nuevoRefreshToken);

            actor.remember("accessToken", nuevoAccessToken);
            actor.remember("refreshToken", nuevoRefreshToken);
        }
    }
}
