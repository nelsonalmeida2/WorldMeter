package pt.ulusofona.aed.deisiworldmeter;

import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

public class TestSumPopulations {
    @Test
    public void sumPopulation() {
        if (Main.parseFiles(new File("test-files/sumPopulations"))) {
            Result result = Main.execute("SUM_POPULATIONS Andorra");
            assertNotNull(result);
            assertTrue(result.success);
            String[] resultParts = result.result.split("\n");
            assertArrayEquals(new String[]{
                    "314310800"
            }, resultParts);
            result = Main.execute("SUM_POPULATIONS Andorra,Alemanha");
            assertNotNull(result);
            assertTrue(result.success);
            resultParts = result.result.split("\n");
            assertArrayEquals(new String[]{
                    "397563274"
            }, resultParts);
        } else {
            fail("Ficheiro não encontrado");
        }
    }
}
