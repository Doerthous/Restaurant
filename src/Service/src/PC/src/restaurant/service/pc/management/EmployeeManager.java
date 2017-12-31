package restaurant.service.pc.management;

import restaurant.communication.core.ICommandObserver;
import restaurant.communication.core.IData;
import restaurant.communication.core.IPeer;
import restaurant.database.IDb;
import restaurant.service.core.impl.InterModuleCommunication;
import restaurant.service.core.vo.Employee;
import restaurant.service.pc.vo.PO2VO;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class EmployeeManager implements ICommandObserver {
    private IPeer peer;
    private IDb db;
    // 缓存
    private List<restaurant.database.po.Employee> employees;
    private List<Employee> onlineWaiters; // 在线服务员

    public EmployeeManager(IPeer peer, IDb db) {
        this.peer = peer;
        this.db = db;
        this.onlineWaiters = new ArrayList<>();
        peer.addCommandObserver(this, InterModuleCommunication.CommandToManagement.KITCHEN_ISSUE_REPORT);
        peer.addCommandObserver(this, InterModuleCommunication.CommandToManagement.WAITER_LOGIN);
        peer.addCommandObserver(this, InterModuleCommunication.CommandToManagement.WAITER_CHANGE_PASSWORD);
        peer.addCommandObserver(this, InterModuleCommunication.CommandToManagement.WAITER_ISSUE_REPORT);
    }

    // 数据库操作
    private String newEmployeeId(){
        int max = 0;
        for(restaurant.database.po.Employee employee:employees){
            if(isDigits(employee.getId())){
                int id = Integer.valueOf(employee.getId());
                if(id > max){
                    max = id;
                }
            }
        }
        String newId = String.valueOf(max+1);
        int l = 11 - newId.length();
        String prefix = "";
        for (int i = 0; i < l; ++i){
            prefix += "0";
        }
        return prefix+newId;
    }
    private static boolean isDigits(String str){
        return Pattern.compile("^\\d+$").matcher(str).find();
    }
    public String createEmployee(String name, String position, Integer salary, String sex,
                                 String password, String phone, String nativePlace, String photoUrl) {
        String id = newEmployeeId();
        restaurant.database.po.Employee employee = PO2VO.newEmployeePo(id, name, photoUrl,
                position, salary, password, sex, nativePlace, phone);
        // debug("New employee: " + employee.toString());
        if(db.insertEmployee(employee)){
            employees.add(employee);
            return id;
        }
        return null;
    }
    public Boolean modifiEmployee(String id, String name, String position, Integer salary, String sex,
                                  String password, String phone, String nativePlace, String photoUrl) {
        if(employees == null){
            this.employees = db.getAllEmployee();
        }
        restaurant.database.po.Employee targetEmployee = null;
        for(restaurant.database.po.Employee employee : this.employees){
            if(employee.getId().equals(id)){
                targetEmployee = employee;
                break;
            }
        }
        restaurant.database.po.Employee employee = PO2VO.setEmployeePo(id, name, position, salary,
                sex, password, phone, nativePlace, photoUrl, targetEmployee);
        //debug("Update employee: " + employee.toString());
        if(employee != null){
            return db.updateEmployee(employee);
        }
        return false;
    }
    public Boolean deleteEmployee(String id) {
        restaurant.database.po.Employee targetEmployee = PO2VO.newEmployeePo(id);
        // debug("Delete employee: " + targetEmployee.toString());
        if(db.deleteEmployee(targetEmployee)){
            getAllEmployee();
            return true;
        }
        return false;
    }
    public List<restaurant.service.core.vo.Employee> getAllEmployee() {
        this.employees = db.getAllEmployee();
        List<restaurant.service.core.vo.Employee> employees = new ArrayList<>();
        for(restaurant.database.po.Employee e: this.employees){
            employees.add(PO2VO.employee(e));
        }
        return employees;
    }
    public List<restaurant.service.core.vo.Employee> getEmployeeByPosition(String position) {
        if(employees == null){
            getAllEmployee();
        }
        List<restaurant.service.core.vo.Employee> employees = new ArrayList<>();
        for(restaurant.database.po.Employee e: this.employees){
            if(position.equals("全部")|| e.getPosition().equals(position)){
                employees.add(PO2VO.employee(e));
            }
        }
        return employees;
    }
    public List<restaurant.service.core.vo.Employee> getEmployeeByPositionAndSex(String position, String sex) {
        if(employees == null){
            getAllEmployee();
        }
        List<restaurant.service.core.vo.Employee> employees = new ArrayList<>();
        for(restaurant.database.po.Employee e: this.employees){
            if(position.equals("全部")|| e.getPosition().equals(position)){
                if(sex.equals("全部") || sex.equals(e.getSex())) {
                    employees.add(PO2VO.employee(e));
                }
            }
        }
        return employees;
    }
    public List<String> getAllEmployeePositions() {
        if(employees == null){
            getAllEmployee();
        }
        List<String> positions = new ArrayList<>();
        positions.add("全部");
        for(restaurant.database.po.Employee e: employees){
            if(!positions.contains(e.getPosition())){
                positions.add(e.getPosition());
            }
        }
        return positions;
    }
    public restaurant.service.core.vo.Employee getEmployeeByCode(String code) {
        restaurant.database.po.Employee employee = db.getEmployeeById(code); // 如果不返回空，怎么表示不存在的数据？
        if(employee != null) {
            return PO2VO.employee(employee);
        }
        return null;
    }


    // 通讯
    public List<Employee> getOnlineWaiter() {
        return onlineWaiters;
    }
    public void dishFinish(String dishName, String tableId, String waiterId) {
        peer.sendCommand(waiterId, InterModuleCommunication.CommandToWaiter.MANAGEMENT_DISH_FINISHED,
                InterModuleCommunication.Data.MW.dishDistribute(dishName, tableId));
    }
    public void customerCall(String tableId, String waiterId) {
        peer.sendCommand(waiterId, InterModuleCommunication.CommandToWaiter.MANAGEMENT_SERVE_CUSTOMER,
                InterModuleCommunication.Data.MW.customerCall(tableId));
    }

    @Override
    public void update(IData data) {
        String cmd = data.getCommand();
        if (InterModuleCommunication.CommandToManagement.KITCHEN_ISSUE_REPORT.equals(cmd)){
            //
        } else if(InterModuleCommunication.CommandToManagement.WAITER_LOGIN.equals(cmd)) {
            InterModuleCommunication.Data.MW mw = (InterModuleCommunication.Data.MW)data.getData();
            String fromId = data.getFromId();
            String waiterId = mw.account;
            // 用数据库验证
            restaurant.database.po.Employee employee = db.getEmployeeById(waiterId);
            if(employee == null){
                peer.sendCommand(fromId, InterModuleCommunication.CommandToWaiter.MANAGEMENT_LOGIN_ACK,
                        InterModuleCommunication.Data.MW.loginAck(waiterId,
                                false, "工号不存在"));
            } else if(!employee.getPassword().equals(mw.password)) {
                peer.sendCommand(fromId, InterModuleCommunication.CommandToWaiter.MANAGEMENT_LOGIN_ACK,
                        InterModuleCommunication.Data.MW.loginAck(waiterId,
                                false, "密码不正确"));
            } else {
                peer.sendCommand(fromId, InterModuleCommunication.CommandToWaiter.MANAGEMENT_LOGIN_ACK,
                        InterModuleCommunication.Data.MW.loginAck(waiterId,
                                true, ""));
                onlineWaiters.add(PO2VO.employee(employee));
            }
        } else if(InterModuleCommunication.CommandToManagement.WAITER_CHANGE_PASSWORD.equals(cmd)) {
            InterModuleCommunication.Data.MW mw = (InterModuleCommunication.Data.MW)data.getData();
            String waiterId = data.getFromId();
            // 用数据库验证
            restaurant.database.po.Employee employee = db.getEmployeeById(mw.waiterId);
            if(employee == null){
                peer.sendCommand(waiterId, InterModuleCommunication.CommandToWaiter.MANAGEMENT_LOGIN_ACK,
                        InterModuleCommunication.Data.MW.changePasswordAck(false, "工号不存在"));
            } else if(!employee.getPassword().equals(mw.password)) {
                peer.sendCommand(waiterId, InterModuleCommunication.CommandToWaiter.MANAGEMENT_LOGIN_ACK,
                        InterModuleCommunication.Data.MW.changePasswordAck(false, "密码不正确"));
            } else {
                employee.setPassword(mw.newPassword);
                db.updateEmployee(employee);
                peer.sendCommand(waiterId, InterModuleCommunication.CommandToWaiter.MANAGEMENT_LOGIN_ACK,
                        InterModuleCommunication.Data.MW.changePasswordAck(true, ""));
            }
        } else if(InterModuleCommunication.CommandToManagement.WAITER_ISSUE_REPORT.equals(cmd)) {
            //
        }
    }
}
