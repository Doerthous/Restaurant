package restaurant.service.pc.management;

import restaurant.communication.core.ICommandObserver;
import restaurant.communication.core.IData;
import restaurant.communication.core.IPeer;
import restaurant.database.IDb;
import restaurant.database.po.Employee;
import restaurant.service.core.IManagementService;
import restaurant.service.core.impl.InterModuleCommunication;
import restaurant.service.core.impl.utils.Debug;

public class ManagementService implements IManagementService, ICommandObserver {
    private IPeer peer;
    private IDb db;
    private IObserver observer;


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
        peer.start(InterModuleCommunication.ModuleId.MANAGEMENT);
        debug = new Debug(getClass());
        debug.on();
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

    @Override
    public void tableStart(String tableId) {

    }

    @Override
    public void addObserver(IObserver observer) {
        this.observer = observer;
    }

    @Override
    public void update(IData data) {
        String cmd = data.getCommand();
        if (InterModuleCommunication.CommandToManagement.KITCHEN_DISH_FINISHED.equals(cmd)){
            InterModuleCommunication.Data.MK d = (InterModuleCommunication.Data.MK) data.getData();
            observer.dishFinish(d.dishName, d.tableId);
        } else if (InterModuleCommunication.CommandToManagement.KITCHEN_ISSUE_REPORT.equals(cmd)){
            //
        } else if(InterModuleCommunication.CommandToManagement.WAITER_LOGIN.equals(cmd)) {
            verifyWaiterLogin(data.getFromId(), (InterModuleCommunication.Data.MW) data.getData());
        } else if(InterModuleCommunication.CommandToManagement.WAITER_CHANGE_PASSWORD.equals(cmd)) {
            changeWaiterPassword(data.getFromId(), (InterModuleCommunication.Data.MW) data.getData());
        } else if(InterModuleCommunication.CommandToManagement.WAITER_ISSUE_REPORT.equals(cmd)) {
            //
        } else if(InterModuleCommunication.CommandToManagement.CLIENT_REQUEST_SERVICE.equals(cmd)) {
            observer.requestService(data.getFromId());
        }
    }

    private Debug debug;
    private void debug(String msg){
        debug.println("["+peer.getId()+"]: "+msg);
    }

    // test data
    private void verifyWaiterLogin(String waiterId, InterModuleCommunication.Data.MW data){
        // 用数据库验证
        debug(data.account + " login in with password: " + data.password);
        //
        Employee employee = db.getEmployeeById(data.account);
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
        Employee employee = db.getEmployeeById(data.waiterId);
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
}
