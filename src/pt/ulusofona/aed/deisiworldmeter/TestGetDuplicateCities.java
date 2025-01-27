package pt.ulusofona.aed.deisiworldmeter;

import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

public class TestGetDuplicateCities {

    @Test
    public void getCitiesByCountry() {
        if (Main.parseFiles(new File("test-files/TestGetDuplicateCities"))) {
            Result result = Main.execute("GET_DUPLICATE_CITIES 1000");
            assertNotNull(result);
            assertTrue(result.success);
            String[] resultParts = result.result.split("\n");
            assertArrayEquals(new String[]{
                    "Land of sunshine (Wakanda,1)","Birnin Zana 1 (Wakanda,1)"
            }, resultParts);
            result = Main.execute("GET_DUPLICATE_CITIES 10000000");
            assertNotNull(result);
            assertTrue(result.success);
            resultParts = result.result.split("\n");
            assertArrayEquals(new String[]{
                    "Sem resultados"
            }, resultParts);
        }else {
            fail("Ficheiro não encontrado");
        }
    }
}
