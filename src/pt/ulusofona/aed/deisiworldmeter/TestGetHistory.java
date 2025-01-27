package pt.ulusofona.aed.deisiworldmeter;

import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

public class TestGetHistory {
    @Test
    public void getMissingHistory() {
        if (Main.parseFiles(new File("test-files/getMissingHistory"))) {
            Result result = Main.execute("GET_HISTORY 2020 2024 Alemanha");
            assertNotNull(result);
            assertTrue(result.success);
            String[] resultParts = result.result.split("\n");
            assertArrayEquals(new String[]{
                    "2020:217359k:111841k",
                    "2021:227501k:117093k",
                    "2022:238047k:122555k",
                    "2024:41103k:42148k"
            }, resultParts);
            result = Main.execute("GET_HISTORY 2020 2022 Andorra");
            assertNotNull(result);
            assertTrue(result.success);
            resultParts = result.result.split("\n");
            assertArrayEquals(new String[]{
                    "2020:198052k:101847k"
            }, resultParts);
        }
        else {
            fail("Ficheiro não encontrado");
        }
    }
}
