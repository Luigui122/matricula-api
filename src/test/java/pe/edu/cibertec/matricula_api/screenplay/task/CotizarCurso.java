
package pe.edu.cibertec.matricula_api.screenplay.task;

import io.restassured.http.ContentType;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.rest.SerenityRest;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.rest.interactions.Post;

import java.util.List;
import java.util.Map;

public class CotizarCurso implements Task {

    private final String alumno;
    private final String curso;

    public CotizarCurso(String alumno, String curso) {
        this.alumno = alumno;
        this.curso = curso;
    }

    public static CotizarCurso para(String alumno, String curso) {
        return new CotizarCurso(alumno, curso);
    }

    @Override
    @Step("{0} cotiza el curso #curso para el alumno #alumno")
    public <T extends Actor> void performAs(T actor) {

        String token = actor.recall("accessToken");

        actor.attemptsTo(
                Post.to("/api/matriculas/cotizacion")
                        .with(req -> req
                                .header("Authorization", "Bearer " + token)
                                .contentType(ContentType.JSON)
                                .body(Map.of(
                                        "codigoAlumno", alumno,
                                        "cursos", List.of(curso)
                                ))
                        )
        );

        if (SerenityRest.lastResponse().statusCode() == 200) {
            double total = SerenityRest.lastResponse()
                    .jsonPath()
                    .getDouble("total");

            actor.remember("totalCotizacion", total);
        }
    }
}
