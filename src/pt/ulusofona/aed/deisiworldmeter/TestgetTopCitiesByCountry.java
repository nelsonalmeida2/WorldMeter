package pt.ulusofona.aed.deisiworldmeter;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.fail;

public class TestgetTopCitiesByCountry {
    @Test
    public void testgetTopCitiesByCountryDesempates() {
        if (Main.parseFiles(new File("test-files/getTopCitiesByCountry"))) {
            String resultadoEsperado =
                    "lisbon:517K\n" +
                            "amadora de cima 2:249K\n" +
                            "amadora de cima 3:249K\n" +
                            "amadora de cima 4:249K\n" +
                            "braga:249K\n";
            Result result = Main.execute("GET_TOP_CITIES_BY_COUNTRY 5 Portugal");
            Assertions.assertEquals(resultadoEsperado, result.result, "testgetTopCitiesByCountryDesempates esta errado");
        }else {
            fail("Ficheiro não encontrado");
        }
    }
}
