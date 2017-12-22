package restaurant.communication.core.impl;

import restaurant.communication.core.IData;

import java.util.Date;

public class Data implements IData {
    private String fromId;
    private String toId;
    private String command;
    private Date date;
    private Object data;

    public Data(String fromId, String toId, String command, Object data) {
        this.fromId = fromId;
        this.toId = toId;
        this.command = command;
        this.date = new Date();
        this.data = data;
    }

    @Override
    public String getFromId() {
        return fromId;
    }
    @Override
    public String getToId() {
        return toId;
    }
    @Override
    public String getCommand() {
        return command;
    }
    @Override
    public Date getDate() {
        return date;
    }
    @Override
    public Object getData() {
        return data;
    }
}
