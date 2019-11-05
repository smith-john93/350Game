/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs350project;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.io.PrintWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.util.ArrayList;
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
    private DataOutputStream commandOutputStream;
    private DataInputStream commandInputStream;
    private OutputStream messageStream;
    private final ArrayList<IncomingCommunicationListener> incomingCommunicationListeners = new ArrayList<>();
    
    public void connect() {
        try {
            Socket commandSocket = new Socket(host, commandPort);
            commandOutputStream = new DataOutputStream(commandSocket.getOutputStream());
            commandInputStream = new DataInputStream(commandSocket.getInputStream());
        } catch(IOException e) {
            JOptionPane.showMessageDialog(null,"Cannot send commands to the server.");
        }
        try {
            Socket messageSocket = new Socket(host, messagePort);
            messageWriter = new PrintWriter(messageSocket.getOutputStream(),true);
        } catch(IOException e) {
            JOptionPane.showMessageDialog(null,"Cannot send messages to the server.");
        }
        Thread listenerThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while(true) {
                    try {
                        while(true) {
                            byte command = commandInputStream.readByte();
                            byte matchObjectType = commandInputStream.readByte();
                            short objectID = commandInputStream.readShort();
                            int stateCode = commandInputStream.readShort();
                            int x = commandInputStream.readShort();
                            int y = commandInputStream.readShort();
                            /*System.out.print("command: " + command);
                            System.out.print("match object type: " + matchObjectType);
                            System.out.print("object id: " + objectID);
                            System.out.print("state code: " + stateCode);
                            System.out.print("x: " + x);
                            System.out.println("y: " + y);*/
                            for(IncomingCommunicationListener incomingCommunicationListener : incomingCommunicationListeners) {
                                incomingCommunicationListener.updatePlayerCharacter(objectID,stateCode,x,y);
                            }
                        }
                    } catch(IOException e) {
                        System.err.println(e.getMessage());
                    }
                }
            }
        });
        listenerThread.start();
    }
    
    public void addIncomingCommandListener(IncomingCommunicationListener incomingCommandListener) {
        incomingCommunicationListeners.add(incomingCommandListener);
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
        
        //System.out.println("");
        
        if(commandOutputStream != null) {
            try {
                commandOutputStream.writeShort(command);
            } catch (IOException e) {
                System.out.println("Failed to write command to stream.");
            }
        } else {
            System.out.println("Unable to send command.");
        }
    }
    
    public byte receiveCommand() throws IOException {
        return commandInputStream.readByte();
    }
}
