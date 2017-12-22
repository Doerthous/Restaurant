package restaurant.service.pc.management;

import restaurant.communication.core.ICommandObserver;
import restaurant.communication.core.IData;
import restaurant.communication.core.IPeer;
import restaurant.service.core.IManagementService;
import restaurant.service.core.impl.InterModuleCommunication;

public class ManagementService implements IManagementService, ICommandObserver {
    private IPeer peer;
    private IDishFinishObserver dishFinishObserver;

    public ManagementService(IPeer peer){
        this.peer = peer;
        peer.start();
        peer.addCommandObserver(this, InterModuleCommunication.CommandToManagement.KITCHEN_DISH_FINISHED);
    }
    @Override
    public void dishFinish(String dishName, String tableId, String waiterId) {
        peer.sendCommand(waiterId, InterModuleCommunication.CommandToWaiter.MANAGEMENT_DISH_FINISHED,
                new InterModuleCommunication.Data.DishFinish("", tableId));
    }

    @Override
    public void customerCall(String tableId, String waiterId) {

    }

    @Override
    public void tableStart(String tableId) {

    }

    @Override
    public void addDishFinishObserver(IDishFinishObserver observer) {
        dishFinishObserver = observer;
    }

    @Override
    public void update(IData data) {
        String cmd = data.getCommand();
        if (InterModuleCommunication.CommandToManagement.KITCHEN_DISH_FINISHED.equals(cmd)){
            InterModuleCommunication.Data.DishFinish d =
                    (InterModuleCommunication.Data.DishFinish) data.getData();
            dishFinishObserver.dishFinish(d.dishName, d.tableId);
        } else if (InterModuleCommunication.CommandToManagement.KITCHEN_ISSUE_REPORT.equals(cmd)){
            InterModuleCommunication.Data.DishFinish d =
                    (InterModuleCommunication.Data.DishFinish) data.getData();
            dishFinishObserver.dishFinish(d.dishName, d.tableId);
        }
    }
}
