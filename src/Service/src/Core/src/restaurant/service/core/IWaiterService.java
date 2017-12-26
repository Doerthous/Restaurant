package restaurant.service.core;

public interface IWaiterService {
    String getAccount();

    Boolean login(String account, String password);
    String getLoginFailedReason();

    Boolean changePassword(String account, String oldPassword, String newPassword);
    String getChangePasswrodFailedReason();

    interface INotificationObserver {
        void dishFinish(String tableId, String type);
        void customerCall(String tableId);
    }
    void addNotificationObserver(INotificationObserver observer);
    interface IAccountObserver {
        void forceGoDown();
    }
    void addAccountObserver(IAccountObserver observer);

    void reportIssue(String waiterId, String issue);
}
