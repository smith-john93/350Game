package cs350project.communication;

public interface OutgoingCommandListener {
    void sendCommand(ClientCommand clientCommand);
}
