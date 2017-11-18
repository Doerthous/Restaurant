package restaurant.communication;

import java.util.List;

public interface IPeer extends ICommands {
    // 线程、socket相关
    void send(IData data);
    void listen();
    void stop();
    boolean isRunning();

    // 命令功能
    /*
        给ip为id的Peer发送command命令
     */
    void sendCommand(String id, Integer command);
    /*
        给ip为id的Peer发送command命令，附带数据data
     */
    void sendCommand(String id, Integer command, Byte[] data);
    void addCommandObserver(ICommandObserver observer, Integer command);
    void removeCommandObserver(ICommandObserver observer, Integer command);

    // 聊天功能
    String getId();
    void setId(String id);
    /*
        获取所有在线人的id（ip）
     */
    List<String> getAllPeersId();
    /*
        给ip为id的人发送消息
     */
    void sendMessage(String id, String message);
    /*
        获取与id的会话（聊天记录）
     */
    List<IData> getSessionById(String id);
    /*
        结束与id的会话
     */
    void endSessionWith(String id);
    /*
        结束所有会话
     */
    void endAllSessions();
}
