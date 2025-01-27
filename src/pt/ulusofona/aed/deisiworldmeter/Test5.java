package pt.ulusofona.aed.deisiworldmeter;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.fail;

public class Test5 {
    @Test
    public void testLeituraDosFicheirosComErros() {
        if (Main.parseFiles(new File("test-files/Test5"))) {
            ArrayList input_invalid = Main.getObjects(TipoEntidade.INPUT_INVALIDO);
            String invalidaPaises = "paises.csv | 1 | 9 | 3";
            String invalidaCidades = "cidades.csv | 1 | 3 | 2";
            String invalidaPopulacao = "populacao.csv | 0 | 8 | 2";


            Assertions.assertEquals(invalidaPaises, input_invalid.get(0).toString());
            Assertions.assertEquals(invalidaCidades, input_invalid.get(1).toString());
            Assertions.assertEquals(invalidaPopulacao, input_invalid.get(2).toString());
        }else {
            fail("Ficheiro não encontrado");
        }
    }
}
