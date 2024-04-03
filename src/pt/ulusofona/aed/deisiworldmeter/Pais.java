package pt.ulusofona.aed.deisiworldmeter;

public class Pais {
    int id;
    String alfa2;
    String alfa3;
    String nome;

    public Pais() {
    }

    public Pais(int id, String alfa2, String alfa3, String nome) {
        this.id = id;
        this.alfa2 = alfa2;
        this.alfa3 = alfa3;
        this.nome = nome;
    }

    @Override
    public String toString() {
        return nome + " | " + id + " | " + alfa2 + " | " + alfa3;
    }
}
