package servidor;

import modelo.Mensagem;
import modelo.Status;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor {

    // instanciando o objeto ServerSocket.
    private ServerSocket serverSocket;
    private Mensagem resposta;
    private Mensagem mensagem;
    private Status status;

    // método que cria o server recebendo uma porta como parâmetro.
    public void criaServer(int porta) throws IOException {
        // inicializando a variável de criação do socket.
        serverSocket = new ServerSocket(porta);
    }

    // método que adquire conexão, retorna um socket.
    public Socket esperaConexao() throws IOException {
        return serverSocket.accept();
    }

    private void trataConexao(Socket socket) throws IOException {

        try {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());

            System.out.println("Tratando...");

            while (status != Status.SAIR) {
                mensagem = (Mensagem) objectInputStream.readObject();
                System.out.println("Mensagem do cliente: \n\t" + mensagem);


                String operacao = mensagem.getOperacao();

                Float op1 = (Float) mensagem.getParam("op1");
                Float op2 = (Float) mensagem.getParam("op2");

                resposta = new Mensagem(operacao + "REPLY");

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

    public void divisao(Float op1, Float op2) {
        try {
            if (op2 == 0) {
                resposta.setStatus(Status.DIVZERO);
            } else {
                resposta.setStatus(Status.OK);
                Float divisao = op1 / op2;
                resposta.setParam("resposta", divisao);
            }

        } catch (Exception e) {
            resposta.setStatus(Status.PARAMERROR);
        }
    }

    public void subtracao(Float op1, Float op2) {
        try {

            resposta.setStatus(Status.OK);
            Float subtracao = op1 - op2;
            resposta.setParam("resposta", subtracao);

        } catch (Exception e) {
            resposta.setStatus(Status.PARAMERROR);
        }
    }

    public void multiplicacao(Float op1, Float op2) {
        try {
            resposta.setStatus(Status.OK);
            Float multiplicacao = op1 * op2;
            resposta.setParam("resposta", multiplicacao);

        } catch (Exception e) {
            resposta.setStatus(Status.PARAMERROR);
        }
    }

    public void soma(Float op1, Float op2) {
        try {

            resposta.setStatus(Status.OK);
            Float subtracao = op1 + op2;
            resposta.setParam("resposta", subtracao);

        } catch (Exception e) {
            resposta.setStatus(Status.PARAMERROR);
        }
    }

    public void defaultMessage() {
        resposta.setStatus(Status.ERROR);
        resposta.setParam("mensagem", "Mensagem não autorizada ou inválida!");
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
