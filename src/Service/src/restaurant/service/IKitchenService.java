package restaurant.service;

import java.util.Map;

/*
    Kitchen模块，业务层（service）向视图层（UI）暴露的接口
 */
public interface IKitchenService {
    /*
        顾客订单
     */
    interface IOrderData {
        /*
            餐桌桌号
         */
        String getTableId();
        /*
            菜品数量详情
                数据格式
                    Map {
                        酱爆肉 : 1
                        小炒肉 : 2
                        ...
                    }
         */
        Map<String, Integer> getOrder();
    }
    /*
        订单监听器
     */
    interface IOrderDataObserver {
        /*
            新订单事件，表明有新订单
         */
        void newOrder(IOrderData orderData);
    }

    /*
        订单监听注册函数
     */
    void addOrderDataObserver(IOrderDataObserver orderDataObserver);
}
