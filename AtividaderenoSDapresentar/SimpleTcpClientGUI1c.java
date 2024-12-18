import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.Socket;

public class SimpleTcpClientGUI1c {
    private static final String SERVER_ADDRESS = "localhost"; // Endereço do servidor
    private static final int SERVER_PORT = 12345; // Porta do servidor

    private JFrame frame;
    private JTextField inputField;
    private JTextArea outputArea;
    private JButton invertButton;
    private JButton countButton;
    private JButton sumButton;

    public SimpleTcpClientGUI1c() {
        frame = new JFrame("Cliente TCP");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLayout(new BorderLayout());

        inputField = new JTextField();
        outputArea = new JTextArea();
        outputArea.setEditable(false);
        outputArea.setLineWrap(true);
        outputArea.setWrapStyleWord(true);

        invertButton = new JButton("Inverter Texto");
        countButton = new JButton("Contar Caracteres");
        sumButton = new JButton("Somar Números");

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(1, 3));
        panel.add(invertButton);
        panel.add(countButton);
        panel.add(sumButton);

        frame.add(inputField, BorderLayout.NORTH);
        frame.add(new JScrollPane(outputArea), BorderLayout.CENTER);
        frame.add(panel, BorderLayout.SOUTH);

        invertButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendCommand("invert " + inputField.getText());
            }
        });

        countButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendCommand("count " + inputField.getText());
            }
        });

        sumButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendCommand(inputField.getText());
            }
        });

        frame.setVisible(true);
    }

    private void sendCommand(String command) {
        try (Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            out.println(command);
            String response = in.readLine();
            outputArea.append("Comando: " + command + "\n");
            outputArea.append("Resposta do servidor: " + response + "\n\n");
            inputField.setText("");
        } catch (IOException e) {
            outputArea.append("Erro ao conectar ao servidor: " + e.getMessage() + "\n");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(SimpleTcpClientGUI1c::new);
    }
}