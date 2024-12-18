import java.io.*;
import java.net.*;

public class SimpleTcpClient {
    public static void main(String[] args) {
        String serverAddress = "localhost"; // Endereço do servidor
        int port = 12345; // Porta do servidor

        try (Socket socket = new Socket(serverAddress, port);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in))) {

            String userInput;
            System.out.println("Bem-vindo ao cliente TCP!");
            System.out.println("Escolha uma opção:");
            System.out.println("1. Inverter texto (comando: invert <texto>)");
            System.out.println("2. Contar caracteres (comando: count <texto>)");
            System.out.println("3. Somar números (digite dois números separados por espaço)");
            System.out.println("Digite 'sair' para encerrar o cliente.");

            while (true) {
                System.out.print("Digite seu comando: ");
                userInput = stdIn.readLine();

                if (userInput.equalsIgnoreCase("sair")) {
                    System.out.println("Encerrando o cliente.");
                    break;
                }

                out.println(userInput);
                String response = in.readLine();
                System.out.println("Resposta do servidor: " + response);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}