package pt.ulusofona.aed.deisiworldmeter;

public class EstatisticaFicheiro {
        String nomeFicheiro ;
        int numLinhasValidas = 0;
        int numLinhasInvalidas = 0;
        Integer posicaoPrimeiraLinhaInvalida = -1;

    public EstatisticaFicheiro(String nomeFicheiro, int numLinhasValidas, int numLinhasIUnvalidas, int posicaoPrimeiraLinhaInvalida) {
        this.nomeFicheiro = nomeFicheiro;
        this.numLinhasValidas = numLinhasValidas;
        this.numLinhasInvalidas = numLinhasIUnvalidas;
        this.posicaoPrimeiraLinhaInvalida = posicaoPrimeiraLinhaInvalida;
    }

    public EstatisticaFicheiro(String nomeFicheiro) {
        this.nomeFicheiro = nomeFicheiro;
    }

    public String getNomeFicheiro() {
        return nomeFicheiro;
    }

    public void setNomeFicheiro(String nomeFicheiro) {
        this.nomeFicheiro = nomeFicheiro;
    }

    public int getNumLinhasValidas() {
        return numLinhasValidas;
    }

    public void setNumLinhasValidas(int numLinhasValidas) {
        this.numLinhasValidas = numLinhasValidas;
    }

    public int getNumLinhasInvalidas() {
        return numLinhasInvalidas;
    }

    public void setNumLinhasInvalidas(int numLinhasInvalidas) {
        this.numLinhasInvalidas = numLinhasInvalidas;
    }

    public Integer getPosicaoPrimeiraLinhaInvalida() {
        return posicaoPrimeiraLinhaInvalida;
    }

    public void setPosicaoPrimeiraLinhaInvalida(Integer posicaoPrimeiraLinhaInvalida) {
        this.posicaoPrimeiraLinhaInvalida = posicaoPrimeiraLinhaInvalida;
    }

    @Override
    public String toString() {
        return nomeFicheiro + " | " + numLinhasValidas + " | " + numLinhasInvalidas + " | " + posicaoPrimeiraLinhaInvalida;
    }
}
