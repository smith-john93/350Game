/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs350project;
import java.net.Socket;
import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Mark Masone
 */
public class Communication implements OutgoingMessageListener, OutgoingCommandListener {
    private final String host = "127.0.0.1";
    private final int messagePort = 12346;
    private final int commandPort = 12345;
    private PrintWriter commandWriter;
    private PrintWriter messageWriter;
    private OutputStream commandStream;
    private OutputStream messageStream;
    
    public void connect() {
        try {
            Socket commandSocket = new Socket(host, commandPort);
            commandStream = commandSocket.getOutputStream();
        } catch(IOException e) {
            JOptionPane.showMessageDialog(null,"Cannot send commands to the server.");
        }
        try {
            Socket messageSocket = new Socket(host, messagePort);
            messageWriter = new PrintWriter(messageSocket.getOutputStream(),true);
        } catch(IOException e) {
            JOptionPane.showMessageDialog(null,"Cannot send messages to the server.");
        }
    }
    
    @Override
    public void sendMessage(String message) {
        if (messageWriter != null) {
            messageWriter.println(message);
            System.out.println("Message sent: " + message);
        } else {
            System.out.println("Unable to send message.");
        }
        //BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        //BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
    }

    @Override
    public void sendCommand(int command) {
        //System.out.println(command);
        for(Command c : Command.values()) {
            if((command & c.getCode()) == c.getCode()) {
                System.out.print(c + " ");
            }
        }
        System.out.println("");
        
        if(commandStream != null) {
            try {
                commandStream.write((byte)command);
                short id = 356;
                byte[] idBytes = new byte[2];
                idBytes[0] = (byte)(id >> 8);
                idBytes[1] = (byte)id;
                commandStream.write(idBytes);
                System.out.println("Command sent: " + command);
            } catch (IOException e) {
                System.out.println("Failed to write command to stream.");
            }
        } else {
            //System.out.println("Unable to send command.");
        }
    }
}
