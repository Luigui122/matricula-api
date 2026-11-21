
package pe.edu.cibertec.matricula_api.screenplay.stepdefinitions;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.restassured.http.ContentType;
import net.serenitybdd.model.environment.EnvironmentSpecificConfiguration;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.rest.abilities.CallAnApi;
import net.serenitybdd.screenplay.rest.interactions.Post;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.model.environment.SystemEnvironmentVariables;
import pe.edu.cibertec.matricula_api.screenplay.questions.LaRespuesta;

import java.util.Map;

import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static net.serenitybdd.screenplay.actors.OnStage.theActorCalled;
import static net.serenitybdd.screenplay.actors.OnStage.theActorInTheSpotlight;
import static org.hamcrest.Matchers.equalTo;

public class ComunesStepDefinitions {

    private final String baseUrl = EnvironmentSpecificConfiguration
            .from(SystemEnvironmentVariables.currentEnvironmentVariables())
            .getProperty("restapi.baseurl");

    @Given("que el alumno {string} inicia sesión correctamente")
    public void elAlumnoIniciaSesion(String codigoAlumno) {

        Actor alumno = theActorCalled(codigoAlumno)
                .whoCan(CallAnApi.at(baseUrl));

        alumno.attemptsTo(
                Post.to("/api/auth/login").with(request ->
                        request
                                .contentType(ContentType.JSON)
                                .body(Map.of(
                                        "username", codigoAlumno,
                                        "password", "Cibertec2026"
                                ))
                )
        );

        int status = SerenityRest.lastResponse().statusCode();

        if (status != 200) {
            throw new AssertionError(
                    "Error al iniciar sesión. HTTP: " + status
            );
        }

        String accessToken = SerenityRest.lastResponse()
                .jsonPath().getString("accessToken");

        String refreshToken = SerenityRest.lastResponse()
                .jsonPath().getString("refreshToken");

        if (accessToken == null || accessToken.isBlank()) {
            throw new AssertionError("La API no devolvió accessToken");
        }

        alumno.remember("accessToken", accessToken);
        alumno.remember("refreshToken", refreshToken);
    }

    @Then("el código de respuesta es {int}")
    public void elCodigoDeRespuestaEsInt(int status) {
        theActorInTheSpotlight().should(
                seeThat(LaRespuesta.codigoDeEstado(), equalTo(status))
        );
    }

    @And("el código de error es {string}")
    public void elCodigoDeErrorEsString(String codigo) {
        theActorInTheSpotlight().should(
                seeThat(LaRespuesta.campo("codigo"), equalTo(codigo))
        );
    }
}
