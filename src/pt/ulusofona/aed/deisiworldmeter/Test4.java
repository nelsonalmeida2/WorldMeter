package pt.ulusofona.aed.deisiworldmeter;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.fail;

public class Test4 {
    @Test
    public void testLeituraDosFicheirosSemErros() {
        if (Main.parseFiles(new File("test-files/Test4"))) {
            ArrayList cidades = Main.getObjects(TipoEntidade.CIDADE);
            ArrayList paises = Main.getObjects(TipoEntidade.PAIS);

            String cidade1 = "anar darreh | AF | 06 | 10023 | (32.758697999999995,61.653969)";
            String cidade2 = "andarab | AF | 03 | 27034 | (35.633484,69.260195)";
            String cidade3 = "verwoerdburg | ZA | 06 | 233394 | (-25.840795,28.176102)";
            String cidade4 = "viljoenskroon | ZA | 03 | 55025 | (-27.208406,26.948553000000004)";

            String pais1 = "Afeganistão | 4 | AF | AFG";
            String pais2 = "África do Sul | 710 | ZA | ZAF | 0";


            assert cidades != null;
            assert paises != null;

            Assertions.assertEquals(pais1, paises.get(0).toString());
            Assertions.assertEquals(pais2, paises.get(1).toString());
            Assertions.assertEquals(cidade1, cidades.get(0).toString());
            Assertions.assertEquals(cidade2, cidades.get(1).toString());
            Assertions.assertEquals(cidade3, cidades.get(2).toString());
            Assertions.assertEquals(cidade4, cidades.get(3).toString());
        }else {
            fail("Ficheiro não encontrado");
        }
    }
}
