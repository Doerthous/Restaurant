package test.ui.layout.custom;

import java.awt.*;

public class PageLayout implements LayoutManager {
    @Override
    public void addLayoutComponent(String name, Component comp) {
    }
    @Override
    public void removeLayoutComponent(Component comp) {
    }
    @Override
    public Dimension preferredLayoutSize(Container parent) {
        synchronized (parent.getTreeLock()) {
            return parent.getSize();
        }
    }
    @Override
    public Dimension minimumLayoutSize(Container parent) {
        synchronized (parent.getTreeLock()) {
            return parent.getSize();
        }
    }
    @Override
    public void layoutContainer(Container parent) {
        synchronized (parent.getTreeLock()) {
            Insets insets = padding;//parent.getInsets();
            int realHeight = (int) (parent.getSize().getHeight() - insets.top - insets.bottom);
            int realWidth = (int) (parent.getSize().getWidth() - insets.left - insets.right);

            /*
                hide all child components
             */
            Component[] comps = parent.getComponents();
            for(int i = 0; i < comps.length; ++i){
                comps[i].setVisible(false);
            }

            /*
                find the first visible child component
             */
            int height = 0;
            int cp = currPage;
            currPage = 1;
            int currPageStartIdx = 0;
            int i = 0;
            for(; i < comps.length; ++i){
                if(cp == 1){
                    break;
                }
                height += comps[i].getPreferredSize().getHeight() + hgap;
                if(height > realHeight){
                    height = 0;
                    --cp;
                    ++currPage;
                    currPageStartIdx = i;
                    --i;
                }
            }

            /*
                lay out child components
             */
            int y = insets.top;
            for(i = currPageStartIdx; i < comps.length; ++i) {
                int h = (int) comps[i].getPreferredSize().getHeight();
                if(y + h > parent.getSize().getHeight()){
                    break;
                }
                comps[i].setBounds(insets.left, y, realWidth, h);
                comps[i].setVisible(true);
                y += h + hgap;
            }

            parent.revalidate();
        }
    }

    private int hgap = 5;
    private Insets padding = new Insets(5,5,5,5);

    private int currPage = 1;
    public void first(Container parent){
        synchronized (parent.getTreeLock()) {
            if (currPage != 1) {
                currPage = 1;
                layoutContainer(parent);
            }
        }
    }
    public void prior(Container parent){
        synchronized (parent.getTreeLock()) {
            if (currPage > 1) {
                --currPage;
                layoutContainer(parent);
            }
        }
    }
    public void next(Container parent){
        synchronized (parent.getTreeLock()) {
            if (currPage <= getPageCount(parent)) {
                ++currPage;
                layoutContainer(parent);
            }
        }
    }
    public void last(Container parent){
        synchronized (parent.getTreeLock()) {
            int pc = getPageCount(parent);
            if (currPage != pc) {
                currPage = pc;
                layoutContainer(parent);
            }
        }
    }
    public void to(Container parent, int page){
        synchronized (parent.getTreeLock()) {
            if (page <= getPageCount(parent)) {
                currPage = page;
                layoutContainer(parent);
            }
        }
    }
    public int getPageCount(Container parent){
        int count = 1;
        Insets insets = parent.getInsets();
        int realHeight = (int) (parent.getSize().getHeight() - insets.top - insets.bottom);

        int height = 0;
        Component[] comps = parent.getComponents();
        for (int i = 0; i < comps.length; ++i) {
            height += comps[i].getPreferredSize().getHeight() + hgap;
            if(height > realHeight){
                height = 0;
                ++count;
                --i;
            }
        }
        return count;
    }
    public int getCurrentPage(Container parent){
        return currPage;
    }
}
