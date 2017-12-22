package doerthous.other.cli;


import restaurant.communication.v1.ICommands;

public interface ITestCommands extends ICommands {
    int CMD_SHUTDOWN = 100000;
    int CMD_SHUTDOWN_CANCEL = 300000;
}
