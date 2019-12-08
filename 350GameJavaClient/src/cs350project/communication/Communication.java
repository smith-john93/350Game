/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs350project.communication;
import cs350project.Settings.ActionMapping;
import cs350project.screens.MessageDialog;
import cs350project.characters.CharacterType;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.io.PrintWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;
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
    // multicast code
    //private final Thread detectServerThread;

    private Communication() {
        incomingCommandListeners = new CopyOnWriteArrayList<>();
        // multicast code
        /*detectServerThread = new Thread() {
            @Override
            public void run() {
                serverAddr = Multicast.getServerAddress();
                System.out.println("got server address: " + serverAddr.getHostAddress());
            }
        };*/
    }

    public static Communication getInstance() {
        if(communication == null) {
            communication = new Communication();
        }
        return communication;
    }
    
    public void setServerAddress(String host) throws UnknownHostException {
        serverAddr = InetAddress.getByName(host);
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
        // multicast code
        //detectServerThread.start();
    }

    public void connect() throws CommunicationException {
        // multicast code
        /*try {
            detectServerThread.join(5000);
        } catch (InterruptedException e) {
            System.err.println(e.getMessage());
        }*/
        
        if(serverAddr == null) {
            throw new CommunicationException("Could not detect server.");
        }
        
        if (dataSocket == null || !dataSocket.isConnected()) {
            connected = false;
            
            try {
                dataSocket = new Socket(serverAddr, commandPort);
                dataOutputStream = new DataOutputStream(dataSocket.getOutputStream());
                dataInputStream = new DataInputStream(dataSocket.getInputStream());
                listen();
            } catch (IOException e) {
                throw new CommunicationException("Unable to connect to server.");
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
    
    private void listen() throws CommunicationException {
        Class c = getClass();
        String error = "Unable to listen for incoming data.";
        if(dataInputStream == null || !dataSocket.isConnected()) {
            throw new CommunicationException(error);
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
                    } catch(NoSuchElementException e) {
                        MessageDialog.showErrorMessage(e.getMessage(), c);
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
            //System.out.println("Message sent: " + message);
        } else {
            //System.out.println("Unable to send message.");
        }
        //BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        //BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
    }

    @Override
    public void sendClientCommand(ClientCommand clientCommand) throws IOException {
        dataOutputStream.writeByte(clientCommand.getValue());
        System.out.println("sent command: " + clientCommand + " " + clientCommand.getValue());
    }

    private void sendCredentials(String username, char[] password) throws IOException {
        sendStringNullTerminated(username);
        for(char c : password) {
            dataOutputStream.write(c);
        }
        Arrays.fill(password,'0'); // Clear the password array for security.
        //System.out.println("sent credentials");
    }

    private void sendString(String s) throws IOException {
        dataOutputStream.writeBytes(s);
        System.out.println("comm: sent string: " + s);
    }
    
    private void sendStringNullTerminated(String s) throws IOException {
        sendString(s);
        dataOutputStream.writeByte(0);
        System.out.println("comm: sent ascii 0");
    }

    private void sendCharacterState(int stateCode) throws IOException {
        dataOutputStream.writeByte(stateCode);
        System.out.println("sent character state: " + stateCode);
    }

    private void sendCharacterType(CharacterType characterType) throws IOException {
        dataOutputStream.writeByte(characterType.getValue());
        System.out.println("sent character type: " + characterType + " " + characterType.getValue());
    }

    public void createAccount(String username, char[] password) throws CommunicationException {
        connect();
        try {
            sendClientCommand(ClientCommand.CREATE_ACCOUNT);
            sendCredentials(username, password);
        } catch (IOException e) {
            throw new CommunicationException("Unable to create account.");
        }
    }

    public void login(String username, char[] password) throws CommunicationException {
        connect();
        try {
            sendClientCommand(ClientCommand.LOGIN);
            sendCredentials(username, password);
        } catch (IOException ex) {
            throw new CommunicationException("Unable to log in.");
        }
    }

    public void createMatch(String matchName) throws CommunicationException {
        connect();
        try {
            sendClientCommand(ClientCommand.CREATE_MATCH);
            sendString(matchName);
        } catch (IOException ex) {
            throw new CommunicationException("Unable to create match.");
        }
    }

    public void joinMatch(String matchName) throws CommunicationException {
        connect();
        try {
            sendClientCommand(ClientCommand.JOIN_MATCH);
            sendString(matchName);
        } catch (IOException e) {
            throw new CommunicationException("Unable to join match.");
        }
    }
    
    public void updateMatch(int stateCode) throws CommunicationException {
        connect();
        try {
            sendClientCommand(ClientCommand.UPDATE_MATCH);
            sendCharacterState(stateCode);
        } catch (IOException e) {
            throw new CommunicationException("Unable to update match.");
        }
    }
    
    public void characterSelected(CharacterType characterType) throws CommunicationException {
        connect();
        try {
            sendClientCommand(ClientCommand.CHARACTER_SELECTED);
            sendCharacterType(characterType);
        } catch (IOException e) {
            throw new CommunicationException("Unable to send character selection.");
        }
    }
    
    public void sendActionMappings(ActionMapping[] actionMappings) throws CommunicationException {
        connect();
        try {
            sendClientCommand(ClientCommand.SAVE_KEY_MAPPINGS);
            for(ActionMapping actionMapping : actionMappings) {
                sendCharacterState(actionMapping.stateCode);
                sendStringNullTerminated(actionMapping.getKeyCodesCSV());
            }
            sendClientCommand(ClientCommand.SAVE_ALL_MAPPINGS);
        } catch(IOException e) {
            throw new CommunicationException(
                    "Unable to send key mappings to server. "
                    + "Key mappings will not persist after the game has restarted."
            );
        }
    }
}
