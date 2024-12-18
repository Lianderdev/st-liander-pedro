import java.net.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class UDPServer {
    private static final int PORT = 12345;

    public static void main(String[] args) {
        try (DatagramSocket socket = new DatagramSocket(PORT)) {
            System.out.println("Servidor UDP iniciado na porta " + PORT);
            byte[] buffer = new byte[1024];

            while (true) {
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                socket.receive(packet);
                String message = new String(packet.getData(), 0, packet.getLength());
                System.out.println("Recebido: " + message);

                String response = processMessage(message);
                byte[] responseData = response.getBytes();
                DatagramPacket responsePacket = new DatagramPacket(responseData, responseData.length, packet.getAddress(), packet.getPort());
                socket.send(responsePacket);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String processMessage(String message) {
        if (message.startsWith("UPPER:")) {
            return message.substring(6).toUpperCase();
        } else if (message.startsWith("REVERSE:")) {
            return new StringBuilder(message.substring(8)).reverse().toString();
        } else if (message.startsWith("CALC:")) {
            return calculate(message.substring(6));
        } else if (message.equals("PING")) {
            return "PONG";
        } else if (message.equals("TIME")) {
            return new SimpleDateFormat("HH:mm:ss").format(new Date());
        } else {
            return "Comando inválido.";
        }
    }

    private static String calculate(String operation) {
        try {
            String[] parts = operation.split(" ");
            double num1 = Double.parseDouble(parts[0]);
            String operator = parts[1];
            double num2 = Double.parseDouble(parts[2]);

            switch (operator) {
                case "+":
                    return String.valueOf(num1 + num2);
                case "-":
                    return String.valueOf(num1 - num2);
                case "*":
                    return String.valueOf(num1 * num2);
                default:
                    return "Operador inválido.";
            }
        } catch (Exception e) {
            return "Erro na operação.";
        }
    }
}