package pt.ulusofona.aed.deisiworldmeter;


import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

public class TestGetCitiesByCountry {
    @Test
    public void getCitiesByCountry() {
        if (Main.parseFiles(new File("test-files/geCitiesByCountry"))) {
            Result result = Main.execute("GET_CITIES_BY_COUNTRY 2 Wakanda");
            assertNotNull(result);
            assertTrue(result.success);
            String[] resultParts = result.result.split("\n");
            assertArrayEquals(new String[]{
                    "Birnin Zana 1",
                    "Birnin Zana 2"
            }, resultParts);
            result = Main.execute("GET_CITIES_BY_COUNTRY 3 Lalaland");
            assertNotNull(result);
            assertTrue(result.success);
            resultParts = result.result.split("\n");
            assertArrayEquals(new String[]{
                    "Land of sunshine"
            }, resultParts);
        }
        else {
            fail("Ficheiro não encontrado");
        }
    }
}
