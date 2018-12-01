package modelo;

import java.io.Serializable;
import java.util.Date;

public class Mensagem implements Serializable {
    private Pessoa pessoa;
    private String conteudo;
    private Date datahora;


    public Mensagem(){}

    public Mensagem(Pessoa pessoa, String conteudo, Date datahora){
        this.pessoa = pessoa;
        this.conteudo = conteudo;
        this.datahora = datahora;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }

    public String getConteudo() {
        return conteudo;
    }

    public void setConteudo(String conteudo) {
        this.conteudo = conteudo;
    }

    public Date getDatahora() {
        return datahora;
    }

    public void setDatahora(Date datahora) {
        this.datahora = datahora;
    }

    @Override
    public String toString() {
        return datahora + " | " + pessoa.getNome() + "@" + pessoa.getNivel() + " diz: " + conteudo;
    }
}
