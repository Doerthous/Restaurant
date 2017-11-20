package personal.code.doerthous.service;

public class ChatDataImpl implements IChatData{
    private String tableId;
    private String time;
    private String message;

    public ChatDataImpl(String tableId, String time, String message) {
        this.tableId = tableId;
        this.time = time;
        this.message = message;
    }

    @Override
    public String getTime() {
        return time;
    }

    @Override
    public String getTableId() {
        return tableId;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
