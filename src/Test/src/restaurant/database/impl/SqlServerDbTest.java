package restaurant.database.impl;

import org.junit.Test; 
import org.junit.Before; 
import org.junit.After;
import restaurant.database.DbFactory;
import restaurant.database.IDb;
import restaurant.database.po.Dish;
import restaurant.database.po.Employee;
import restaurant.database.po.Seat;

import java.util.Date;
import java.util.List;

import static junit.framework.TestCase.assertNull;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
* SqlServerDb Tester. 
* 
* @author <Authors name> 
* @since <pre>十二月 27, 2017</pre>
* @version 1.0 
*/ 
public class SqlServerDbTest { 

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
    IDb db = DbFactory.getDb(DbFactory.DbType.SqlServer);
    db.init("jdbc:sqlserver://192.168.155.1:1433;DatabaseName=OrderDish", "ODuser","1234567890");
    List<Employee> ems = db.getAllEmployee();
    assertNotNull(ems);
    for(Employee em : ems){
        System.out.println(em.toString());
    }
} 

/** 
* 
* Method: getEmployeeById(String id) 
* 
*/ 
@Test
public void testGetEmployeeById() throws Exception { 
//TODO: Test goes here...
    IDb db = DbFactory.getDb(DbFactory.DbType.SqlServer);
    db.init("jdbc:sqlserver://192.168.155.1:1433;DatabaseName=OrderDish", "ODuser","1234567890");
    Employee em = db.getEmployeeById("c01");
    assertNotNull(em);
    System.out.println(em.toString());
    em = db.getEmployeeById(new Date().toString());
    assertNull(em);
} 

/** 
* 
* Method: getEmployeeByName(String name, Boolean fuzzy) 
* 
*/ 
@Test
public void testGetEmployeeByName() throws Exception { 
//TODO: Test goes here...
    IDb db = DbFactory.getDb(DbFactory.DbType.SqlServer);
    db.init("jdbc:sqlserver://192.168.155.1:1433;DatabaseName=OrderDish", "ODuser","1234567890");
    List<Employee> ems = db.getEmployeeByName("大伟",true);
    assertNotNull(ems);
    for(Employee em : ems){
        System.out.println(em.toString());
    }
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
    IDb db = DbFactory.getDb(DbFactory.DbType.SqlServer);
    db.init("jdbc:sqlserver://192.168.155.1:1433;DatabaseName=OrderDish", "ODuser","1234567890");
    List<Employee> ems = db.getEmployeeByPosition("服务员");
    assertNotNull(ems);
    for(Employee em : ems){
        System.out.println(em.toString());
    }
    ems = db.getEmployeeByPosition("000");
    assertNotNull(ems);
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
    IDb db = DbFactory.getDb(DbFactory.DbType.SqlServer);
    db.init("jdbc:sqlserver://192.168.155.1:1433;DatabaseName=OrderDish", "ODuser","1234567890");
    Boolean result = db.updateEmployee(new Employee("c01", "名字", new Date(), "男", "籍贯", "职位",
            1000, new Date(), "联系方式", "住址", "000",null));
    assertTrue(result);
    result = db.updateEmployee(new Employee("01", "名字", new Date(), "男", "籍贯", "职位",
            1000, new Date(), "联系方式", "住址", "密码",null));
    assertFalse(result);
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
    IDb db = DbFactory.getDb(DbFactory.DbType.SqlServer);
    db.init("jdbc:sqlserver://192.168.155.1:1433;DatabaseName=OrderDish", "ODuser","1234567890");
    List<Dish> dss = db.getDishByType("小吃");
    for(Dish ds : dss){
        System.out.println(ds.toString());
    }
} 

/** 
* 
* Method: getDishByPrice(Boolean bool)
* 
*/ 
@Test
public void testGetDishByPrice() throws Exception {
//TODO: Test goes here...
    IDb db = DbFactory.getDb(DbFactory.DbType.SqlServer);
    db.init("jdbc:sqlserver://192.168.155.1:1433;DatabaseName=OrderDish", "ODuser","1234567890");
    List<Dish> dss = db.getDishByPrice(true);
    for(Dish ds : dss){
        System.out.println(ds.toString());
    }
}

/** 
* 
* Method: getDishMenu() 
* 
*/ 
@Test
public void testGetDishMenu() throws Exception { 
//TODO: Test goes here...
    IDb db = DbFactory.getDb(DbFactory.DbType.SqlServer);
    db.init("jdbc:sqlserver://192.168.155.1:1433;DatabaseName=OrderDish", "ODuser","1234567890");
    List<Dish> dss = db.getDishMenu();
    for(Dish ds : dss){
        System.out.println(ds.toString());
    }
}

/**
*
* Method: getDishBySales(Boolean bool)
*
*/


    @Test
    public void testGetDishBySales() throws Exception {
        IDb db = DbFactory.getDb(DbFactory.DbType.SqlServer);
        db.init("jdbc:sqlserver://192.168.155.1:1433;DatabaseName=OrderDish", "ODuser","1234567890");
        List<Dish> dss = db.getDishBySales(false);
        for(Dish ds : dss){
            System.out.println(ds.toString());
        }
    }

/**
*
* Method: deleteDish(Dish dish)
*
*/
@Test
public void testDeleteDish() throws Exception {
//TODO: Test goes here...
    IDb db = DbFactory.getDb(DbFactory.DbType.SqlServer);
    db.init("jdbc:sqlserver://192.168.155.1:1433;DatabaseName=OrderDish", "ODuser","1234567890");
    Boolean result = db.deleteDish(new Dish("m065","鸡蛋",(float)15.0,"小吃",true,null));
    assertFalse(result);
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

    @Test
    public void testGetSeatById() throws Exception {
        IDb db = DbFactory.getDb(DbFactory.DbType.SqlServer);
        db.init("jdbc:sqlserver://192.168.155.1:1433;DatabaseName=OrderDish", "ODuser","1234567890");
        Seat st = db.getSeatById("bushi");
        assertNull(st);
        //System.out.println(st.toString());
    }
}
