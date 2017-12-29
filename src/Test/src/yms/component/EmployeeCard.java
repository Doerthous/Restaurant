package yms.component;

import yms.ColorConstants;
import restaurant.ui.component.builder.JButtonBuilder;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * Created by YMS on 2017/12/24.
 */
public class EmployeeCard extends JPanel {
    public EmployeeCard(int width, int height,
                    String url,String[] str,
                    ActionListener modify, ActionListener delete) {
        setPreferredSize(new Dimension(width, height));
        setLayout(new BorderLayout());
        setBackground(ColorConstants.title);
        Border border = BorderFactory.createLineBorder(Color.black);
        //west
        JPanel west = new JPanel();
        west.setBackground(Color.orange);
        west.setPreferredSize(new Dimension(width/4,height));
        west.setBorder(border);
        add("West",west);
        /*center

         */
        JPanel center = new JPanel(new BorderLayout());
        center.setOpaque(false);
        //center-north
        JPanel cnorth = new JPanel(new BorderLayout());
        cnorth.setOpaque(false);
        cnorth.setPreferredSize(new Dimension(width*2/3,height/4));
        //center-north-west
        JLabel namelable = new JLabel(str[0],JLabel.CENTER);
        namelable.setOpaque(false);
        namelable.setPreferredSize(new Dimension(width/3,0));
        cnorth.add("West",namelable);
        //center-north-east
        JPanel cneast = new JPanel(new GridLayout(1,3));
        cneast.setOpaque(false);
        cneast.setPreferredSize(new Dimension(width/3,0));
        cneast.add(JButtonBuilder.getInstance().text("修改").listener(modify)
                .preferredSize(new Dimension(width/9,height/4)).
                        background(ColorConstants.title).build());
        cneast.add(JButtonBuilder.getInstance().text("删除").listener(delete)
                .preferredSize(new Dimension(width/9,height/4))
                .background(ColorConstants.title).build());
        JCheckBox checkBox = new JCheckBox("选中");
        checkBox.setOpaque(false);
        checkBox.addActionListener(e->{
            if(checkBox.isSelected()){
                System.out.println("ok");
            } else {
                System.out.println("no");
            }
        });
        //checkBox.setBorder(border);
        //checkBox.setPreferredSize(new Dimension(width/9,height/4));
        cneast.add(checkBox);
        cnorth.add("Center",cneast);
        center.add("North",cnorth);
        /*
        center-center
         */
        JPanel ccenter = new JPanel(new GridLayout(6,2));
        ccenter.setOpaque(false);
        ccenter.setBorder(border);
        String  title[] = {"性别：","工号：","类型：","薪资：","手机：","籍贯："};
        int i = 0;
        while(i<6)
        {
            JLabel lt = new JLabel(title[i],JLabel.CENTER);
            lt.setOpaque(false);
            JLabel lstr = new JLabel(str[i+1],JLabel.CENTER);
            lstr.setOpaque(false);
            ccenter.add(lt);
            ccenter.add(lstr);
            i++;
        }
        center.add("Center",ccenter);
        add("Center",center);

}


}


