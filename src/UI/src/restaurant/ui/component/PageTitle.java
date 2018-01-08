package restaurant.ui.component;

import restaurant.ui.Constants;
import restaurant.ui.component.border.AdvLineBorder;
import restaurant.ui.component.builder.JLabelBuilder;
import restaurant.ui.component.builder.JPanelBuilder;

import javax.swing.*;
import java.awt.*;

public class PageTitle extends JPanel {
    public PageTitle(String left, String right) {
        setLayout(new BorderLayout());
        setOpaque(false);
        add(JLabelBuilder.getInstance().text(left).opaque(false).build(), BorderLayout.WEST);
        add(JLabelBuilder.getInstance().text(right).opaque(false).build(), BorderLayout.EAST);
        setBorder(BorderFactory.createCompoundBorder(
                        new AdvLineBorder().setBottom(1).setBottomColor(Constants.Color.subtitle),
                        BorderFactory.createEmptyBorder(5,10,5,10)));
    }
    public PageTitle(String title){
        setLayout(new BorderLayout());
        setOpaque(false);
        add(JLabelBuilder.getInstance().text(title).opaque(false).build());
        setBorder(BorderFactory.createCompoundBorder(
                new AdvLineBorder().setBottom(1).setBottomColor(Constants.Color.subtitle),
                BorderFactory.createEmptyBorder(5,10,5,10)));
    }
    public PageTitle(String col1, String col2, String col3, String col4){
        setLayout(new GridLayout(1, 4));
        setOpaque(false);
        add(JLabelBuilder.getInstance().text(col1).opaque(false).build());
        add(JLabelBuilder.getInstance().text(col2).opaque(false).build());
        add(JLabelBuilder.getInstance().text(col3).opaque(false).build());
        add(JLabelBuilder.getInstance().text(col4).opaque(false).build());
        setBorder(BorderFactory.createCompoundBorder(
                new AdvLineBorder().setBottom(1).setBottomColor(Constants.Color.subtitle),
                BorderFactory.createEmptyBorder(5,10,5,10)));
    }
    public PageTitle(LayoutManager layoutManager, String[] cols, Object[] constraints){
        setLayout(layoutManager);
        setOpaque(false);
        for(int i = 0; i < cols.length; ++i){
            add(JLabelBuilder.getInstance().text(cols[i]).opaque(false).build(), constraints[i]);
        }
        setBorder(BorderFactory.createCompoundBorder(
                new AdvLineBorder().setBottom(1).setBottomColor(Constants.Color.subtitle),
                BorderFactory.createEmptyBorder(5,10,5,10)));
    }
}
