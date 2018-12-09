package cliente;

//import modelo.Mensagem;
//import modelo.Status;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Cliente {

    public static void main(String[] args) {

        Scanner leitorOperacao = new Scanner(System.in);
        Scanner leitorOperador1 = new Scanner(System.in);
        Scanner leitorOperador2 = new Scanner(System.in);

        Scanner leitorip = new Scanner(System.in);



        System.out.println("digite o ip do server: ");
        String ip = leitorip.nextLine();


        try {
            System.out.println("Estabelecendo conexão...");
            Socket socket = new Socket(ip, 5050);
            System.out.println("\tConexão estabelecida!");

            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream objectInputStram = new ObjectInputStream(socket.getInputStream());

            while (true) {

                System.out.println("\n<1> --- SOMA\n<2> --- SUBTRAÇÃO\n<3> --- DIVISÃO\n<4> --- MULTIPLICAÇÃO\n<5> --- SAIR\n");
                System.out.print("Qual operação deseja realizar?");
                int escolha = leitorOperacao.nextInt();
                if (escolha == 5) {
                    break;
                }

                System.out.print("Digite o primeiro número: ");
                Float operador1 = leitorOperador1.nextFloat();

                System.out.print("Digite o segundo número ");
                Float operador2 = leitorOperador2.nextFloat();

                ArrayList<Object> mensagem = new ArrayList<>();

                switch (escolha) {
                    case 1:
                        mensagem.add("SOMA");
                        break;
                    case 2:
                        mensagem.add("SUB");
                        break;
                    case 3:
                        mensagem.add("DIV");
                        break;
                    case 4:
                        mensagem.add("MULT");
                        break;
                }



                mensagem.add(operador1);
                mensagem.add(operador2);

                System.out.println("Enviando mensagem...");

                //envia mensagem ao cliente.
                objectOutputStream.writeObject(mensagem);
                // libera o buffer de mensagens para que não haja concorrência.
                objectOutputStream.flush();

                System.out.println("\nMensagem enviada: " + mensagem);
                String respmensagem = (String) objectInputStram.readObject();
                System.out.println("\nResposta: " + respmensagem);
            }


            objectInputStram.close();
            objectOutputStream.close();
            socket.close();
        } catch (IOException ioe) {
            System.out.println("Erro no cliente: " + ioe);
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ioe);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


    }
}
