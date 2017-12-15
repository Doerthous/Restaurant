package restaurant.communication.v2;

public interface IPeer {
    String BROADCAST_ID = "255.255.255.255";
    void start();
    void stop();

    String getId();
    void setId(String id);
    void sendCommand(String id, String command, Object data);
    void addCommandObserver(ICommandObserver observer, String command);
    void removeCommandObserver(ICommandObserver observer, String command);
}
