package restaurant.ui.management.component;

import restaurant.service.core.vo.Order;
import restaurant.ui.Constants;
import restaurant.ui.component.PagePanel;
import restaurant.ui.component.PageTitle;
import restaurant.ui.component.builder.JPanelBuilder;
import restaurant.ui.utils.Utility;

import javax.swing.*;
import java.util.List;
import java.util.Map;

public class OrderView  extends JDialog {
    public OrderView(Order order) {
        setModal(true);
        setSize(320,400);
        setLocationRelativeTo(null);
        getContentPane().setBackground(Constants.Color.background);
        JPanel panel = JPanelBuilder.getInstance().opaque(false).build();
        Map<String, Integer> detail = order.getDetail();
        for(String dishName: detail.keySet()){
            panel.add(new OrderDetailCard(dishName, detail.get(dishName).toString()));
        }
        add(new PagePanel(panel, new PageTitle("菜品名称","数量"))
                .setPageButtonBackground(Constants.Color.title));
        setVisible(true);
    }
}
