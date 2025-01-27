package pt.ulusofona.aed.deisiworldmeter;

public class Populacao {
    Pais pais;
    Integer ano;
    int populacaoMasculina;
    int populacaoFeminina;
    double densidade;

    public Populacao(Pais pais, Integer ano, int populacaoMasculina, int populacaoFeminina, double densidade) {
        this.pais = pais;
        this.ano = ano;
        this.populacaoMasculina = populacaoMasculina;
        this.populacaoFeminina = populacaoFeminina;
        this.densidade = densidade;
    }

    public Populacao() {

    }

    public Pais getPais() {
        return pais;
    }

    public void setPais(Pais pais) {
        this.pais = pais;
    }

    public Integer getAno() {
        return ano;
    }

    public void setAno(Integer ano) {
        this.ano = ano;
    }

    public int getPopulacaoMasculina() {
        return populacaoMasculina;
    }

    public void setPopulacaoMasculina(int populacaoMasculina) {
        this.populacaoMasculina = populacaoMasculina;
    }

    public int getPopulacaoFeminina() {
        return populacaoFeminina;
    }

    public void setPopulacaoFeminina(int populacaoFeminina) {
        this.populacaoFeminina = populacaoFeminina;
    }

    public double getDensidade() {
        return densidade;
    }

    public void setDensidade(double densidade) {
        this.densidade = densidade;
    }

    @Override
    public String toString() {
        return pais.id + " | " + ano + " | " + populacaoMasculina + " | " + populacaoFeminina + " | " + densidade;
    }
}
