import java.io.*; // Importa classes para manipulação de entrada e saída.
import java.net.*; // Importa classes para programação de rede.

public class TcpServer1b { // Classe principal que representa o servidor TCP.
    public static void main(String[] args) {
        int port = 12345; // Porta em que o servidor ficará escutando conexões.
        try (ServerSocket serverSocket = new ServerSocket(port)) { 
            // Cria um socket do servidor que escuta na porta especificada.
            System.out.println("Servidor TCP está escutando na porta " + port);

            while (true) { 
                // Loop infinito para aceitar conexões de clientes.
                Socket clientSocket = serverSocket.accept(); 
                // Aguarda e aceita uma conexão de cliente.
                new ClientHandler(clientSocket).start(); 
                // Cria uma nova thread para lidar com o cliente conectado.
            }
        } catch (IOException e) { 
            // Trata exceções relacionadas a entrada/saída.
            e.printStackTrace(); // Imprime o rastreamento da pilha do erro.
        }
    }
}

class ClientHandler extends Thread { 
    // Classe que lida com cada cliente conectado. Herda de Thread para permitir execução paralela.
    private Socket clientSocket; // Socket que representa a conexão com o cliente.

    public ClientHandler(Socket socket) { 
        // Construtor que inicializa o socket do cliente.
        this.clientSocket = socket;
    }

    public void run() { 
        // Método executado quando a thread é iniciada.
        try (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream())); 
             // Objeto para leitura de dados enviados pelo cliente.
             PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) { 
             // Objeto para enviar dados de volta ao cliente.

            String inputLine; 
            // String para armazenar as mensagens recebidas do cliente.
            while ((inputLine = in.readLine()) != null) { 
                // Lê mensagens do cliente até que ele desconecte.
                String response = processCommand(inputLine); 
                // Processa o comando recebido.
                out.println(response); 
                // Envia a resposta processada de volta ao cliente.
            }
        } catch (IOException e) { 
            // Trata exceções relacionadas a entrada/saída durante a comunicação.
            e.printStackTrace();
        } finally {
            try {
                clientSocket.close(); 
                // Fecha o socket ao final da comunicação.
            } catch (IOException e) {
                e.printStackTrace(); // Imprime rastreamento do erro caso ocorra ao fechar o socket.
            }
        }
    }

    private String processCommand(String command) { 
        // Método para processar comandos enviados pelo cliente.
        if (command.startsWith("invert ")) { 
            // Verifica se o comando é para inverter uma string.
            String textToInvert = command.substring(7); 
            // Extrai o texto a ser invertido, removendo o prefixo "invert ".
            return new StringBuilder(textToInvert).reverse().toString(); 
            // Retorna o texto invertido.
        } else if (command.startsWith("count ")) { 
            // Verifica se o comando é para contar o número de caracteres em uma string.
            String textToCount = command.substring(6); 
            // Extrai o texto a ser contado, removendo o prefixo "count ".
            return String.valueOf(textToCount.length()); 
            // Retorna o comprimento da string.
        } else { 
            // Caso o comando não comece com "invert" ou "count", tenta tratá-lo como números para somar.
            String[] numbers = command.split(" "); 
            // Divide a string recebida em partes separadas por espaço.
            try {
                int sum = 0; 
                // Variável para armazenar a soma dos números.
                for (String number : numbers) { 
                    // Itera por cada número fornecido.
                    sum += Integer.parseInt(number); 
                    // Converte cada parte em um número inteiro e o soma.
                }
                return String.valueOf(sum); 
                // Retorna a soma como string.
            } catch (NumberFormatException e) { 
                // Caso alguma parte não seja um número válido.
                return "Erro: Entrada inválida para soma."; 
                // Retorna uma mensagem de erro.
            }
        }
    }
}
