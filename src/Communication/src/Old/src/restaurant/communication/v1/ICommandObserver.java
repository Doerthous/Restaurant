package restaurant.communication.v1;

public interface ICommandObserver extends ICommands {
    void update(IData data);
}
