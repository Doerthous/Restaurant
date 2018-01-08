package restaurant.ui.component;

import restaurant.ui.component.layout.PageLayout;

import javax.swing.*;
import java.awt.*;

public class PagePanel extends JPanel {
    private JPanel pageTitle;
    private PageButton pageButton;
    private PageLayout pageLayout;
    public PagePanel(JPanel container){
        pageLayout = new PageLayout().setSingleCol(true);
        container.setLayout(pageLayout);
        pageButton = new PageButton(pageLayout, container);
        setLayout(new BorderLayout());
        setOpaque(false);
        add(container, BorderLayout.CENTER);
        add(pageButton, BorderLayout.SOUTH);
    }
    public PagePanel setPageButtonBackground(Color background){
        pageButton.setOpaque(true);
        pageButton.setBackground(background);
        return this;
    }

    public PagePanel(JPanel container, JPanel title){
        this(container);
        add(title, BorderLayout.NORTH);
    }
    // 将page button分离
    public JPanel getPageButton() {
        remove(pageButton);
        return pageButton;
    }
    public PagePanel setPageButton(){
        add("South", pageButton);
        return this;
    }
}
