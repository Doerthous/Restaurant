package restaurant.ui.management.component;

import restaurant.ui.component.builder.JButtonBuilder;
import restaurant.ui.component.RectangleCard;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class OnlineTable extends RectangleCard implements ActionListener {
    private java.util.List<ActionListener> listeners;
    private String tableId;
    public OnlineTable(String tableId) {
        super(new Dimension(100,100));
        setLayout(new BorderLayout(0,0));
        this.tableId = tableId;
        listeners = new ArrayList<>();
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                actionPerformed(new ActionEvent(OnlineTable.this,1,""));
            }
        });
        add(JButtonBuilder.getInstance().text(tableId)
                .listener(this).build());
    }
    public OnlineTable addActionListener(ActionListener listener){
        listeners.add(listener);
        return this;
    }

    public String getTableId(){
        return tableId;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        e = new ActionEvent(this, 1, "");
        for(ActionListener listener: listeners){
            listener.actionPerformed(e);
        }
    }
}