package restaurant.service.pc.management;

import restaurant.communication.core.ICommandObserver;
import restaurant.communication.core.IData;
import restaurant.communication.core.IPeer;
import restaurant.database.IDb;
import restaurant.database.po.Detail;
import restaurant.database.po.Order;
import restaurant.service.core.IManagementService;
import restaurant.service.core.impl.InterModuleCommunication;
import restaurant.service.core.impl.utils.Debug;
import restaurant.service.core.vo.Dish;
import restaurant.service.core.vo.Employee;
import restaurant.service.pc.vo.PO2VO;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.*;

public class ManagementService implements IManagementService, ICommandObserver {
    private IPeer peer;
    private IDb db;
    private List<ITableObserver> tos;
    private Map<String, TableInfo> tis;
    // 缓存
    private List<restaurant.database.po.Dish> dishes;
    private List<restaurant.database.po.Employee> employees;

    public ManagementService(IPeer peer, IDb db){
        this.peer = peer;
        this.db = db;
        peer.init();
        peer.addCommandObserver(this, InterModuleCommunication.CommandToManagement.KITCHEN_DISH_FINISHED);
        peer.addCommandObserver(this, InterModuleCommunication.CommandToManagement.KITCHEN_ISSUE_REPORT);
        peer.addCommandObserver(this, InterModuleCommunication.CommandToManagement.WAITER_LOGIN);
        peer.addCommandObserver(this, InterModuleCommunication.CommandToManagement.WAITER_CHANGE_PASSWORD);
        peer.addCommandObserver(this, InterModuleCommunication.CommandToManagement.WAITER_ISSUE_REPORT);
        peer.addCommandObserver(this, InterModuleCommunication.CommandToManagement.CLIENT_REQUEST_SERVICE);
        peer.addCommandObserver(this, InterModuleCommunication.CommandToManagement.CLIENT_TABLE_ONLINE);
        peer.addCommandObserver(this, InterModuleCommunication.CommandToManagement.CLIENT_NEW_ORDER);
        peer.start(InterModuleCommunication.ModuleId.MANAGEMENT);
        tos = new ArrayList<>();
        tis = new HashMap<>();
        debug = new Debug(getClass());
        debug.on();
    }



    // dish 相关
    @Override
    public boolean createDish(String name, Float price, String type, Boolean isSaled, String pictureUrl) {
        debug("create new dish: ["+name+", "+price+", "+type+", "+isSaled+", "+pictureUrl+"]");
        boolean result;
        restaurant.database.po.Dish dish = PO2VO.newDishPo(name, name, price, type,isSaled, pictureUrl);
        result = db.insertDish(dish);
        return result;
    }
    @Override
    public boolean modifiDish(String name, Float price, String type, Boolean isSaled, String pictureUrl) {
        debug("create new dish: ["+name+", "+price+", "+type+", "+isSaled+", "+pictureUrl+"]");
        if(dishes == null){
            this.dishes = db.getAllDish();
        }
        restaurant.database.po.Dish targetDish = null;
        for(restaurant.database.po.Dish dish : this.dishes){
            if(dish.getName().equals(name)){
                targetDish = dish;
                break;
            }
        }
        restaurant.database.po.Dish dish = PO2VO.setDishPo(name, name, price, type,
                isSaled, pictureUrl, targetDish);
        return db.updateDish(dish);
    }
    @Override
    public boolean deleteDish(String name) {
        return db.deleteDish(PO2VO.newDishPo(name));
    }
    @Override
    public List<Dish> getAllDish() {
        this.dishes = db.getAllDish();
        List<Dish> dishes = new ArrayList<>();
        for(restaurant.database.po.Dish d: this.dishes){
            dishes.add(PO2VO.dish(d));
        }
        return dishes;
    }
    @Override
    public List<Dish> getDishByType(String type) {
        if(this.dishes == null){
            getAllDish();
        }
        List<Dish> dishes = new ArrayList<>();
        for(restaurant.database.po.Dish d: this.dishes){
            if(d.getType().equals(type)){
                dishes.add(PO2VO.dish(d));
            }
        }
        return dishes;
    }
    @Override
    public List<String> getDishTypes() {
        if(this.dishes == null){
            getAllDish();
        }
        List<String> types = new ArrayList<>();
        for(restaurant.database.po.Dish dish: dishes){
            if(!types.contains(dish.getType())){
                types.add(dish.getType());
            }
        }
        return types;
    }
    @Override
    public Dish getDishByName(String name) {
        List<restaurant.database.po.Dish> dishes = db.getDishByName(name);
        if(dishes.size() > 0) {
            restaurant.database.po.Dish dish = dishes.get(0);
            return PO2VO.dish(dish);
        }
        return null;
    }

    // employee 相关
    @Override
    public List<Employee> getAllEmployee() {
        this.employees = db.getAllEmployee();
        List<Employee> employees = new ArrayList<>();
        for(restaurant.database.po.Employee e: this.employees){
            employees.add(PO2VO.employee(e));
        }
        return employees;
    }
    @Override
    public List<Employee> getEmployeeByPosition(String position) {
        if(employees == null){
            getAllEmployee();
        }
        List<Employee> employees = new ArrayList<>();
        for(restaurant.database.po.Employee e: this.employees){
            if(e.getPosition().equals(position)){
                employees.add(PO2VO.employee(e));
            }
        }
        return employees;
    }
    @Override
    public List<String> getAllEmployeePositions() {
        if(employees == null){
            getAllEmployee();
        }
        List<String> positions = new ArrayList<>();
        for(restaurant.database.po.Employee e: employees){
            if(!positions.contains(e.getPosition())){
                positions.add(e.getPosition());
            }
        }
        return positions;
    }
    @Override
    public Employee getEmployeeByCode(String code) {
        restaurant.database.po.Employee employee = db.getEmployeeById(code);
        if(employee != null) {
            return PO2VO.employee(employee);
        }
        return null;
    }


    @Override
    public void update(IData data) {
        String cmd = data.getCommand();
        if (InterModuleCommunication.CommandToManagement.KITCHEN_DISH_FINISHED.equals(cmd)){
            InterModuleCommunication.Data.MK d = (InterModuleCommunication.Data.MK) data.getData();
            for(ITableObserver o : tos) {
                o.dishFinish(d.dishName, d.tableId);
            }
        } else if (InterModuleCommunication.CommandToManagement.KITCHEN_ISSUE_REPORT.equals(cmd)){
            //
        } else if(InterModuleCommunication.CommandToManagement.WAITER_LOGIN.equals(cmd)) {
            verifyWaiterLogin(data.getFromId(), (InterModuleCommunication.Data.MW) data.getData());
        } else if(InterModuleCommunication.CommandToManagement.WAITER_CHANGE_PASSWORD.equals(cmd)) {
            changeWaiterPassword(data.getFromId(), (InterModuleCommunication.Data.MW) data.getData());
        } else if(InterModuleCommunication.CommandToManagement.WAITER_ISSUE_REPORT.equals(cmd)) {
            //
        } else if(InterModuleCommunication.CommandToManagement.CLIENT_REQUEST_SERVICE.equals(cmd)) {
            for(ITableObserver o : tos) {
                o.requestService(data.getFromId());
            }
        } else if(InterModuleCommunication.CommandToManagement.CLIENT_TABLE_ONLINE.equals(cmd)) {
            verifyTableLogin(data.getFromId(), (InterModuleCommunication.Data.MC) data.getData());
        } else if(InterModuleCommunication.CommandToManagement.CLIENT_NEW_ORDER.equals(cmd)) {
            newOrder(data.getFromId(), (InterModuleCommunication.Data.MC) data.getData());
        }
    }

    //
    private Debug debug;
    private void debug(String msg){
        debug.println("["+peer.getId()+"]: "+msg);
    }

    // waiter 相关
    private void verifyWaiterLogin(String waiterId, InterModuleCommunication.Data.MW data){
        // 用数据库验证
        debug(data.account + " login in with password: " + data.password);
        //
        restaurant.database.po.Employee employee = db.getEmployeeById(data.account);
        if(employee == null){
            peer.sendCommand(waiterId, InterModuleCommunication.CommandToWaiter.MANAGEMENT_LOGIN_ACK,
                    InterModuleCommunication.Data.MW.loginAck(data.account,
                            false, "工号不存在"));
        } else if(!employee.getPassword().equals(data.password)) {
            peer.sendCommand(waiterId, InterModuleCommunication.CommandToWaiter.MANAGEMENT_LOGIN_ACK,
                    InterModuleCommunication.Data.MW.loginAck(data.account,
                            false, "密码不正确"));
        } else {
            peer.sendCommand(waiterId, InterModuleCommunication.CommandToWaiter.MANAGEMENT_LOGIN_ACK,
                    InterModuleCommunication.Data.MW.loginAck(data.account,
                            true, ""));
        }
    }
    private void changeWaiterPassword(String waiterId, InterModuleCommunication.Data.MW data) {
        // 用数据库验证
        debug(data.account + " change password with password: " +
                data.password + " to new password: " + data.newPassword);
        //
        restaurant.database.po.Employee employee = db.getEmployeeById(data.waiterId);
        if(employee == null){
            peer.sendCommand(waiterId, InterModuleCommunication.CommandToWaiter.MANAGEMENT_LOGIN_ACK,
                    InterModuleCommunication.Data.MW.changePasswordAck(false, "工号不存在"));
        } else if(!employee.getPassword().equals(data.password)) {
            peer.sendCommand(waiterId, InterModuleCommunication.CommandToWaiter.MANAGEMENT_LOGIN_ACK,
                    InterModuleCommunication.Data.MW.changePasswordAck(false, "密码不正确"));
        } else {
            employee.setPassword(data.newPassword);
            db.updateEmployee(employee);
            peer.sendCommand(waiterId, InterModuleCommunication.CommandToWaiter.MANAGEMENT_LOGIN_ACK,
                    InterModuleCommunication.Data.MW.changePasswordAck(true, ""));
        }
    }
    @Override
    public void dishFinish(String dishName, String tableId, String waiterId) {
        peer.sendCommand(waiterId, InterModuleCommunication.CommandToWaiter.MANAGEMENT_DISH_FINISHED,
                InterModuleCommunication.Data.MW.dishDistribute(dishName, tableId));
    }
    @Override
    public void customerCall(String tableId, String waiterId) {
        peer.sendCommand(waiterId, InterModuleCommunication.CommandToWaiter.MANAGEMENT_SERVE_CUSTOMER,
                InterModuleCommunication.Data.MW.customerCall(tableId));
    }
    // table 相关
    private void verifyTableLogin(String tableId, InterModuleCommunication.Data.MC data){
        debug("table[" +data.tableId+"] login ");
        if(db.getSeatById(data.tableId) == null){
            data = InterModuleCommunication.Data.MC.loginAck(data.tableId,false, "");
        } else {
            data = InterModuleCommunication.Data.MC.loginAck(data.tableId, true, "");
            tis.put(data.tableId, new TableInfo(data.tableId));
            for(ITableObserver to : tos){
                to.online(data.tableId);
            }
        }
        peer.sendCommand(tableId,
                InterModuleCommunication.CommandToClient.MANAGEMENT_TABLE_ONLINE_ACK, data);
    }
    private void newOrder(String tableId, InterModuleCommunication.Data.MC data){
        if(tis.keySet().contains(tableId)){
            TableInfo ti = tis.get(tableId);
            ti.setTotalCost(data.order.totalCost);
            ti.setOrderId(generateOrderId(tableId));
            ti.setOrder(data.order.orderDetail);
            for(ITableObserver o : tos) {
                o.newOrder(tableId);
            }
        }
    }
    static class TableInfo implements ITableInfo{
        private String tableId;
        private State state;
        private Integer customerCount;
        private Float totalCost;
        private String orderId;
        private Map<String, Integer> order;

        // 创建Free table的info
        public TableInfo(String tableId) {
            this(tableId, State.FREE, 0, 0f, null);
        }

        private TableInfo(String tableId, State state, Integer customerCount, Float totalCost, String orderId) {
            this.tableId = tableId;
            this.state = state;
            this.customerCount = customerCount;
            this.totalCost = totalCost;
            this.orderId = orderId;
        }

        @Override
        public String getTableId() {
            return tableId;
        }

        @Override
        public State getTableState() {
            return state;
        }

        @Override
        public Integer getCustomerCount() {
            return customerCount;
        }

        @Override
        public Float getTotalCost() {
            return totalCost;
        }

        @Override
        public String getOrderId() {
            return orderId;
        }

        @Override
        public Map<String, Integer> getOrder() {
            return order;
        }

        public void setTableId(String tableId) {
            this.tableId = tableId;
        }

        public void setState(State state) {
            this.state = state;
        }

        public void setCustomerCount(Integer customerCount) {
            this.customerCount = customerCount;
        }

        public void setTotalCost(Float totalCost) {
            this.totalCost = totalCost;
        }

        public void setOrderId(String orderId) {
            if(orderId != null) {
                this.orderId = orderId;
            }
        }

        public void setOrder(Map<String, Integer> order) {
            this.order = order;
        }
    }
    private static String generateOrderId(String tableId){
        return tableId+"-"+new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
    }
    @Override
    public void openTable(String tableId, Integer customerCount) {
        if(tis.keySet().contains(tableId)) {
            peer.sendCommand(tableId, InterModuleCommunication.CommandToClient.MANAGEMENT_OPEN_TABLE, null);
            TableInfo ti = tis.get(tableId);
            ti.setCustomerCount(customerCount);
            ti.setState(ITableInfo.State.BUSY);
        }
    }
    @Override
    public void closeTable(String tableId) {
        peer.sendCommand(tableId, InterModuleCommunication.CommandToClient.MANAGEMENT_CLOSE_TABLE, null);
        TableInfo ti = tis.get(tableId);
        if(ti != null) {
            if (db.insertOrder(new Order(ti.getOrderId(), new Date(), tableId, ti.getTotalCost()))) {
                Map<String, Integer> order = ti.getOrder();
                for (String dish : order.keySet()) {
                    if (db.insertDetial(new Detail(ti.getOrderId(), dish, order.get(dish)))) {
                        debug("订单插入失败");
                    }
                }
            } else {
                debug("订单插入失败");
            }
        }
        tis.put(tableId, new TableInfo(tableId));
    }
    @Override
    public ITableInfo getTableInfo(String tableId) {
        return tis.get(tableId);
    }
    @Override
    public void addTableObserver(ITableObserver observer) {
        tos.add(observer);
    }
    @Override
    public void removeTableObserver(ITableObserver observer) {
        if(tos.contains(observer)){
            tos.remove(observer);
        }
    }

}
