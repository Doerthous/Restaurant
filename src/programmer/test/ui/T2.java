package programmer.test.ui;

import programmer.test.ui.component.*;
import restaurant.service.IDishService;
import restaurant.service.ServiceFactory;
import restaurant.service.vo.Dish;

import javax.swing.*;
import java.awt.*;

public class T2 {
    private static JPanel cCenter;
    public static void main(String[] args) {
        SimpleFrame sf = new SimpleFrame();
        JPanel north = new JPanel();
        JPanel south = new JPanel();
        JPanel center = new JPanel();
        JPanel east = new JPanel();
        north.setPreferredSize(new Dimension(0, 100));
        north.setBackground(Color.green);
        south.setPreferredSize(new Dimension(0, 60));
        south.setBackground(Color.green);
        center.setLayout(new BorderLayout(0,0));
        JPanel cNorth = new JPanel();
        cNorth.setBackground(Color.black);
        cNorth.setPreferredSize(new Dimension(0,80));
        cNorth.add(new JButton("<"));
        cNorth.add(new JButton("全部"));
        cNorth.add(new JButton("荤菜"));
        cNorth.add(new JButton("素菜"));
        cNorth.add(new JButton(">"));
        cCenter = new JPanel();
        cCenter.setLayout(new ModifiedFlowLayout(FlowLayout.LEFT));
        JScrollPane cCenterScrollPane = new JScrollPane(cCenter);
        center.add("North", cNorth);
        center.add("Center", cCenterScrollPane);
        east.setPreferredSize(new Dimension(300, 0));
        east.setBackground(Color.blue);
        east.setLayout(new BorderLayout(0,0));
        JPanel eNorth = new JPanel();
        eNorth.setBackground(Color.cyan);
        eNorth.setPreferredSize(new Dimension(0,80));
        eNorth.add(new JLabel("菜品管理"));
        JPanel eCenter = new JPanel();
        eCenter.setLayout(new VFlowLayout());
        eCenter.add(new JButton("菜单设置"));
        eCenter.add(new JButton("新增菜品"));
        eCenter.add(new JButton("新增类别"));
        JButton t = new JButton("+测试+");
        t.addActionListener(e -> {
            cCenter.add(new DishCard(200,200,"酱爆肉","12元","xxx"));
            cCenter.revalidate();
        });
        eCenter.add(t);
        east.add("North", eNorth);
        east.add("Center", eCenter);
        sf.add("North", north);
        sf.add("South", south);
        sf.add("Center", center);
        sf.add("East", east);

        sf.setVisible(true);

        getMenu();
    }
    private static void getMenu(){
        IDishService service = ServiceFactory.getDishService();
        java.util.List<Dish> dss = service.getDishMenu();
        for(Dish ds : dss){
            cCenter.add(new DishCard(200,200,ds.getName(),ds.getPrice()+"元","xxx"));
        }
        cCenter.revalidate();
    }
}
