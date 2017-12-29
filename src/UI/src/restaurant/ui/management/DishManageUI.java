package restaurant.ui.management;

import restaurant.service.core.vo.Dish;
import restaurant.ui.Constants;
import restaurant.ui.component.BasePanel3;
import restaurant.ui.component.ConfirmDialog;
import restaurant.ui.component.builder.JButtonBuilder;
import restaurant.ui.component.JComboBoxEx;
import restaurant.ui.component.PageButton;
import restaurant.ui.component.layout.PageLayout;
import restaurant.ui.component.thirdpart.ShadowBorder;
import restaurant.ui.management.component.DishCard;
import restaurant.ui.management.component.DishForm;
import restaurant.ui.utils.Utility;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DishManageUI extends BasePanel3 implements ActionListener {
    private ManagementFrame mf;

    public DishManageUI(ManagementFrame mf) {
        this.mf = mf;
        PageLayout layout = new PageLayout();
        getContentLeft().setLayout(layout);
        PageButton pageButton = new PageButton(layout, getContentLeft());
        getFootLeft().add(pageButton);

        // subtitle
        getSubtitleLeft().setLayout(new PageLayout().setSingleRow(true)
                .setHgap(0).setVgap(10).setPadding(new Insets(2,15,0,0)));
        getSubtitleLeft().add(createMenuButton("菜单管理"));
        getSubtitleLeft().add(createMenuButton("套餐管理"));
        // getSubtitleLeft().add(createMenuButton("类别管理"));

        // content
        java.util.List<Dish> dishes = mf.getService().getAllDish();
        for(Dish dish: dishes){
            getContentLeft().add(new DishCard(dish.getName(), dish.getPrice(), new ImageIcon(dish.getPicture()))
                    .setActionListener(this));
        }
        getContentRightTop().setLayout(new PageLayout().setSingleCol(true));
        getContentRightTop().add(new JComboBoxEx("排序", new String[]{"按价格排序","按销量排序"}));
        getContentRightTop().add(new JComboBoxEx("类型", mf.getService().getDishTypes().toArray(new String[]{})));

        // foot
        addFootRight(
                JButtonBuilder.getInstance().text("新增").background(restaurant.ui.client.Constants.Color.title)
                        .listener(e->createDish()).build()
        );
        addFootRight(
                JButtonBuilder.getInstance().text("返回").background(restaurant.ui.client.Constants.Color.title)
                        .listener(e -> mf.main()).build()
        );
    }



    /*
        创建菜单按钮，管理端主页面的四个转页按钮
     */
    private JButton createMenuButton(String text){
        return JButtonBuilder.getInstance().text(text)
                .listener(this).background(Constants.Color.background)
                .border(BorderFactory.createCompoundBorder(
                        ShadowBorder.newBuilder().buildSpecial(new Insets(0,0,2,0)),
                        BorderFactory.createEmptyBorder(15,15,15,15))
                ).build();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() instanceof DishCard){
            DishCard dc = (DishCard)e.getSource();
            if (e.getActionCommand().equals(DishCard.MODIFY)) {
                modifyDish(dc);
            } else if (e.getActionCommand().equals(DishCard.DELETE)){
                deleteDish(dc);
            }
        }
    }

    private void createDish(){
        DishForm d = new DishForm(mf.getService().getDishTypes().toArray(new String[0]));
        DishForm.Data data = d.open();
        if(mf.getService().createDish(data.dishName, data.dishPrice, data.dishType,
                data.dishIsSaled, data.dishPicUrl)){
            // 提示创建成功
        } else {
            // 提示创建失败，失败原因
        }
    }
    private void modifyDish(DishCard dc){
        Dish dish = mf.getService().getDishByName(dc.getDishName());
        DishForm.Data data = new DishForm.Data(dish.getName(), dish.getPrice(), dish.getType(), dish.getIsSaled(),
                new ImageIcon(dish.getPicture()));
        DishForm d = new DishForm(mf.getService().getDishTypes().toArray(new String[0]), data);
        data = d.open();
        if(mf.getService().modifiDish(data.dishName, data.dishPrice, data.dishType,
                data.dishIsSaled, data.dishPicUrl)){
            // 提示创建成功
        } else {
            // 提示创建失败，失败原因
        }
        System.out.println(d.open().toString());
    }
    private void deleteDish(DishCard dc){
        // 此处应该弹框警告
        int isDelete = new ConfirmDialog("删除后将无法恢复，确定删除吗？").open();
        if(isDelete == ConfirmDialog.YES_OPTION) {
            if (mf.getService().deleteDish(dc.getDishName())) {
                // 删除成功
                System.out.println("");
            } else {
                //
            }
            getContentLeft().remove(dc);
            Utility.revalidate(getContentLeft());
        }
    }

}
