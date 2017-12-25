package yms;

import restaurant.ui.ColorConstants;
import restaurant.ui.FontConstants;
import restaurant.ui.component.BasePanel;
import restaurant.ui.component.thirdpart.ModifiedFlowLayout;
import restaurant.ui.component.thirdpart.ShadowBorder;
import yms.component.DishCard;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * Created by YMS on 2017/12/23.
 */
public class DishUI extends BasePanel{
    private Frame parent;

    public DishUI(Frame parent) {
        this.parent = parent;

        //initServic
        initUIFoot();
        initUISubTitle();
        initUIContent();
       // initUIContent();
        //eComponent();
        //initUIComponent();

    }
    public void initUIFoot()
    {
        JPanel fp = new JPanel(new BorderLayout());
        fp.setOpaque(false);
        fp.setPreferredSize(new Dimension(Constants.ContentEastWidth,0));
        JButton plus = new JButton("新增");
        plus.setPreferredSize(new Dimension(Constants.ContentEastWidth/2,0));
        JButton back = new JButton("返回");
        back.addActionListener(e -> parent.main());
        back.setPreferredSize(new Dimension(Constants.ContentEastWidth/2,0));
        back.setBackground(ColorConstants.title);
        plus.setBackground(ColorConstants.title);
        fp.add("West",plus);
        fp.add("East",back);
        getFoot().add("East",fp);
    }
    public void  initUISubTitle()
    {
        JPanel sp = new JPanel();
        //sp.setPreferredSize(new Dimension(Constants.ContentEastWidth, 0));
        sp.setOpaque(false);
        sp.add(createMainButton("菜单管理", e->{ parent.dishmanage(); }));
        sp.add(createMainButton("套餐管理", e->{ parent.setmeat(); }));
        //sp.add(createMainButton("数据统计", e->{ parent.callService(); }));
        sp.add(createMainButton("类别管理", e->{ parent.categorymanage(); }));
        getSubtitle().add("West", sp);
    }
    public void initUIContent()
    {
        JLabel name = new JLabel("菜名：");
        JLabel sort = new JLabel("排序：");
        JLabel category = new JLabel("类型：");
        JComboBox sortbx = new JComboBox(new String[]{"按价格排序","按销量排序"});
        java.util.List l = Service.getDishTypes();
        String str[] = (String[]) l.toArray(new String[l.size()]);
        JComboBox categorybx = new JComboBox(str);
        JTextArea nametext = new JTextArea();
        nametext.setBackground(ColorConstants.background);
        sortbx.setBackground(ColorConstants.background);
        categorybx.setBackground(ColorConstants.background);
        JPanel ce = new JPanel(new BorderLayout());
        ce.setOpaque(false);
        ce.setPreferredSize(new Dimension(Constants.ContentEastWidth,0));
        Border border = BorderFactory.createLineBorder(Color.black);
        ce.setBorder(border);
        JPanel cen = new JPanel(new GridLayout(3,2));
        cen.setOpaque(false);
        cen.setPreferredSize(new Dimension(Constants.ContentEastWidth,200));
        cen.add(name);
        cen.add(nametext);
        cen.add(sort);
        cen.add(sortbx);
        cen.add(category);
        cen.add(categorybx);
        ce.add("North",cen);
        getContent().add("East",ce);

        /*center

         */
        JPanel center = new JPanel();
        center.setLayout(new ModifiedFlowLayout());

        center.setBackground(ColorConstants.background);


        JScrollPane jsp = new JScrollPane(center);
        jsp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        getContent().add("Center",jsp);

        int i =0;
        while(i<50) {
            center.add(new DishCard(200, 200, "骚鸡", "20", "", e -> {
            }, e -> {
            }));
            i++;
        }
    }
    private JButton createMainButton(String name, ActionListener listener){
        JButton b = new JButton(name);
        b.setPreferredSize(new Dimension(Constants.MainButtonSize,Constants.SubTitleButtonHeight));
        b.addActionListener(listener);
        b.setBorder(ShadowBorder.newBuilder().shadowSize(2).build());
        b.setBackground(ColorConstants.background);
        b.setFont(new Font("微软雅黑", Font.PLAIN, 15));
        return b;
    }
}
