package pt.ulusofona.aed.deisiworldmeter;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.fail;

public class Test1 {
        @Test
        public void testConversaoStringPaisesComIdMaiorQue700() {
            // Carrega os dados necessários para o teste
            if (Main.parseFiles(new File("test-files/Test1"))) {
                // Obtém a lista de países
                ArrayList paises = Main.getObjects(TipoEntidade.PAIS);

                // Verifica se a lista foi preenchida corretamente
                assert paises != null;

                // Verifica se há pelo menos um país na lista
                Assertions.assertFalse(paises.isEmpty(), "A lista de países está vazia");

                // Verifica se o primeiro país na lista possui os dados esperados
                String resultadoEsperado = "África do Sul | 710 | ZA | ZAF | 0";
                String resultadoAtual = paises.get(1).toString();
                Assertions.assertEquals(resultadoEsperado, resultadoAtual);
            }
            else {
                fail("Ficheiro não encontrado");
            }
        }
}
