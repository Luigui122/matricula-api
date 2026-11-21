
package pe.edu.cibertec.matricula_api.screenplay.task;

import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.rest.interactions.Get;

public class ConsultarPagos implements Task {

    public static ConsultarPagos deLaAPI() {
        return new ConsultarPagos();
    }

    @Override
    @Step("{0} consulta los pagos registrados en la API")
    public <T extends Actor> void performAs(T actor) {

        String token = actor.recall("accessToken");

        actor.attemptsTo(
                Get.resource("/api/pagos")
                        .with(request -> request
                                .header("Authorization", "Bearer " + token)
                        )
        );
    }
}
