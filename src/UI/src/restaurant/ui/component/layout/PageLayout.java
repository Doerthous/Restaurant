package restaurant.ui.component.layout;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PageLayout implements LayoutManager {
    public static final int HORIZONTAL_TOP = 0;
    public static final int HORIZONTAL_CENTER = 1;
    public static final int HORIZONTAL_BOTTOM = 2;
    public static final int VERTICAL_LEFT = 3;
    public static final int VERTICAL_CENTER = 4;
    public static final int VERTICAL_RIGHT = 5;

    @Override
    public void addLayoutComponent(String name, Component comp) {}
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
            layout(parent);
            parent.revalidate();
        }
    }

    private int hgap;
    private int vgap;
    private Insets padding;
    private int halign;
    private int valign;
    private boolean singleRow;
    private boolean singleCol;
    public PageLayout(){
        hgap = 5;
        vgap = 5;
        halign = HORIZONTAL_TOP;
        valign = VERTICAL_LEFT;
        padding = new Insets(5,5,5,5);
    }
    public PageLayout setHgap(int hgap) {
        this.hgap = hgap;
        return this;
    }
    public PageLayout setVgap(int vgap) {
        this.vgap = vgap;
        return this;
    }
    public PageLayout setPadding(Insets padding){
        this.padding = padding;
        return this;
    }
    public PageLayout setHorizontalAlignment(int halign){
        this.halign = halign;
        return this;
    }
    public PageLayout setVerticalAlignment(int valign){
        this.valign = valign;
        return this;
    }
    public PageLayout setSingleRow(boolean singleRow) {
        this.singleRow = singleRow;
        return this;
    }
    public PageLayout setSingleCol(boolean singleCol) {
        this.singleCol = singleCol;
        return this;
    }

    //
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
        List<List<Integer>> l = getPageData(parent, -1);
        int pageCount = l.get(l.size()-1).get(0);
        if(currPage > pageCount){
            currPage = pageCount;
        }
        return pageCount;
    }
    public int getCurrentPage(Container parent){
        return currPage;
    }


    /*
        bi: begin index
        hl: height limit
        wl: width limit
        rh: remain hight
        vg: vgap
        hg: hgap
        ignsi: index of those components who will be ignored
        ret: bi, ignsi ..., w, mh, ei
     */
    private List<Integer> layoutRow(Component[] cs, int bi, int hl, int wl, int rh, int vg, int hg){
        java.util.List<Integer> ret = new ArrayList<>();
        ret.add(bi);
        int rw = wl; // remain width;
        int mh = 0;
        int i = bi;
        for(; i < cs.length; ++i) {
            int ch = (int) cs[i].getPreferredSize().getHeight();
            int cw = (int) cs[i].getPreferredSize().getWidth();
            if(ch > hl || cw > wl) { // ignore
                ret.add(i);
            } else if(ch+hg > rh) { // page full
                break;
            } else if(cw+vg > rw) { // row full
                break;
            } else {
                // lay this out
                if(ch > mh){
                    mh = ch;
                }
                rw -= cw + vg;
            }
        }
        ret.add(wl-rw); // width
        ret.add(mh+hg); // max height;
        ret.add(i-1);   // end index
        return ret;
    }

    /*
        bi: begin index
        hl: height limit
        wl: width limit
        vg: vgap
        hg: hgap
        ret:
            bi, ignsi ..., w, mh, ei (row1)
            bi, ignsi ..., w, mh, ei (row2)
            ...
            pc
     */
    private List<List<Integer>> layoutPage(Component[] cs, int bi, int hl, int wl, int vg, int hg) {
        java.util.List<java.util.List<Integer>> ret = new ArrayList<>();
        int rh = hl;
        while(rh > 0) {
            java.util.List<Integer> d = layoutRow(cs, bi, hl, wl, rh, vg, hg);
            int rbi = d.get(0);
            int rei = d.get(d.size() - 1);
            if(rbi <= rei){ // sucess lay out a row
                ret.add(d);
            }
            if (rei == cs.length-1) { // component lay out finish
                break;
            } else if (cs[rei+1].getPreferredSize().getHeight() > rh) { // page full
                break;
            }
            rh -= d.get(d.size()-2);
            bi = rei+1;
        }
        return ret;
    }

    /*
        targetPage: 
            [1, pagecoun] for target page data
            otherwise, the last page data
        pc: page count
        ret:
            bi, ignsi ..., w, mh, ei (row1)
            bi, ignsi ..., w, mh, ei (row2)
            ...
            pc
     */
    private List<List<Integer>> getPageData(Container container, int targetPage) {
        int pageCount = 1;
        int cw = (int) container.getSize().getWidth();
        int ch = (int) container.getSize().getHeight();
        int wl = cw - padding.left - padding.right;
        int hl = ch - padding.top - padding.bottom;
        Component[] cs = container.getComponents();
        setChildComponentForSingle(cs, cw, ch);
        java.util.List<java.util.List<Integer>> page = new ArrayList<>();
        if(hl > 0 && wl > 0 && cs.length > 0) {
            int ei = 0;
            while (ei < cs.length && pageCount-1 != targetPage) {
                page = layoutPage(container.getComponents(), ei,
                        hl, wl, vgap, hgap);
                java.util.List<Integer> lastRow = page.get(page.size() - 1);
                ei = lastRow.get(lastRow.size() - 1)+1;
                ++pageCount;
            }
            --pageCount;
        }
        page.add(Arrays.asList(pageCount));
        return page;
    }

    /*
        cw: container width
        ch: container height
     */
    private void setChildComponentForSingle(Component[] comps, int cw, int ch){
        if(singleCol){
            int w = (int) (cw - padding.left - padding.right - vgap);
            for(Component comp : comps){
                int h = (int) comp.getPreferredSize().getHeight();
                comp.setPreferredSize(new Dimension(w, h));
            }
        } else if(singleRow) {
            int h = (int) (ch - padding.top - padding.bottom - hgap);
            for(Component comp : comps){
                int w = (int) comp.getPreferredSize().getWidth();
                comp.setPreferredSize(new Dimension(w, h));
            }
        }
    }

    private void layout(Container container){
        List<List<Integer>> page = getPageData(container, currPage);
        int count = page.get(page.size()-1).get(0);
        if(currPage > count){
            currPage = count;
        }
        Component[] comps = container.getComponents();
        for (Component comp : comps){
            comp.setVisible(false);
        }
        page.remove(page.size()-1);
        int hl = (int) container.getSize().getHeight() - padding.top - padding.bottom;
        int wl = (int) container.getSize().getWidth() - padding.left - padding.right;
        int y = 0;
        int x = 0;
        int h = 0;
        for(List<Integer> row : page){
            h += row.get(row.size()-2);
        }
        if(halign == HORIZONTAL_TOP){
            y = padding.top+hgap/2;
        } else if(halign == HORIZONTAL_CENTER){
            y = (hl-h+hgap)/2;
        } else if(halign == HORIZONTAL_BOTTOM){
            y = (hl-h)+hgap/2;
        }
        for(List<Integer> row : page){
            int bi = row.get(0);
            int ei = row.get(row.size()-1);
            int w = row.get(row.size()-3);
            int mh = row.get(row.size()-2);
            if(valign == VERTICAL_LEFT) {
                x = padding.left+hgap/2;
            } else if(valign == VERTICAL_CENTER) {
                x = (wl-w+hgap)/2;
            } else if(valign == VERTICAL_RIGHT) {
                x = wl-w+hgap/2;
            }
            row.remove(0);
            row.remove(row.size()-1);
            row.remove(row.size()-1);
            row.remove(row.size()-1);
            for(int i = bi; i <= ei; ++i) {
                if(!row.contains(i)){
                    int cw = (int)comps[i].getPreferredSize().getWidth();
                    int ch = (int)comps[i].getPreferredSize().getHeight();
                    comps[i].setBounds(x, y, cw, ch);
                    comps[i].setVisible(true);
                    x += cw+vgap;
                }
            }
            y += mh;
        }
    }
}
