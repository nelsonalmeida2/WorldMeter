package pt.ulusofona.aed.deisiworldmeter;

import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

public class TestLeastPopulous {
    @Test
    public void getLeastPopulous() {
        if (Main.parseFiles(new File("test-files/TestLeastPopulous"))) {
            Result result = Main.execute("GET_LEAST_POPULOUS 1");
            assertNotNull(result);
            assertTrue(result.success);
            String[] resultParts = result.result.split("\n");
            assertArrayEquals(new String[]{
                    "Andorra:canillo:3292"
            }, resultParts);
            result = Main.execute("GET_LEAST_POPULOUS 2");
            assertNotNull(result);
            assertTrue(result.success);
            resultParts = result.result.split("\n");
            assertArrayEquals(new String[]{
                    "Andorra:canillo:3292","Alemanha:loxstedt:16783"
            }, resultParts);
        }else {
            fail("Ficheiro não encontrado");
        }
    }
}
