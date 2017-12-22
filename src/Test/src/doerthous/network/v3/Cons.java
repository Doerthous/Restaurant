package doerthous.network.v3;

public class Cons<CAR, CDR> {
    private CAR car;
    private CDR cdr;

    public Cons(CAR car, CDR cdr){
        this.car = car;
        this.cdr = cdr;
    }
    public CAR car(){
        return car;
    }

    public CDR cdr(){
        return cdr;
    }

    public Cons Cons(CAR car, CDR cdr){
        return null;
    }

    public static void main(String[] args) {
        new Cons(1,2);
    }
}
