package pe.edu.cibertec.matricula_api.screenplay.stepdefinitions;

import io.cucumber.java.en.And;
import io.cucumber.java.en.When;
import pe.edu.cibertec.matricula_api.screenplay.questions.LaRespuesta;
import pe.edu.cibertec.matricula_api.screenplay.task.ConsultarCurso;
import pe.edu.cibertec.matricula_api.screenplay.task.ListarCursos;

import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;

import static net.serenitybdd.screenplay.actors.OnStage.theActorCalled;
import static net.serenitybdd.screenplay.actors.OnStage.theActorInTheSpotlight;
import static org.hamcrest.Matchers.equalTo;

public class CursoStepDefinitions {

    @When("busca el curso {string}")
    public void buscaElCurso(String codigo) {
        theActorInTheSpotlight().attemptsTo(ConsultarCurso.conCodigo(codigo));
    }


    @When("lista los cursos con modalidad {string}")
    public void listaLosCursosConModalidad(String modalidad) {
        theActorInTheSpotlight().attemptsTo(ListarCursos.porModalidad(modalidad));
    }

    @And("el curso se llama {string}")
    public void elCursoSeLlama(String nombre) {
        theActorInTheSpotlight().should(seeThat(LaRespuesta.campo("nombre"), equalTo(nombre)));
    }

    @And("obtiene {int} cursos")
    public void obtieneCursosPorModalidad(int cantidad) {
        theActorInTheSpotlight().should(seeThat(LaRespuesta.cantidadDeElementos(), equalTo(cantidad)));
    }

}
