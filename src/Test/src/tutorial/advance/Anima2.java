package tutorial.advance;

import restaurant.ui.component.RectangleCard;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Anima2 extends RectangleCard implements ActionListener{
    //常数定义
    private static final int ANIMATION_FRAMES=50;
    private static final int ANIMATION_INTERVAL=20;
    //帧索引
    private int frameIndex;
    //时钟
    private Timer timer;

    public Anima2() {
    }


    @Override
    public void paint(Graphics g){
        if(isAnimating()){
            //根据当前帧显示当前透明度的内容组件
            Color oldColor = g.getColor();
            g.setColor(getParent().getBackground());
            g.fillRect(0, 0, this.getWidth(), this.getHeight());
            float alpha=(float)frameIndex/(float)ANIMATION_FRAMES;
            Graphics2D g2d=(Graphics2D)g;
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
            //Renderer渲染机制
            super.paint(g2d);
            g2d.setColor(oldColor);
        } else {
            frameIndex=ANIMATION_FRAMES;
            timer=new Timer(ANIMATION_INTERVAL, this);
            timer.start();
        }
    }

    //判断当前是否正在进行动画
    private boolean isAnimating(){
        return timer!=null && timer.isRunning();
    }
    //关闭时钟，重新初始化
    private void closeTimer() {
        if(isAnimating()){
            timer.stop();
            frameIndex=ANIMATION_FRAMES;
            timer=null;
            Container parent = getParent();
            parent.remove(this);
            parent.revalidate();
        }
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        frameIndex--;
        if(frameIndex>=0)
            //最后一帧，关闭动画
            repaint();
        else//更新当前一帧
            closeTimer();
    }
}
