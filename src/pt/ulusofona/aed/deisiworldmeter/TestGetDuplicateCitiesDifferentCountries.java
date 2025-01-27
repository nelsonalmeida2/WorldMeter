package pt.ulusofona.aed.deisiworldmeter;

import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

public class TestGetDuplicateCitiesDifferentCountries {

    @Test
    public void TestGetDuplicateCitiesDifferentCountriesSimples() {
        if (Main.parseFiles(new File("test-files/TestGetDuplicateCitiesDifferentCountries"))) {
            Result result = Main.execute("GET_DUPLICATE_CITIES_DIFFERENT_COUNTRIES 1000");
            String valorPretendido = "Birnin Zana 1: Lalaland,Wakanda\n" +
                    "Land of sunshine: Lalaland,Wakanda\n";
            assertNotNull(result);
            assertTrue(result.success);
            assertEquals(valorPretendido, result.result);
        } else {
            fail("Ficheiro não encontrado");
        }
    }
}
