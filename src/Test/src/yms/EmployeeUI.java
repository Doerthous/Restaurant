package yms;

import restaurant.ui.component.BasePanel;
import yms.component.EmployeeCard;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

/**
 * Created by YMS on 2017/12/23.
 */
public class EmployeeUI extends BasePanel{

    private Frame parent;

    public EmployeeUI(Frame parent) {
        this.parent = parent;
        initUIFoot();
        initUIContent();
        //initServiceComponent();
        //initUIComponent();

    }
    public void initUIFoot() {
        JPanel fp = new JPanel(new BorderLayout());
        fp.setOpaque(false);
        fp.setPreferredSize(new Dimension(Constants.ContentEastWidth*2, 0));
        JButton plus = new JButton("新增");
        plus.setPreferredSize(new Dimension(Constants.ContentEastWidth /2, 0));
        JButton search = new JButton("查询");
        search.setPreferredSize(new Dimension(Constants.ContentEastWidth / 2, 0));
        JButton delete = new JButton("删除");
        delete.setPreferredSize(new Dimension(Constants.ContentEastWidth / 2, 0));
        JButton back = new JButton("返回");
        back.addActionListener(e -> parent.main());
        back.setPreferredSize(new Dimension(Constants.ContentEastWidth / 2, 0));
        JPanel fc = new JPanel(new BorderLayout());
        fc.add("East",delete);
        fc.add("Center",search);
        back.setBackground(ColorConstants.title);
        delete.setBackground(ColorConstants.title);
        plus.setBackground(ColorConstants.title);
        search.setBackground(ColorConstants.title);
        fp.add("West", plus);
        fp.add("Center", fc);
        fp.add("East", back);
        getFoot().add("East", fp);
    }
    public void initUIContent()
    {
        JLabel name = new JLabel("姓名：");
        JLabel sex = new JLabel("性别：");
        JLabel category = new JLabel("类型：");
        JComboBox sexbx = new JComboBox(new String[]{"男","女"});
        JComboBox categorybx = new JComboBox(new String[]{"服务员","经理","清洁工","厨师"});
        JTextArea nametext = new JTextArea();
        nametext.setBackground(ColorConstants.background);
        sexbx.setBackground(ColorConstants.background);
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
        cen.add(sex);
        cen.add(sexbx);
        cen.add(category);
        cen.add(categorybx);
        ce.add("North",cen);
        getContent().add("East",ce);
        JPanel ccenter = new JPanel();
        ccenter.setOpaque(false);
        String s[] ={"1","2","3","4","5","6","7"};
        int i=0;
        while(i<8) {
            ccenter.add(new EmployeeCard(500, 200, "df", s,
                    e -> {
                        System.out.println("改");
                    }, new Listener()));
            i++;
        }
        getContent().add("Center",ccenter);
    }

}
