/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs350project.communication;
import cs350project.screens.MessageDialog;
import cs350project.characters.CharacterClass;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.io.PrintWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.util.NoSuchElementException;
import java.util.concurrent.CopyOnWriteArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author Mark Masone
 */
public class Communication implements OutgoingMessageListener, OutgoingCommandListener {
    private final String host = "127.0.0.1"; // for testing
    private final int messagePort = 12346;
    private final int commandPort = 12345;
    private PrintWriter messageWriter;
    private DataOutputStream dataOutputStream;
    private DataInputStream dataInputStream;
    private OutputStream messageStream;
    private Socket dataSocket;
    private final CopyOnWriteArrayList<IncomingCommandListener> incomingCommandListeners;
    private static Communication communication;

    private Communication() {
        incomingCommandListeners = new CopyOnWriteArrayList<>();
    }

    public static Communication getInstance() {
        if(communication == null) {
            communication = new Communication();
        }
        return communication;
    }

    public void addIncomingCommandListener(IncomingCommandListener incomingCommandListener) {
        incomingCommandListeners.add(incomingCommandListener);
    }
    
    public void removeIncomingCommandListener(IncomingCommandListener incomingCommandListener) {
        incomingCommandListeners.remove(incomingCommandListener);
    }

    public void connect() {
        if (dataSocket == null || !dataSocket.isConnected()) {
            try {
                // use ipv6 address here
                //InetAddress host = InetAddress.getByName("");
                //System.out.println(host);
                dataSocket = new Socket(host, commandPort);
                dataOutputStream = new DataOutputStream(dataSocket.getOutputStream());
                dataInputStream = new DataInputStream(dataSocket.getInputStream());
                listen();
            } catch (IOException e) {
                System.out.println(e.getMessage());
                JOptionPane.showMessageDialog(null, "Cannot send commands to the server.");
            }
        }
        /*
        try {
            Socket messageSocket = new Socket(host, messagePort);
            messageWriter = new PrintWriter(messageSocket.getOutputStream(),true);
        } catch(IOException e) {
            JOptionPane.showMessageDialog(null,"Cannot send messages to the server.");
        }*/
        
    }
    
    private void listen() {
        Class c = getClass();
        String error = "Unable to listen for incoming data.";
        if(dataInputStream == null || !dataSocket.isConnected()) {
            MessageDialog.showErrorMessage(error, c);
        } else {
            Thread listenerThread = new Thread(){
                @Override
                public void run() {
                    try {
                        while(true) {
                            ServerCommand serverCommand = receiveCommand();
                            for(IncomingCommandListener incomingCommandListener : incomingCommandListeners) {
                                incomingCommandListener.commandReceived(serverCommand, dataInputStream);
                            }
                            
                        }
                    } catch(IOException e) {
                        MessageDialog.showErrorMessage(error, c);
                    }
                }
            };
            listenerThread.start();
        }
    }

    private ServerCommand receiveCommand() throws IOException {
        byte value = dataInputStream.readByte();
        for(ServerCommand serverCommand : ServerCommand.values()) {
            if(value == serverCommand.getValue()) {
                return serverCommand;
            }
        }
        throw new NoSuchElementException("Invalid command received.");
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
    public void sendCommand(ClientCommand clientCommand) {
        System.out.println("send command: " + clientCommand);

        //System.out.println("");

        if (dataOutputStream != null) {
            try {
                dataOutputStream.writeByte(clientCommand.getValue());
            } catch (IOException e) {
                System.out.println("Failed to write command to stream.");
            }
        } else {
            System.out.println("Unable to send command.");
        }
    }
    
    public void joinMatch(CharacterClass characterClass) throws IOException {
        dataOutputStream.writeByte(ClientCommand.JOIN_MATCH.getValue());
        dataOutputStream.writeByte(characterClass.getValue());
    }
    
    public void updateMatch(int stateCode) throws IOException {
        dataOutputStream.writeByte(ClientCommand.UPDATE_MATCH.getValue());
        dataOutputStream.writeShort(stateCode);
    }
}
