package pt.ulusofona.aed.deisiworldmeter;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

public class TestGetMostPopulous {
    @Test
    public void testGetCitiesAtDistance2() {
        if (Main.parseFiles(new File("test-files/getMostPopulous"))) {
            Result result = Main.execute("GET_MOST_POPULOUS 2");
            assertNotNull(result);
            assertTrue(result.success);
            String[] resultParts = result.result.split("\n");
            assertArrayEquals(new String[]{
                    "Espanha:city of angels:18244" ,"Portugal:city of demons:11357"
            }, resultParts);
        } else {
            fail("Ficheiro não encontrado");
        }
    }
}
