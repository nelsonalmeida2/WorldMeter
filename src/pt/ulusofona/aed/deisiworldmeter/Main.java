package pt.ulusofona.aed.deisiworldmeter;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;



public class Main {

    public static ArrayList<Pais> listaPaises = new ArrayList<Pais>();
    public static ArrayList<Populacao> listaPopulacao = new ArrayList<Populacao>();
    public static ArrayList<Cidade> listaCidades = new ArrayList<Cidade>();

    public static Boolean parseFiles(File nomePasta) { // Ler os 3 ficheiros CSV, recebe o nome da pasta

        try {
            Scanner scanner = new Scanner(nomePasta);

            int linhaNum = 0;
            boolean primeiraLinha = true;

            switch (nomePasta.getName()) {
                case "paises.csv":
                    while (scanner.hasNext()){
                        String linha = scanner.nextLine();
                        if (primeiraLinha) {
                            primeiraLinha = false;
                        } else {
                            String[] partes = linha.split(",");
                            Pais pais = new Pais(Integer.parseInt(partes[0]),partes[1],partes[2],partes[3]);
                            listaPaises.add(pais);
                        }
                    }
                    break;

                case "populacao.csv":
                    while (scanner.hasNext()){
                        String linha = scanner.nextLine();
                        if (primeiraLinha) {
                            primeiraLinha = false;
                        } else {
                            String[] partes = linha.split(",");
                            Populacao populacao = new Populacao(Integer.parseInt(partes[0]),Integer.parseInt(partes[1]),
                                    Integer.parseInt(partes[2]),Integer.parseInt(partes[3]),Double.parseDouble(partes[4]));
                            listaPopulacao.add(populacao);
                        }
                    }
                    break;

                case "cidades.csv":
                    while (scanner.hasNext()){
                        String linha = scanner.nextLine();
                        if (primeiraLinha) {
                            primeiraLinha = false;
                        } else {
                            String[] partes = linha.split(",");
                            if (partes[3] == "") { // ver o que se deve fazer quando a populacao é ""
                                partes[3] = "0.0";
                            }
                            Cidade cidade = new Cidade(partes[0],partes[1],partes[2],Double.parseDouble(partes[3]),
                                    Double.parseDouble(partes[4]),Double.parseDouble(partes[5]));
                            listaCidades.add(cidade);
                        }
                    }
                    break;

                default:
                    return false;
            }
        } catch (FileNotFoundException e) {
            return false;
        }
        return true;
    }


    public static void main(String[] args) {
        File file = new File("cidades.csv");
        parseFiles(file);
        for (Cidade cidade: listaCidades) {
            System.out.println(cidade);
        }
    }
}
