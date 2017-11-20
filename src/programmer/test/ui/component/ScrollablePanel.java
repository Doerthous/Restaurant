package programmer.test.ui.component;

import javax.swing.*;
import java.awt.*;

/*
    http://www.it1352.com/314504.html
    JScrollPane中的JPanel中的JTextArea文字消失问题
 */
public class ScrollablePanel extends JPanel implements Scrollable{

    public ScrollablePanel() {
    }

    public ScrollablePanel(LayoutManager layout) {
        super(layout);
    }

    public Dimension getPreferredScrollableViewportSize() {
        return super.getPreferredSize(); //tell the JScrollPane that we want to be our 'preferredSize' - but later, we'll say that vertically, it should scroll.
    }

    public int getScrollableUnitIncrement(Rectangle visibleRect, int orientation, int direction) {
        return 16;//set to 16 because that's what you had in your code.
    }

    public int getScrollableBlockIncrement(Rectangle visibleRect, int orientation, int direction) {
        return 16;//set to 16 because that's what you had set in your code.
    }

    public boolean getScrollableTracksViewportWidth() {
        return true;//track the width, and re-size as needed.
    }

    public boolean getScrollableTracksViewportHeight() {
        return false; //we don't want to track the height, because we want to scroll vertically.
    }
}
