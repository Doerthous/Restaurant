package yms;

import restaurant.ui.component.BasePanel;
import restaurant.ui.component.JButtonBuilder;
import restaurant.ui.component.thirdpart.ShadowBorder;
import yms.component.MinSetMeatCard;
import yms.component.SetMeatCard;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Created by YMS on 2017/12/23.
 */
public class SetMeatUI extends BasePanel {
    private Frame parent;

    public SetMeatUI(Frame parent) {
        this.parent = parent;
        initUIFoot();
        initUISubTitle();
        initUIContent();
    }

    public void initUIFoot() {
        JPanel fp = new JPanel(new BorderLayout());
        fp.setOpaque(false);
        fp.setPreferredSize(new Dimension(Constants.ContentEastWidth, 0));
        JButton plus = new JButton("新增");
        plus.setPreferredSize(new Dimension(Constants.ContentEastWidth / 3, 0));
        JButton save = new JButton("保存");
        save.setPreferredSize(new Dimension(Constants.ContentEastWidth / 3, 0));
        JButton back = new JButton("返回");
        back.addActionListener(e -> parent.dish());
        back.setPreferredSize(new Dimension(Constants.ContentEastWidth / 3, 0));
        back.setBackground(ColorConstants.title);
        save.setBackground(ColorConstants.title);
        plus.setBackground(ColorConstants.title);
        fp.add("West", plus);
        fp.add("Center", save);
        fp.add("East", back);
        getFoot().add("East", fp);
    }

    public void initUISubTitle() {
        JPanel se = new JPanel(new BorderLayout());
        se.setOpaque(false);
        se.setPreferredSize(new Dimension(Constants.ContentEastWidth, 0));
        JLabel sl = new JLabel("套餐详情", JLabel.CENTER);
        sl.setFont(FontConstants.font1);
        sl.setBackground(ColorConstants.subtitle);
        sl.setForeground(Color.white);
        se.add("Center", sl);


        JPanel sw = new C1(3, ColorConstants.subtitle);
        //sw.setOpaque(false);
        for (String type : Service.getDishTypes()) {
            sw.add(createMainButton(type, e -> {
                parent.predishmanage();
            }));
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
        //enorth
        JPanel enorth = new JPanel(new BorderLayout());
        enorth.setOpaque(false);
        enorth.setPreferredSize(new Dimension(Constants.ContentEastWidth,50));
        JComboBox seatname = new JComboBox(new String[]{"套餐1","套餐2"});
        seatname.setBackground(ColorConstants.background);
        JButton delete = new JButton("删除");
        delete.setBackground(ColorConstants.background);
        delete.setPreferredSize(new Dimension(Constants.ContentEastWidth/3,0));
        enorth.add("Center",seatname);
        enorth.add("East",delete);
        east.add("North",enorth);
        //esouth
        JPanel esouth = new JPanel(new GridLayout(2,2));
        esouth.setPreferredSize(new Dimension(Constants.ContentEastWidth,80));
        esouth.setOpaque(false);
        JLabel dishTotal = new JLabel("菜品数：",JLabel.CENTER);
        JLabel priceTotal = new JLabel("总价：",JLabel.CENTER);
        JLabel total = new JLabel("3",JLabel.CENTER);
        JLabel price = new JLabel("100",JLabel.CENTER);
        esouth.add(dishTotal);
        esouth.add(total);
        esouth.add(priceTotal);
        esouth.add(price);
        east.add("South",esouth);
        //ecenter
        JPanel ecenter = new JPanel();
        ecenter.setOpaque(false);

        east.add("Center",ecenter);
        int i=0;
        while(i<8) {
            ecenter.add(new MinSetMeatCard(Constants.ContentEastWidth, 50, "骚鸡", "20", e -> {}));
            i++;
        }
        getContent().add("East",east);
        /*center

         */
        JPanel center = new JPanel();
        center.setOpaque(false);
        int j=0;
        while(i<50) {
            center.add(new SetMeatCard(200, 200, "骚鸡", "20", "url"));
            i++;
        }
        getContent().add("Center",center);
    }

    private JButton createMainButton(String name, ActionListener listener) {

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

        public C1(int cols, Color background) {
            this.cols = cols;
            components = new ArrayList<>();
            setLayout(new BorderLayout());
            add("West", JButtonBuilder.getInstance().text("<").listener(e -> priorPage())
                    .opaque(false).contentAreaFilled(false).focusPainted(false).build());
            add("East", JButtonBuilder.getInstance().text(">").listener(e -> nextPage())
                    .opaque(false).contentAreaFilled(false).focusPainted(false).build());
            content = new JPanel(new GridLayout(1, cols, 1, 0));
            content.setBackground(background);
            setBackground(background);
            add("Center", content);
        }

        private java.util.List<Component> components;

        public Component add(Component component) {
            components.add(component);
            fresh();
            return component;
        }

        private void nextPage() {
            if ((1 + currPage) * cols < components.size()) {
                ++currPage;
            }
            fresh();
        }

        private void priorPage() {
            if (currPage > 0) {
                --currPage;
            }
            fresh();
        }

        public void fresh() {
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


