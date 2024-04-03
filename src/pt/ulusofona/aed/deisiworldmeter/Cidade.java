package pt.ulusofona.aed.deisiworldmeter;

public class Cidade {
    String alfa2;
    String cidade;
    int regiao;
    int populacao;
    Double latitude;
    Double longitude;

    public Cidade() {
    }

    public Cidade(String alfa2, String cidade, int regiao, int populacao, Double latitude, Double longitude) {
        this.alfa2 = alfa2;
        this.cidade = cidade;
        this.regiao = regiao;
        this.populacao = populacao;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    @Override
    public String toString() {
        return cidade + " | " + alfa2 + " | " + regiao + " | " + populacao + " | " + "(" + latitude + "," + longitude + ")";
    }
}
