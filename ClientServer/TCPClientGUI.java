import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.*;

public class TCPClientGUI {
    private static final String SERVER_ADDRESS = "localhost";
    private static final int SERVER_PORT = 12345;
    private static Socket socket;
    private static PrintWriter out;
    private static BufferedReader in;

    public static void main(String[] args) {
        JFrame frame = new JFrame("Cliente TCP");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLayout(new BorderLayout());

        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
        frame.add(scrollPane, BorderLayout.CENTER);

        JTextField inputField = new JTextField();
        frame.add(inputField, BorderLayout.SOUTH);

        JButton sendButton = new JButton("Enviar");
        frame.add(sendButton, BorderLayout.EAST);

        // Conectar ao servidor
        try {
            socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            textArea.append("Erro ao conectar ao servidor: " + e.getMessage() + "\n");
            return;
        }

        // Ação do botão de enviar
        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String userInput = inputField.getText();
                if (!userInput.isEmpty()) {
                    out.println(userInput);
                    inputField.setText(""); // Limpa o campo de entrada
                    try {
                        String response = in.readLine();
                        textArea.append("Você: " + userInput + "\n");
                        textArea.append("Servidor: " + response + "\n");
                    } catch (IOException ex) {
                        textArea.append("Erro ao receber resposta do servidor: " + ex.getMessage() + "\n");
                    }
                }
            }
        });

        frame.setVisible(true);
    }
}