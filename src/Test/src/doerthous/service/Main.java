package doerthous.service;


import doerthous.ui.AFrameForJPanelTest;
import restaurant.service.core.IManagementService;
import restaurant.service.pc.ServiceFactory;
import restaurant.ui.Constants;
import restaurant.ui.component.builder.JButtonBuilder;
import restaurant.ui.component.PicturePanel;
import restaurant.ui.utils.Utility;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.*;
import java.io.File;
import java.util.Scanner;

public class Main {
    static class WaiterNotificationTest {
        public static void main(String[] args) {
            IManagementService ms = ServiceFactory.getManagementService();
            ms.addTableObserver(new IManagementService.ITableObserver() {
                @Override
                public void online(String tableId) {

                }

                @Override
                public void dishFinish(String dishName, String tableId) {
                    System.out.println(tableId + "'s " + dishName+" finished");
                    Scanner scanner = new Scanner(System.in);
                    System.out.print("waiter id: ");
                    ms.dishFinish(dishName, tableId, scanner.nextLine());
                }

                @Override
                public void requestService(String tableId) {
                    System.out.println(tableId + "request service");
                    Scanner scanner = new Scanner(System.in);
                    System.out.print("waiter id: ");
                    ms.customerCall(tableId, scanner.nextLine());
                }

                @Override
                public void newOrder(String tableId) {

                }
            });
        }
    }

    static class InsertDishTest {
        public static void main(String[] args) {
            IManagementService ms = ServiceFactory.getManagementService();
            AFrameForJPanelTest f = new AFrameForJPanelTest();
            f.getContentPane().add(JButtonBuilder.getInstance().text("新增菜品").listener(e->{
                MyJDialog d = new MyJDialog(new String[]{"特色菜","荤菜"});
                d.setVisible(true);
                MyJDialog.Data da = d.getData();
                System.out.println(da.toString());
                ms.createDish(da.dishName, da.dishPrice, da.dishType, da.dishIsSaled, da.dishPicUrl);
            }).build());
            f.open();
        }
        static class MyJDialog extends JDialog {
            public class Data {
                public String dishName;
                public Float dishPrice;
                public String dishType;
                public Boolean dishIsSaled;
                public String dishPicUrl;

                @Override
                public String toString() {
                    return "Data{" +
                            "dishName='" + dishName + '\'' +
                            ", dishPrice=" + dishPrice +
                            ", dishType='" + dishType + '\'' +
                            ", dishIsSaled=" + dishIsSaled +
                            ", dishPicUrl='" + dishPicUrl + '\'' +
                            '}';
                }
            }

            private Data data;
            private JPanel picture;


            public MyJDialog(String[] types) {
                data = new Data();
                // 设置样式
                setModal(true);
                setBounds(new Rectangle(0,0,600,300));
                Container container=getContentPane();

                // 创建组件
                picture =  new JPanel(new BorderLayout());
                JTextField name = new JTextField();
                JTextField price = new JTextField();
                JCheckBox isSaled = new JCheckBox();
                JComboBox type = new JComboBox(types);

                JPanel form = new JPanel();
                form.setLayout(new GridLayout(6,2));
                form.add(new JLabel("菜品名称"));
                form.add(name); // 菜品名称
                form.add(new JLabel("菜品价格"));
                form.add(price); // 价格
                form.add(new JLabel("菜品类型"));
                form.add(type); // 类型
                form.add(new JLabel("菜品图片"));
                form.add(JButtonBuilder.getInstance().text("选择图片").listener(e->{
                    JFileChooser chooseFile = new JFileChooser();
                    chooseFile.addChoosableFileFilter(new FileCanChoose());
                    chooseFile.setFileSelectionMode(JFileChooser.FILES_ONLY);
                    chooseFile.setAcceptAllFileFilterUsed(false);
                    int returnVal = chooseFile.showOpenDialog(this);
                    if (returnVal == chooseFile.APPROVE_OPTION) {
                        File f = chooseFile.getSelectedFile();
                        String fname = f.getAbsolutePath();
                        picture.removeAll();
                        picture.add(new PicturePanel(fname));
                        picture.revalidate();
                        data.dishPicUrl = fname;
                    }
                }).build());
                form.add(new JLabel("是否售卖"));
                form.add(isSaled);
                form.add(JButtonBuilder.getInstance().text("确认").listener(e -> {
                    if(name.getText().toString().length() == 0){
                        //
                        JOptionPane.showMessageDialog(this, "请输入菜名");
                    } else if(price.getText().toString().length() == 0){
                        //
                        JOptionPane.showMessageDialog(this, "请输入价格");
                    } else if(!Utility.isNumeric(price.getText().toString())) {
                        JOptionPane.showMessageDialog(this, "价格格式不合法");
                    } else{
                        data.dishName = name.getText();
                        data.dishPrice = Float.valueOf(price.getText());
                        data.dishIsSaled = isSaled.isSelected();
                        data.dishType = (String) type.getSelectedItem();
                        dispose();
                    }
                }).build());
                form.add(JButtonBuilder.getInstance().text("取消").listener(e->{
                    dispose();
                }).build());

                // 设置组件样式
                picture.setPreferredSize(new Dimension(200,200));
                picture.setBorder(BorderFactory.createLineBorder(Constants.Color.subtitle));

                // 添加组件
                container.add("West", picture);
                container.add("Center", form);
            }

            class FileCanChoose extends FileFilter { //文件过滤器，设置选择对应类型的文件
                public boolean accept(File file) {
                    String name = file.getName();
                    return(name.toLowerCase().endsWith(".gif")||
                            name.toLowerCase().endsWith(".jpg")||
                            name.toLowerCase().endsWith(".bmp")||
                            name.toLowerCase().endsWith(".png")||
                            name.toLowerCase().endsWith(".jpeg") ||
                            file.isDirectory());
                }
                public String getDescription() {
                    return "图片文件：.gif、 .jpg、 .bmp、 .png、 .jpeg";
                }
            }


            public Data getData() {
                return data;
            }

        }
    }

    static class StartTableTest {
        public static void main(String[] args) {
            IManagementService ms = ServiceFactory.getManagementService();
            AFrameForJPanelTest f = new AFrameForJPanelTest();
            JTextField tableId = new JTextField();
            JPanel panel = new JPanel(new GridLayout(2,1));
            panel.add(JButtonBuilder.getInstance().text("开台").listener(e -> {
                ms.openTable(tableId.getText(), 0);
            }).build());
            panel.add(JButtonBuilder.getInstance().text("关台").listener(e -> {
                ms.closeTable(tableId.getText());
            }).build());
            f.getContentPane().add("Center", tableId);
            f.getContentPane().add("East", panel);
            f.open();
        }
    }
}


