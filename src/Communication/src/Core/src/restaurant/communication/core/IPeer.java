package restaurant.communication.core;

public interface IPeer {
    String BROADCAST_ID = "broadcast";
    /*
        阻塞
     */
    void start();
    void stop();

    String getId();
    void setId(String id);
    void sendCommand(String id, String command, Object data);
    void addCommandObserver(ICommandObserver observer, String command);
    void removeCommandObserver(ICommandObserver observer, String command);
}
