package pt.ulusofona.aed.deisiworldmeter;

public class InputInvalido {
    String nomeFicheiro;
    int linhasCorretas;
    int linhasIncorretas;
    int primeiraLinhaIncorreta;

    public InputInvalido() {
    }

    public InputInvalido(String nomeFicheiro, int linhasCorretas, int linhasIncorretas, int primeiraLinhaIncorreta) {
        this.nomeFicheiro = nomeFicheiro;
        this.linhasCorretas = linhasCorretas;
        this.linhasIncorretas = linhasIncorretas;
        this.primeiraLinhaIncorreta = primeiraLinhaIncorreta;
    }

    @Override
    public String toString() {
        return nomeFicheiro + " | " + linhasCorretas + " | " + linhasIncorretas + " | " + primeiraLinhaIncorreta;
    }
}
