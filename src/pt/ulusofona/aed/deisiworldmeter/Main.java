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

        try { // tenta ler o ficheiro pais senão retorna falso e não le mais nenhum
            File filePais = new File(nomePasta,"paises.csv");
            Scanner scannerPais = new Scanner(filePais);
            int linhaNum = 0;
            boolean primeiraLinha = true;
            boolean primeiraInvalida = true;
            listaPaises = new ArrayList<Pais>();
            listaPaisesInvalidos = new InputInvalido("paises.csv",0,0,-1);
            while (scannerPais.hasNext()){
                String linha = scannerPais.nextLine();

                if (primeiraLinha) {
                    primeiraLinha = false;
                } else {
                    String[] partes = linha.split(",");
                    if (partes.length != 4) {
                        if (primeiraInvalida) {
                            listaPaisesInvalidos.primeiraLinhaIncorreta = linhaNum;
                            primeiraInvalida = false;
                        }
                        listaPaisesInvalidos.linhasIncorretas ++;
                    } else if (partes[0] == "" || partes[1] == "" || partes[2] == "" ||
                            partes[3] == "") {
                        if (primeiraInvalida) {
                            listaPaisesInvalidos.primeiraLinhaIncorreta = linhaNum;
                            primeiraInvalida = false;
                        }
                        listaPaisesInvalidos.linhasIncorretas ++;

                    } else {
                        boolean repetido = false;
                        for (Pais pais : listaPaises) {
                            if (pais.id == Integer.parseInt(partes[0])) {
                                repetido = true;
                                break; // Sai do ciclo assim que encontra repetido
                            }
                        }
                        if (repetido) {
                            if (primeiraInvalida) {
                                listaPaisesInvalidos.primeiraLinhaIncorreta = linhaNum;
                                primeiraInvalida = false;
                            }
                            listaPaisesInvalidos.linhasIncorretas ++;
                        } else {
                            if (Integer.parseInt(partes[0]) >= 700) {
                                Pais pais = new Pais(Integer.parseInt(partes[0]),partes[1].toUpperCase(),partes[2].toUpperCase(),partes[3],0);
                                listaPaises.add(pais);
                                listaPaisesInvalidos.linhasCorretas ++;
                            }else {
                                Pais pais = new Pais(Integer.parseInt(partes[0]),partes[1].toUpperCase(),partes[2].toUpperCase(),partes[3]);
                                listaPaises.add(pais);
                                listaPaisesInvalidos.linhasCorretas ++;
                            }
                        }
                    }
                    linhaNum++;
                }
            }
        } catch (FileNotFoundException e) {
            return false;
        }

        try { // tenta ler o cidade pais senão retorna falso e não le mais nenhum
            File fileCidades = new File(nomePasta,"cidades.csv");
            Scanner scannerCidades = new Scanner(fileCidades);
            int linhaNum = 0;
            boolean primeiraLinha = true;
            boolean primeiraInvalida = true;
            listaCidades = new ArrayList<Cidade>();
            listaCidadesInvalidos = new InputInvalido("cidades.csv",0,0,-1);
            while (scannerCidades.hasNext()){
                String linha = scannerCidades.nextLine();
                if (primeiraLinha) {
                    primeiraLinha = false;
                } else {
                    String[] partes = linha.split(",");
                    if (partes.length != 6) {
                        if (primeiraInvalida) {
                            listaCidadesInvalidos.primeiraLinhaIncorreta = linhaNum;
                            primeiraInvalida = false;
                        }
                        listaCidadesInvalidos.linhasIncorretas ++;
                    }else if (partes[0] == "" || partes[1] == "" || partes[2] == "" ||
                            partes[3] == "" || partes[4] == "" || partes[5] == "") {
                        if (primeiraInvalida) {
                            listaCidadesInvalidos.primeiraLinhaIncorreta = linhaNum;
                            primeiraInvalida = false;
                        }
                        listaCidadesInvalidos.linhasIncorretas ++;
                    } else {
                        boolean paisExiste = false;
                            for (Pais pais : listaPaises) {
                                if (partes[0].toUpperCase().equals(pais.alfa2)) {
                                    paisExiste = true;
                                    break;
                                }
                        }
                        if (!paisExiste) {
                            if (primeiraInvalida) {
                                listaCidadesInvalidos.primeiraLinhaIncorreta = linhaNum;
                                primeiraInvalida = false;
                            }
                            listaCidadesInvalidos.linhasIncorretas ++;
                        } else {
                            Cidade cidade = new Cidade(partes[0].toUpperCase(),partes[1],partes[2],(int)Double.parseDouble(partes[3]),
                                    Double.parseDouble(partes[4]),Double.parseDouble(partes[5]));
                            listaCidades.add(cidade);
                            listaCidadesInvalidos.linhasCorretas ++;
                        }
                    }
                    linhaNum++;
                }
            }
        } catch (FileNotFoundException e) {
            return false;
        }

        try { // tenta ler o ficheiro populacao senão retorna falso
            File filePopulacao = new File(nomePasta,"populacao.csv");
            Scanner scannerPopulacao = new Scanner(filePopulacao);
            int linhaNum = 0;
            boolean primeiraLinha = true;
            boolean primeiraInvalida = true;
            listaPopulacao = new ArrayList<Populacao>();
            listaPopulacaoInvalidos = new InputInvalido("populacao.csv",0,0,-1);
            while (scannerPopulacao.hasNext()){
                String linha = scannerPopulacao.nextLine();
                if (primeiraLinha) {
                    primeiraLinha = false;
                } else {
                    String[] partes = linha.split(",");
                    if (partes.length != 5) {
                        if (primeiraInvalida) {
                            listaPopulacaoInvalidos.primeiraLinhaIncorreta = linhaNum;
                            primeiraInvalida = false;
                        }
                        listaPopulacaoInvalidos.linhasIncorretas ++;
                    }else if (partes[0] == "" || partes[1] == "" || partes[2] == "" ||
                            partes[3] == "" || partes[4] == "" || Objects.equals(partes[1], "Medium")) {
                        if (primeiraInvalida) {
                            listaPopulacaoInvalidos.primeiraLinhaIncorreta = linhaNum;
                            primeiraInvalida = false;
                        }
                        listaPopulacaoInvalidos.linhasIncorretas ++;
                    } else {
                        boolean paisExiste = false;
                        for (Pais pais: listaPaises) {
                            if (Integer.parseInt(partes[0]) == pais.id) {
                                paisExiste = true;
                                if (pais.id >= 700) {
                                    pais.indicadoresEstatisticos ++;
                                }
                                break;
                            }
                        }
                        if (!paisExiste) {
                            if (primeiraInvalida) {
                                listaPopulacaoInvalidos.primeiraLinhaIncorreta = linhaNum;
                                primeiraInvalida = false;
                            }
                            listaPopulacaoInvalidos.linhasIncorretas ++;
                        } else {
                            Populacao populacao = new Populacao(Integer.parseInt(partes[0]),Integer.parseInt(partes[1]),
                                    Integer.parseInt(partes[2]),Integer.parseInt(partes[3]),Double.parseDouble(partes[4]));
                            listaPopulacao.add(populacao);
                            listaPopulacaoInvalidos.linhasCorretas ++;
                        }
                    }
                    linhaNum++;
                }
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

            default:
                return new ArrayList<>();
        }
    }

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        boolean parseOK = parseFiles(new File("C:\\Users\\nelso\\IdeaProjects\\WorldMeter"));
        long end = System.currentTimeMillis();
        if (!parseOK) {
            System.out.println("Erro na leitura dos ficheiros");
        } else {
            System.out.println("Ficheiros lidos com sucesso em " + (end-start) + "ms");
        }
        System.out.println(getObjects(TipoEntidade.PAIS));

    }
}
