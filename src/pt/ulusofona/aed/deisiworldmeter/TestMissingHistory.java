package pt.ulusofona.aed.deisiworldmeter;

import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

public class TestMissingHistory {
    @Test
    public void getMissingHistory() {
        if (Main.parseFiles(new File("test-files/getMissingHistory"))) {
            Result result = Main.execute("GET_MISSING_HISTORY 2020 2021");
            assertNotNull(result);
            assertTrue(result.success);
            String[] resultParts = result.result.split("\n");
            assertArrayEquals(new String[]{
                    "ad:Andorra"
            }, resultParts);
            result = Main.execute("GET_MISSING_HISTORY 2019 2020");
            assertNotNull(result);
            assertTrue(result.success);
            resultParts = result.result.split("\n");
            assertArrayEquals(new String[]{
                    "ad:Andorra",
                    "de:Alemanha"
            }, resultParts);
        }else {
            fail("Ficheiro não encontrado");
        }
    }
}
