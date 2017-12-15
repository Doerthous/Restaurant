package restaurant.communication.v1;

import java.util.Date;

public interface IData {
    /*
        fromId, toId, Command, Date, Data
     */
    String getFromId();
    String getToId();
    Integer getCommand();
    Byte[] getData();
    Date getDate();
}
