package pt.ulusofona.aed.deisiworldmeter;

import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

public class TestRemoveCountry {
    @Test
    public void removeCountry() {
        if (Main.parseFiles(new File("test-files/TestRemoveCountry"))) {
            Result result = Main.execute("REMOVE_COUNTRY Lalaland");
            assertNotNull(result);
            assertTrue(result.success);
            String[] resultParts = result.result.split("\n");
            assertArrayEquals(new String[]{
                    "Removido com sucesso"
            }, resultParts);
            result = Main.execute("REMOVE_COUNTRY adasda");
            assertNotNull(result);
            assertTrue(result.success);
            resultParts = result.result.split("\n");
            assertArrayEquals(new String[]{
                    "Pais invalido"
            }, resultParts);
        } else {
            fail("Ficheiro não encontrado");
        }
    }
}
