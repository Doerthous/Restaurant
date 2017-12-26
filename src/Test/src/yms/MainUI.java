package yms;

import restaurant.ui.component.BasePanel;
import restaurant.ui.component.thirdpart.ShadowBorder;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * Created by YMS on 2017/12/22.
 */
public class MainUI extends BasePanel
{
    private Frame parent;

    public MainUI(Frame parent) {
        this.parent = parent;
        initUIFoot();
        initUISubTitle();
        initUIContent();
        //initServiceComponent();
        //initUIComponent();

    }
/*
  UI组件
 */
    public void initUIFoot()
    {
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        JLabel fl = new JLabel( sdf.format(d));
        fl.setPreferredSize(new Dimension(Constants.ContentEastWidth, 0));
        getFoot().add("East",fl);
    }
    public  void initUISubTitle()
    {
        JPanel sp = new JPanel();
        //sp.setPreferredSize(new Dimension(Constants.ContentEastWidth, 0));
        sp.setOpaque(false);
        sp.add(createMainButton("菜单管理", e->{ parent.dish(); }));
        sp.add(createMainButton("员工管理", e->{ parent.employee(); }));
        sp.add(createMainButton("数据统计", e->{ parent.datacount(); }));
        sp.add(createMainButton("系统设置", e->{ parent.system(); }));
        getSubtitle().add("West", sp);
    }
    public  void initUIContent()
    {
        JPanel cw = new JPanel();
        //cw.setPreferredSize(new Dimension());
        cw.setOpaque(false);
        JPanel ce = new JPanel();
        ce.setPreferredSize(new Dimension(Constants.ContentEastWidth,0));
        ce.setOpaque(false);
        ce.setLayout(new BorderLayout());

        JPanel en = new JPanel(new GridLayout(5,1));
        en.setOpaque(false);
        en.setPreferredSize(new Dimension(Constants.ContentEastWidth,200));
        JPanel es = new JPanel(new GridLayout(3,2));
        es.setOpaque(false);
        en.add(new JLabel("客流量"));
        en.add(new JLabel("当前客户数"));
        en.add(new JLabel("当日收入"));
        en.add(new JLabel("已用餐桌"));
        en.add(new JLabel("餐桌总数"));

        es.add(new JLabel("餐桌号："));
        es.add(new JButton("指派服务员"));
        es.add(new JLabel("客户数："));
        es.add(new JButton("打印订单"));
        es.add(new JLabel("总消费："));
        es.add(new JButton("结账"));
        Border border = BorderFactory.createLineBorder(Color.black);
        en.setBorder(border);
        es.setBorder(border);
        ce.add("North",en);
        ce.add("Center",es);

        getContent().add("West",cw);
        getContent().add("East",ce);

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

