package yms;

import restaurant.ui.component.BasePanel;
import restaurant.ui.component.JButtonBuilder;
import restaurant.ui.component.thirdpart.ShadowBorder;
import yms.component.MinSetMeatCard;
import yms.component.SetDishCard;
import yms.component.SetMeatCard;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Created by YMS on 2017/12/23.
 */
public class DishManageUI extends BasePanel {
    private Frame parent;

    public DishManageUI(Frame parent) {
        this.parent = parent;
        initUIFoot();
        initUISubTitle();
        initUIContent();
    }
    public void initUIFoot()
    {
        JPanel fp = new JPanel(new BorderLayout());
        fp.setOpaque(false);
        fp.setPreferredSize(new Dimension(Constants.ContentEastWidth,0));
        JButton save = new JButton("保存");
        save.setPreferredSize(new Dimension(Constants.ContentEastWidth/2,0));
        JButton back = new JButton("返回");
        back.addActionListener(e -> parent.dish());
        back.setPreferredSize(new Dimension(Constants.ContentEastWidth/2,0));
        back.setBackground(ColorConstants.title);
        save.setBackground(ColorConstants.title);
        fp.add("West",save);
        fp.add("East",back);
        getFoot().add("East",fp);
    }
    public void  initUISubTitle()
    {
        JPanel se = new JPanel(new BorderLayout());
        se.setOpaque(false);
        se.setPreferredSize(new Dimension(Constants.ContentEastWidth, 0));
        JLabel sl= new JLabel("菜单详情",JLabel.CENTER);
        sl.setFont(FontConstants.font1);
        sl.setBackground(ColorConstants.subtitle);
        sl.setForeground(Color.white);
        se.add("Center",sl);




        JPanel sw = new C1(3, ColorConstants.subtitle);
        //sw.setOpaque(false);
        for(String type : Service.getDishTypes()){
            sw.add(createMainButton(type, e->{ parent.predishmanage(); }));
        }
        getSubtitle().add("Center", sw);
        getSubtitle().add("East", se);
    }
    public  void initUIContent(){
        /*
        east
         */
        JPanel east = new JPanel(new BorderLayout());
        east.setOpaque(false);
        east.setPreferredSize(new Dimension(Constants.ContentEastWidth,0));
        Border border = BorderFactory.createLineBorder(Color.black);
        east.setBorder(border);
        //ecenter
        JPanel ecenter = new JPanel();
        ecenter.setOpaque(false);
        int i=0;
        while(i<8) {
            ecenter.add(new MinSetMeatCard(Constants.ContentEastWidth, 50, "骚鸡", "20", e -> {}));
            i++;
        }
        east.add("Center",ecenter);
        //esouth
        JPanel esouth = new JPanel(new GridLayout(2,2));
        esouth.setPreferredSize(new Dimension(Constants.ContentEastWidth,80));
        esouth.setOpaque(false);
        JLabel dishcategory = new JLabel("类别：",JLabel.CENTER);
        JLabel dishtotal = new JLabel("总数：",JLabel.CENTER);
        JLabel category = new JLabel("荤菜",JLabel.CENTER);
        JLabel total = new JLabel("10",JLabel.CENTER);
        esouth.add(dishcategory);
        esouth.add(category);
        esouth.add(dishtotal);
        esouth.add(total);
        east.add("South",esouth);
        getContent().add("East",east);
        /*center

         */
        JPanel center = new JPanel();
        center.setOpaque(false);
        int j=0;
        while(i<50) {
            center.add(new SetDishCard(200, 200, "骚鸡", "20", "url"));
            i++;
        }
        getContent().add("Center",center);
    }
    private JButton createMainButton(String name, ActionListener listener){

        JButton button = JButtonBuilder.getInstance().text(name).listener(listener)
                .foreground(Color.white).background(ColorConstants.subtitle).font(FontConstants.font1)
                .build();
       // if(name=="<"||name==">"){ b.setPreferredSize(new Dimension(Constants.MainButtonSize/4,Constants.SubTitleButtonHeight));}
       // else {b.setPreferredSize(new Dimension(Constants.MainButtonSize/2,Constants.SubTitleButtonHeight));}b.setPreferredSize(new Dimension(Constants.MainButtonSize/4,Constants.SubTitleButtonHeight));

        return button;
    }
    class C1 extends JPanel {

        private int cols = 3;
        private int currPage = 0;
        private JPanel content;
        public C1(int cols, Color background){
            this.cols = cols;
            components = new ArrayList<>();
            setLayout(new BorderLayout());
            add("West", JButtonBuilder.getInstance().text("<").listener(e->priorPage())
                    .opaque(false).contentAreaFilled(false).focusPainted(false).build());
            add("East", JButtonBuilder.getInstance().text(">").listener(e->nextPage())
                    .opaque(false).contentAreaFilled(false).focusPainted(false).build());
            content = new JPanel(new GridLayout(1, cols, 1,0));
            content.setBackground(background);
            setBackground(background);
            add("Center", content);
        }

        private java.util.List<Component> components;
        public Component add(Component component){
            components.add(component);
            fresh();
            return component;
        }

        private void nextPage(){
            if((1+currPage)*cols < components.size()){
                ++currPage;
            }
            fresh();
        }
        private void priorPage(){
            if(currPage > 0){
                --currPage;
            }
            fresh();
        }
        public void fresh(){
            content.removeAll();
            int startIdx = currPage * cols;
            int endIdx = startIdx + cols;
            int compCount = components.size();
            for (int i = startIdx; i < endIdx; ++i) {
                if (i < compCount) {
                    content.add(components.get(i));
                } else {
                    JLabel l = new JLabel("");
                    content.add(l);
                }
            }
            content.setVisible(false);
            content.revalidate();
            content.setVisible(true);
        }
    }
}

class Service {
    public static java.util.List<String> getDishTypes(){
        java.util.List l = new ArrayList<>();
        l.add("全部");
        l.add("荤菜");
        l.add("素菜");
        l.add("甜品");
        return l;
    }
}