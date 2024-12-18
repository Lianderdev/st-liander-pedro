import java.net.*;
import java.util.Scanner;

public class UDPClient {
    private static final String SERVER_ADDRESS = "localhost";
    private static final int SERVER_PORT = 12345;

    public static void main(String[] args) {
        try (DatagramSocket socket = new DatagramSocket()) {
            Scanner scanner = new Scanner(System.in);
            byte[] buffer;

            while (true) {
                System.out.print("Digite uma mensagem (ou 'sair' para encerrar): ");
                String message = scanner.nextLine();
                if (message.equalsIgnoreCase("sair")) {
                    break;
                }

                buffer = message.getBytes();
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length, InetAddress.getByName(SERVER_ADDRESS), SERVER_PORT);
                socket.send(packet);

                // Receber resposta do servidor
                byte[] responseBuffer = new byte[1024];
                DatagramPacket responsePacket = new DatagramPacket(responseBuffer, responseBuffer.length);
                socket.receive(responsePacket);
                String response = new String(responsePacket.getData(), 0, responsePacket.getLength());
                System.out.println("Resposta do servidor: " + response);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}