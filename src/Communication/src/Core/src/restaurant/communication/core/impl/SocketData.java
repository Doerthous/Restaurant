package restaurant.communication.core.impl;

import java.io.Serializable;


public class SocketData{
    public static class Data implements ISocketWrapper.IData {
        public static class Address implements ISocketWrapper.IAddress {
            private String i;
            private Integer p;
            public Address(String i, Integer p) {
                this.i = i;
                this.p = p;
            }
            @Override
            public String getIp() {
                return i;
            }
            @Override
            public Integer getPort() {
                return p;
            }
        }
        private String si;
        private Integer sp;
        private String ti;
        private Integer tp;
        private Serializable d;
        private Data(String si, Integer sp, String ti, Integer tp, Serializable d) {
            this.si = si;
            this.sp = sp;
            this.ti = ti;
            this.tp = tp;
            this.d = d;
        }
        @Override
        public ISocketWrapper.IAddress getSourceAddress() {
            return new Address(si, sp);
        }
        @Override
        public ISocketWrapper.IAddress getTargetAddress() {
            return new Address(ti, tp);
        }
        @Override
        public Serializable getData() {
            return d;
        }

    }
    public static ISocketWrapper.IData packData(String sourceIp, Integer sourcePort,
                                                String targetIp, Integer targetPort, Serializable data) {
        return new Data(sourceIp, sourcePort, targetIp, targetPort, data);
    }
    public static ISocketWrapper.IAddress packAddress(String ip, Integer port) {
        return new Data.Address(ip, port);
    }

}
