package restaurant.ui.client;

import programmer.test.ui.component.ModifiedFlowLayout;
import programmer.test.ui.component.OrderDishCard;
import programmer.test.ui.component.ShadowBorder;
import programmer.test.ui.component.VFlowLayout;
import restaurant.ui.ColorConstants;

import javax.swing.*;
import java.awt.*;

public class OrderUI extends JPanel{
    private ClientFrame cf;
    private JPanel cCenter;

    public OrderUI(ClientFrame cf) {
        this.cf = cf;
        setLayout(new BorderLayout());
        JPanel north = new JPanel();
        JPanel south = new JPanel();
        JPanel center = new JPanel();
        JPanel east = new JPanel();
        north.setPreferredSize(new Dimension(0, 100));
        north.setBackground(ColorConstants.title);
        north.setBorder(ShadowBorder.newBuilder().shadowSize(5).buildSpecial(new Insets(0,0,10,0)));
        south.setPreferredSize(new Dimension(0, 60));
        south.setBackground(ColorConstants.title);
        center.setLayout(new BorderLayout(0,0));
        JPanel cNorth = new JPanel();
        cNorth.setBackground(ColorConstants.subtitle);
        cNorth.setPreferredSize(new Dimension(0,80));
        cNorth.add(new JButton("<"));
        cNorth.add(new JButton("全部"));
        cNorth.add(new JButton("荤菜"));
        cNorth.add(new JButton("素菜"));
        cNorth.add(new JButton(">"));
        cCenter = new JPanel(); // new ScrollablePanel();
        cCenter.setLayout(new ModifiedFlowLayout(FlowLayout.LEFT));
        cCenter.setBackground(ColorConstants.background);
        JScrollPane cCenterScrollPane = new JScrollPane(cCenter);
        center.add("North", cNorth);
        center.add("Center", cCenterScrollPane);
        east.setPreferredSize(new Dimension(300, 0));
        east.setBackground(Color.blue);
        east.setLayout(new BorderLayout(0,0));
        JPanel eNorth = new JPanel();
        eNorth.setBackground(ColorConstants.subtitle);
        eNorth.setPreferredSize(new Dimension(0,80));
        eNorth.add(new JLabel("菜品管理"));
        JPanel eCenter = new JPanel();
        eCenter.setLayout(new VFlowLayout());
        eCenter.add(new JButton("菜单设置"));
        eCenter.add(new JButton("新增菜品"));
        eCenter.add(new JButton("新增类别"));
        JButton t = new JButton("+测试+");
        t.addActionListener(e -> {
            cCenter.add(new OrderDishCard(200,200,"酱爆肉","12元","xxx"));
            cCenter.revalidate();
        });
        eCenter.add(t);
        east.add("North", eNorth);
        east.add("Center", eCenter);
        JButton ret = new JButton("返回");
        ret.addActionListener(e->{
            cf.main();
        });
        east.add("South", ret);
        add("North", north);
        add("South", south);
        add("Center", center);
        add("East", east);
        setBackground(ColorConstants.subtitle);
    }
}
