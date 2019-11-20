package cs350project.communication;

import java.io.IOException;

public interface OutgoingCommandListener {
    void sendClientCommand(ClientCommand clientCommand) throws IOException;
}
