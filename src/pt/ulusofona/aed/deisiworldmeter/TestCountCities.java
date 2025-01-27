package pt.ulusofona.aed.deisiworldmeter;

import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

public class TestCountCities {
    @Test
    public void getCitiesByCountry() {
        if (Main.parseFiles(new File("test-files/countCities"))) {
            Result result = Main.execute("COUNT_CITIES 10000");
            assertNotNull(result);
            assertTrue(result.success);
            String[] resultParts = result.result.split("\n");
            assertArrayEquals(new String[]{
                    "4"
            }, resultParts);
            result = Main.execute("COUNT_CITIES 7000");
            assertNotNull(result);
            assertTrue(result.success);
            resultParts = result.result.split("\n");
            assertArrayEquals(new String[]{
                    "5"
            }, resultParts);
        }else {
            fail("Ficheiro não encontrado");
        }
    }
}
