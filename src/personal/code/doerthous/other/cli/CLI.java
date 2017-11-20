package personal.code.doerthous.other.cli;


import restaurant.communication.ICommandObserver;
import restaurant.communication.IData;
import restaurant.communication.IPeer;
import restaurant.communication.PeerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.lang.System.exit;
import static java.lang.Thread.sleep;


public class CLI implements ICommandObserver, ITestCommands {
    //
    private BufferedReader br;
    private IPeer p;
    private int choice;
    private boolean running;
    //
    private Map<String, Boolean> hasNewMessage;
    private void printMainMenu(){
        System.out.println(p.getId() +", choose one of the selections below");
        System.out.println("1: send message");
        System.out.println("2: read message");
        System.out.println("3: send command");
        System.out.println("4: help");
        System.out.println("0: quit");
        System.out.print("> ");
    }
    private void printHelp(){
        System.out.println("while in say mode, after you finish your message, " +
                "type \"\\\\\\\" to quit");
    }
    private void printSession(List<IData> data) {
        String message = "";
        for(int i = 0; i < data.size(); ++i){
            message += data.get(i).getDate().toString();
            message += " ";
            message += data.get(i).getFromId() + " to " + data.get(i).getToId() +"\n";
            Byte[] B = data.get(i).getData();
            byte[] b = new byte[B.length];
            for(int j = 0; j < b.length; ++j){
                b[j] = B[j];
            }
            message += new String(b) + "\n";
        }
        System.out.print(message);
    }
    private void sendMessage() throws IOException {
        List<String> names = p.getAllPeersId();
        for(int i = 0; i < names.size(); ++i){
            System.out.println(i+1 + ": " + names.get(i));
        }
        System.out.print("> ");
        choice = Integer.valueOf(br.readLine());
        if(choice < names.size()+1){
            System.out.println("type \"\\\\\\\" in a new line as end of input\nmessage:");
            System.out.print("> ");
            String aline = br.readLine();
            String message = "";
            while (!"\\\\\\".equals(aline)) {
                System.out.print("> ");
                message += aline+"\n";
                aline = br.readLine();
            }
            System.out.println("message has sended");
            p.sendMessage(names.get(choice-1), message);
        } else {
            System.out.println("No such name");
        }
    }
    private void sendCommand() throws IOException {
        System.out.println("who: ");
        System.out.print("> ");
        String who = br.readLine();
        System.out.println("command:");
        System.out.println("1: exit");
        System.out.println("2: shutdown");
        System.out.println("3: shutdown cancel");
        System.out.print("> ");
        choice = Integer.valueOf(br.readLine());
        switch (choice) {
            case 1:{
                p.sendCommand(who, CMD_EXIT);
            } break;
            case 2:{
                p.sendCommand(who, CMD_SHUTDOWN);
            } break;
            case 3:{
                p.sendCommand(who, CMD_SHUTDOWN_CANCEL);
            } break;
            default:{
                System.out.println("No such command");
                return;
            }
        }
    }
    private void readMessage() throws IOException {
        List<String> names = p.getAllPeersId();
        for(int i = 0; i < names.size(); ++i){
            String name = names.get(i);
            System.out.print(i+1 + ": " + name);
            if(hasNewMessage.getOrDefault(name, false)){
                System.out.print("    *"); // 有消息
                hasNewMessage.put(name, false);
            }
            System.out.println("");
        }
        System.out.print("> ");
        choice = Integer.valueOf(br.readLine());
        if(choice < names.size()+1){ // 负数呢？
            List<IData> session = p.getSessionById(names.get(choice-1));
            printSession(session);
        } else {
            System.out.println("No such name");
        }
    }
    private void quit(){
        running = false;
        p.stop();
        exit(0);
    }

    private CLI() throws IOException {
        running = true;
        br = new BufferedReader(new InputStreamReader(System.in));
        hasNewMessage = new HashMap<>();
        p = PeerFactory.newPeer(PeerFactory.T2);
        p.addCommandObserver(this, CMD_EXIT);
        p.addCommandObserver(this, CMD_SHUTDOWN);
        p.addCommandObserver(this, CMD_SHUTDOWN_CANCEL);
        p.addCommandObserver(this, CMD_INIT_FINISHED);
        p.addCommandObserver(this, CMD_NEW_MESSAGE);
    };
    private void loop() throws IOException {
        while(running) {
            printMainMenu();
            choice = Integer.valueOf(br.readLine());
            switch (choice) {
                case 1: {
                    sendMessage();
                } break;
                case 2: {
                    readMessage();
                } break;
                case 3: {
                    sendCommand();
                } break;
                case 4:{
                    printHelp();
                } break;
                default: {
                    quit();
                }
            }
        }
    }
    private void run() throws IOException, InterruptedException {
        System.out.println("Communication layer test program");
        p.listen();
        System.out.printf("waiting for init...");
        while (running){
            sleep(1000);
        }
        System.out.println();
        running = true;
        loop();
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        CLI cli = new CLI();
        cli.run();
    }

    //
    @Override
    public void update(IData data) {
        switch (data.getCommand()){
            case CMD_EXIT:{
                quit();
            } break;
            case CMD_SHUTDOWN:{
                shutdown(5000);
            } break;
            case CMD_SHUTDOWN_CANCEL:{
                shutdown(-1);
            } break;
            case CMD_INIT_FINISHED:{
                running = false;
            } break;
            case CMD_NEW_MESSAGE:{
                hasNewMessage.put(data.getFromId(), true);
            }
        }
    }
    private void shutdown(int time) {
        try {
            CTWIN.shutdown(time);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

