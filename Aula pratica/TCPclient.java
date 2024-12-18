import java.net.*;
import java.io.*;

public class TCPclient{
    public static void main(String[] args) {
        String message = " Olá, servidor ";// Defina a mensagem diretamente
        String serverAddress = " 127.0.0.1 " // Defina o endereço do servidor diretamente
        
        Socket socket = null;
        DataInputStream in = null;
        DataOutputStream out = null;
        
        try{
            int severPort = 'numero_da_porta'; // Porta do servidor

            //Tenta conectar ao servidor
            System.out.println(" Conectado ao servidor " + serverAddress + " na porta " + severPort + "...");
            socket = new Socket(serverAddress, severPort); // Conecta ao servidor

            // Cria os strems para comunicação
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());

            // Recebe a mensagem do servidor
            System.out.println(" Enviando mensagem:  " + message);
            out.writeUTF(message);

            // Recebe a resposta do servidor
            String response = in.readUTF();
            System.out.println(" Resposta recebida do servidor: " + response);
        } catch (UnknownHostException e){
            system.out.println(" Host desconhecido: " + e.getMessage())
        } catch (EOFException e){
            system.out.println(" Conexão encerrada pelo servidor. ")
        }catch (IOException e){
            system.out.println(" Erro de I/O " + e.getMessage())
        } finally {
            // Fecha os streams e o socket
            try{
                if (in != null) in.close();
                if (out != null) out.close();
                if (socket != null) socket.close();
                System.out.println( IOException){
                    System.out.println(" Erro ao fechar o socket ou streams: " + e.getMessage());
                }
                
            }
        } 
    }
}