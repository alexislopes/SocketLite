package servidor;

//import modelo.Mensagem;
//import modelo.Status;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Servidor {

    // instanciando o objeto ServerSocket.
    private ServerSocket serverSocket;
    private String resposta;
    private ArrayList<Object> mensagem;


    // método que cria o server recebendo uma porta como parâmetro.
    private void criaServer(int porta) throws IOException {
        // inicializando a variável de criação do socket.
        serverSocket = new ServerSocket(porta);
    }

    // método que adquire conexão, retorna um socket.
    private  Socket esperaConexao() throws IOException {
        return serverSocket.accept();
    }

    private void trataConexao(Socket socket) throws IOException {

        try {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());

            System.out.println("Tratando...");

            while (true) {
                mensagem = (ArrayList<Object>) objectInputStream.readObject();
                System.out.println("Mensagem do cliente: \n\t" + mensagem);


                String operacao = (String) mensagem.get(0);

                if(operacao.equalsIgnoreCase("sair")) {
                    break;
                }

                Float op1 = (Float) mensagem.get(1);
                Float op2 = (Float) mensagem.get(2);

                switch (operacao) {
                    case "DIV":
                        divisao(op1, op2);
                        break;
                    case "SUB":
                        subtracao(op1, op2);
                        break;
                    case "MULT":
                        multiplicacao(op1, op2);
                        break;
                    case "SOMA":
                        soma(op1, op2);
                        break;
                    default:
                        defaultMessage();
                        break;
                }

                System.out.println("\nEnviando resposta...");
                objectOutputStream.writeObject(resposta);
                objectOutputStream.flush();
            }


            objectInputStream.close();
            objectOutputStream.close();

        } catch (IOException ioe) {
            System.out.println("Erro ao estabelecer conexão com o cliente: " + socket.getInetAddress());
            System.out.println("Erro: " + ioe);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            fechaServer(socket);
        }

    }

    private void divisao(Float op1, Float op2) {
        try {
            if (op2 == 0) {

                resposta = "Não é possível dividir por 0!";
            } else {

                Float divisao = op1 / op2;
                resposta = Float.toString(divisao);
            }

        } catch (Exception e) {
            resposta = "Erro nos parametros!";
        }
    }

    private void subtracao(Float op1, Float op2) {
        try {
            Float subtracao = op1 - op2;
            resposta = Float.toString(subtracao);

        } catch (Exception e) {
            resposta = "Erro nos parametros!";
        }
    }

    private void multiplicacao(Float op1, Float op2) {
        try {
            Float multiplicacao = op1 * op2;
            resposta = Float.toString(multiplicacao);

        } catch (Exception e) {
            resposta = "Erro nos parametros!";
        }
    }

    private void soma(Float op1, Float op2) {
        try {
            Float soma = op1 + op2;
            resposta = Float.toString(soma);

        } catch (Exception e) {
            resposta = "Erro nos parametros!";
        }
    }

   private void defaultMessage() {
        resposta = "Operação fora do padrão";
    }

    private void fechaServer(Socket socket) throws IOException {
        socket.close();
    }

    // método main para funcionar os métodos criados.
    public static void main(String[] args) throws IOException {
        // instanciando e inicializando a classe servidor.

        Servidor servidor = new Servidor();

        servidor.criaServer(5050);
        while (true) {
            System.out.println("Aguadando conexão...");
            Socket socket = servidor.esperaConexao();
            System.out.println("Cliente conectado: " + socket.getInetAddress());

            servidor.trataConexao(socket);
            System.out.println("Cliente finalizado\n");
        }

    }

}
