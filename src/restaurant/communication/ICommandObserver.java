package restaurant.communication;

public interface ICommandObserver extends ICommands {
    void update(IData data);
}
