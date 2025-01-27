package pt.ulusofona.aed.deisiworldmeter;

import java.util.*;

public class Commands {
    public static Result countCities(int numPopulacao) {
        int countCidades = 0;
        for (Cidade c : Main.listaCidades) {
            if (c.getPopulacao() >= numPopulacao) {
                countCidades++;
            }
        }
        return new Result(true, null, String.valueOf(countCidades));
    }

    public static Result getCitiesByCountry(int numResults, String paisNome) {
        //Exemplo:
        //Input
        //5 Portugal
        //Lisboa
        //Carcavelos
        //...
        //Nota: tem quebra de linha
        //caso passarmos um pais que nao existe no ficheiro ele dá
        //INPUT
        //5 Espanha
        //OUTPUT
        //"Pais invalido: Espanha"
        for (Pais p : Main.listaPaises) {
            if (p.getNome().equals(paisNome)) {
                StringBuilder resultBuilder = new StringBuilder();
                for (Cidade cidade : p.cidades) {
                    resultBuilder.append(cidade.getNome()).append("\n");
                    if (--numResults == 0) {
                        break;
                    }
                }
                //resultBuilder.append("\n");
                return new Result(true, null, resultBuilder.toString()/*.trim()*/);
            }
        }
        return new Result(true, null, "Pais invalido: " + paisNome);
    }

    public static Result sumPopulations(String[] nomePaises) {
        Result resultado = new Result();
        String[] nomePaisesCalcular = nomePaises[1].split(",");
        ArrayList<Integer> ids = new ArrayList<Integer>();
        int populacaoSomada = 0;
        for (int i = 0; i < nomePaisesCalcular.length; i++) { // vai precorrer a lista de nomes de paises
            Integer id = null;
            for (int j = 0; j < Main.listaPaises.size(); j++) { // vai precorrer a lista paises para enconttrar o id do pais

                if (nomePaisesCalcular[i].equals(Main.listaPaises.get(j).getNome())) { // compara o nome do pais com o nome da lista para encontrar o id
                    id = Integer.parseInt(String.valueOf(Main.listaPaises.get(j).getId()));
                    ids.add(id);
                    break;
                }

            }
            if (id == null) {
                resultado.success = true;
                resultado.error = null;
                resultado.result = "Pais invalido: " + nomePaisesCalcular[i];
                return resultado;
            }
        }


        for (int i = 0; i < ids.size(); i++) {

            for (int j = 0; j < Main.listaPopulacao.size(); j++) {

                if (Main.listaPopulacao.get(j).getAno() == 2024 && Main.listaPopulacao.get(j).getPais().getId() == ids.get(i)) {
                    populacaoSomada += Main.listaPopulacao.get(j).getPopulacaoFeminina() + Main.listaPopulacao.get(j).getPopulacaoMasculina(); // adiciona a pop mas
                }

            }

        }

        resultado.success = true;
        resultado.setError(null);
        resultado.setResult(String.valueOf(populacaoSomada));

        return resultado;

    }

    public static Result getHistory(Integer anoStart, Integer anoEnd, String paisNome) {

        //diz qual a pop mas e fem no intervalo desses anos anoStart até anoEnd desse pais
        //Exemplo:
        //INPUT
        //GET_HISTORY 2016 2020 Portugal
        //OUTPUT
        //2016:4046k:4370k
        //2017:4071k:4400k
        //...
        //Nota: tem quebra de linha entre os anos e o k é minusculo

        if (anoEnd >= anoStart) {
            ArrayList<Populacao> listaPopulacao = Main.listaPopulacao;
            StringBuilder resultado = new StringBuilder();
            Pais paisEncontrado = null;
            for (Pais pais : Main.listaPaises) {
                if (pais.getNome().equals(paisNome)) {
                    paisEncontrado = pais;
                    break;
                }
            }
            if (paisEncontrado != null) {
                int idPaisNomePopulacao = paisEncontrado.id;
                for (int ano = anoStart; ano <= anoEnd; ano++) {
                    boolean dadosDisponiveis = false;
                    String popMasculina = "";
                    String popFeminina = "";
                    for (Populacao populacao : listaPopulacao) {
                        if (populacao.getPais().getId() == idPaisNomePopulacao && populacao.getAno() == ano) {
                            int populacaoMasc = populacao.getPopulacaoMasculina();
                            int populacaoFem = populacao.getPopulacaoFeminina();
                            popMasculina = (populacaoMasc / 1000) + "k";
                            popFeminina = (populacaoFem / 1000) + "k";
                            dadosDisponiveis = true;
                            break;
                        }
                    }
                    if (dadosDisponiveis) {
                        resultado.append(ano).append(":").append(popMasculina).append(":").append(popFeminina).append("\n");
                    }
                }
                return new Result(true, null, resultado.toString());
            }
        }
        return new Result(true, null, "Pais invalido: " + paisNome);

    }

    public static Result getMissingHistory(Integer anoStart, Integer anoEnd) {
        Map<Integer, Set<Integer>> anosPorPais = new HashMap<>();
        Set<String> paisesComDadosAFaltar = new HashSet<>();

        // Preenche o mapa com os anos de população disponíveis para cada país
        for (Populacao populacao : Main.listaPopulacao) {
            int idPais = populacao.getPais().getId();
            int ano = populacao.getAno();

            // Adiciona o ano ao conjunto de anos para o país correspondente
            anosPorPais.computeIfAbsent(idPais, k -> new HashSet<>()).add(ano);
        }

        // Itera por todos os países
        for (Pais pais : Main.listaPaises) {
            int idPais = pais.getId();
            String alfa2 = pais.getAlfa2();
            String nomePais = pais.getNome();

            // Verifica se todos os anos no intervalo especificado estão presentes no conjunto de anos do país
            boolean faltaInformacao = false;
            for (int ano = anoStart; ano <= anoEnd; ano++) {
                Set<Integer> anosDoPais = anosPorPais.get(idPais);
                if (anosDoPais == null || !anosDoPais.contains(ano)) {
                    faltaInformacao = true;
                    break;
                }
            }

            // Se falta informação, adiciona o país ao conjunto
            if (faltaInformacao) {
                paisesComDadosAFaltar.add(alfa2.toLowerCase() + ":" + nomePais);
            }
        }

        // Se nenhum país tem dados a faltar, retorna "Sem resultados"
        if (paisesComDadosAFaltar.isEmpty()) {
            return new Result(true, null, "Sem resultados");
        }

        // Constrói o resultado com os países que têm dados faltando
        StringBuilder resultado = new StringBuilder();
        for (String pais : paisesComDadosAFaltar) {
            resultado.append(pais).append("\n");
        }

        return new Result(true, null, resultado.toString());
    }

    public static Result getMostPopulous(int numResults) {
        // Lista para armazenar as cidades mais populosas
        // Mapa para armazenar a cidade mais populosa de cada país
        HashMap<Pais, Cidade> cidadesMaisPopulosasPorPais = new HashMap<>();

        // Itera sobre a lista de cidades para encontrar a cidade mais populosa de cada país
        for (Cidade cidade : Main.listaCidades) {
            Pais pais = cidade.getPais();
            if (!cidadesMaisPopulosasPorPais.containsKey(pais) ||
                    cidade.getPopulacao() > cidadesMaisPopulosasPorPais.get(pais).getPopulacao()) {
                cidadesMaisPopulosasPorPais.put(pais, cidade);
            }
        }

        // Converte os valores do mapa para uma lista
        List<Cidade> cidadesMaisPopulosas = new ArrayList<>(cidadesMaisPopulosasPorPais.values());

        // Ordena a lista de cidades por ordem decrescente de população
        cidadesMaisPopulosas.sort((cidade1, cidade2) -> Integer.compare(cidade2.getPopulacao(), cidade1.getPopulacao()));

        // Constrói o resultado com as cidades mais populosas
        StringBuilder topCidades = new StringBuilder();
        int count = 0;
        for (Cidade cidade : cidadesMaisPopulosas) {
            if (count >= numResults) {
                break; // Limita o número de resultados
            }
            topCidades.append(cidade.getPais().getNome())
                    .append(":")
                    .append(cidade.getNome())
                    .append(":")
                    .append(cidade.getPopulacao())
                    .append("\n");
            count++;
        }

        return new Result(true, null, topCidades.toString());
    }

    public static Result getLeastPopulous(int numResults) {
        // Lista para armazenar as cidades menos populosas
        // Mapa para armazenar a cidade menos populosa de cada país
        HashMap<Pais, Cidade> cidadesMenosPopulosasPorPais = new HashMap<>();

        // Itera sobre a lista de cidades para encontrar a cidade menos populosa de cada país
        for (Cidade cidade : Main.listaCidades) {
            Pais pais = cidade.getPais();
            if (!cidadesMenosPopulosasPorPais.containsKey(pais) ||
                    cidade.getPopulacao() < cidadesMenosPopulosasPorPais.get(pais).getPopulacao()) {
                cidadesMenosPopulosasPorPais.put(pais, cidade);
            }
        }

        // Converte os valores do mapa para uma lista
        List<Cidade> cidadesMenosPopulosas = new ArrayList<>(cidadesMenosPopulosasPorPais.values());

        // Ordena a lista de cidades por ordem crescente de população
        cidadesMenosPopulosas.sort(Comparator.comparingInt(Cidade::getPopulacao));

        // Constrói o resultado com as cidades menos populosas
        StringBuilder leastPopulousCities = new StringBuilder();
        int count = 0;
        for (Cidade cidade : cidadesMenosPopulosas) {
            if (count >= numResults) {
                break; // Limita o número de resultados
            }
            leastPopulousCities.append(cidade.getPais().getNome())
                    .append(":")
                    .append(cidade.getNome())
                    .append(":")
                    .append(cidade.getPopulacao())
                    .append("\n");
            count++;
        }

        return new Result(true, null, leastPopulousCities.toString());
    }

    public static Result getTopCitiesByCountry(int numResults, String nomePais) {
        //Esta função dá as cidades mais populosas de um certo pais por ordem crescente
        //Se aparecer um cidade que tem a mesma populacao o desempate é feito por ordem alfabetica, ou seja fica por cima
        //A cidade que tem a letra que vem primeiro no alfabeto
        //Além disso se quisermos todas as cidades desse país passamos -1, ou seja,

        //INPUT Exemplo do -1:
        //GET_TOP_CITIES_BY_COUNTRY -1 Portugal
        //Vai aparecer todas as cidades que tiverem no ficheiro por ordem crescente populacao e se houver empates
        //Por ordem alfabetica
        //INPUT Exemplo normal:
        //GET_TOP_CITIES_BY_COUNTRY 5 Portugal
        //setubal:117k
        //queluz:103k
        //almada:34k
        //...
        //Nota: O k aqui é MAIÚSCULO
        // Verifica se o país existe e coleta as cidades do país
        // Verifica se o país existe e coleta as cidades do país
        ArrayList<Cidade> cidadesPretendidas = new ArrayList<>();
        for (Pais pais : Main.listaPaises) {
            if (pais.getNome().equalsIgnoreCase(nomePais)) { // Comparação com alfa2 do país
                cidadesPretendidas.addAll(pais.getCidades());
                break;
            }
        }

        // Ordena as cidades por população (decrescente) e depois por nome (alfabético)
        cidadesPretendidas.sort((c1, c2) -> {
            // Comparação da população
            int populacaoComparison = Long.compare(c2.getPopulacao() / 1000, c1.getPopulacao() / 1000);
            // Se a população for igual, compara os nomes das cidades
            if (populacaoComparison == 0) {
                return c1.getNome().compareToIgnoreCase(c2.getNome());
            }
            // Caso contrário, retorna a comparação da população
            return populacaoComparison;
        });

        // Constrói a string com as cidades mais populosas
        StringBuilder topCidades = new StringBuilder();
        int count = 0;
        for (Cidade cidade : cidadesPretendidas) {
            // Constrói o resultado somente para cidades com população > 10000
            if (cidade.getPopulacao() > 10000) {
                topCidades.append(cidade.getNome())
                        .append(":")
                        .append(cidade.getPopulacao() / 1000)
                        .append("K")
                        .append("\n");
                count++;
                // Verifica o limite de resultados se numResults não for -1
                if (numResults != -1 && count >= numResults) {
                    break;
                }
            }
        }

        // Verifica se não há resultados
        if (topCidades.isEmpty()) {
            return new Result(true, null, "Sem resultados");
        }

        // Retorna o resultado
        return new Result(true, null, topCidades.toString().trim() + "\n");
    }

    public static Result getDuplicateCities(int minPopulacao) {
        // Map para armazenar cidades originais
        HashMap<String, Cidade> cidadesOriginais = new HashMap<>();
        // Lista para armazenar cidades duplicadas
        List<Cidade> cidadesDuplicadas = new ArrayList<>();

        // Itera sobre todas as cidades na lista de cidades
        for (Cidade cidade : Main.listaCidades) {
            // Verifica se a população da cidade é maior ou igual à população mínima especificada
            if (cidade.getPopulacao() >= minPopulacao) {
                String nomeCidade = cidade.getNome(); // Obtém o nome da cidade

                // Verifica se o nome da cidade já foi encontrado antes
                if (cidadesOriginais.containsKey(nomeCidade)) {
                    // Adiciona a cidade duplicada à lista de duplicados
                    cidadesDuplicadas.add(cidade);
                } else {
                    // Se o nome da cidade ainda não foi encontrado, adiciona-o ao mapa de originais
                    cidadesOriginais.put(nomeCidade, cidade);
                }
            }
        }

        // Se não houver duplicatas, retorna "Sem resultados"
        if (cidadesDuplicadas.isEmpty()) {
            return new Result(true, null, "Sem resultados");
        }

        // StringBuilder para construir o resultado final
        StringBuilder resultado = new StringBuilder();

        // Formata as cidades duplicadas na saída desejada
        for (Cidade cidade : cidadesDuplicadas) {
            resultado.append(cidade.getNome()) // Nome da cidade
                    .append(" (")
                    .append(cidade.getPais().getNome()) // Nome do país
                    .append(",")
                    .append(cidade.getRegiao()) // Região da cidade
                    .append(")\n");
        }

        // Retorna o resultado com as cidades duplicadas formatadas
        return new Result(true, null, resultado.toString());
    }

    public static Result getCountriesGenderGap(int genderGap) {
        //Esta função é para ver a discrepancia no ano de 2024
        //Para isso tem de se usar uma fórmula que é o gender population imbalance
        //gender pop imbalance = absoluto(populacao_mascu-populacao_femin)/populacao_mascu+populacao_femin * 100
        //No numerador o que interessa é a diferenca entre eles, ou seja o absoluto.. É sempre positivo
        //O return é passado com 2 casas decimais mas o utilizador passa como int e apresenta os paises
        //INPUT
        //GET_COUNTRIES_GENDER_GAP 0
        //OUTPUT
        //Brasil:1.85
        //Portugal:4.21
        //Angola:1.11
        //Cabo Verde:0.57
        //Guiné_Bissau:1.13
        //Ele limita-se a mostrar os países que têm um gender gap igual ou superior a 0 neste caso especifico
        //se por alguma razão não houver resultados
        //OUTPUT
        //"Sem resultados"
        Map<Pais, Populacao> populacao2024PorPais = new HashMap<>();
        StringBuilder resultado = new StringBuilder();

        // Construir índice para o ano 2024
        for (Populacao populacao : Main.listaPopulacao) {
            if (populacao.getAno() == 2024) {
                populacao2024PorPais.put(populacao.getPais(), populacao);
            }
        }

        boolean hasResults = false;
        for (Pais pais : populacao2024PorPais.keySet()) {
            Populacao populacao2024 = populacao2024PorPais.get(pais);
            double genderPopImbalance = Math.abs((populacao2024.getPopulacaoMasculina() - populacao2024.getPopulacaoFeminina()) * 1.0 / (populacao2024.getPopulacaoMasculina() + populacao2024.getPopulacaoFeminina()) * 100);

            if (genderPopImbalance >= genderGap) {
                resultado.append(pais.getNome()).append(":").append(String.format("%.2f", genderPopImbalance)).append("\n");
                hasResults = true;
            }
        }

        if (!hasResults) {
            return new Result(true, null, "Sem resultados");
        }

        return new Result(true, null, resultado.toString());
    }

    public static Populacao getPopulacaoPorAnoPais(int ano, Pais pais) {
        for (Populacao populacao : Main.listaPopulacao) {
            if (populacao.getAno() == ano && populacao.getPais().equals(pais)) {
                return populacao;
            }
        }
        return null;
    }

    public static Result getTopPopulationIncrease(Integer anoStart, Integer anoEnd) {
        //Calcula o aumento da população dentro de um intervalo de anos relativamente a nivel mundial
        //Ou seja quais foram os paises que mais aumentaram a sua populacao nesse intervalo de anos
        //populacao_total = populacao_mascu + populacao_femin
        // se populacao_total(ano) - populacao_total(ano_anterior) for < 0 pode-se descartar logo porque não é aumento
        // caso contrário
        //populacao_total(ano) - populacao_total(ano_anterior) / populacao_total(ano) * 100
        //valor final é apresentado por casas decimais
        //Nota aquele ano anterior não é necessariamente apenas 1 ano para trás
        //O melhor é começar com caso mais simples ou seja de 1 ano de diferença
        //Podemos ter varios intervalos por exemplo: 2020-2024 temos de 2020-2021 2021-2022... etc
        //Podemos nao ter sempre por exemplo o intervalo de 2020-2021 ou 2022-2023 pois pode não haver esse ano do pais
        // Verifica se o ano de término é maior ou igual ao ano de início
        // Verifica se o ano de término é maior ou igual ao ano de início
        if (anoEnd < anoStart) {
            return new Result(true, null, "Sem resultados");
        }

        // Mapa para armazenar a população por país e ano
        Map<String, Map<Integer, Populacao>> populacaoPorPaisEAno = new HashMap<>();

        for (Populacao populacao : Main.listaPopulacao) {
            String nomePais = populacao.getPais().getNome();
            int ano = populacao.getAno();

            populacaoPorPaisEAno
                    .computeIfAbsent(nomePais, k -> new HashMap<>())
                    .put(ano, populacao);
        }

        List<AumentoPopulacional> aumentosPercentuais = new ArrayList<>();

        for (Pais pais : Main.listaPaises) {
            String nomePais = pais.getNome();
            Map<Integer, Populacao> populacaoPorAno = populacaoPorPaisEAno.get(nomePais);
            if (populacaoPorAno == null) {
                continue;
            }

            for (int startYear = anoStart; startYear < anoEnd; startYear++) {
                Populacao populacaoInicio = populacaoPorAno.get(startYear);
                if (populacaoInicio == null) {
                    continue;
                }

                int populacaoTotalInicio = populacaoInicio.getPopulacaoMasculina() + populacaoInicio.getPopulacaoFeminina();

                for (int endYear = startYear + 1; endYear <= anoEnd; endYear++) {
                    Populacao populacaoFim = populacaoPorAno.get(endYear);
                    if (populacaoFim == null) {
                        continue;
                    }

                    int populacaoTotalFim = populacaoFim.getPopulacaoMasculina() + populacaoFim.getPopulacaoFeminina();
                    int aumentoPopulacional = populacaoTotalFim - populacaoTotalInicio;

                    if (aumentoPopulacional > 0) {
                        double aumentoPercentual = ((double) aumentoPopulacional / populacaoTotalFim) * 100;
                        aumentosPercentuais.add(new AumentoPopulacional(nomePais, startYear, endYear, aumentoPercentual));
                    }
                }
            }
        }

        aumentosPercentuais.sort((a1, a2) -> Double.compare(a2.aumentoPercentual, a1.aumentoPercentual));

        StringBuilder resultado = new StringBuilder();
        Set<String> paisesIntervalos = new HashSet<>();
        int count = 0;

        for (AumentoPopulacional aumento : aumentosPercentuais) {
            String paisIntervalo = aumento.pais + ":" + aumento.startYear + "-" + aumento.endYear;
            if (!paisesIntervalos.contains(paisIntervalo)) {
                paisesIntervalos.add(paisIntervalo);
                resultado.append(aumento.pais).append(":").append(aumento.startYear).append("-").append(aumento.endYear)
                        .append(":").append(String.format("%.2f", aumento.aumentoPercentual)).append("%\n");
                count++;
                if (count >= 5) {
                    break;
                }
            }
        }

        if (resultado.isEmpty()) {
            return new Result(true, null, "Sem resultados");
        }
        return new Result(true, null, resultado.toString().trim() + "\n");
    }

    // Classe auxiliar para armazenar os dados do aumento populacional
    static class AumentoPopulacional {
        String pais;
        int startYear;
        int endYear;
        double aumentoPercentual;

        AumentoPopulacional(String pais, int startYear, int endYear, double aumentoPercentual) {
            this.pais = pais;
            this.startYear = startYear;
            this.endYear = endYear;
            this.aumentoPercentual = aumentoPercentual;
        }
    }

    public static Result getDuplicateCitiesDifferentCountries(int numPopulacao) {
        if (numPopulacao <= 0) {
            return new Result(true, null, "Sem resultados");
        } else {
            HashMap<String, String> cidadesMaioresQueNumPopulacao = new HashMap<>();
            for (Pais pais : Main.listaPaises) {
                HashSet<Cidade> cidadesPais = new HashSet<>(); // Para não haver duplicados do mesmo país
                for (Cidade cidade : pais.cidades) {
                    if (cidade.populacao >= numPopulacao) { // só adiciona ao HashMap a cidades com população maior OU IGUAL que numPopulacao
                        cidadesPais.add(cidade);
                    }
                }
                for (Cidade cidade : cidadesPais) { // ciclo para ver se a cidade já existe
                    if (cidadesMaioresQueNumPopulacao.containsKey(cidade.nome)) { // se existir vai adicionar ao valor o nome do outro país
                        String valorAtual = cidadesMaioresQueNumPopulacao.get(cidade.nome);
                        if (!valorAtual.contains(cidade.pais.nome)) { // verifica se o país já foi adicionado
                            cidadesMaioresQueNumPopulacao.put(cidade.nome, valorAtual + "," + cidade.pais.nome);
                        }
                    } else {
                        cidadesMaioresQueNumPopulacao.put(cidade.nome, cidade.pais.nome);
                    }
                }
            }

            StringBuilder resultado = new StringBuilder();
            for (Map.Entry<String, String> cidade : cidadesMaioresQueNumPopulacao.entrySet()) {
                String[] paises = cidade.getValue().split(",");
                if (paises.length > 1) {
                    //Collator collator = Collator.getInstance(new Locale("pt", "BR"));
                    //collator.setStrength(Collator.PRIMARY);
                    //Arrays.sort(paises, collator);
                    Arrays.sort(paises);

                    resultado.append(cidade.getKey()).append(": ");
                    for (int i = 0; i < paises.length; i++) {
                        resultado.append(paises[i]);
                        if (i < paises.length - 1) {
                            resultado.append(",");
                        }
                    }
                    resultado.append("\n");
                }
            }
            return new Result(true, null, resultado.toString());
        }
    }

    public static double calculateHaversineDistance(double lat1, double lon1, double lat2, double lon2) {
        final int EARTH_RADIUS = 6371; // Radius of the Earth in kilometers

        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);

        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                        Math.sin(dLon / 2) * Math.sin(dLon / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return EARTH_RADIUS * c;
    }

    public static Result getCitiesAtDistance1(int distance, String nomePais) {
        boolean paisExiste = false;
        ArrayList<Cidade> cidadesPretendidas = new ArrayList<>();

        for (Pais pais : Main.listaPaises) {
            if (pais.getNome().equals(nomePais)) {
                cidadesPretendidas.addAll(pais.cidades);
                paisExiste = true;
                break;  // Exit loop early once the country is found
            }
        }

        if (!paisExiste) {
            return new Result(true, null, "Pais invalido: " + nomePais);
        } else {
            HashSet<String> cidadesDistanciaPedida = new HashSet<>();
            for (int i = 0; i < cidadesPretendidas.size(); i++) {
                for (int j = i + 1; j < cidadesPretendidas.size(); j++) {
                    StringBuilder distanciaDasCidades = new StringBuilder();
                    double distanciaEntreCidades = calculateHaversineDistance(
                            cidadesPretendidas.get(i).latitude, cidadesPretendidas.get(i).longitude,
                            cidadesPretendidas.get(j).latitude, cidadesPretendidas.get(j).longitude);
                    if (distanciaEntreCidades > distance - 1 && distanciaEntreCidades < distance + 1) {
                        int comparaAlfabeticamente = cidadesPretendidas.get(i).nome.compareToIgnoreCase(cidadesPretendidas.get(j).nome);
                        if (comparaAlfabeticamente < 0) {
                            distanciaDasCidades.append(cidadesPretendidas.get(i).nome)
                                    .append("->")
                                    .append(cidadesPretendidas.get(j).nome);
                        } else {
                            distanciaDasCidades.append(cidadesPretendidas.get(j).nome)
                                    .append("->")
                                    .append(cidadesPretendidas.get(i).nome);
                        }
                        cidadesDistanciaPedida.add(distanciaDasCidades.toString());
                    }
                }
            }

            List<String> sortedList = new ArrayList<>(cidadesDistanciaPedida);
            sortedList.sort(String::compareToIgnoreCase);

            StringBuilder success = new StringBuilder();
            for (String elemento : sortedList) {
                success.append(elemento).append("\n");
            }
            return new Result(true, null, success.toString());
        }
    }

    public static Result getCitiesAtDistance2(int distance, String nomePais) {
        /*
        boolean paisExiste = false;
        ArrayList<Cidade> cidadesPretendidas = new ArrayList<>();
        String alfa2NomePais = "";

        for (Pais pais : Main.listaPaises) {
            if (pais.getNome().equals(nomePais)) {
                cidadesPretendidas.addAll(pais.cidades);
                alfa2NomePais = pais.getAlfa2();
                paisExiste = true;
                break;  // Exit loop early once the country is found
            }
        }

        if (!paisExiste) {
            return new Result(true, null, "Pais invalido: " + nomePais);
        } else {
            HashSet<String> cidadesDistanciaPedida = new HashSet<>();
            for (int i = 0; i < cidadesPretendidas.size(); i++) {
                for (Cidade cidade : Main.listaCidades) {
                    if (cidade.pais.alfa2.equals(alfa2NomePais)) {
                        continue; // Skip cities with the same country code
                    }
                    StringBuilder distanciaDasCidades = new StringBuilder();
                    Double distanciaEntreCidades = calculateHaversineDistance(
                            cidadesPretendidas.get(i).latitude, cidadesPretendidas.get(i).longitude,
                            cidade.latitude, cidade.longitude);
                    if (distanciaEntreCidades > distance - 1 && distanciaEntreCidades < distance + 1) {
                        int comparaAlfabeticamente = cidadesPretendidas.get(i).nome.compareToIgnoreCase(cidade.nome);
                        if (comparaAlfabeticamente < 0) {
                            distanciaDasCidades.append(cidadesPretendidas.get(i).nome)
                                    .append("->")
                                    .append(cidade.nome);
                        } else {
                            distanciaDasCidades.append(cidade.nome)
                                    .append("->")
                                    .append(cidadesPretendidas.get(i).nome);
                        }
                        cidadesDistanciaPedida.add(distanciaDasCidades.toString());
                    }
                }
            }

            List<String> sortedList = new ArrayList<>(cidadesDistanciaPedida);
            sortedList.sort(String::compareToIgnoreCase);

            StringBuilder success = new StringBuilder();
            for (String elemento : sortedList) {
                success.append(elemento).append("\n");
            }
            return new Result(true, null, success.toString());
        }
        */

        Pais paisAlvo = null;
        for (Pais pais : Main.listaPaises) {
            if (pais.getNome().equalsIgnoreCase(nomePais)) {
                paisAlvo = pais;
                break;
            }
        }

        // Verificar se o país foi encontrado
        if (paisAlvo == null) {
            return new Result(true, null, "Pais invalido: " + nomePais);
        }

        // Obter as cidades do país alvo
        ArrayList<Cidade> cidadesPretendidas = paisAlvo.getCidades();
        String alfa2NomePais = paisAlvo.getAlfa2();

        // Inicializar o conjunto para armazenar as cidades encontradas dentro da distância especificada
        HashSet<String> cidadesDistanciaPedida = new HashSet<>();

        // Pré-calcular as coordenadas dos limites do quadrado bruto
        double distanceInDegrees = distance / 111.0; // Aproximação, 1 grau é aproximadamente 111 km
        double minLat = Double.MAX_VALUE, maxLat = -Double.MAX_VALUE;
        double minLon = Double.MAX_VALUE, maxLon = -Double.MAX_VALUE;
        for (Cidade cidade : cidadesPretendidas) {
            double lat = cidade.getLatitude();
            double lon = cidade.getLongitude();
            minLat = Math.min(minLat, lat - distanceInDegrees);
            maxLat = Math.max(maxLat, lat + distanceInDegrees);
            minLon = Math.min(minLon, lon - distanceInDegrees);
            maxLon = Math.max(maxLon, lon + distanceInDegrees);
        }

        // Iterar sobre todas as cidades
        for (Cidade cidade : Main.listaCidades) {
            // Verificar se a cidade está dentro do quadrado bruto
            double lat = cidade.getLatitude();
            double lon = cidade.getLongitude();
            if (lat < minLat || lat > maxLat || lon < minLon || lon > maxLon) {
                continue; // A cidade está fora do quadrado bruto, ignorar
            }

            // Verificar se a cidade é do mesmo país
            if (cidade.getPais().getAlfa2().equals(alfa2NomePais)) {
                continue; // Ignorar cidades com o mesmo código de país
            }

            // Verificar a distância entre as cidades usando a fórmula de Haversine
            for (Cidade cidadePretendida : cidadesPretendidas) {
                double distanciaEntreCidades = calculateHaversineDistance(
                        cidadePretendida.getLatitude(), cidadePretendida.getLongitude(),
                        lat, lon);
                if (distanciaEntreCidades >= distance - 1 && distanciaEntreCidades <= distance + 1) {
                    // Construir a string representando a relação de distância entre as cidades
                    String[] cidades = {cidadePretendida.getNome(), cidade.getNome()};
                    Arrays.sort(cidades);
                    String distanciaDasCidades = cidades[0] + "->" + cidades[1];

                    // Adicionar a relação de distância ao conjunto
                    cidadesDistanciaPedida.add(distanciaDasCidades);
                }
            }
        }

        // Ordenar o conjunto e construir a string de sucesso
        //List<String> sortedList = new ArrayList<>(cidadesDistanciaPedida);
        int i=0;
        String[] sortedList = new String[cidadesDistanciaPedida.size()];
        for (String ele: cidadesDistanciaPedida) {
            sortedList[i++] = ele;
        }

        Arrays.sort(sortedList);

        StringBuilder success = new StringBuilder();
        for (String elemento : sortedList) {
            success.append(elemento).append("\n");
        }

        

        // Retornar o resultado
        return new Result(true, null, success.toString());
    }
    public static Result insertCity(String alfa2, String nomeCidade, String regiao, int populacao) {

        Result result = new Result();
        boolean inserido = false;
        for (Pais pais : Main.listaPaises) { // Precorre o array lista paises e se o pais existir insere
            if (alfa2.equalsIgnoreCase(pais.getAlfa2())) {
                Cidade novaCidade = new Cidade(pais, nomeCidade, regiao, populacao, 0.0, 0.0);
                Main.listaCidades.add(novaCidade); // Insere no lista cidades
                pais.getCidades().add(novaCidade); // Insere no arraylist de cidades que esta dentro do elemento da lista paises
                inserido = true;
                break;
            }
        }
        if (!inserido) { // se nÃ£o foi inseiro quer dizer que o pais Ã© invalido
            result.success = true;
            result.result = "Pais invalido";
            result.error = null;
            return result;

        } else {
            result.success = true;
            result.result = "Inserido com sucesso";
            result.error = null;
            return result;
        }
        /*Result result = new Result();
        boolean inserido = false;
        for (int i = 0; i < Main.listaPaises.size(); i++) { // Precorre o array lista paises e se o pais existir insere
            if (alfa2.equals(Main.listaPaises.get(i).alfa2.toLowerCase())) {
                Main.listaCidades.add(new Cidade(Main.listaPaises.get(i),nomeCidade,regiao,populacao)); // insere no lista cidades
                Main.listaPaises.get(i).cidades.add(new Cidade(Main.listaPaises.get(i),nomeCidade,regiao,populacao)); // isere no arraylist de cidades que esta dentro do elemento da lista paises
                inserido = true;
                break;
            }
        }
        if (!inserido) { // se nÃ£o foi inseiro quer dizer que o pais Ã© invalido
            result.success = true;
            result.result = "Pais invalido";
            result.error = null;
            return result;

        } else {
            result.success = true;
            result.result = "Inserido com sucesso";
            result.error = null;
            return result;
        }*/
    }

    public static Result removeCountry(String paisARemover) {
        ArrayList<Cidade> cidadesARemover = new ArrayList<>();
        boolean removidoComSucesso = false;

        Pais paisEncontrado = null;

        // Encontrar o país a ser removido
        for (Pais pais : Main.listaPaises) {
            if (pais.getNome().equals(paisARemover)) {
                paisEncontrado = pais;
                break;
            }
        }

        // Se o país for encontrado, marcar cidades para remoção
        if (paisEncontrado != null) {
            String alfa2paisRemover = paisEncontrado.alfa2;
            for (Cidade cidade : Main.listaCidades) {
                if (cidade.pais.alfa2.equalsIgnoreCase(alfa2paisRemover)) {
                    cidadesARemover.add(cidade);
                }
            }

            // Remover o país da lista de países
            Main.listaPaises.remove(paisEncontrado);

            // Remover as cidades associadas da lista de cidades
            Main.listaCidades.removeAll(cidadesARemover);

            // Remover as entradas correspondentes na lista de populações
            Iterator<Populacao> iterator = Main.listaPopulacao.iterator();
            while (iterator.hasNext()) {
                Populacao populacao = iterator.next();
                if (populacao.getPais().getId() == paisEncontrado.getId()) {
                    iterator.remove();
                }
            }

            removidoComSucesso = true;
        }

        if (removidoComSucesso) {
            return new Result(true, null, "Removido com sucesso");
        } else {
            return new Result(true, null, "Pais invalido");
        }
    }

    public static String help() {
        return ("-------------------------\n" +
                "Commands available:\n" +
                "COUNT_CITIES <min_population>\n" +
                "GET_CITIES_BY_COUNTRY <num-results> <country-name>\n" +
                "SUM_POPULATIONS <countries-list>\n" +
                "GET_HISTORY <year-start> <year-end> <country_name>\n" +
                "GET_MISSING_HISTORY <year-start> <year-end>\n" +
                "GET_MOST_POPULOUS <num-results>\n" +
                "GET_LEAST_POPULOUS <num-results>\n" +
                "GET_TOP_CITIES_BY_COUNTRY <num-results> <country-name>\n" +
                "GET_DUPLICATE_CITIES <min_population>\n" +
                "GET_COUNTRIES_GENDER_GAP <min-gender-gap>\n" +
                "GET_TOP_POPULATION_INCREASE <year-start> <year_end>\n" +
                "GET_DUPLICATE_CITIES_DIFFERENT_COUNTRIES <min-population>\n" +
                "GET_CITIES_AT_DISTANCE <distance> <country-name>\n" +
                "GET_CITIES_AT_DISTANCE2 <distance> <country-name>\n" +
                "INSERT_CITY <alfa2> <city-name> <region> <population>\n" +
                "REMOVE_COUNTRY <country-name>\n" +
                "HELP\n" +
                "QUIT\n" +
                "-------------------------\n");
    }

    public static void quit() {
        return;
    }

}

