package restaurant.ui.component;

import restaurant.ui.component.layout.PageLayout;

import javax.swing.*;
import java.awt.*;

public class PagePanel extends JPanel {
    private PageButton pageButton;
    private PageLayout pageLayout;
    private JPanel buttonBackground;
    public PagePanel(JPanel container){
        pageLayout = new PageLayout().setSingleCol(true);
        container.setLayout(pageLayout);
        pageButton = new PageButton(pageLayout, container);
        setLayout(new BorderLayout());
        setOpaque(false);
        buttonBackground = new JPanel(new BorderLayout());
        buttonBackground.add(pageButton);
        buttonBackground.setOpaque(false);
        add("Center", container);
        add("South", buttonBackground);
    }
    public PagePanel setPageButtonBackground(Color background){
        buttonBackground.setOpaque(true);
        buttonBackground.setBackground(background);
        return this;
    }
}
