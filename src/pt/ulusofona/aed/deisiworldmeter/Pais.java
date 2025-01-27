package pt.ulusofona.aed.deisiworldmeter;

import java.util.ArrayList;
import java.util.List;

public class Pais {
    int id;
    String nome;
    ArrayList<Cidade> cidades;
    String alfa2;
    String alfa3;
    int numPopulacoes = 0;

    public Pais(int id, String alfa2, String alfa3, String nome) {
        this.id = id;
        this.alfa2 = alfa2;
        this.alfa3 = alfa3;
        this.nome = nome;
        this.cidades = new ArrayList<>();
    }

    public String getNome() {
        return nome;
    }

    public int getId() {
        return id;
    }
    public void adicionarCidade(Cidade cidade){
        this.cidades.add(cidade);
    }
    public Pais() {
        this.cidades = new ArrayList<>();
    }

    public ArrayList<Cidade> getCidades() {
        return cidades;
    }

    public void setCidades(ArrayList<Cidade> cidades) {
        this.cidades = cidades;
    }
    public String getAlfa2(){
        return alfa2;
    }
    @Override
    public String toString() {
        if (id > 700) {
            return nome + " | " + id + " | " + alfa2 + " | " + alfa3 + " | " + numPopulacoes;

        }
        return nome + " | " + id + " | " + alfa2 + " | " + alfa3;
    }
}
