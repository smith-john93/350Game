/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs350project.communication;

import java.io.DataInputStream;

/**
 *
 * @author Mark Masone
 */
public interface IncomingCommandListener {
    void commandReceived(ServerCommand serverCommand, DataInputStream dataInputStream);
}
