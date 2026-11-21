
package pe.edu.cibertec.matricula_api.screenplay.stepdefinitions;

import io.cucumber.java.Before;
import net.serenitybdd.screenplay.actors.OnStage;
import net.serenitybdd.screenplay.actors.OnlineCast;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Hooks {

    private static final String NOMBRE_ESTUDIANTE =
            "Luigui Alejandro Cordova Cahuaza";

    private static final String CODIGO_ESTUDIANTE =
            "i202501352";

    @Before
    public void beforeScenario() {

        OnStage.setTheStage(new OnlineCast());

        String fechaHora = LocalDateTime.now().format(
                DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")
        );

        System.out.println("\n==========================================");
        System.out.println("        EJECUCIÓN DE PRUEBAS");
        System.out.println("==========================================");
        System.out.println("Estudiante: " + NOMBRE_ESTUDIANTE);
        System.out.println("Código:     " + CODIGO_ESTUDIANTE);
        System.out.println("Fecha:      " + fechaHora);
        System.out.println("==========================================\n");
    }
}
