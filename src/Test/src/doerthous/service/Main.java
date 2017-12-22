package doerthous.service;


import restaurant.service.core.IManagementService;
import restaurant.service.pc.ServiceFactory;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        IManagementService ms = ServiceFactory.getManagementService();
        ms.addObserver(new IManagementService.IObserver() {
            @Override
            public void dishFinish(String dishName, String tableId) {
                System.out.println(tableId + "'s " + dishName+" finished");
                Scanner scanner = new Scanner(System.in);
                System.out.print("waiter id: ");
                ms.dishFinish(dishName, tableId, scanner.nextLine());
            }

            @Override
            public void requestService(String tableId) {
                System.out.println(tableId + "request service");
                Scanner scanner = new Scanner(System.in);
                System.out.print("waiter id: ");
                ms.customerCall(tableId, scanner.nextLine());
            }
        });
    }
}
