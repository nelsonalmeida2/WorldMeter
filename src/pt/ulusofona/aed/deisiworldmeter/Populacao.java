package pt.ulusofona.aed.deisiworldmeter;

public class Populacao {
    int id;
    int ano;
    int populacaomasculina;
    int populacaofeminina;
    Double densidade;

    public Populacao() {
    }

    public Populacao(int id, int ano, int populacaomasculina, int populacaofeminina, Double densidade) {
        this.id = id;
        this.ano = ano;
        this.populacaomasculina = populacaomasculina;
        this.populacaofeminina = populacaofeminina;
        this.densidade = densidade;
    }

}
