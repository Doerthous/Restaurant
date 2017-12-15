package restaurant.communication.v2;

public interface ICommandObserver {
    void update(IData data);
}
