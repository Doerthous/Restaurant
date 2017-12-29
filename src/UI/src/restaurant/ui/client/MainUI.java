package restaurant.ui.client;

import restaurant.ui.component.*;
import restaurant.ui.component.builder.JButtonBuilder;
import restaurant.ui.component.builder.JLabelBuilder;
import restaurant.ui.component.thirdpart.ShadowBorder;

import javax.swing.*;
import java.awt.*;

public class MainUI extends BasePanel2 {

    public MainUI(ClientFrame cf) {
        hideFoot();
        hideSubTitle();

        getContentLeft().setLayout(new FlowLayout());
        getContentLeft().add(JButtonBuilder.getInstance().text("点餐").listener(e -> cf.order())
                        .background(Constants.Color.title).border(ShadowBorder.newBuilder().shadowSize(5).build())
                        .preferredSize(new Dimension(Constants.MainButtonSize,Constants.MainButtonSize))
                        .build());
        getContentLeft().add(JButtonBuilder.getInstance().text("聊天").listener(e -> cf.chat())
                        .background(Constants.Color.title).border(ShadowBorder.newBuilder().shadowSize(5).build())
                        .preferredSize(new Dimension(Constants.MainButtonSize,Constants.MainButtonSize))
                        .build());
        getContentLeft().add(JButtonBuilder.getInstance().text("呼叫服务").listener(e -> cf.requestService())
                        .background(Constants.Color.title).border(ShadowBorder.newBuilder().shadowSize(5).build())
                        .preferredSize(new Dimension(Constants.MainButtonSize,Constants.MainButtonSize))
                        .build());
        getContentLeft().add(JButtonBuilder.getInstance().text("结账").listener(e -> cf.pay())
                        .background(Constants.Color.title).border(ShadowBorder.newBuilder().shadowSize(5).build())
                        .preferredSize(new Dimension(Constants.MainButtonSize,Constants.MainButtonSize))
                        .build());

        getContentRight().add(JLabelBuilder.getInstance().horizontalAlignment(JLabel.CENTER)
                .text("餐桌号："+cf.getService().getTableId()).build());

        stop();
    }

    public void start(){
        getContentLeft().setVisible(true);
    }
    public void stop(){
        getContentLeft().setVisible(false);
    }

}
