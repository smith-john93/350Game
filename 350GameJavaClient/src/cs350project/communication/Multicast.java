/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs350project.communication;

import cs350project.screens.MessageDialog;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.MulticastSocket;
import java.util.Arrays;

/**
 *
 * @author Mark Masone
 */
public class Multicast {

    private static final String MESSAGE = "o";
    private static final int PORT = 12340;
    private static final String GROUP = "ff01::6";
    
    public static InetAddress getServerAddress() {
        InetAddress serverAddr = null;
        InetSocketAddress addr = new InetSocketAddress(PORT);
        try (MulticastSocket socket = new MulticastSocket(addr)) {
            InetAddress groupAddr = InetAddress.getByName(GROUP);
            socket.joinGroup(groupAddr);
            byte[] messageBytes = MESSAGE.getBytes();
            byte[] buffer = new byte[messageBytes.length];
            while (true) {
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);

                socket.receive(packet);
                if(Arrays.compare(packet.getData(),messageBytes) == 0) {
                    serverAddr = packet.getAddress();
                    break;
                } else {
                    System.out.println("received unexpected multicast message");
                }
            }
            socket.leaveGroup(groupAddr);
        } catch (IOException e) {
            MessageDialog.showErrorMessage("Unable to detect server.", Multicast.class);
        }
        return serverAddr;
    }
    
}
