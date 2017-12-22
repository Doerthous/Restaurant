package restaurant.communication.v1.impl;

import restaurant.communication.v1.IData;

import java.io.Serializable;
import java.util.Date;

public class Data implements IData, Serializable {
    private String fromId;
    private String toId;
    private Integer command;
    private Date date;
    private Byte[] data;

    public Data(String fromId, String toId, Integer command, Date date, Byte[] data) {
        this.fromId = fromId;
        this.toId = toId;
        this.command = command;
        this.date = date;
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
    public Integer getCommand() {
        return command;
    }

    @Override
    public Byte[] getData() {
        return data;
    }

    @Override
    public Date getDate() {
        return date;
    }
}
