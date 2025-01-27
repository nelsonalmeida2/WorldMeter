package pt.ulusofona.aed.deisiworldmeter;

import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

public class TestGenderGap {
    @Test
    public void getGenderGap() {
        if (Main.parseFiles(new File("test-files/TestGenderGap"))) {
            Result result = Main.execute("GET_COUNTRIES_GENDER_GAP 0");
            assertNotNull(result);
            assertTrue(result.success);
            String[] resultParts = result.result.split("\n");
            for (int i = 0; i < resultParts.length; i++) {
                resultParts[i] = resultParts[i].replace('.', ',');
            }
            assertArrayEquals(new String[]{
                    "Andorra:32,07","Alemanha:1,26"
            }, resultParts);
        }else {
            fail("Ficheiro não encontrado");
        }
    }
}
