package pt.ulusofona.aed.deisiworldmeter;

import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

public class TestGetCitiesAtDistance1 {
    @Test
    public void getCitiesAtDistance1() {
        if (Main.parseFiles(new File("test-files/TestGetCitiesAtDistance1"))) {
            Result result = Main.execute("GET_CITIES_AT_DISTANCE 10 Andorra");
            assertNotNull(result);
            assertTrue(result.success);
            String[] resultParts = result.result.split("\n");
            assertArrayEquals(new String[]{
                    "andorra la vella->canillo","canillo->les escaldes"
            }, resultParts);
            result = Main.execute("GET_CITIES_AT_DISTANCE 5 Andorra");
            assertNotNull(result);
            assertTrue(result.success);
            resultParts = result.result.split("\n");
            assertArrayEquals(new String[]{
                    "andorra la vella->la massana","encamp->la massana",
                    "encamp->les escaldes","la massana->les escaldes"
            }, resultParts);
        }else {
            fail("Ficheiro não encontrado");
        }
    }
}
