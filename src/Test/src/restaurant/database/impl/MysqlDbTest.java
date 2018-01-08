package restaurant.database.impl;

import org.junit.Test; 
import org.junit.Before; 
import org.junit.After;
import restaurant.database.IDb;
import restaurant.database.po.Dish;

import java.sql.Connection;
import java.util.List;

import static org.junit.Assert.assertNotNull;

/** 
* MysqlDb Tester. 
* 
* @author <Authors name> 
* @since <pre>һ�� 1, 2018</pre> 
* @version 1.0 
*/ 
public class MysqlDbTest { 

@Before
public void before() throws Exception { 
} 

@After
public void after() throws Exception { 
} 

/** 
* 
* Method: init(String url, String user, String password) 
* 
*/ 
@Test
public void testInit() throws Exception { 
//TODO: Test goes here...
} 

/** 
* 
* Method: getAllEmployee() 
* 
*/ 
@Test
public void testGetAllEmployee() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: getEmployeeById(String id) 
* 
*/ 
@Test
public void testGetEmployeeById() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: getEmployeeByName(String name, Boolean fuzzy) 
* 
*/ 
@Test
public void testGetEmployeeByName() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: getEmployeeByAgeRange(Integer inf, Integer sup) 
* 
*/ 
@Test
public void testGetEmployeeByAgeRange() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: getEmployeeBySex(Boolean isMale) 
* 
*/ 
@Test
public void testGetEmployeeBySex() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: getEmployeeByPosition(String position) 
* 
*/ 
@Test
public void testGetEmployeeByPosition() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: getEmployeeBySalaryRange(Integer inf, Integer sup) 
* 
*/ 
@Test
public void testGetEmployeeBySalaryRange() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: getEmployeeByHiredateRange(Integer inf, Integer sup) 
* 
*/ 
@Test
public void testGetEmployeeByHiredateRange() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: getEmployeeByNativePlace(String nativePlace, Boolean fuzzy) 
* 
*/ 
@Test
public void testGetEmployeeByNativePlace() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: updateEmployee(Employee employee) 
* 
*/ 
@Test
public void testUpdateEmployee() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: insertEmployee(Employee employee) 
* 
*/ 
@Test
public void testInsertEmployee() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: deleteEmployee(Employee employee) 
* 
*/ 
@Test
public void testDeleteEmployee() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: getAllDish() 
* 
*/ 
@Test
public void testGetAllDish() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: getDishByName(String name) 
* 
*/ 
@Test
public void testGetDishByName() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: getDishByType(String type) 
* 
*/ 
@Test
public void testGetDishByType() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: getDishByPrice(Boolean bool) 
* 
*/ 
@Test
public void testGetDishByPrice() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: getDishMenu() 
* 
*/ 
@Test
public void testGetDishMenu() throws Exception { 
//TODO: Test goes here...
    IDb db = new MysqlDb();
    db.init("localhost", (short) 3306, "orderdish", "root", "123456");
    List<Dish> menu = db.getDishMenu();
    for(Dish dish: menu){
        System.out.println(dish.toString());
    }
} 

/** 
* 
* Method: getDishBySales(Boolean bool) 
* 
*/ 
@Test
public void testGetDishBySales() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: insertDish(Dish dish) 
* 
*/ 
@Test
public void testInsertDish() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: deleteDish(Dish dish) 
* 
*/ 
@Test
public void testDeleteDish() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: updateDish(Dish dish) 
* 
*/ 
@Test
public void testUpdateDish() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: getAllSeat() 
* 
*/ 
@Test
public void testGetAllSeat() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: getSeatById(String id) 
* 
*/ 
@Test
public void testGetSeatById() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: getSeatByType(String type) 
* 
*/ 
@Test
public void testGetSeatByType() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: getSeatByCapacity(int capacity) 
* 
*/ 
@Test
public void testGetSeatByCapacity() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: insertSeat(Seat seat) 
* 
*/ 
@Test
public void testInsertSeat() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: deleteSeat(Seat seat) 
* 
*/ 
@Test
public void testDeleteSeat() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: updateSeat(Seat seat) 
* 
*/ 
@Test
public void testUpdateSeat() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: insertOrder(Order order) 
* 
*/ 
@Test
public void testInsertOrder() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: insertDetial(Detail detail) 
* 
*/ 
@Test
public void testInsertDetial() throws Exception { 
//TODO: Test goes here... 
} 


/** 
* 
* Method: getConnection() 
* 
*/ 
@Test
public void testGetConnection() throws Exception { 
//TODO: Test goes here... 
/* 
try { 
   Method method = MysqlDb.getClass().getMethod("getConnection"); 
   method.setAccessible(true); 
   method.invoke(<Object>, <Parameters>); 
} catch(NoSuchMethodException e) { 
} catch(IllegalAccessException e) { 
} catch(InvocationTargetException e) { 
} 
*/ 
} 

} 
