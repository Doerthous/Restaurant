package restaurant.communication.core;

public interface IPeer {
    String BROADCAST_ID = "broadcast";
    String CMD_ID_IS_ALREADY_IN_USED = IPeer.class.getName() + "[IIAIU]";
    /*
        阻塞（底层初始化）
     */
    void init();
    /*
        以id运行，若有重复则产生CMD_ID_IS_ALREADY_IN_USED消息
     */
    void start(String id);
    /*
        stop后Peer不可重用
     */
    void stop();

    String getId();
    void sendCommand(String id, String command, Object data);
    void addCommandObserver(ICommandObserver observer, String command);
    void removeCommandObserver(ICommandObserver observer, String command);
}
