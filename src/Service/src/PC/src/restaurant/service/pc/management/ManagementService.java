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
import restaurant.service.core.vo.Table;
import restaurant.service.pc.vo.PO2VO;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;

public class ManagementService implements IManagementService {
    //private IPeer peer;
    //private IDb db;
    //private Debug debug;
    //
    private TableManager tableManager;
    private DishManager dishManager;
    private EmployeeManager employeeManager;
    private OrderManager orderManager;

    public ManagementService(IPeer peer, IDb db){
        //this.peer = peer;
        //this.db = db;
        peer.init();
        orderManager = new OrderManager(peer, db); // order的初始化必须先于table，数据需求，暂时的解决b办法
        tableManager = new TableManager(peer, db);
        dishManager = new DishManager(db);
        employeeManager = new EmployeeManager(peer, db);
        peer.start(InterModuleCommunication.ModuleId.MANAGEMENT);
        //debug = new Debug(getClass());
        //debug.on();
    }


    // dish 相关
    @Override
    public Boolean createDish(String name, Float price, String type, Boolean isSaled, String pictureUrl) {
        return dishManager.createDish(name, price, type, isSaled, pictureUrl);
    }
    @Override
    public Boolean modifiDish(String name, Float price, String type, Boolean isSaled, String pictureUrl) {
        return dishManager.modifiDish(name, price, type, isSaled, pictureUrl);
    }
    @Override
    public Boolean deleteDish(String name) {
        return dishManager.deleteDish(name);
    }
    @Override
    public List<Dish> getAllDish() {
        return dishManager.getAllDish();
    }
    @Override
    public List<Dish> getDishByType(String type) {
        return dishManager.getDishByType(type);
    }
    @Override
    public List<Dish> getDishByTypeSortByPrice(String type, Boolean asc) {
        return dishManager.getDishByTypeSortByPrice(type, asc);
    }
    @Override
    public List<Dish> getDishByTypeSortBySaledCount(String type, Boolean asc) {
        return dishManager.getDishByTypeSortBySaledCount(type, asc);
    }
    @Override
    public List<String> getDishTypes() {
        return dishManager.getDishTypes();
    }
    @Override
    public Dish getDishByName(String name) {
        return dishManager.getDishByName(name);
    }



    // employee 相关
    @Override
    public String createEmployee(String name, String position, Integer salary, String sex,
                                  String password, String phone, String nativePlace, String photoUrl) {
        return employeeManager.createEmployee(name, position, salary, sex, password, phone, nativePlace, photoUrl);
    }
    @Override
    public Boolean modifiEmployee(String id, String name, String position, Integer salary, String sex,
                                  String password, String phone, String nativePlace, String photoUrl) {
        return employeeManager.modifiEmployee(id, name, position, salary, sex, password, phone, nativePlace, photoUrl);
    }
    @Override
    public Boolean deleteEmployee(String id) {
        return employeeManager.deleteEmployee(id);
    }
    @Override
    public List<Employee> getAllEmployee() {
        return employeeManager.getAllEmployee();
    }
    @Override
    public List<Employee> getEmployeeByPosition(String position) {
        return employeeManager.getEmployeeByPosition(position);
    }
    @Override
    public List<Employee> getEmployeeByPositionAndSex(String position, String sex) {
        return employeeManager.getEmployeeByPositionAndSex(position, sex);
    }
    @Override
    public List<String> getAllEmployeePositions() {
        return employeeManager.getAllEmployeePositions();
    }
    @Override
    public Employee getEmployeeByCode(String code) {
        return employeeManager.getEmployeeByCode(code);
    }
    @Override
    public List<Employee> getOnlineWaiter() {
        return employeeManager.getOnlineWaiter();
    }
    @Override
    public void dishFinish(String dishName, String tableId, String waiterId) {
        employeeManager.dishFinish(dishName, tableId, waiterId);
    }
    @Override
    public void customerCall(String tableId, String waiterId) {
        employeeManager.customerCall(tableId, waiterId);
    }



    // table 相关
    @Override
    public String createTable(String type, Integer floor, Integer capacity) {
        return tableManager.createTable(type, floor, capacity);
    }
    @Override
    public Boolean modifiTable(String id, String type, Integer floor, Integer capacity) {
        return tableManager.modifyTable(id, type, floor, capacity);
    }
    @Override
    public Boolean deleteTable(String id) {
        return tableManager.deleteTable(id);
    }
    @Override
    public List<Table> getAllTable() {
        return tableManager.getAllTable();
    }
    @Override
    public Table getTableById(String id) {
        return tableManager.getTableById(id);
    }
    @Override
    public List<String> getTableTypes() {
        return tableManager.getTableTypes();
    }
    @Override
    public void openTable(String tableId, Integer customerCount) {
        tableManager.openTable(tableId);
    }
    @Override
    public void closeTable(String tableId) {
        tableManager.closeTable(tableId);
        orderManager.createOrder(tableId);
    }
    @Override
    public void addTableObserver(ITableObserver observer) {
        tableManager.addTableObserver(observer);
    }
    @Override
    public void removeTableObserver(ITableObserver observer) {
        tableManager.removeTableObserver(observer);
    }


    // order 相关
    @Override
    public Float getTotalConsumption(String tableId) {
        return orderManager.getTotalConsumption(tableId);
    }
    @Override
    public Map<String, Integer> getOrderDetail(String tableId) {
        return orderManager.getOrderDetail(tableId);
    }


    // debug
  /*  private void debug(String msg){
        debug.println("["+peer.getId()+"]: "+msg);
    }*/
}
