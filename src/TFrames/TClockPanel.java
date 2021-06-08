package TFrames;

import TFrames.AFLayouts.AfAnyWhere;
import TFrames.AFLayouts.AfMargin;
import TManagers.TManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TClockPanel {
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
            second.setText(Integer.toString(TManager.getInstance().getNowTime().getSecond()));
            minute.setText(Integer.toString(TManager.getInstance().getNowTime().getMinute()));
            hour.setText(Integer.toString(TManager.getInstance().getNowTime().getHour()));
        }
    }

    private void initClockFrame(){
        rpanel = new JPanel();
        rpanel.setLayout(new AfAnyWhere());
        second = new JLabel();
        minute = new JLabel();
        hour = new JLabel();
        JPanel temppanel = new JPanel();
        temppanel.setLayout(new FlowLayout());

        second.setText(Integer.toString(TManager.getInstance().getNowTime().getSecond()));
        minute.setText(Integer.toString(TManager.getInstance().getNowTime().getMinute()));
        hour.setText(Integer.toString(TManager.getInstance().getNowTime().getHour()));
        second.setFont(TFrameTools.CLOCKFONT);
        minute.setFont(TFrameTools.CLOCKFONT);
        hour.setFont(TFrameTools.CLOCKFONT);
        colon1.setFont(TFrameTools.CLOCKFONT);
        colon2.setFont(TFrameTools.CLOCKFONT);


        temppanel.add(hour);
        temppanel.add(colon1);
        temppanel.add(minute);
        temppanel.add(colon2);
        temppanel.add(second);
        //temppanel.add(hour);

        rpanel.add(temppanel, AfMargin.CENTER);
        //rpanel.add(colon1,AfMargin.TOP_CENTER);

        timer = new Timer(100,new TimeAction());
        timer.start();
    }

    public TClockPanel(){
        initClockFrame();
    }

    public JPanel getPanel(){return rpanel;}

    public static void main(String[] args) {
        JFrame test = new JFrame("clocktest");
        test.setBounds(100,100,500,500);
        test.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        test.setLayout(new BorderLayout());

        TClockPanel frame = new TClockPanel();
        test.add(frame.getPanel(),BorderLayout.CENTER);
        test.setVisible(true);
    }

    public static JPanel simpleClockPanel(){
        JPanel panel = new JPanel();


        return panel;
    }

}
