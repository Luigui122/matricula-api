
package pe.edu.cibertec.matricula_api.screenplay.stepdefinitions;

import io.cucumber.java.en.When;
import net.serenitybdd.screenplay.actors.OnStage;
import pe.edu.cibertec.matricula_api.screenplay.task.Matricularse;
import pe.edu.cibertec.matricula_api.screenplay.task.PagarMatricula;
import pe.edu.cibertec.matricula_api.screenplay.task.CotizarCurso;
import pe.edu.cibertec.matricula_api.screenplay.task.ConsultarPagos;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.And;

import pe.edu.cibertec.matricula_api.screenplay.questions.LaRespuesta;

import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static net.serenitybdd.screenplay.actors.OnStage.theActorInTheSpotlight;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import net.serenitybdd.rest.SerenityRest;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class MatriculaStepDefinitions {

    @When("se matricula con el codigo {string} en el curso {string}")
    public void seMatriculaEnElCurso(String alumno, String curso) {

        OnStage.theActorInTheSpotlight().attemptsTo(
                Matricularse.en(alumno, curso)
        );
    }

    @When("paga la matrícula mediante {string}")
    public void pagaLaMatricula(String metodo) {

        OnStage.theActorInTheSpotlight().attemptsTo(
                PagarMatricula.conMetodo(metodo)
        );
    }

    @When("consulta la cotización del curso {string} para el alumno {string}")
    public void consultaLaCotizacion(String curso, String alumno) {

        OnStage.theActorInTheSpotlight().attemptsTo(
                CotizarCurso.para(alumno, curso)
        );
    }

    @Then("la cotización contiene {int} créditos")
    public void validaCreditos(int creditos) {
        theActorInTheSpotlight().should(
                seeThat(LaRespuesta.entero("creditos"), equalTo(creditos))
        );
    }


    @And("el subtotal es {int}")
    public void validaSubtotal(int subtotal) {
        theActorInTheSpotlight().should(
                seeThat(
                        LaRespuesta.decimal("subtotal"),
                        equalTo((double) subtotal)
                )
        );
    }

    @And("el descuento es {int}")
    public void validaDescuento(int descuento) {
        theActorInTheSpotlight().should(
                seeThat(
                        LaRespuesta.decimal("descuento"),
                        equalTo((double) descuento)
                )
        );
    }

    @And("el total es {int}")
    public void validaTotal(int total) {
        theActorInTheSpotlight().should(
                seeThat(
                        LaRespuesta.decimal("total"),
                        equalTo((double) total)
                )
        );
    }

    @And("el monto de la matrícula es {int}")
    public void validaMontoMatricula(int monto) {
        theActorInTheSpotlight().should(
                seeThat(
                        LaRespuesta.decimal("monto"),
                        equalTo((double) monto)
                )
        );
    }


    @And("el estado de la matrícula es {string}")
    public void validaEstadoMatricula(String estado) {
        theActorInTheSpotlight().should(
                seeThat(LaRespuesta.campo("estado"), equalTo(estado))
        );
    }

    @And("la fecha de pago está registrada")
    public void validaFechaPago() {
        theActorInTheSpotlight().should(
                seeThat(LaRespuesta.campoTieneValor("fechaPago"), is(true))
        );
    }

    @And("la respuesta cumple el esquema {string}")
    public void validaEsquema(String archivo) {

        SerenityRest.lastResponse()
                .then()
                .assertThat()
                .body(matchesJsonSchemaInClasspath("schemas/" + archivo));
    }

    @When("consulta los pagos de la API")
    public void consultaLosPagos() {
        theActorInTheSpotlight().attemptsTo(
                ConsultarPagos.deLaAPI()
        );
    }



    @And("verifica que el pago corresponde a la matrícula")
    public void verificaPagoDeMatricula() {
        theActorInTheSpotlight().should(
                seeThat(
                        LaRespuesta.pagoCorrespondeMatricula(),
                        equalTo(true)
                )
        );
    }

    @And("el método de pago es {string}")
    public void validaMetodoPago(String metodo) {
        theActorInTheSpotlight().should(
                seeThat(
                        LaRespuesta.pagoTieneMetodo(metodo),
                        equalTo(true)
                )
        );
    }

    @And("el número de operación tiene el formato {string}")
    public void validaFormatoOperacion(String formato) {
        theActorInTheSpotlight().should(
                seeThat(
                        LaRespuesta.numeroOperacionTieneFormato(formato),
                        equalTo(true)
                )
        );
    }

}
