package restaurant.communication.core;

import java.io.Serializable;
import java.util.Date;

public interface IData extends Serializable {
    String getFromId();
    String getToId();
    String getCommand();
    Date getDate();
    Object getData();
}
