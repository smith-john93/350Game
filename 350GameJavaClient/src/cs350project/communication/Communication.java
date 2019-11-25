/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs350project.communication;
import cs350project.screens.MessageDialog;
import cs350project.characters.CharacterClass;
import cs350project.characters.CharacterType;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.io.PrintWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetAddress;
import java.util.NoSuchElementException;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 *
 * @author Mark Masone
 */
public class Communication implements OutgoingMessageListener, OutgoingCommandListener {
    //private final String host = "192.168.163.126"; // for testing
    private final int messagePort = 12346;
    private final int commandPort = 12345;
    private PrintWriter messageWriter;
    private DataOutputStream dataOutputStream;
    private DataInputStream dataInputStream;
    private OutputStream messageStream;
    private Socket dataSocket;
    private final CopyOnWriteArrayList<IncomingCommandListener> incomingCommandListeners;
    private static Communication communication;
    private boolean connected = false;
    private InetAddress serverAddr;
    private final Thread detectServerThread;

    private Communication() {
        incomingCommandListeners = new CopyOnWriteArrayList<>();
        detectServerThread = new Thread() {
            @Override
            public void run() {
                serverAddr = Multicast.getServerAddress();
                System.out.println("got server address: " + serverAddr.getHostAddress());
            }
        };
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

    public boolean isConnected() {
        return connected;
    }
    
    public void detectServer() {
        detectServerThread.start();
    }

    public boolean connect() {
        try {
            detectServerThread.join(5000);
        } catch (InterruptedException e) {
            System.err.println(e.getMessage());
        }
        
        if(serverAddr == null) {
            MessageDialog.showErrorMessage("Could not detect server.", getClass());
            return false;
        }
        
        if (dataSocket == null || !dataSocket.isConnected()) {
            connected = false;
            try {
                dataSocket = new Socket(serverAddr, commandPort);
                dataOutputStream = new DataOutputStream(dataSocket.getOutputStream());
                dataInputStream = new DataInputStream(dataSocket.getInputStream());
                return listen();
            } catch (IOException e) {
                System.err.println(e.getMessage());
                MessageDialog.showErrorMessage("Unable to connect to server.", getClass());
                return false;
            }
        }
        /*
        try {
            Socket messageSocket = new Socket(host, messagePort);
            messageWriter = new PrintWriter(messageSocket.getOutputStream(),true);
        } catch(IOException e) {
            JOptionPane.showMessageDialog(null,"Cannot send messages to the server.");
        }*/
        return true;
    }
    
    private boolean listen() {
        Class c = getClass();
        String error = "Unable to listen for incoming data.";
        if(dataInputStream == null || !dataSocket.isConnected()) {
            MessageDialog.showErrorMessage(error, c);
            return false;
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
                        System.err.println(e.getMessage());
                        MessageDialog.showErrorMessage(error, c);
                    } catch(NoSuchElementException e) {
                        MessageDialog.showErrorMessage(e.getMessage(), c);
                    }
                }
            };
            listenerThread.start();
            return true;
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
    
    public void sendCredentials(String username, char[] password) {
        if (dataOutputStream != null) {
            try {
                dataOutputStream.writeBytes(username);
                dataOutputStream.write(0);
                for(char c : password) {
                    dataOutputStream.write(c);
                }
                //dataOutputStream.writeChar;
            } catch (IOException e) {
                System.out.println("Failed to write command to stream.");
            }
        } else {
            System.out.println("Unable to send command.");
        }
    }
    
    public void sendMatchName(String matchName) {
        System.out.println("send match name: " + matchName);
        if (dataOutputStream != null) {
            try {
                dataOutputStream.writeBytes(matchName);
            } catch (IOException e) {
                System.out.println("Failed to write match name to stream.");
            }
        } else {
            System.out.println("Unable to send match name.");
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
    
    public void characterSelected(CharacterType characterType) throws IOException {
        dataOutputStream.writeByte(ClientCommand.CHARACTER_SELECTED.getValue());
        dataOutputStream.writeByte(characterType.getValue());
    }
}
