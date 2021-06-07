package TFrames;

import TFrames.AFLayouts.AfAnyWhere;
import TFrames.AFLayouts.AfMargin;
import TFrames.AFLayouts.AfSimpleLayout;
import TManagers.TManager;
import TTimepkg.TClock;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TClockFrame {
    JPanel rpanel;
    JLabel hour;
    JLabel minute;
    JLabel second;
    JLabel colon1 = new JLabel(":");
    JLabel colon2 = new JLabel(":");
    Timer timer ;//= new Timer(1000,e->second.setText(TManager.getInstance().getClock()));

    private class TimeAction implements ActionListener{
        public TimeAction(){}

        @Override
        public void actionPerformed(ActionEvent e) {
            second.setText(TManager.getInstance().getClock());
        }
    }

    private void initClockFrame(){
        rpanel = new JPanel();
        rpanel.setLayout(new AfAnyWhere());
        second = new JLabel();
        second.setText(TManager.getInstance().getClock());

        rpanel.add(second, AfMargin.CENTER);
        rpanel.add(colon1,AfMargin.TOP_CENTER);
        timer = new Timer(100,new TimeAction());
        timer.start();
    }

    public TClockFrame(){
        initClockFrame();
    }

    public JPanel getPanel(){return rpanel;}

    public static void main(String[] args) {
        JFrame test = new JFrame("clocktest");
        test.setBounds(100,100,500,500);

        test.setLayout(new BorderLayout());

        TClockFrame frame = new TClockFrame();

        test.add(frame.getPanel(),BorderLayout.CENTER);
        test.setVisible(true);
    }

}
