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
        sortRules = new String[]{
                "按价格升序",
                "按价格降序",
                "按销量升序",
                "按销量降序"
        };
        dishTypes = mf.getService().getDishTypes().toArray(new String[]{});

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
        jcbSortBy = new JComboBoxEx("排序", sortRules)
                .addActionListener(this);
        jcbTypes = new JComboBoxEx("类型", dishTypes)
                .addActionListener(this);
        loadDish(mf.getService().getDishByTypeSortByPrice(dishTypes[0], true));
        getContentRightTop().setLayout(new PageLayout().setSingleCol(true));
        getContentRightTop().add(jcbSortBy);
        getContentRightTop().add(jcbTypes);

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


    private JComboBoxEx jcbSortBy;
    private JComboBoxEx jcbTypes;
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
        } else if(e.getSource() instanceof JComboBoxEx) {
            sortDish();
        }
    }

    private String[] sortRules; // 这个规则应该由谁管理比较合适？
    private String[] dishTypes; //
    private void createDish(){
        DishForm d = new DishForm(mf.getService().getDishTypes().toArray(new String[0]));
        DishForm.Data data = d.open();
        if(data != null) {
            if (mf.getService().createDish(data.dishName, data.dishPrice, data.dishType,
                    data.dishIsSaled, data.dishPicUrl)) {
                // 提示创建成功
                getContentLeft().add(new DishCard(data.dishName, data.dishPrice, new ImageIcon(data.dishPicUrl))
                        .setActionListener(this));
                Utility.showTipDialog("增添成功", 2000);
                Utility.revalidate(getContentLeft());
            } else {
                // 提示创建失败，失败原因
            }
        }
    }
    private void modifyDish(DishCard dc){
        Dish dish = mf.getService().getDishByName(dc.getDishName());
        DishForm.Data data = new DishForm.Data(dish.getName(), dish.getPrice(), dish.getType(), dish.getIsSaled(),
                new ImageIcon(dish.getPicture()));
        DishForm d = new DishForm(mf.getService().getDishTypes().toArray(new String[0]), data);
        data = d.open();
        if(data != null) {
            if (mf.getService().modifiDish(data.dishName, data.dishPrice, data.dishType,
                    data.dishIsSaled, data.dishPicUrl)) {
                Utility.showTipDialog("修改成功", 2000);
                dc.setDishName(data.dishName)
                        .setDishPrice(data.dishPrice)
                        .setDishPicture(new ImageIcon(data.dishPicUrl));
                Utility.revalidate(dc);
            } else {
                // 提示修改失败，失败原因
            }
        }
    }
    private void deleteDish(DishCard dc){
        // 此处应该弹框警告
        int isDelete = Utility.showConfirmDialog("删除后将无法恢复，确认删除吗？");
        if(isDelete == ConfirmDialog.YES_OPTION) {
            if (mf.getService().deleteDish(dc.getDishName())) {
                Utility.showTipDialog("删除成功",2000);
            } else {
                // 提示失败，失败原因
            }
            getContentLeft().remove(dc);
            Utility.revalidate(getContentLeft());
        }
    }
    private void sortDish(){
        String type = (String) jcbTypes.getSelectedItem();
        String sortBy = (String) jcbSortBy.getSelectedItem();
        if(sortBy.equals(sortRules[0])){
            loadDish(mf.getService().getDishByTypeSortByPrice(type, true));
            return;
        }
        if(sortBy.equals(sortRules[1])){
            loadDish(mf.getService().getDishByTypeSortByPrice(type, false));
            return;
        }
        if(sortBy.equals(sortRules[2])){
            loadDish(mf.getService().getDishByTypeSortBySaledCount(type, true));
            return;
        }
        if(sortBy.equals(sortRules[3])){
            loadDish(mf.getService().getDishByTypeSortBySaledCount(type, false));
            return;
        }
    }

    private void loadDish(java.util.List<Dish> dishes){
        getContentLeft().removeAll();
        for(Dish dish: dishes){
            getContentLeft().add(new DishCard(dish.getName(), dish.getPrice(), new ImageIcon(dish.getPicture()))
                    .setActionListener(this));
        }
        Utility.revalidate(getContentLeft());
    }

    private void loadDishAnotherWay(java.util.List<Dish> dishes){
        Component[] components = getContentLeft().getComponents();
        int i = 0;
        int dishCount = dishes.size();
        for(; i < components.length; ++i){
            if(i < dishCount && components[i] instanceof DishCard){
                DishCard dc = (DishCard)components[i];
                Dish dish = dishes.get(i);
                dc.setDishName(dish.getName());
                dc.setDishPrice(dish.getPrice());
                dc.setDishPicture(new ImageIcon(dish.getPicture()));
                Utility.revalidate(dc);
            } else if(i >= dishCount) {
                getContentLeft().remove(components[i]);
            }
        }
        for(; i < dishCount; ++i){
            Dish dish = dishes.get(i);
            getContentLeft().add(new DishCard(dish.getName(), dish.getPrice(),
                    new ImageIcon(dish.getPicture())).setActionListener(this));
        }
        Utility.revalidate(getContentLeft());
    }

}
