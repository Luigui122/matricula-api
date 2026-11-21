
package pe.edu.cibertec.matricula_api.screenplay.stepdefinitions;

import io.cucumber.java.en.When;
import net.serenitybdd.model.environment.EnvironmentSpecificConfiguration;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.rest.abilities.CallAnApi;
import net.serenitybdd.screenplay.rest.interactions.Get;
import net.serenitybdd.screenplay.rest.interactions.Post;
import net.thucydides.model.environment.SystemEnvironmentVariables;
import pe.edu.cibertec.matricula_api.screenplay.task.CerrarSesion;
import pe.edu.cibertec.matricula_api.screenplay.task.IniciarSesion;
import pe.edu.cibertec.matricula_api.screenplay.task.RenovarToken;

import static net.serenitybdd.screenplay.actors.OnStage.theActorCalled;
import static net.serenitybdd.screenplay.actors.OnStage.theActorInTheSpotlight;

public class SeguridadStepDefinitions {

    private final String baseUrl = EnvironmentSpecificConfiguration
            .from(SystemEnvironmentVariables.currentEnvironmentVariables())
            .getProperty("restapi.baseurl");

    private static final String PASSWORD_CORRECTA = "Cibertec2026";
    private static final String PASSWORD_INCORRECTA = "ClaveIncorrecta123";

    @When("renueva el refresh token original")
    public void renuevaElRefreshTokenOriginal() {
        theActorInTheSpotlight().attemptsTo(
                RenovarToken.conRefreshTokenOriginal()
        );
    }

    @When("reutiliza el refresh token original")
    public void reutilizaElRefreshTokenOriginal() {

        Actor actor = theActorInTheSpotlight();

        String refreshTokenOriginal = actor.recall("refreshTokenOriginal");

        actor.attemptsTo(
                Post.to("/api/auth/refresh").with(request ->
                        request
                                .contentType("application/json")
                                .body(java.util.Map.of(
                                        "refreshToken",
                                        refreshTokenOriginal
                                ))
                )
        );
    }

    @When("consulta su perfil con el access token renovado")
    public void consultaPerfilConAccessTokenRenovado() {

        Actor actor = theActorInTheSpotlight();

        String tokenRenovado = actor.recall("accessTokenRenovado");

        actor.attemptsTo(
                Get.resource("/api/auth/perfil").with(request ->
                        request.header(
                                "Authorization",
                                "Bearer " + tokenRenovado
                        )
                )
        );
    }

    @When("cierra su sesión")
    public void cierraSuSesion() {
        theActorInTheSpotlight().attemptsTo(
                CerrarSesion.actual()
        );
    }

    @When("consulta su perfil con el mismo access token")
    public void consultaPerfilConElMismoAccessToken() {

        Actor actor = theActorInTheSpotlight();

        String accessToken = actor.recall("accessToken");

        actor.attemptsTo(
                Get.resource("/api/auth/perfil").with(request ->
                        request.header(
                                "Authorization",
                                "Bearer " + accessToken
                        )
                )
        );
    }

    @When("el usuario {string} realiza cuatro intentos de inicio de sesión con contraseña incorrecta")
    public void realizaCuatroIntentosFallidos(String username) {

        Actor actor = theActorCalled(username)
                .whoCan(CallAnApi.at(baseUrl));

        for (int i = 0; i < 4; i++) {
            actor.attemptsTo(
                    IniciarSesion.conCredenciales(
                            username,
                            PASSWORD_INCORRECTA
                    )
            );
        }
    }

    @When("realiza el quinto intento de inicio de sesión con contraseña incorrecta")
    public void realizaQuintoIntentoFallido() {

        Actor actor = theActorInTheSpotlight();

        actor.attemptsTo(
                IniciarSesion.conCredenciales(
                        actor.getName(),
                        PASSWORD_INCORRECTA
                )
        );
    }

    @When("intenta iniciar sesión con la contraseña correcta")
    public void intentaIniciarSesionConPasswordCorrecta() {

        Actor actor = theActorInTheSpotlight();

        actor.attemptsTo(
                IniciarSesion.conCredenciales(
                        actor.getName(),
                        PASSWORD_CORRECTA
                )
        );
    }

    @When("consulta la lista de alumnos")
    public void consultaListaDeAlumnos() {

        Actor actor = theActorInTheSpotlight();

        String accessToken = actor.recall("accessToken");

        actor.attemptsTo(
                Get.resource("/api/alumnos").with(request ->
                        request.header(
                                "Authorization",
                                "Bearer " + accessToken
                        )
                )
        );
    }
}
