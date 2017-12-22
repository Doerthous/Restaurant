package restaurant.service.core.impl;

import java.io.Serializable;

/*
    业务层（service）模块间的通讯

 */
public final class InterModuleCommunication {
    /*
        模块名称
     */
    public static final class ModuleId {
        public static final String MANAGEMENT = "management";
        public static final String KITCHEN = "kitchen";
    }

    /*
        模块间通信命令
     */
    public static final class CommandToManagement {
        /*
            后厨通知管理端菜品完成
         */
        public static final String KITCHEN_DISH_FINISHED = CommandToManagement.class.getName()+"[KDF]";
        /*
            后厨上报问题到管理端
         */
        public static final String KITCHEN_ISSUE_REPORT = CommandToManagement.class.getName()+"[KIR]";
        /*
            服务员请求管理端验证登陆
         */
        public static final String WAITER_LOGIN = CommandToManagement.class.getName()+"[WL]";
        /*
            服务员上报问题到管理端
         */
        public static final String WAITER_ISSUE_REPORT = CommandToManagement.class.getName()+"[WIR]";
        /*
            服务员请求管理端修改密码
         */
        public static final String WAITER_CHANGE_PASSWORD = CommandToManagement.class.getName()+"[WCP]";
    }
    public static final class CommandToWaiter {
        /*
            登陆验证回复
         */
        public static final String MANAGEMENT_LOGIN_ACK = CommandToWaiter.class.getName()+"[MLA]";
        /*
            登陆验证回复
         */
        public static final String MANAGEMENT_CHANGE_PASSWORD_ACK = CommandToWaiter.class.getName()+"[MCPA]";
        /*
            管理端通知服务员服务
         */
        public static final String MANAGEMENT_SERVE_CUSTOMER = CommandToWaiter.class.getName()+"[MSC]";
        /*
           管理端通知服务员传菜
        */
        public static final String MANAGEMENT_DISH_FINISHED = CommandToWaiter.class.getName()+"[MDF]";
    }
    public static final class CommandToKitchen {
        /*
            客户端向后厨发送新订单
         */
        public static final String CLIENT_NEW_ORDER = CommandToKitchen.class.getName()+"[CNO]";
    }
    public static final class CommandToClient {

    }
    public static final class CommandToAll {

    }

    /*
        模块间传输的数据的格式
     */
    public static final class Data {
        public static final class DishFinish implements Serializable {
            public String dishName;
            public String tableId;

            public DishFinish(String dishName, String tableId) {
                this.dishName = dishName;
                this.tableId = tableId;
            }
        }
        /*
            Management and Waiter
         */
        public static final class MW implements Serializable {
            public String account;
            public String password;
            public Boolean isSuccess;
            public String failedReason;
            public String tableId;
            public String type;
            public String waiterId;
            public String issue;
            public String newPassword;

            public static MW login(String account, String password) {
                MW m = new MW();
                m.account = account;
                m.password = password;
                return m;
            }

            public static MW loginAck(String account, Boolean isSuccess, String failedReason) {
                MW m = new MW();
                m.account = account;
                m.isSuccess = isSuccess;
                m.failedReason = failedReason;
                return m;
            }

            public static MW notification(String tableId, String type){
                MW m = new MW();
                m.tableId = tableId;
                m.type = type;
                return m;
            }

            public static MW issueReport(String waiterId, String issue){
                MW m = new MW();
                m.waiterId = waiterId;
                m.issue = issue;
                return m;
            }

            public static MW changePassword(String waiterId, String password, String newPassword){
                MW m = new MW();
                m.waiterId = waiterId;
                m.password = password;
                m.newPassword = newPassword;
                return m;
            }

            public static MW changePasswordAck(Boolean isSuccess, String failedReason){
                MW m = new MW();
                m.isSuccess = isSuccess;
                m.failedReason = failedReason;
                return m;
            }
        }
    }
}
