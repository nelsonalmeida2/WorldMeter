package pt.ulusofona.aed.deisiworldmeter;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.fail;

public class Test2 {
    @Test
    public void testConversaoStringPaisesComIdMenorQue700() {
        // Carrega os dados necessários para o teste
        if (Main.parseFiles(new File("test-files/Test2"))) {
            // Obtém a lista de países
            ArrayList paises = Main.getObjects(TipoEntidade.PAIS);

            // Verifica se a lista foi preenchida corretamente
            assert paises != null;

            Assertions.assertTrue(!paises.isEmpty(), "A lista de países está vazia");

            String resultadoEsperado = "Afeganistão | 4 | AF | AFG";
            String resultadoAtual = paises.get(0).toString();
            Assertions.assertEquals(resultadoEsperado, resultadoAtual);
        }
        else {
            fail("Ficheiro não encontrado");
        }
    }
}
