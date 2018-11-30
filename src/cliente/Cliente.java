package cliente;

import javax.xml.bind.SchemaOutputResolver;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Cliente {

    public static void main(String[] args) throws IOException {


        try {
            System.out.println("estabelecendo conexão...");
            Socket socket = new Socket("localhost", 5050);
            System.out.println("Conexão estabelecida!");

            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream objectInputStram = new ObjectInputStream(socket.getInputStream());

            System.out.println("Enviando mensagem...");
            String mensagem = "Hello!";
            //envia mensagem ao cliente.
            objectOutputStream.writeUTF(mensagem);
            // libera o buffer de mensagens para que não haja concorrência.
            objectOutputStream.flush();

            System.out.println("Mensagem enviada: " + mensagem);
            mensagem = objectInputStram.readUTF();
            System.out.println("Resposta: " + mensagem);

            objectInputStram.close();
            objectOutputStream.close();
            socket.close();
        } catch (IOException ioe) {
            System.out.println("Erro no cliente: " + ioe);
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ioe);
        }


    }
}
