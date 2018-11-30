package servidor;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor {

    // instanciando o objeto ServerSocket.
    private ServerSocket serverSocket;

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
            Mensagem mensagem = objectInputStream.readObject();
            String operacao = mensagem.getOperacao();

            if(operacao.equals("HELLO")){
                String nome = (String) mensagem.getParam("nome");
                String sobrenome = (String) mensagem.getParam("sobrenome");

                Mensagem resposta = new Mensagem("HELLOREPLY");
                resposta.setStatus("OK");
                resposta.setParam("mensagem", "Hello World", nome, sobrenome);
            }

            objectOutputStream.writeObject(mensagem);
            objectOutputStream.flush();

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
