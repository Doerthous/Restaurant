package restaurant.ui.management.component;

import restaurant.ui.component.RectangleCard;
import restaurant.ui.component.builder.JLabelBuilder;

import javax.swing.*;

public class OrderDetailCard extends RectangleCard {
    public OrderDetailCard(String dishName, String dishCount) {
        add("West", JLabelBuilder.getInstance()
                .text(dishName).horizontalAlignment(JLabel.CENTER).build());
        add("East", JLabelBuilder.getInstance()
                .text(dishCount).horizontalAlignment(JLabel.CENTER).build());
    }
}