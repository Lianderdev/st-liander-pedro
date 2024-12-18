import java.net.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class UdpServer {
    public static void main(String[] args) {
        int port = 9876; // Porta do servidor
        DatagramSocket socket = null;

        try {
            socket = new DatagramSocket(port);
            System.out.println("Servidor UDP está escutando na porta " + port);

            byte[] receiveData = new byte[1024];

            while (true) {
                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                socket.receive(receivePacket);

                String message = new String(receivePacket.getData(), 0, receivePacket.getLength());
                InetAddress clientAddress = receivePacket.getAddress();
                int clientPort = receivePacket.getPort();

                String response = processMessage(message);
                byte[] sendData = response.getBytes();
                DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, clientAddress, clientPort);
                socket.send(sendPacket);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (socket != null && !socket.isClosed()) {
                socket.close();
            }
        }
    }

    private static String processMessage(String message) {
        if (message.startsWith("UPPER ")) {
            return message.substring(6).toUpperCase();
        } else if (message.startsWith("LOWER ")) {
            return message.substring(6).toLowerCase();
        } else if (message.startsWith("INVERT ")) {
            return new StringBuilder(message.substring(7)).reverse().toString();
        } else if (message.startsWith("ADD ")) {
            String[] parts = message.split(" ");
            if (parts.length == 3) {
                try {
                    int num1 = Integer.parseInt(parts[1]);
                    int num2 = Integer.parseInt(parts[2]);
                    return String.valueOf(num1 + num2);
                } catch (NumberFormatException e) {
                    return "Erro: Números inválidos.";
                }
            }
        } else if (message.startsWith("SUB ")) {
            String[] parts = message.split(" ");
            if (parts.length == 3) {
                try {
                    int num1 = Integer.parseInt(parts[1]);
                    int num2 = Integer.parseInt(parts[2]);
                    return String.valueOf(num1 - num2);
                } catch (NumberFormatException e) {
                    return "Erro: Números inválidos.";
                }
            }
        } else if (message.startsWith("MUL ")) {
            String[] parts = message.split(" ");
            if (parts.length == 3) {
                try {
                    int num1 = Integer.parseInt(parts[1]);
                    int num2 = Integer.parseInt(parts[2]);
                    return String.valueOf(num1 * num2);
                } catch (NumberFormatException e) {
                    return "Erro: Números inválidos.";
                }
            }
        } else if (message.equals("PING")) {
            return "PONG";
        } else if (message.equals("TIME")) {
            SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
            return formatter.format(new Date());
        }

        return "Comando não reconhecido.";
    }
}