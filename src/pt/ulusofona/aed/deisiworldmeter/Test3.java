package pt.ulusofona.aed.deisiworldmeter;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.fail;

public class Test3 {
    @Test
    public void testConversaoStringCidades() {
        if (Main.parseFiles(new File("test-files/Test3"))) {
            // Obtém a lista de cidades
            ArrayList cidades = Main.getObjects(TipoEntidade.CIDADE);

            // Verifica se a lista foi preenchida corretamente
            assert cidades != null;

            Assertions.assertTrue(!cidades.isEmpty(), "A lista de cidades está vazia");

            String resultadoEsperado = "andorra la vella | AD | 07 | 20430 | (42.5,1.5166667)";
            String resultadoAtual = cidades.get(0).toString();
            Assertions.assertEquals(resultadoEsperado, resultadoAtual);
        }else {
            fail("Ficheiro não encontrado");
        }
    }
}
