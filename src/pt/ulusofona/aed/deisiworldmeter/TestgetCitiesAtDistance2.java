package pt.ulusofona.aed.deisiworldmeter;

import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

public class TestgetCitiesAtDistance2 {
    @Test
    public void testGetCitiesAtDistance2() {
        if (Main.parseFiles(new File("test-files/TestgetCitiesAtDistance2"))) {
            Result result = Main.execute("GET_CITIES_AT_DISTANCE2 100 Portugal");
            assertNotNull(result);
            assertTrue(result.success);
            String[] resultParts = result.result.split("\n");
            assertArrayEquals(new String[]{
                    "city of angels->city of demons"

            }, resultParts);
        } else {
            fail("Ficheiro não encontrado");
        }
    }
}
