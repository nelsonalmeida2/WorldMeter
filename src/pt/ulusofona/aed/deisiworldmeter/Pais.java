package pt.ulusofona.aed.deisiworldmeter;

public class Pais {
    int id;
    String alfa2;
    String alfa3;
    String nome;
    int indicadoresEstatisticos;

    public Pais() {
    }

    public Pais(int id, String alfa2, String alfa3, String nome) {
        this.id = id;
        this.alfa2 = alfa2;
        this.alfa3 = alfa3;
        this.nome = nome;
    }

    public Pais(int id, String alfa2, String alfa3, String nome, int indicadoresEstatisticos) {
        this.id = id;
        this.alfa2 = alfa2;
        this.alfa3 = alfa3;
        this.nome = nome;
        this.indicadoresEstatisticos = indicadoresEstatisticos;
    }

    @Override
    public String toString() {
        if (id >= 700) {
            return nome + " | " + id + " | " + alfa2 + " | " + alfa3 + " | " + indicadoresEstatisticos;
        } else {
            return nome + " | " + id + " | " + alfa2 + " | " + alfa3;
        }

    }
}
