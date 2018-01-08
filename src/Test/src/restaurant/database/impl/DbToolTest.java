package restaurant.database.impl;

import org.junit.Test; 
import org.junit.Before; 
import org.junit.After;

import java.sql.Connection;

import static org.junit.Assert.assertNotNull;

/** 
* DbTool Tester. 
* 
* @author <Authors name> 
* @since <pre>һ�� 1, 2018</pre> 
* @version 1.0 
*/ 
public class DbToolTest { 

@Before
public void before() throws Exception { 
} 

@After
public void after() throws Exception { 
} 

/** 
* 
* Method: getSqlServerConnection(String host, Short port, String database, String user, String password) 
* 
*/ 
@Test
public void testGetSqlServerConnection() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: getMysqlConnection(String host, Short port, String database, String user, String password) 
* 
*/ 
@Test
public void testGetMysqlConnection() throws Exception { 
//TODO: Test goes here...
    Class.forName("com.mysql.jdbc.Driver");
    Connection conn = DbTool.getMysqlConnection("localhost", (short) 3306,
            "orderdish","root","123456");

    assertNotNull(conn);
} 


} 
