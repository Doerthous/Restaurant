package restaurant.service.pc.management;

import restaurant.communication.core.ICommandObserver;
import restaurant.communication.core.IData;
import restaurant.communication.core.IPeer;
import restaurant.database.IDb;
import restaurant.service.core.IManagementService;
import restaurant.service.core.impl.InterModuleCommunication;
import restaurant.service.core.vo.Table;
import restaurant.service.pc.vo.PO2VO;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class TableManager implements ICommandObserver {
    private IPeer peer;
    private IDb db;
    // 缓存数据库的数据
    private List<restaurant.database.po.Seat> tables;
    // table消息监听器
    private List<IManagementService.ITableObserver> tableObservers;


    public TableManager(IPeer peer, IDb db) {
        this.peer = peer;
        this.db = db;
        peer.addCommandObserver(this, InterModuleCommunication.CommandToManagement.KITCHEN_DISH_FINISHED);
        peer.addCommandObserver(this, InterModuleCommunication.CommandToManagement.CLIENT_REQUEST_SERVICE);
        peer.addCommandObserver(this, InterModuleCommunication.CommandToManagement.CLIENT_TABLE_ONLINE);
        peer.addCommandObserver(this, InterModuleCommunication.CommandToManagement.CLIENT_NEW_ORDER);
        tableObservers = new ArrayList<>();
    }

    // 数据库操作
    private String newTableId(Integer floor){
        int floorLimit = 3;
        int numberLimit = 4;
        int max = 0;
        String prefix = "";
        String floorStr = String.valueOf(floor);
        int l = floorLimit - floorStr.length(); // 不过百层
        for (int i = 0; i < l; ++i){
            prefix += "0";
        }
        floorStr = prefix + floorStr;
        for(restaurant.database.po.Seat table: tables){
            if(isDigits(table.getId())){
                String id = table.getId();
                String tableFloor = id.substring(0,floorLimit);
                String tableNumber = id.substring(floorLimit);
                if(tableFloor.equals(floorStr) && tableNumber.length() == numberLimit) {
                    int tn = Integer.valueOf(tableNumber);
                    if (tn > max) {
                        max = tn;
                    }
                }
            }
        }
        String newNumber = String.valueOf(max+1);
        l = numberLimit - newNumber.length(); // 不过万桌
        prefix = "";
        for (int i = 0; i < l; ++i){
            prefix += "0";
        }
        return floorStr+prefix+newNumber;
    }
    private static boolean isDigits(String str){
        return Pattern.compile("^\\d+$").matcher(str).find();
    }
    public String createTable(String type, Integer floor, Integer capacity){
        String id = newTableId(floor);
        restaurant.database.po.Seat seat = PO2VO.newSeatPo(id, type, floor, capacity);
        if(db.insertSeat(seat)){
            tables.add(seat);
            return id;
        }
        return null;
    }
    public Boolean deleteTable(String tableId){
        restaurant.database.po.Seat targetTable = PO2VO.newSeatPo(tableId);
        //debug("Delete table: " + targetTable.toString());
        if(db.deleteSeat(targetTable)){
            getAllTable();
            return true;
        }
        return false;
    }
    public Boolean modifyTable(String tableId, String type, Integer floor, Integer capacity){
        if(tables == null){
            this.tables = db.getAllSeat();
        }
        restaurant.database.po.Seat targetTable = null;
        for(restaurant.database.po.Seat table : this.tables){
            if(table.getId().equals(tableId)){
                targetTable = table;
                break;
            }
        }
        restaurant.database.po.Seat table = PO2VO.setSeatPo(tableId, type, floor, capacity, targetTable);
        //debug("Update table: " + table.toString());
        if(table != null){
            return db.updateSeat(table);
        }
        return false;
    }
    public List<Table> getAllTable(){
        this.tables = db.getAllSeat();
        List<Table> tables = new ArrayList<>();
        for(restaurant.database.po.Seat t: this.tables){
            tables.add(PO2VO.table(t));
        }
        return tables;
    }
    public Table getTableById(String tableId){
        return PO2VO.table(db.getSeatById(tableId));
    }
    public List<String> getTableTypes() {
        if(tables == null){
            getAllTable();
        }
        List<String> types = new ArrayList<>();
        types.add("全部");
        for(restaurant.database.po.Seat e: tables){
            if(!types.contains(e.getType())){
                types.add(e.getType());
            }
        }
        return types;
    }


    // 通讯
    public void openTable(String tableId){
        peer.sendCommand(tableId, InterModuleCommunication.CommandToClient.MANAGEMENT_OPEN_TABLE, null);
    }
    public void closeTable(String tableId){
        peer.sendCommand(tableId, InterModuleCommunication.CommandToClient.MANAGEMENT_CLOSE_TABLE, null);
        //insertOrder(tableId);
        //orders.put(tableId, null);
    }
    public void addTableObserver(IManagementService.ITableObserver observer){
        tableObservers.add(observer);
    }
    public void removeTableObserver(IManagementService.ITableObserver observer){
        if(tableObservers.contains(observer)){
            tableObservers.remove(observer);
        }
    }
    @Override
    public void update(IData data) {
        String cmd = data.getCommand();
        if (InterModuleCommunication.CommandToManagement.KITCHEN_DISH_FINISHED.equals(cmd)){
            // 能发出此条消息，说明table登陆成功，即fromId等于tableId，通知监听器
            InterModuleCommunication.Data.MK d = (InterModuleCommunication.Data.MK) data.getData();
            for(IManagementService.ITableObserver tableObserver : tableObservers) {
                tableObserver.dishFinish(d.dishName, d.tableId);
                d.tableId = data.getFromId(); //
            }
        } else if(InterModuleCommunication.CommandToManagement.CLIENT_REQUEST_SERVICE.equals(cmd)) {
            // 能发出此条消息，说明table登陆成功，即fromId等于tableId，通知监听器
            for(IManagementService.ITableObserver tableObserver : tableObservers) {
                tableObserver.requestService(data.getFromId());
            }
        } else if(InterModuleCommunication.CommandToManagement.CLIENT_TABLE_ONLINE.equals(cmd)) {
            // 验证table的登陆，这里的fromId与tableId不一致，fromId是临时id
            InterModuleCommunication.Data.MC mc = (InterModuleCommunication.Data.MC)data.getData();
            if(db.getSeatById(mc.tableId) == null){
                mc = InterModuleCommunication.Data.MC.loginAck(mc.tableId,false, "");
            } else {
                mc = InterModuleCommunication.Data.MC.loginAck(mc.tableId, true, "");
                for(IManagementService.ITableObserver tableObserver : tableObservers){
                    tableObserver.online(mc.tableId);
                }
            }
            peer.sendCommand(data.getFromId(), InterModuleCommunication.CommandToClient.MANAGEMENT_TABLE_ONLINE_ACK, mc);
        } else if(InterModuleCommunication.CommandToManagement.CLIENT_NEW_ORDER.equals(cmd)) {
            // 能发出此条消息，说明table登陆成功，即fromId等于tableId
            String tableId = data.getFromId();
            for(IManagementService.ITableObserver tableObserver : tableObservers) {
                tableObserver.newOrder(tableId);
            }
        }
    }
}
