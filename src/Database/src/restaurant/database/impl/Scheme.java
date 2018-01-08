package restaurant.database.impl;

public interface Scheme {
    interface DishTable {
        String name = "MENU";
        interface Column {
            String id = "菜品id";
            String name = "菜名";
            String price = "单价";
            String type = "品类";
            String isSaled = "是否售卖";
            String picture = "图片";
            String saleCount1 = "一月销售量";
            String saleCount2 = "二月销售量";
            String saleCount3 = "三月销售量";
            String saleCount4 = "四月销售量";
            String saleCount5 = "五月销售量";
            String saleCount6 = "六月销售量";
            String saleCount7 = "七月销售量";
            String saleCount8 = "八月销售量";
            String saleCount9 = "九月销售量";
            String saleCount10 = "十月销售量";
            String saleCount11 = "十一月销售量";
            String saleCount12 = "十二月销售量";
        }
    }
    interface OrderTable {
        String name = "ORDERS";
        interface Column {
            String id = "订单id";
            String tableId = "桌号";
            String date = "日期时间";
            String expend = "消费金额";
        }
    }
    interface DetailTable {
        String name = "DETAILS";
        interface Column {
            String id = "订单id";
            String dishId = "菜品id";
            String count = "菜品数量";
        }
    }
}
