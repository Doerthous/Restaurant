package restaurant.ui.component.border;

import javax.swing.border.AbstractBorder;
import java.awt.*;

public class AdvLineBorder extends AbstractBorder {
    private int left;
    private int right;
    private int top;
    private int bottom;
    private Color lColor;
    private Color rColor;
    private Color tColor;
    private Color bColor;
    public AdvLineBorder(){
        left = 0;
        right = 0;;
        top = 0;;
        bottom = 0;;
        lColor = Color.black;
        rColor = Color.black;
        tColor = Color.black;
        bColor = Color.black;
    }

    public int getLeft() {
        return left;
    }

    public AdvLineBorder setLeft(int left) {
        this.left = left;
        return this;
    }

    public int getRight() {
        return right;
    }

    public AdvLineBorder setRight(int right) {
        this.right = right;
        return this;
    }

    public int getTop() {
        return top;
    }

    public AdvLineBorder setTop(int top) {
        this.top = top;
        return this;
    }

    public int getBottom() {
        return bottom;
    }

    public AdvLineBorder setBottom(int bottom) {
        this.bottom = bottom;
        return this;
    }

    public Color getLeftColor() {
        return lColor;
    }

    public AdvLineBorder setLeftColor(Color lColor) {
        this.lColor = lColor;
        return this;
    }

    public Color getRighttColor() {
        return rColor;
    }

    public AdvLineBorder setRightColor(Color rColor) {
        this.rColor = rColor;
        return this;
    }

    public Color getTopColor() {
        return tColor;
    }

    public AdvLineBorder setTopColor(Color tColor) {
        this.tColor = tColor;
        return this;
    }

    public Color getBottomColor() {
        return bColor;
    }

    public AdvLineBorder setBottomColor(Color bColor) {
        this.bColor = bColor;
        return this;
    }

    @Override
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        if (g instanceof Graphics2D) {
            Graphics2D g2d = (Graphics2D) g;

            Color oldColor = g2d.getColor();

            if(left > 0 && left < width){
                paintLeftBorder(g2d, x, y, width, height);
            }
            if(right > 0 && right < width){
                paintRightBorder(g2d, x, y, width, height);
            }
            if(top > 0 && top < height){
                paintTopBorder(g2d, x, y, width, height);
            }
            if(bottom > 0 && bottom < height){
                paintBottomBorder(g2d, x, y, width, height);
            }

            g2d.setColor(oldColor);
        }
    }

    private void paintLeftBorder(Graphics2D g, int x, int y, int width, int height) {
        g.setColor(lColor);
        g.fillRect(x, y, x+left, y+height);
    }
    private void paintRightBorder(Graphics2D g, int x, int y, int width, int height) {
        g.setColor(rColor);
        g.fillRect(x+width-right, y, x+right, y+height);
    }
    private void paintTopBorder(Graphics2D g, int x, int y, int width, int height) {
        g.setColor(tColor);
        g.fillRect(x, y, x+width, y+top);
    }
    private void paintBottomBorder(Graphics2D g, int x, int y, int width, int height) {
        g.setColor(bColor);
        g.fillRect(x, y+height-bottom, x+width, y+bottom);
    }
}
