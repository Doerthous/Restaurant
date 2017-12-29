package tutorial.layout;

import javax.swing.*;
import java.awt.*;

public class GridBagLayoutTest {
    public static void main(String[] args) {
        // 创建窗口
        JFrame jf = new JFrame("测试窗口");
        jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jf.setSize(300, 200);
        jf.setLocationRelativeTo(null);

        JPanel content = new JPanel();
        content.setLayout(new GridBagLayout());

        //左侧的具体工具面板
        content.add(new JLabel("1"),new GBC(0,0,1,1)
                .setFill(GBC.BOTH));
        content.add(new JButton("1"),new GBC(1,0,1,1)
                .setWeight(1,1).setFill(GBC.BOTH));
        content.add(new JButton("1"),new GBC(2,0,1,4)
                .setWeight(1,1).setFill(GBC.BOTH));

        content.add(new JLabel("1"),new GBC(0,1,1,1)
                .setWeight(1,1).setFill(GBC.BOTH));
        content.add(new JButton("1"),new GBC(1,1,1,1)
                .setWeight(1,1).setFill(GBC.BOTH));
        content.add(new JLabel("1"),new GBC(0,2,1,1)
                .setWeight(1,1).setFill(GBC.BOTH));
        content.add(new JButton("1"),new GBC(1,2,1,1)
                .setWeight(1,1).setFill(GBC.BOTH));
        content.add(new JLabel("1"),new GBC(0,3,1,1)
                .setWeight(1,1).setFill(GBC.BOTH));
        content.add(new JButton("1"),new GBC(1,3,1,1)
                .setWeight(1,1).setFill(GBC.BOTH));

        content.add(new JButton("1"),new GBC(0,4,3,1)
                .setWeight(1,1).setFill(GBC.BOTH));

        jf.add(content);
        jf.setVisible(true);
                }
    public static class GBC extends GridBagConstraints
    {
        //初始化左上角位置
        public GBC(int gridx, int gridy)
        {
            this.gridx = gridx;
            this.gridy = gridy;
        }

        //初始化左上角位置和所占行数和列数
        public GBC(int gridx, int gridy, int gridwidth, int gridheight)
        {
            this.gridx = gridx;
            this.gridy = gridy;
            this.gridwidth = gridwidth;
            this.gridheight = gridheight;
        }

        //对齐方式
        public GBC setAnchor(int anchor)
        {
            this.anchor = anchor;
            return this;
        }

        //是否拉伸及拉伸方向
        public GBC setFill(int fill)
        {
            this.fill = fill;
            return this;
        }

        //x和y方向上的增量
        public GBC setWeight(double weightx, double weighty)
        {
            this.weightx = weightx;
            this.weighty = weighty;
            return this;
        }

        //外部填充
        public GBC setInsets(int distance)
        {
            this.insets = new Insets(distance, distance, distance, distance);
            return this;
        }

        //外填充
        public GBC setInsets(int top, int left, int bottom, int right)
        {
            this.insets = new Insets(top, left, bottom, right);
            return this;
        }

        //内填充
        public GBC setIpad(int ipadx, int ipady)
        {
            this.ipadx = ipadx;
            this.ipady = ipady;
            return this;
        }
    }
}
