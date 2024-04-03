package pt.ulusofona.aed.deisiworldmeter;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Timer;


public class Main {

    public static ArrayList<Pais> listaPaises = new ArrayList<Pais>();
    public static ArrayList<Populacao> listaPopulacao = new ArrayList<Populacao>();
    public static ArrayList<Cidade> listaCidades = new ArrayList<Cidade>();
    public static InputInvalido listaPaisesInvalidos = new InputInvalido("paises.csv",0,0,0);
    public static InputInvalido listaPopulacaoInvalidos = new InputInvalido("populacao.csv",0,0,0);
    public static InputInvalido listaCidadesInvalidos = new InputInvalido("cidades.csv",0,0,0);
    public static Boolean parseFiles(File nomePasta) { // Ler os 3 ficheiros CSV, recebe o nome da pasta
        try {
            Scanner scanner = new Scanner(nomePasta);

            int linhaNum = 0;
            boolean primeiraLinha = true;
            boolean primeiraInvalida = true;

            switch (nomePasta.getName()) {
                case "paises.csv": // se o id for maior de 700 if
                    listaPaises = new ArrayList<Pais>();
                    listaPaisesInvalidos = new InputInvalido("paises.csv",0,0,0);
                    while (scanner.hasNext()){
                        String linha = scanner.nextLine();
                        if (primeiraLinha) {
                            primeiraLinha = false;
                        } else {
                            String[] partes = linha.split(",");
                            Pais pais = new Pais(Integer.parseInt(partes[0]),partes[1].toUpperCase(),partes[2].toUpperCase(),partes[3]);
                            listaPaises.add(pais);
                        }
                    }
                    break;

                case "populacao.csv":
                    listaPopulacao = new ArrayList<Populacao>();
                    listaPopulacaoInvalidos = new InputInvalido("populacao.csv",0,0,0);
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
                    listaCidades = new ArrayList<Cidade>();
                    listaCidadesInvalidos = new InputInvalido("cidades.csv",0,0,0);
                    while (scanner.hasNext()){
                        String linha = scanner.nextLine();
                        if (primeiraLinha) {
                            primeiraLinha = false;
                        } else {
                            String[] partes = linha.split(",");
                            if (partes[0] == "" || partes[1] == "" || partes[2] == "" ||
                                    partes[3] == "" || partes[4] == "" || partes[5] == "") {
                                linhaNum ++;
                                listaCidadesInvalidos.linhasIncorretas ++;
                                if (primeiraInvalida) {
                                    listaCidadesInvalidos.primeiraLinhaIncorreta = linhaNum;
                                    primeiraInvalida = false;
                                    System.out.println(partes[0]);
                                }
                            } else {
                                linhaNum++;
                                int count = 0;
                                String populacao = "";
                                while (!(partes[3].charAt(count) == '.')) {
                                    populacao += String.valueOf(partes[3].charAt(count));
                                    count++;
                                }
                                partes[3] = populacao;
                                Cidade cidade = new Cidade(partes[0].toUpperCase(),partes[1],Integer.parseInt(partes[2]),Integer.parseInt(partes[3]),
                                        Double.parseDouble(partes[4]),Double.parseDouble(partes[5]));
                                listaCidades.add(cidade);
                                listaCidadesInvalidos.linhasCorretas ++;
                            }
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

    public static void getObjects(TipoEntidade entidade) { // ArrayList<String>

    }

    public static void main(String[] args) {
        File file = new File("cidades.csv");
        parseFiles(file);
        for (Cidade cidade: listaCidades) {
            System.out.println(cidade);
        }
        System.out.println(listaCidadesInvalidos);
    }
}
