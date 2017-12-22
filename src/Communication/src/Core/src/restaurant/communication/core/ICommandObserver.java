package restaurant.communication.core;

public interface ICommandObserver {
    void update(IData data);
}
