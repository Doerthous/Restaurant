package restaurant.communication.v2.test;


class Broadcast {
    public static void main(String[] args) throws InterruptedException {
        IPIdentify ii1 = new IPIdentify();
        Thread t = new Thread(ii1);
        t.start();
        t.join();
        System.out.println(ii1.getIp());
    }
}
