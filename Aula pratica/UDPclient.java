import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UDPclient{
    public static void main(String [] args){
        try{
            DatagramSocket socket = new DatagramSocket();
            InetAddress servAddress = InetAddress.getByName("localhost")

            String message = "Hello, Server!";
            byte[] buffer = message.getBytes();

            DatagramPacket packet = new DatagramPacket(buffer, buffer.length, serverAddress, "port()" );
            socket.send(packet); // Certifique-se que termina com ;

            // Receber reposta do servidor
            byte[] responseBuffer = new byte[1024];
            DatagramPacket responsePacket = new DatagramPacket(responseBuffer, responseBuffer.length);
            socket.receive(responsePacket);

            String response = new String(responsePacket.getData(), 0, responsePacket.getLength());
            System.out.println(" Resposta do servidor "+ response);

            socket.close();
        }catch (Exception) {
            e.printStackTrace();
    }   
}