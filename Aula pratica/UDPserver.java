import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UDPserver{
    public static void main(String [] args){
        try{
            // criar socket UDP na porta ('')
            DatagramSocket serverSocket  = new DatagramSocket( 'porta' );
            System.out.println("Servidor UDP ouvindo na porta 'porta' ");

            //Buffer para receber dados
            byte[] receiveBuffer = new byte[1024];

            while (true){
                //Receber pacote do cliente
                DatagramPacket receivePacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);
                serverSocket.receive(receivePacket);

                //Extrair dados, endereço e porta cliente
                String clientMessage = new String(receivePacket.getData(), 0,receivePacket.getLength());
                InetAddress clientAddress = receivePacket.getAddress();
                int clientPort = receivePacket.getPort();

            System.out.println(" Menssagem recebida: " + clientMessage);
            System.out.println(" Cliente: " + clientAddress + " : " + clientPort);
            
            // Preparar resposta
            String responseMessage = "Olá, cliente! Recebi sua mensagem: " + clientMessage;
            byte[] responseBuffer = responseMessage.getBytes();

            // Enviar resposta ao cliente
            DatagramPacket responsPacket = new DatagramPacket(responseBuffer, responseBuffer.length, clientAddress, clientPort);
            serverSocket.send(responsPacket);

            System.out.println(" Resposta enviada para o cliente");
        }
    } catch (Exception) {
        e.printStackTrace();
    }
    }    
}