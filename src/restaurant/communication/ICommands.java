package restaurant.communication;

public interface ICommands {
    /*
        50000 以下的命令用于点对点
        50000 - 100000 用于广播
     */
    int CMD_CHAT = 0; // 聊天命令
    int CMD_EXIT = 1; // 收到这条命令后，Peer线程退出
    int CMD_IDENTIFY_RESPONSE = 2; // 当收到CMD_IDENTIFY时，点对点回复这条消息
    int CMD_INIT_FINISHED = 3; // 自收消息，以update这条消息的关注者（例如：ui层）
    int CMD_NEW_MESSAGE = CMD_CHAT; // 当有消息来时自己发消息给自己，以update这条消息的关注者
    int CMD_ONLINE_PEER_CONFIRM_ACK = 5; //

    int CMD_IDENTIFY = 50000; // 广播确认自己的id
    int CMD_ONLINE_PEER_CONFIRM = 50001; // 广播确认在线Peer（udp可能丢包）
}
