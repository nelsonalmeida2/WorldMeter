package pt.ulusofona.aed.deisiworldmeter;

import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

public class Test2LeastPopulous {
    @Test
    public void getLeastPopulous2() {
        if (Main.parseFiles(new File("test-files/Test2LeastPopulous"))) {
            Result result = Main.execute("GET_LEAST_POPULOUS 2");
            assertNotNull(result);
            assertTrue(result.success);
            String[] resultParts = result.result.split("\n");
            assertArrayEquals(new String[]{
                    "Angola:canillo:3292","Albânia:la massana:7211"
            }, resultParts);
            result = Main.execute("GET_LEAST_POPULOUS 3");
            assertNotNull(result);
            assertTrue(result.success);
            resultParts = result.result.split("\n");
            assertArrayEquals(new String[]{
                    "Angola:canillo:3292","Albânia:la massana:7211","Andorra:encamp:11224"
            }, resultParts);
        }else {
            fail("Ficheiro não encontrado");
        }
    }
}
