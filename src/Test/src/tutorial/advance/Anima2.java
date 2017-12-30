package tutorial.advance;

import restaurant.ui.component.RectangleCard;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Anima2 extends RectangleCard {
    //常数定义
    private static final int ANIMATION_FRAMES=50;
    private static final int ANIMATION_INTERVAL=300;
    //帧索引
    private int frameIndex;
    //时钟
    private Timer timer;
    //
    private StartAnima sa;
    private EndAnima ea;

    public Anima2() {
        sa = new StartAnima();
        ea = new EndAnima();
    }

    @Override
    public void paint(Graphics g){
        if(isAnimating()){
            //根据当前帧显示当前透明度的内容组件
            float alpha=(float)frameIndex/(float)ANIMATION_FRAMES;
            Graphics2D g2d=(Graphics2D)g;
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
            //Renderer渲染机制
            super.paint(g2d);
        }else{
            //如果是第一次，启动动画时钟
            frameIndex=0;
            timer=new Timer(ANIMATION_INTERVAL, sa);
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
            frameIndex=0;
            timer=null;
        }
    }

    class StartAnima implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            //前进一帧
            frameIndex++;
            if(frameIndex>=ANIMATION_FRAMES) {
                //最后一帧，一秒后进入淡出动画
                timer.stop();
                timer = new Timer(1000, ea);
                timer.start();
            }
            else//更新当前一帧
                repaint();
        }
    }
    class EndAnima implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if(frameIndex > 0) {
                if(frameIndex >= ANIMATION_FRAMES){
                    timer.stop();
                    timer = new Timer(ANIMATION_INTERVAL, ea);
                    timer.start();
                }
                repaint();
            }
            else//更新当前一帧
                closeTimer();
            //后退一帧
            frameIndex--;
        }
    }

}
