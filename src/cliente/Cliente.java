package cliente;

import modelo.Mensagem;
import modelo.Status;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Cliente {

    public static void main(String[] args) {

        Scanner leitorOperacao = new Scanner(System.in);
        Scanner leitorOperador1 = new Scanner(System.in);
        Scanner leitorOperador2 = new Scanner(System.in);


        try {
            System.out.println("Estabelecendo conexão...");
            Socket socket = new Socket("localhost", 5050);
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

                System.out.print("Digite o primeito operador: ");
                float operador1 = leitorOperador1.nextFloat();

                System.out.print("Digite o segundo operador: ");
                float operador2 = leitorOperador2.nextFloat();

                Mensagem mensagem = new Mensagem();

                switch (escolha) {
                    case 1:
                        mensagem.setOperacao("SOMA");
                        break;
                    case 2:
                        mensagem.setOperacao("SUB");
                        break;
                    case 3:
                        mensagem.setOperacao("DIV");
                        break;
                    case 4:
                        mensagem.setOperacao("MULT");
                        break;
                }


                mensagem.setStatus(Status.SOLICITACAO);
                mensagem.setParam("op1", operador1);
                mensagem.setParam("op2", operador2);

                System.out.println("Enviando mensagem...");

                //envia mensagem ao cliente.
                objectOutputStream.writeObject(mensagem);
                // libera o buffer de mensagens para que não haja concorrência.
                objectOutputStream.flush();

                System.out.println("\nMensagem enviada: " + mensagem);
                mensagem = (Mensagem) objectInputStram.readObject();
                System.out.println("\nResposta: " + mensagem);
                if (mensagem.getStatus() == Status.OK) {
                    float resposta = (float) mensagem.getParam("resposta");
                } else {
                    System.out.println("Erro: " + mensagem.getStatus());
                }
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
