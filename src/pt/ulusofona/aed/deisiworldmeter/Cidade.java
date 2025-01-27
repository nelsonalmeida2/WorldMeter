package pt.ulusofona.aed.deisiworldmeter;

public class Cidade {
    Pais pais;
    String nome;
    String regiao;
    int populacao;
    double latitude;
    double longitude;
    boolean original;

    public Cidade(Pais pais, String nome, String regiao, int populacao) {
        this.pais = pais;
        this.nome = nome;
        this.regiao = regiao;
        this.populacao = populacao;
    }

    public void setOriginal(boolean original) {
        this.original = original;
    }
    public Cidade(Pais pais, String nome, String regiao, int populacao, double latitude, double longitude) {
        this.pais = pais;
        this.nome = nome;
        this.regiao = regiao;
        this.populacao = populacao;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Cidade() {
    }

    public Pais getPais() {
        return pais;
    }

    public void setPais(Pais pais) {
        this.pais = pais;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getRegiao() {
        return regiao;
    }

    public void setRegiao(String regiao) {
        this.regiao = regiao;
    }

    public int getPopulacao() {
        return populacao;
    }

    public void setPopulacao(int populacao) {
        this.populacao = populacao;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }



    @Override
    public String toString() {
        return nome + " | " + pais.alfa2 + " | " + regiao + " | " + populacao + " | (" + latitude + "," + longitude + ")";
    }
}
