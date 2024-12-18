import java.io.*;
import java.net.*;
import java.util.concurrent.atomic.AtomicInteger;

public class TCPServer {
    private static final int PORT = 12345;
    private static AtomicInteger clientCount = new AtomicInteger(0);
    private static AtomicInteger messageCount = new AtomicInteger(0);
    private static long startTime;

    public static void main(String[] args) {
        startTime = System.currentTimeMillis();
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Servidor iniciado na porta " + PORT);
            while (true) {
                Socket clientSocket = serverSocket.accept();
                clientCount.incrementAndGet();
                System.out.println("Cliente conectado. Total de clientes: " + clientCount.get());
                new ClientHandler(clientSocket).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static class ClientHandler extends Thread {
        private Socket socket;

        public ClientHandler(Socket socket) {
            this.socket = socket;
        }

        public void run() {
            try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                 PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    messageCount.incrementAndGet();
                    String response = processMessage(inputLine);
                    out.println(response);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    socket.close();
                    clientCount.decrementAndGet();
                    System.out.println("Cliente desconectado. Total de clientes: " + clientCount.get());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        private String processMessage(String message) {
            if (message.startsWith("invert ")) {
                return new StringBuilder(message.substring(7)).reverse().toString();
            } else if (message.startsWith("count ")) {
                return String.valueOf(message.substring(6).length());
            } else {
                String[] numbers = message.split(" ");
                if (numbers.length == 2) {
                    try {
                        int num1 = Integer.parseInt(numbers[0]);
                        int num2 = Integer.parseInt(numbers[1]);
                        return String.valueOf(num1 + num2);
                    } catch (NumberFormatException e) {
                        return "Erro: Entrada inválida para soma.";
                    }
                }
            }
            return "Comando inválido.";
        }
    }
}