package pt.ulusofona.aed.deisiworldmeter;

import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

public class TestTopPopulationIncrease {
    @Test
    public void topPopulationIncrease() {
        if (Main.parseFiles(new File("test-files/countCities"))) {
            Result result = Main.execute("GET_TOP_POPULATION_INCREASE 2020 2022");
            assertNotNull(result);
            assertTrue(result.success);
            String[] resultParts = result.result.split("\n");
            for (int i = 0; i < resultParts.length; i++) {
                resultParts[i] = resultParts[i].replace('.', ',');
            }
            assertArrayEquals(new String[]{
                    "Alemanha:2020-2022:8,71%","Andorra:2020-2021:4,59%","Alemanha:2020-2021:4,47%","Alemanha:2021-2022:4,44%"
            }, resultParts);
        }else {
            fail("Ficheiro não encontrado");
        }
    }
}
