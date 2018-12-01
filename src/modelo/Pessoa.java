package modelo;

import com.google.gson.Gson;

import java.io.Serializable;

public class Pessoa implements Serializable {
    private String nome;
    private String nivel;

    public Pessoa(){}

    public Pessoa(String nome, String nivel){
        this.nome = nome;
        this.nivel = nivel;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNivel() {
        return nivel;
    }

    public void setNivel(String nivel) {
        this.nivel = nivel;
    }

    @Override
    public String toString() {
        return "Pessoa{" + "nome:'" + nome + "," + "nivel:" + nivel + "}";
    }
}
