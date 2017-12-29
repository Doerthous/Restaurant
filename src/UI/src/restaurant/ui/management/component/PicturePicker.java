package restaurant.ui.management.component;

import restaurant.ui.Constants;
import restaurant.ui.component.PicturePanel;
import restaurant.ui.component.builder.JLabelBuilder;
import restaurant.ui.component.builder.JPanelBuilder;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;

public class PicturePicker extends JPanel implements MouseListener {
    private JPanel picture;
    private String url;
    public PicturePicker() {
        this(new Dimension(400,360));
    }
    public PicturePicker(Dimension size){
        setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        setPreferredSize(size);
        setBackground(Constants.Color.subtitle);
        setLayout(new BorderLayout());
        picture = JPanelBuilder.getInstance().opaque(false).layout(new BorderLayout()).build();
        picture.setBorder(BorderFactory.createLineBorder(Constants.Color.background));
        picture.add(JLabelBuilder.getInstance().text("单击选择图片").mouseListener(this)
                .verticalAlignment(JLabel.CENTER).horizontalAlignment(JLabel.CENTER)
                .foreground(Color.white).build());
        picture.addMouseListener(this);
        add(picture);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if(e.getSource() instanceof JLabel ||
                e.getSource() instanceof JPanel ||
                e.getSource() instanceof PicturePanel){
            JFileChooser chooseFile = new JFileChooser();
            chooseFile.setBackground(Constants.Color.subtitle);
            chooseFile.addChoosableFileFilter(new FileCanChoose());
            chooseFile.setFileSelectionMode(JFileChooser.FILES_ONLY);
            chooseFile.setAcceptAllFileFilterUsed(false);
            int returnVal = chooseFile.showOpenDialog(this);
            if (returnVal == chooseFile.APPROVE_OPTION) {
                File f = chooseFile.getSelectedFile();
                url = f.getAbsolutePath();
                this.picture.removeAll();
                PicturePanel pp = new PicturePanel(url);
                pp.addMouseListener(this);
                this.picture.add(pp);
                this.picture.revalidate();
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    private class FileCanChoose extends FileFilter { //文件过滤器，设置选择对应类型的文件
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

    public String getUrl(){
        return url;
    }

    public void setPicture(ImageIcon picture){
        this.picture.removeAll();
        PicturePanel pp = new PicturePanel(picture);
        pp.addMouseListener(this);
        this.picture.add(pp);
        this.picture.revalidate();
    }
}
