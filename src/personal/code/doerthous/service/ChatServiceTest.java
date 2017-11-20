package personal.code.doerthous.service;

import java.text.SimpleDateFormat;
import java.util.*;

import static java.lang.Thread.sleep;

public class ChatServiceTest {
    private Map<String, List<IChatData>> data;
    private List<IChatObserver> observers;

    public ChatServiceTest() {
        data = new HashMap<>();
        observers = new ArrayList<>();
        List<IChatData> l = new ArrayList<>();
        l.add(new ChatDataImpl("1T","16:55","有人吗？"));
        l.add(new ChatDataImpl("2T","16:55","没有"));
        data.put("1T", l);
        l = new ArrayList<>();
        l.add(new ChatDataImpl("3T","16:55","在吗？"));
        data.put("3T", l);
        l = new ArrayList<>();
        l.add(new ChatDataImpl("4T","16:55","在？"));
        data.put("4T", l);
        l = new ArrayList<>();
        l.add(new ChatDataImpl("5T","16:55","贵姓？"));
        data.put("5T", l);
        l = new ArrayList<>();
        data.put("6T", l);
        new Thread(()->{
            try {
                sleep(1500);
                for(IChatObserver observer: observers){
                    observer.newMessage("5T");
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    public List<IChatData> getChatRecordWith(String tableId){
        return data.getOrDefault(tableId, new ArrayList<>());
    }
    public List<String> getOhterTableIds(){
        List<String> l =new ArrayList<>(data.keySet());
        l.sort(String::compareTo);
        return l;
    }
    public void sendMessage(String from, String to, String message){
        List l = data.getOrDefault(to, new ArrayList<>());
        l.add(new ChatDataImpl(from, getTime("HH:mm"),message));
        if("6T".equals(to)){
            new Thread(()->{
                try {
                    sleep(3500);
                    List l2 = data.getOrDefault("6T", new ArrayList<>());
                    l2.add(new ChatDataImpl("6T",getTime("HH:mm"),"你谁"));
                    data.putIfAbsent("6T",l2);
                    for(IChatObserver observer: observers){
                        observer.newMessage("6T");
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }
    public void addChatObserver(IChatObserver observer){
        observers.add(observer);
    }
    public void removeChatObserver(IChatObserver observer){
        observers.remove(observer);
    }
    private static String getTime(String format){
        return new SimpleDateFormat(format).format(new Date());
    }
}
