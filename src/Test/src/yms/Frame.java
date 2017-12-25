package yms;

import restaurant.ui.component.BaseFrame;

import java.awt.*;

/**
 * Created by YMS on 2017/12/22.
 */
public class Frame extends BaseFrame {
    public Frame() throws HeadlessException {
        initUICompoent();
    }

    //组件
    private MainUI mainUI;
    private DishUI dishUI;
    private EmployeeUI employeeUI;
    private SystemUI systemUI;
    //菜品管理界面
    private DishManageUI dishManageUI;
    private SetMeatUI setMeatUI;
    private CategoryManageUI categoryManageUI;
    public void initUICompoent() {
        //主界面
        mainUI = new MainUI(this);
        dishUI = new DishUI(this);
        employeeUI = new EmployeeUI(this);
        systemUI =new SystemUI(this);
        //菜品管理界面
        dishManageUI = new DishManageUI(this);
        setMeatUI = new SetMeatUI(this);
        categoryManageUI = new CategoryManageUI(this);
        add("Main", mainUI);
        add("Dish", dishUI);
        add("Employee", employeeUI);
        add("System", systemUI);
        //菜品管理界面
        add("DishManage", dishManageUI);
        add("SetMeat", setMeatUI);
        add("CategoryManage", categoryManageUI);
    }
    public void main(){
        show("Main");
    }
    public void dish(){
        show("Dish");
    }
    public void employee(){
        show("Employee");
    }
    public void system(){
        show( "System");
    }
    public void dishmanage(){show("DishManage");}
    public void categorymanage(){show("CategoryManage");}
    public void setmeat(){show("SetMeat");}
    public  void predishmanage(){}
    public  void nextdishmanage(){}
    public  void datacount(){}
}


