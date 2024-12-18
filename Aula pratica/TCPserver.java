import java.net.*;
import java.io.*;

public class TCPserver{
    public static void main(String[] args) {
        try{
            int severPort = 'numero_da_porta'; // Porta do servidor
            severSocket listenSocket = new ServerSocket(ServerPort);
            System.out.println(" Servidor iniciado na porta: " + severPort);

            //Loop infinito para aceitar conexões de clientes
            while (true) {
                System.out.println(" Aguardando conexões de cliente...");
                Socket clientSocket = listenSocket.accept(); // Aceita conexão do cliente
                System.out.println("Cliente conectado: "+ clientSocket.getInetAddress());
                new ClientConnection(clientSocket) // Cria a conexão em uma nova thread
            }
        } catch (IOException e){
            System.out.println("Erro ao iniciar o servidor: " + e.getMessage());
        }
    }
}

class ClienteConnectionm extends Thread{
    private DataInputStream in; // Fluxo de entrada de dados
    private DataOutputStream out; // Fluxo de saida de dados
    private Socket clientSocket; // Socket do cliente

    public ClientConnection(Socket aClientSocket){
        try {
            clientSocket = aClientSocket;
            in = new DataInputStream(clientSocket.getinputSteam());
            out = new DataInputStream(clientSocket.getOutputStream());
            this.start(); // Inicia a thread
        } catch (IOException) {
            System.out.println("Erro ao criar a conexão: "+e.getMessage());
        }
    }

    public void run(){
        try{
            System.out.println(" Aguardando mensagem do cliente... ");
            String data = in.readUTF(); //Le a mensagem enviada pelo cliente
            System.out.println(" Mensagem: " + data);

            //Envia resposta ao cliente
            String response = " Mensagem: " + data;
            out.writeUTF(response);
            System.out.println(" Resposta enviada ao cliente: " + response);
        } cacth (EOFException e){
            System.out.println(" Concexão encerrada pelo cliente.");
        } cacth (IOFException e){
            System.out.println(" Erro de I/O." + e.getMessage());
        } finally {
            try{
                clientSocket.close() //Fecha o socket do cliente
                System.out.println(" Conexão encerrada.");
            } catch (IOException e){
                System.out.println("Erro ao fechar o socket do cliente: " + e.getMessage());
            }
        }
    }
}