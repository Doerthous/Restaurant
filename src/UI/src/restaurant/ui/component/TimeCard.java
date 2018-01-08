package restaurant.ui.component;

import restaurant.ui.utils.Utility;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeCard extends JPanel implements ActionListener {
    private JLabel jlDate;
    private JLabel jlTime;
    private Timer timer;
    private SimpleDateFormat timeFormat;
    public TimeCard() {
        timeFormat = new SimpleDateFormat("HH:mm:ss");
        setBorder(BorderFactory.createEmptyBorder(0,5,0,5));
        setOpaque(false);
        setLayout(new BorderLayout(5,0));
        jlDate = new JLabel(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        jlTime = new JLabel(timeFormat.format(new Date()));
        add(jlDate, BorderLayout.WEST);
        add(jlTime, BorderLayout.EAST);
        timer = new Timer(1000,this);
        timer.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        jlTime.setText(new SimpleDateFormat("HH:mm:ss").format(new Date()));
        Utility.revalidate(this);
    }
}
