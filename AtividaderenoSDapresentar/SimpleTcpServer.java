import java.io.*;
import java.net.*;
import java.util.concurrent.atomic.AtomicInteger;

public class SimpleTcpServer {
    private static final AtomicInteger clientCount = new AtomicInteger(0);
    private static final AtomicInteger messageCount = new AtomicInteger(0);
    private static long startTime;

    public static void main(String[] args) {
        int port = 12345; // Porta do servidor
        startTime = System.currentTimeMillis();
        
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Servidor TCP está escutando na porta " + port);
            while (true) {
                Socket clientSocket = serverSocket.accept();
                clientCount.incrementAndGet(); // Incrementa o número de clientes conectados
                new ClientHandler(clientSocket).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static class ClientHandler extends Thread {
        private Socket clientSocket;

        public ClientHandler(Socket socket) {
            this.clientSocket = socket;
        }

        public void run() {
            try (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                 PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {

                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    String response = processCommand(inputLine);
                    out.println(response);
                    messageCount.incrementAndGet(); // Incrementa o número de mensagens processadas
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    clientSocket.close();
                    clientCount.decrementAndGet(); // Decrementa o número de clientes conectados
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        private String processCommand(String command) {
            if (command.startsWith("invert ")) {
                String textToInvert = command.substring(7);
                return new StringBuilder(textToInvert).reverse().toString();
            } else if (command.startsWith("count ")) {
                String textToCount = command.substring(6);
                return String.valueOf(textToCount.length());
            } else {
                String[] numbers = command.split(" ");
                try {
                    int sum = 0;
                    for (String number : numbers) {
                        sum += Integer.parseInt(number);
                    }
                    return String.valueOf(sum);
                } catch (NumberFormatException e) {
                    return "Erro: Entrada inválida para soma.";
                }
            }
        }
    }

    public static void printStatistics() {
        long uptime = System.currentTimeMillis() - startTime;
        System.out.println("Estatísticas do Servidor:");
        System.out.println("Número total de clientes conectados: " + clientCount.get());
        System.out.println("Número total de mensagens processadas: " + messageCount.get());
        System.out.println("Tempo de atividade do servidor: " + (uptime / 1000) + " segundos");
    }
}