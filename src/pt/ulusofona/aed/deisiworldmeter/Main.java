package pt.ulusofona.aed.deisiworldmeter;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Main {

    public static void main(String[] args) {
        System.out.println("Welcome to DEISI World Meter");

        long start = System.currentTimeMillis();

        boolean parseOK = parseFiles(new File("test-my-files"));

        if (!parseOK) {
            System.out.println("Error loading files");
            return;
        }
        long end = System.currentTimeMillis();

        System.out.println("Loaded files in " + (end-start) + " ms");

        //System.out.println(getObjects(TipoEntidade.INPUT_INVALIDO));

        Result result = execute("HELP");

        System.out.println(result.result);

        Scanner in = new Scanner(System.in);

        String line = null;

        do {
            System.out.print("> ");
            line = in.nextLine(); // le o input no inicio do ciclo

            if (line != null && !line.equals("QUIT")) {

                start = System.currentTimeMillis();

                result = execute(line);

                end = System.currentTimeMillis();


                if (!result.success) {
                    System.out.println("Error: " + result.error);
                } else {
                    System.out.println(result.result);
                    System.out.println("(took " + (end-start) + " ms)");
                }
            }
        }while (line != null && !line.equals("QUIT"));
    }

    public static ArrayList<Pais> listaPaises = new ArrayList<>();
    public static ArrayList<Cidade> listaCidades = new ArrayList<>();
    public static ArrayList<Populacao> listaPopulacao = new ArrayList<>();

    public static EstatisticaFicheiro estatisticaPaises = new EstatisticaFicheiro("paises.csv");
    public static EstatisticaFicheiro estatisticaCidades = new EstatisticaFicheiro("cidades.csv");
    public static EstatisticaFicheiro estatisticaPopulacao = new EstatisticaFicheiro("populacao.csv");

    static final String COUNT_CITIES = "COUNT_CITIES";
    static final String GET_CITIES_BY_COUNTRY = "GET_CITIES_BY_COUNTRY";
    static final String SUM_POPULATIONS = "SUM_POPULATIONS";
    static final String GET_HISTORY = "GET_HISTORY";
    static final String GET_MISSING_HISTORY = "GET_MISSING_HISTORY";
    static final String GET_MOST_POPULOUS = "GET_MOST_POPULOUS";
    static final String GET_LEAST_POPULOUS = "GET_LEAST_POPULOUS";
    static final String GET_TOP_CITIES_BY_COUNTRY = "GET_TOP_CITIES_BY_COUNTRY";
    static final String GET_DUPLICATE_CITIES = "GET_DUPLICATE_CITIES";
    static final String GET_COUNTRIES_GENDER_GAP = "GET_COUNTRIES_GENDER_GAP";
    static final String GET_TOP_POPULATION_INCREASE = "GET_TOP_POPULATION_INCREASE";
    static final String GET_DUPLICATE_CITIES_DIFFERENT_COUNTRIES = "GET_DUPLICATE_CITIES_DIFFERENT_COUNTRIES";
    static final String GET_CITIES_AT_DISTANCE = "GET_CITIES_AT_DISTANCE";
    static final String GET_CITIES_AT_DISTANCE2 = "GET_CITIES_AT_DISTANCE2";
    static final String INSERT_CITY = "INSERT_CITY";
    static final String REMOVE_COUNTRY = "REMOVE_COUNTRY";
    static final String HELP = "HELP";
    static final String QUIT = "QUIT";

    public static void adicionarCidadePais(Cidade cidade) {
        for (int i = 0; i < listaPaises.size(); i++) {
            String paisaf2 = listaPaises.get(i).alfa2;
            String cidadeaf2 = cidade.pais.alfa2;
            if (cidadeaf2 == paisaf2) {
                listaPaises.get(i).adicionarCidade(cidade);
                return;
            }
        }
    }

    public static void limparEstatisticas() {
        estatisticaPaises.posicaoPrimeiraLinhaInvalida = -1;
        estatisticaPaises.numLinhasInvalidas = 0;
        estatisticaPaises.numLinhasValidas = 0;
        estatisticaCidades.posicaoPrimeiraLinhaInvalida = -1;
        estatisticaCidades.numLinhasInvalidas = 0;
        estatisticaCidades.numLinhasValidas = 0;
        estatisticaPopulacao.posicaoPrimeiraLinhaInvalida = -1;
        estatisticaPopulacao.numLinhasInvalidas = 0;
        estatisticaPopulacao.numLinhasValidas = 0;
    }

    public static void limpaPaisesSemCidades() {
        boolean primeiraInvalida = false;
        if (!primeiraInvalida) {
            for (int i = 0; i < listaPaises.size(); i++) {
                if (listaPaises.get(i).cidades.isEmpty()) {
                    estatisticaPaises.posicaoPrimeiraLinhaInvalida = i + 2; // devido ao ficheiro começar na posicao 1 e devido a saltar o cabeçalho
                    primeiraInvalida = true;
                    break;
                }
            }
        }
        Iterator<Pais> iterator = listaPaises.iterator();
        while (iterator.hasNext()) {
            Pais pais = iterator.next();
            if (pais.cidades == null || pais.cidades.isEmpty()) {
                iterator.remove();
                estatisticaPaises.numLinhasValidas -= 1;
                estatisticaPaises.numLinhasInvalidas ++;
            }
        }
    }

    public static void limpaPopulacaoSemPais() {
        HashSet<Integer> paises = new HashSet<>();
        for (int i = 0; i < listaPaises.size(); i++) {
            paises.add(listaPaises.get(i).id);
        }

        boolean primeiraInvalida = false;

        for (int i = 0; i < listaPopulacao.size(); i++) {
            if (!paises.contains(listaPopulacao.get(i).pais.id)) {
                if (!primeiraInvalida) {
                    if (i < estatisticaPopulacao.posicaoPrimeiraLinhaInvalida) {
                        estatisticaPopulacao.posicaoPrimeiraLinhaInvalida = i + 2;
                    }
                    primeiraInvalida = true;
                }
                estatisticaPopulacao.numLinhasInvalidas++;
            }
        }

        Iterator<Populacao> iterator = listaPopulacao.iterator();
        while (iterator.hasNext()) {
            Populacao populacao = iterator.next();
            if (!paises.contains(populacao.pais.id)) {
                iterator.remove();
                estatisticaPopulacao.numLinhasValidas--;
            }
        }
    }

    public static void lePais(Scanner scannerPaises) {
        int countNumLinha = 1;
        boolean linhaValidaOuNaoPaises = false;
        while (scannerPaises.hasNextLine()) {
            String[] partes = scannerPaises.nextLine().split(",");
            countNumLinha++;
            if (partes.length != 4) {
                estatisticaPaises.setNumLinhasInvalidas(estatisticaPaises.getNumLinhasInvalidas() + 1);
                if (estatisticaPaises.getPosicaoPrimeiraLinhaInvalida() == -1) {
                    System.out.println("1========> " + Arrays.toString(partes));
                    estatisticaPaises.setPosicaoPrimeiraLinhaInvalida(countNumLinha);
                }
                continue;
            }
            for (String parte : partes) {
                if (parte == null || parte.isEmpty()) {
                    linhaValidaOuNaoPaises = true;
                    break;
                }
            }

            if (linhaValidaOuNaoPaises) {
                if (estatisticaPaises.getPosicaoPrimeiraLinhaInvalida() == -1) {
                    System.out.println("2========> " + Arrays.toString(partes));
                    estatisticaPaises.setPosicaoPrimeiraLinhaInvalida(countNumLinha);
                }
                linhaValidaOuNaoPaises = false;

                estatisticaPaises.setNumLinhasInvalidas(estatisticaPaises.getNumLinhasInvalidas() + 1);
                continue;
            }
            int id;
            String alfa2;
            String alfa3;
            String nomePais;
            try {
                id = Integer.parseInt(partes[0]);
                alfa2 = partes[1].toUpperCase();
                alfa3 = partes[2].toUpperCase();
                nomePais = partes[3];
            } catch (NumberFormatException e) {
                if (estatisticaPaises.getPosicaoPrimeiraLinhaInvalida() == -1) {
                    System.out.println("3========> " + Arrays.toString(partes));

                    estatisticaPaises.setPosicaoPrimeiraLinhaInvalida(countNumLinha);
                }
                estatisticaPaises.setNumLinhasInvalidas(estatisticaPaises.getNumLinhasInvalidas() + 1);

                continue;
            }
            //Para cada pais da lista paises ou seja ele vai percorrer os paises todos que tem atualmente adicionado
            // na listaPaises e verifica se essa linha (esse pais) é igual ao id
            for (Pais pais : listaPaises) {
                if (id == pais.id) {
                    linhaValidaOuNaoPaises = true;
                    break;
                }
            }
            // caso encontrar o linhaValidaPaises não adiciona e passa para a próxima linha onde vai ler essa próxima linha
            // isto é para não termos países repetidos no caso
            if (linhaValidaOuNaoPaises) {
                if (estatisticaPaises.getPosicaoPrimeiraLinhaInvalida() == -1) {
                    estatisticaPaises.setPosicaoPrimeiraLinhaInvalida(countNumLinha);
                }
                estatisticaPaises.setNumLinhasInvalidas(estatisticaPaises.getNumLinhasInvalidas() + 1);
                linhaValidaOuNaoPaises = false;
                continue;
            }

            estatisticaPaises.setNumLinhasValidas(estatisticaPaises.getNumLinhasValidas() + 1);

            listaPaises.add(new Pais(id, alfa2, alfa3, nomePais));
        }
    }

    public static void leCidades(Scanner scannerCidades) {
        int countNumLinha = 1;
        boolean elementoCidades = false;
        //int countErros = 0;
        while (scannerCidades.hasNextLine()) {
            countNumLinha++;
            String[] partes = scannerCidades.nextLine().split(",");
            if (partes.length != 6) {
                estatisticaCidades.setNumLinhasInvalidas(estatisticaCidades.getNumLinhasInvalidas() + 1);
                if (estatisticaCidades.getPosicaoPrimeiraLinhaInvalida() == -1) {
                    estatisticaCidades.setPosicaoPrimeiraLinhaInvalida(countNumLinha);
                }

                continue;
            }

            for (int i = 0; i < partes.length; i++) {
                if (i != 1 && (partes[i] == null || partes[i].isEmpty())) {
                    elementoCidades = true;
                    break;
                }
            }

            if (elementoCidades) {
                if (estatisticaCidades.getPosicaoPrimeiraLinhaInvalida() == -1) {
                    estatisticaCidades.setPosicaoPrimeiraLinhaInvalida(countNumLinha);
                }
                elementoCidades = false;
                estatisticaCidades.setNumLinhasInvalidas(estatisticaCidades.getNumLinhasInvalidas() + 1);
                continue;
            }
            String alfa2Cidade = "";
            Pais paisCidade = null;
            String nomeCidade = "";
            String regiao = "";
            double latitude = 0.0;
            double longitude = 0.0;
            int populacaoInt;
            try {//este try vai verificar se uma região é um numero ou nao
                alfa2Cidade = partes[0].toUpperCase();
                //Para cada pais na lista de paises ele verifica se o alfa2 de um certo pais da lista de paises (paises.csv) é igual
                //ao alfa2 do  dessa cidade que estamos a ver e atribui o pais correpondente a essa cidade
                for (Pais pais : listaPaises) {
                    if (pais.alfa2.equals(alfa2Cidade)) {
                        paisCidade = pais;
                        break;
                    }
                }
                nomeCidade = partes[1];
                regiao = partes[2];
                String populacaoString = partes[3];
                double populacaoDouble = Double.parseDouble(populacaoString);
                populacaoInt = (int) populacaoDouble;
                latitude = Double.parseDouble(partes[4]);
                longitude = Double.parseDouble(partes[5]);
            } catch (NumberFormatException e) {
                //countErros++;
                //System.out.println("------------------------------- ERRO INICIO---------------------------------");
                //System.out.println("NumeroErro : " + countErros);
                //System.out.println("NumLinha: " + countNumLinhaCidades);
                //StringBuilder linha = new StringBuilder();
                //for(String p : partes) {
                //    linha.append(p).append(" | ");
                //}
                //System.out.println(linha);
                //System.out.println("------------------------------- ERRO FIM---------------------------------");
                if (estatisticaCidades.getPosicaoPrimeiraLinhaInvalida() == -1) {
                    estatisticaCidades.setPosicaoPrimeiraLinhaInvalida(countNumLinha);
                }
                estatisticaCidades.setNumLinhasInvalidas(estatisticaCidades.getNumLinhasInvalidas() + 1);
                continue;
            }

            // podem existir linhas do ficheiro da cidades que mencionem paises que na realidade não existem (no ficheiro paises.csv)
            if (paisCidade == null) {
                if (estatisticaCidades.getPosicaoPrimeiraLinhaInvalida() == -1) {
                    estatisticaCidades.setPosicaoPrimeiraLinhaInvalida(countNumLinha);
                }
                estatisticaCidades.setNumLinhasInvalidas(estatisticaCidades.getNumLinhasInvalidas() + 1);
                continue;
            }

            estatisticaCidades.setNumLinhasValidas(estatisticaCidades.getNumLinhasValidas() + 1);

            listaCidades.add(new Cidade(paisCidade, nomeCidade, regiao, populacaoInt, latitude, longitude));

            adicionarCidadePais(new Cidade(paisCidade, nomeCidade, regiao, populacaoInt, latitude, longitude));

        }
    }

    public static Cidade findCityByName(String nome){
        for (Cidade c : listaCidades){
            if (Objects.equals(c.getNome(), nome)){
                return c;
            }
        }
        return null;
    }

    public static void lePopulacao (Scanner scannerPopulacao) {
        int countNumLinha = 1;
        boolean elementoPopulacao = false;
        while (scannerPopulacao.hasNextLine()) {
            //linha começa a 1 que supostamente é sempre o cabeçalho e ele vai começar
            // a ler a partir da próxima linha (que é a linha 2 já)  onde divide divide em partes por virgula
            //exemplo
            //id,alfa2,alfa3,nome linha 1
            //4,af,afg,Afeganistão linha 2
            String[] partes = scannerPopulacao.nextLine().split(",");
            countNumLinha++;

            // tratamento de linhas inválidas
            if (partes.length != 5) {
                if (estatisticaPopulacao.getPosicaoPrimeiraLinhaInvalida() == -1) {
                    estatisticaPopulacao.setPosicaoPrimeiraLinhaInvalida(countNumLinha);
                }

                estatisticaPopulacao.setNumLinhasInvalidas(estatisticaPopulacao.getNumLinhasInvalidas() + 1);
                continue;
            }

            for (String parte : partes) {
                if (parte == null || parte.isEmpty()) {
                    elementoPopulacao = true;
                    break;
                }
            }
            if (elementoPopulacao) {
                if (estatisticaPopulacao.getPosicaoPrimeiraLinhaInvalida() == -1) {
                    estatisticaPopulacao.setPosicaoPrimeiraLinhaInvalida(countNumLinha);
                }
                elementoPopulacao = false;

                estatisticaPopulacao.setNumLinhasInvalidas(estatisticaPopulacao.getNumLinhasInvalidas() + 1);
                continue;
            }

            int id = 0;
            Pais paisPopulacao = null;
            int ano = 0;
            int populacaoMasculina = 0;
            int populacaoFeminina = 0;
            double densidade = 0;

            try {//este try vai verificar se uma região é um numero ou nao
                id = Integer.parseInt(partes[0]);
                ano = Integer.parseInt(partes[1]);
                populacaoMasculina = Integer.parseInt(partes[2]);
                populacaoFeminina = Integer.parseInt(partes[3]);
                densidade = Double.parseDouble(partes[4]);
            } catch (NumberFormatException e) {
                if (estatisticaPopulacao.getPosicaoPrimeiraLinhaInvalida() == -1) {
                    estatisticaPopulacao.setPosicaoPrimeiraLinhaInvalida(countNumLinha);
                }

                estatisticaPopulacao.setNumLinhasInvalidas(estatisticaPopulacao.getNumLinhasInvalidas() + 1);
                continue;
            }
            //Para cada pais na lista de paises ele verifica se o id pais da lista de paises (paises.csv) é igual
            //ao id do ficheiro da populacao que estamos a ver e incrementa o tanto numero de ficheiros que encontrar
            // esse id dependendo do tamanho do ID (maior que 700) mostra no toString do pais o tanto de numero de linhas
            // de populacao que foram lidas
            for (Pais pais : listaPaises) {
                if (pais.id == id) {
                    paisPopulacao = pais;
                    pais.numPopulacoes++;
                    break;
                }
            }

            // podem existir linhas do ficheiro da população que mencionem paises que na realidade não existem (no ficheiro paises.csv)
            if (paisPopulacao == null) {
                if (estatisticaPopulacao.getPosicaoPrimeiraLinhaInvalida() == -1) {
                    estatisticaPopulacao.setPosicaoPrimeiraLinhaInvalida(countNumLinha);
                }
                estatisticaPopulacao.setNumLinhasInvalidas(estatisticaPopulacao.getNumLinhasInvalidas() + 1);
                continue;
            }

            listaPopulacao.add(new Populacao(paisPopulacao, ano, populacaoMasculina, populacaoFeminina, densidade));
            estatisticaPopulacao.setNumLinhasValidas(estatisticaPopulacao.getNumLinhasValidas() + 1);

        }
    }

    public static void saltaPrimeiraLinha (Scanner scannerPaises, Scanner scannerCidades, Scanner scannerPopulacao) {
        // Ignorar cabeçalho de todos os ficheiros
        scannerPaises.nextLine();
        scannerCidades.nextLine();
        scannerPopulacao.nextLine();
    }

    public static void limpaListas() {
        listaPaises.clear();
        listaCidades.clear();
        listaPopulacao.clear();
    }

    public static boolean parseFiles(File pasta) {
        // Criar os objetos File para os três arquivos
        // pasta é pasta no caso test-files onde se encontram os ficheiros csv
        // pasta é um parâmetro da função parseFiles, que já contém a referência para a pasta onde os arquivos estão localizados.

        limpaListas();

        limparEstatisticas();

        File filePaises = new File(pasta + "/paises.csv");
        File fileCidades = new File(pasta + "/cidades.csv");
        File filePopulacao = new File(pasta + "/populacao.csv");

        // Verificar se todos os arquivos existem
        try {
            // Tentar ler os arquivos
            Scanner scannerCidades = new Scanner(fileCidades);
            Scanner scannerPaises = new Scanner(filePaises);
            Scanner scannerPopulacao = new Scanner(filePopulacao);

            saltaPrimeiraLinha(scannerPaises,scannerCidades,scannerPopulacao);

            lePais(scannerPaises);
            leCidades(scannerCidades);
            lePopulacao(scannerPopulacao);

            //System.out.println(getObjects(TipoEntidade.PAIS));

            limpaPaisesSemCidades();
            limpaPopulacaoSemPais();

            //System.out.println(getObjects(TipoEntidade.PAIS));

            return true; // Retorna true se todos os arquivos foram lidos com sucesso
        } catch (FileNotFoundException e) {
            return false; // Retorna false se houver uma exceção ao tentar ler os arquivos
        }
    }

    public static ArrayList getObjects(TipoEntidade tipo) {
        switch (tipo) {
            case PAIS -> {
                return listaPaises;
            }
            case CIDADE -> {
                return listaCidades;
            }
            case INPUT_INVALIDO -> {
                // Retorna um array de três posições vazias
                ArrayList<Object> inputInvalido = new ArrayList<>();
                inputInvalido.add(estatisticaPaises);
                inputInvalido.add(estatisticaCidades);
                inputInvalido.add(estatisticaPopulacao);
                return inputInvalido;
            }

        }
        return null;
    }

    public static Result execute(String comando) {
        String[] comandoSplit = comando.split(" ");
        Result result = new Result();
        switch (comandoSplit[0]) {
            case COUNT_CITIES:
                int minPopulacao = Integer.parseInt(comandoSplit[1]);
                Result countResult = Commands.countCities(minPopulacao);
                result.setSuccess(countResult.isSuccess());
                result.setError(countResult.getError());
                result.setResult(countResult.getResult());
                break;
            case GET_CITIES_BY_COUNTRY:
                int numResults = Integer.parseInt(comandoSplit[1]);
                StringBuilder paisNomeCitiesByCountry = new StringBuilder();
                for (int i = 2; i < comandoSplit.length; i++){
                    paisNomeCitiesByCountry.append(comandoSplit[i]);
                    if (i < comandoSplit.length - 1) {
                        paisNomeCitiesByCountry.append(" ");
                    }
                }
                String nomePaisCityByCountry = paisNomeCitiesByCountry.toString();
                Result resultGetCities = Commands.getCitiesByCountry(numResults, nomePaisCityByCountry);
                result.setSuccess(resultGetCities.isSuccess());
                result.setError(resultGetCities.getError());
                result.setResult(resultGetCities.getResult());
                break;
            case SUM_POPULATIONS:
                String[] comandoSplitSum = comando.split(" ");
                StringBuilder nomePaisBuilderSum = new StringBuilder();
                for (int i = 1; i < comandoSplitSum.length; i++) {
                    nomePaisBuilderSum.append(comandoSplitSum[i]).append(" ");
                }
                String nomePaisSum = nomePaisBuilderSum.toString().trim();
                Result sumPop = Commands.sumPopulations(new String[]{"SUM_POPULATIONS", nomePaisSum});
                result.success = sumPop.success;
                result.result = sumPop.result;
                result.error = sumPop.error;
                break;
            case GET_HISTORY:
                int anoStart = Integer.parseInt(comandoSplit[1]);
                int anoEnd = Integer.parseInt(comandoSplit[2]);
                StringBuilder paisACalcularBuilder = new StringBuilder();
                for (int i = 3; i < comandoSplit.length; i++) {
                    paisACalcularBuilder.append(comandoSplit[i]);
                    if (i < comandoSplit.length - 1) {
                        paisACalcularBuilder.append(" ");
                    }
                }
                String paisACalcular = paisACalcularBuilder.toString();
                Result resultGetHistory = Commands.getHistory(anoStart, anoEnd, paisACalcular);
                result.success = resultGetHistory.success;
                result.error = resultGetHistory.error;
                result.result = resultGetHistory.result;
                break;
            case GET_MISSING_HISTORY:
                int anoStartMissingHistory = Integer.parseInt(comandoSplit[1]);
                int anoEndMissingHistory = Integer.parseInt(comandoSplit[2]);
                Result resultGetMssingHistory = Commands.getMissingHistory(anoStartMissingHistory,anoEndMissingHistory);
                result.success = resultGetMssingHistory.success;
                result.error = resultGetMssingHistory.error;
                result.result = resultGetMssingHistory.result;
                break;
            case GET_MOST_POPULOUS:
                int numCidades = Integer.parseInt(comandoSplit[1]);
                Result mostPopulous = Commands.getMostPopulous(numCidades);
                result.result = mostPopulous.result;
                result.error = mostPopulous.error;
                result.success = mostPopulous.success;
                break;
            case GET_LEAST_POPULOUS:
                int numCidades2 = Integer.parseInt(comandoSplit[1]);
                Result leastPopulous = Commands.getLeastPopulous(numCidades2);
                result.result = leastPopulous.result;
                result.error = leastPopulous.error;
                result.success = leastPopulous.success;
                break;
            case GET_TOP_CITIES_BY_COUNTRY:
                int numResultados = Integer.parseInt(comandoSplit[1]);
                StringBuilder nomePaisBuilder = new StringBuilder();
                for (int i = 2; i < comandoSplit.length; i++) {
                    nomePaisBuilder.append(comandoSplit[i]);
                    if (i < comandoSplit.length - 1) {
                        nomePaisBuilder.append(" ");
                    }
                }
                String nomePais = nomePaisBuilder.toString();
                Result topCitiesByCountry = Commands.getTopCitiesByCountry(numResultados, nomePais);
                result.result = topCitiesByCountry.result;
                result.error = topCitiesByCountry.error;
                result.success = topCitiesByCountry.success;
                break;

            case GET_DUPLICATE_CITIES:
                int numPop = Integer.parseInt(comandoSplit[1]);
                Result getDuplicateCities = Commands.getDuplicateCities(numPop);
                result.success = getDuplicateCities.success;
                result.error = getDuplicateCities.error;
                result.result = getDuplicateCities.result;
                break;
            case GET_COUNTRIES_GENDER_GAP:
                int minGenderGap = Integer.parseInt(comandoSplit[1]);
                Result genderGap = Commands.getCountriesGenderGap(minGenderGap);
                result.success = genderGap.success;
                result.error = genderGap.error;
                result.result = genderGap.result;
                break;
            case GET_TOP_POPULATION_INCREASE:
                int anoInicial = Integer.parseInt(comandoSplit[1]);
                int anoFinal = Integer.parseInt(comandoSplit[2]);
                Result populationIncrease = Commands.getTopPopulationIncrease(anoInicial,anoFinal);
                result.success = populationIncrease.success;
                result.error = populationIncrease.error;
                result.result = populationIncrease.result;
                break;
            case GET_DUPLICATE_CITIES_DIFFERENT_COUNTRIES:
                int numPopulation = Integer.parseInt(comandoSplit[1]);
                Result getDuplicateCitiesDifferentCountries = Commands.getDuplicateCitiesDifferentCountries(numPopulation);
                result.success = getDuplicateCitiesDifferentCountries.success;
                result.error = getDuplicateCitiesDifferentCountries.error;
                result.result = getDuplicateCitiesDifferentCountries.result;
                break;
            case GET_CITIES_AT_DISTANCE:
                int distance = Integer.parseInt(comandoSplit[1]);
                StringBuilder nomePaisDistanceBuilder = new StringBuilder();
                for (int i = 2; i < comandoSplit.length ; i++) {
                    nomePaisDistanceBuilder.append(comandoSplit[i]);
                    nomePaisDistanceBuilder.append(" ");
                }
                String nomePaisDistance = nomePaisDistanceBuilder.toString().trim();
                Result getCitiesAtDistance1 = Commands.getCitiesAtDistance1(distance,nomePaisDistance);
                result.success = getCitiesAtDistance1.success;
                result.error = getCitiesAtDistance1.error;
                result.result = getCitiesAtDistance1.result;
                break;
            case GET_CITIES_AT_DISTANCE2:
                int distance2 = Integer.parseInt(comandoSplit[1]);
                StringBuilder nomePaisDistanceBuilder2 = new StringBuilder();
                for (int i = 2; i < comandoSplit.length ; i++) {
                    nomePaisDistanceBuilder2.append(comandoSplit[i]);
                    nomePaisDistanceBuilder2.append(" ");
                }
                String nomePaisDistance2 = nomePaisDistanceBuilder2.toString().trim();
                Result getCitiesAtDistance2 = Commands.getCitiesAtDistance2(distance2,nomePaisDistance2);
                result.success = getCitiesAtDistance2.success;
                result.error = getCitiesAtDistance2.error;
                result.result = getCitiesAtDistance2.result;
                break;
            case INSERT_CITY:
                String alfa2 = comandoSplit[1];
                String nomeCidade = comandoSplit[2];
                String regiao = comandoSplit[3];
                int populacao = Integer.parseInt(comandoSplit[4]);
                Result inserir = Commands.insertCity(alfa2, nomeCidade, regiao, populacao);
                result.success = inserir.success;
                result.error = inserir.error;
                result.result = inserir.result;
                break;
            case REMOVE_COUNTRY:
                StringBuilder paisRemover = new StringBuilder();
                for (int i = 1; i < comandoSplit.length; i++) {
                    paisRemover.append(comandoSplit[i]);
                    if (i < comandoSplit.length - 1) {
                        paisRemover.append(" ");
                    }
                }
                String nomePaisRemover = paisRemover.toString();
                Result removeCountry = Commands.removeCountry(nomePaisRemover);
                result.success = removeCountry.success;
                result.error = removeCountry.error;
                result.result = removeCountry.result;
                break;
            case HELP:
                result.success = true;
                result.setError(null);
                result.setResult(Commands.help());
                break;
            case QUIT:
                break;
            default:
                result.setSuccess(false);
                result.setError("Comando invalido");
                result.setResult(null);

        }
        return result;
    }
}




