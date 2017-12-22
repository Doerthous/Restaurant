package doerthous.service;


import restaurant.service.core.IManagementService;
import restaurant.service.pc.ServiceFactory;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        IManagementService ms = ServiceFactory.getManagementService();
        ms.addDishFinishObserver((dishName, tableId) -> {
            System.out.println(tableId + "'s " + dishName+" finished");
            Scanner scanner = new Scanner(System.in);
            System.out.print("waiter id: ");
            ms.dishFinish(dishName, tableId, scanner.nextLine());
        });
    }
}
