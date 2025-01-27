package pt.ulusofona.aed.deisiworldmeter;

import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

public class TestInsertCity {
    @Test
    public void getCitiesByCountry() {
        if (Main.parseFiles(new File("test-files/TestInsertCity"))) {
            Result result = Main.execute("INSERT_CITY ll CityOfAngels 45 100000");
            assertNotNull(result);
            assertTrue(result.success);
            String[] resultParts = result.result.split("\n");
            assertArrayEquals(new String[]{
                    "Inserido com sucesso"
            }, resultParts);
            result = Main.execute("INSERT_CITY ashha sashhas as 1919119");
            assertNotNull(result);
            assertTrue(result.success);
            resultParts = result.result.split("\n");
            assertArrayEquals(new String[]{
                    "Pais invalido"
            }, resultParts);
        }else {
            fail("Ficheiro não encontrado");
        }
    }
}
