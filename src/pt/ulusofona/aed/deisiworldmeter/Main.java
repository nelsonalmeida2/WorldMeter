package pt.ulusofona.aed.deisiworldmeter;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;


public class Main {

    public static ArrayList<Pais> listaPaises = new ArrayList<Pais>();
    public static ArrayList<Populacao> listaPopulacao = new ArrayList<Populacao>();
    public static ArrayList<Cidade> listaCidades = new ArrayList<Cidade>();
    public static InputInvalido listaPaisesInvalidos = new InputInvalido("paises.csv",0,0,-1);
    public static InputInvalido listaPopulacaoInvalidos = new InputInvalido("populacao.csv",0,0,-1);
    public static InputInvalido listaCidadesInvalidos = new InputInvalido("cidades.csv",0,0,-1);
    public static Boolean parseFiles(File nomePasta) { // Ler os 3 ficheiros CSV, recebe o nome da pasta
        try {
            Scanner scanner = new Scanner(nomePasta);

            int linhaNum = 0;
            boolean primeiraLinha = true;
            boolean primeiraInvalida = true;

            switch (nomePasta.getName()) {
                case "paises.csv": // se o id for maior de 700 if
                    listaPaises = new ArrayList<Pais>();
                    listaPaisesInvalidos = new InputInvalido("paises.csv",0,0,-1);
                    while (scanner.hasNext()){
                        String linha = scanner.nextLine();
                        if (primeiraLinha) {
                            primeiraLinha = false;
                        } else {
                            String[] partes = linha.split(",");
                            if (partes[0] == "" || partes[1] == "" || partes[2] == "" ||
                                    partes[3] == "" || partes.length != 4) {
                                if (primeiraInvalida) {
                                    listaPaisesInvalidos.primeiraLinhaIncorreta = linhaNum;
                                    primeiraInvalida = false;
                                }
                                linhaNum++;
                                listaPaisesInvalidos.linhasIncorretas ++;

                            } else {
                                Pais pais = new Pais(Integer.parseInt(partes[0]),partes[1].toUpperCase(),partes[2].toUpperCase(),partes[3]);
                                listaPaises.add(pais);
                                linhaNum++;
                                listaPaisesInvalidos.linhasCorretas ++;
                            }
                        }
                    }
                    break;

                case "populacao.csv":
                    listaPopulacao = new ArrayList<Populacao>();
                    listaPopulacaoInvalidos = new InputInvalido("populacao.csv",0,0,-1);
                    while (scanner.hasNext()){
                        String linha = scanner.nextLine();
                        if (primeiraLinha) {
                            primeiraLinha = false;
                        } else {
                            String[] partes = linha.split(",");
                            if (partes[0] == "" || partes[1] == "" || partes[2] == "" ||
                                    partes[3] == "" || partes[4] == "" || partes.length != 5 || Objects.equals(partes[1], "Medium")) {
                                if (primeiraInvalida) {
                                    listaPopulacaoInvalidos.primeiraLinhaIncorreta = linhaNum;
                                    primeiraInvalida = false;
                                }
                                linhaNum++;
                                listaPopulacaoInvalidos.linhasIncorretas ++;
                            } else {
                                Populacao populacao = new Populacao(Integer.parseInt(partes[0]),Integer.parseInt(partes[1]),
                                        Integer.parseInt(partes[2]),Integer.parseInt(partes[3]),Double.parseDouble(partes[4]));
                                listaPopulacao.add(populacao);
                                linhaNum++;
                                listaPopulacaoInvalidos.linhasCorretas ++;
                            }
                        }
                    }
                    break;

                case "cidades.csv":
                    listaCidades = new ArrayList<Cidade>();
                    listaCidadesInvalidos = new InputInvalido("cidades.csv",0,0,-1);
                    while (scanner.hasNext()){
                        String linha = scanner.nextLine();
                        if (primeiraLinha) {
                            primeiraLinha = false;
                        } else {
                            String[] partes = linha.split(",");
                            if (partes[0] == "" || partes[1] == "" || partes[2] == "" ||
                                    partes[3] == "" || partes[4] == "" || partes[5] == "" || partes.length != 6) {
                                if (primeiraInvalida) {
                                    listaCidadesInvalidos.primeiraLinhaIncorreta = linhaNum;
                                    primeiraInvalida = false;
                                }
                                linhaNum ++;
                                listaCidadesInvalidos.linhasIncorretas ++;
                            } else {
                                Cidade cidade = new Cidade(partes[0].toUpperCase(),partes[1],partes[2],(int)Double.parseDouble(partes[3]),
                                        Double.parseDouble(partes[4]),Double.parseDouble(partes[5]));
                                linhaNum++;
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

    public static ArrayList getObjects(TipoEntidade entidade) {
        switch (entidade) {
            case PAIS:
                return listaPaises;

            case CIDADE:
                return listaCidades;

            case INPUT_INVALIDO:
                ArrayList inputsInvalidos = new ArrayList<>(3);
                inputsInvalidos.add(listaPaisesInvalidos);
                inputsInvalidos.add(listaCidadesInvalidos);
                inputsInvalidos.add(listaPopulacaoInvalidos);
                return inputsInvalidos;

            case null, default:
                return new ArrayList<>();
        }
    }

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        boolean paisesOK = parseFiles(new File("paises.csv"));
        boolean cidadesOK = parseFiles(new File("cidades.csv"));
        boolean populacaoOK = parseFiles(new File("populacao.csv"));
        long end = System.currentTimeMillis();
        if (!paisesOK || !cidadesOK || !populacaoOK) {
            System.out.println("Erro na leitura dos ficheiros");
        } else {
            System.out.println("Ficheiros lidos com sucesso em " + (end-start) + "ms");
        }
        System.out.println(getObjects(TipoEntidade.INPUT_INVALIDO));
    }
}
