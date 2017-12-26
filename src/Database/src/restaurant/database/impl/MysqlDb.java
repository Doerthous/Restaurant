package restaurant.database.impl;

import restaurant.database.IDb;
import restaurant.database.po.Dish;
import restaurant.database.po.Employee;

import java.util.List;

public class MysqlDb implements IDb {
    @Override
    public void init(String url, String user, String password) {

    }

    @Override
    public List<Employee> getAllEmployee() {
        return null;
    }

    @Override
    public Employee getEmployeeById(String id) {
        return null;
    }

    @Override
    public List<Employee> getEmployeeByName(String name, Boolean fuzzy) {
        return null;
    }

    @Override
    public List<Employee> getEmployeeByAgeRange(Integer inf, Integer sup) {
        return null;
    }

    @Override
    public List<Employee> getEmployeeBySex(Boolean isMale) {
        return null;
    }

    @Override
    public List<Employee> getEmployeeByPosition(String position) {
        return null;
    }

    @Override
    public List<Employee> getEmployeeBySalaryRange(Integer inf, Integer sup) {
        return null;
    }

    @Override
    public List<Employee> getEmployeeByHiredateRange(Integer inf, Integer sup) {
        return null;
    }

    @Override
    public List<Employee> getEmployeeByNativePlace(String nativePlace, Boolean fuzzy) {
        return null;
    }

    @Override
    public Boolean updateEmployee(Employee employee) {
        return null;
    }

    @Override
    public List<Dish> getAllDish() {
        return null;
    }

    @Override
    public List<Dish> getDishByName(String name) {
        return null;
    }

    @Override
    public List<Dish> getDishByType(String type) {
        return null;
    }

    @Override
    public List<Dish> getDishByPriceRange(Float inf, Float sup) {
        return null;
    }

    @Override
    public List<Dish> getDishMenu() {
        return null;
    }
}
