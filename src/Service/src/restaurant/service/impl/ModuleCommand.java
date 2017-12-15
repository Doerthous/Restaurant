package restaurant.service.impl;

/*
    这是各个业务层（service）模块间的通讯层（communication）命令

    格式：
        模块名_TO_模块名_命令说明
 */
public interface ModuleCommand {
    /*
        客户端向后厨发送新订单
     */
    String CLIENT_TO_KITCHEN_NEW_ORDER = "CTKNO";
    /*
        客户端询问后厨id
     */
    String CLIENT_TO_KITCHEN_WHO_IS_KITCHEN = "CTKWIK";
    /*
        后厨回应客户端的“id询问”
     */
    String KITCHEN_TO_CLIENT_WHO_IS_KITCHEN_ACK = "KTCWIKA";
}
