package restaurant.service.core.impl.utils;

public class Debug {
    private Class clazz;
    private boolean on;
    public Debug(Class clazz){
        this.clazz = clazz;
    }
    public void on(){
        on = true;
    }
    public void off(){
        on = false;
    }
    public void println(String message) {
        if(on) {
            System.out.println(clazz.toString() + ": " + message);
        }
    }
}
