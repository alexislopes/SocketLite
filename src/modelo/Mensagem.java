package modelo;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Mensagem implements Serializable {
   private String operacao;
   private Status status;
   Map<String, Object> params;

   public Mensagem(String operacao){
       this.operacao = operacao;
       params = new HashMap<>();
   }

    public String getOperacao() {
        return operacao;
    }

    public void setOperacao(String operacao) {
        this.operacao = operacao;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Object getParam(String chave) {
        return params.get(chave);
    }

    public void setParam(String chave, Object valor) {
        params.put(chave, valor);
    }

    @Override
    public String toString(){
       String m = "Operação: " + operacao;
             m += "\nStatus: " + status;
             m += "\nParametros:";

       for(String chave : params.keySet()){
           m += "\n\t" + chave + ":" + " " + params.get(chave);
       }

       return m;
    }
}
