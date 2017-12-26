package restaurant.ui.component;

import restaurant.ui.component.layout.PageLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ContainerAdapter;
import java.awt.event.ContainerEvent;

public class PageButton extends JPanel {
    private Integer curr;
    private Integer count;
    private JLabel pageInfo;
    public PageButton(PageLayout layout, Container container) {
        setLayout(new BorderLayout());
        setOpaque(false);
        JPanel center = new JPanel(new BorderLayout());
        center.setOpaque(false);
        add("Center", center);

        // button
        JButton fb = JButtonBuilder.getInstance().text("<<").opaque(false)
                .contentAreaFilled(false).listener(e->{
                    curr = 1;
                    layout.first(container);
                    updatePageInfo();
                }).build();
        JButton pb = JButtonBuilder.getInstance().text("<").opaque(false)
                .contentAreaFilled(false).listener(e->{
                    if(curr > 1){
                        --curr;
                        layout.prior(container);
                        updatePageInfo();
                    }
                }).build();
        JButton nb = JButtonBuilder.getInstance().text(">").opaque(false)
                .contentAreaFilled(false).listener(e->{
                    if(curr < count) {
                        ++curr;
                        layout.next(container);
                        updatePageInfo();
                    }
                }).build();
        JButton lb = JButtonBuilder.getInstance().text(">>").opaque(false)
                .contentAreaFilled(false).listener(e->{
                    curr = count;
                    layout.last(container);
                    updatePageInfo();
                }).build();
        // text
        pageInfo = JLabelBuilder.getInstance().text("").horizontalAlignment(JLabel.CENTER).build();

        add("West", fb);
        center.add("West", pb);
        center.add("Center", pageInfo);
        center.add("East", nb);
        add("East", lb);

        // event
        container.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                super.componentResized(e);
                curr = layout.getCurrentPage(container);
                count = layout.getPageCount(container);
                updatePageInfo();
            }
        });
        container.addContainerListener(new ContainerAdapter() {
            @Override
            public void componentAdded(ContainerEvent e) {
                super.componentAdded(e);
                count = layout.getPageCount(container);
                updatePageInfo();
            }
        });
    }
    private void updatePageInfo(){
        pageInfo.setText(curr+"/"+count);
    }
}