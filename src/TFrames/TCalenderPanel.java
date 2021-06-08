package TFrames;

import javax.swing.*;
import java.awt.*;

public class TCalenderPanel extends TPanel{
    //JPanel toolPanel?
    JPanel calenderPanel;
    JPanel dateDescriptionPanel;
    JPanel todoListPanel;

    public TCalenderPanel(){
        panel = new JPanel();
        panel.setLayout(new BorderLayout());
        initCalenderPanel();
        panel.add(calenderPanel,BorderLayout.CENTER);
    }

    private void initCalenderPanel(){
        calenderPanel = new JPanel();
        calenderPanel.setLayout(new GridLayout(7,7));
        JLabel label[] = new JLabel[7];
        String weekdays[] = {"SUN","MON","TUE","WED","THU","FRI","SAT"};
        for(int i=0;i<7;i++){
            label[i] = new JLabel();
            label[i].setText(weekdays[i]);
            label[i].setFont(TFrameTools.BUTTONFONT);
            label[i].setHorizontalAlignment(SwingConstants.CENTER);
            calenderPanel.add(label[i]);
        }
        label[0].setForeground(TFrameTools.copyColor(TFrameTools.HOLIDAYCOLOR));
        label[6].setForeground(TFrameTools.copyColor(TFrameTools.HOLIDAYCOLOR));

        JButton dates[][] = new JButton[6][7];
        for(int i=0;i<6;i++){
            for(int j=0;j<7;j++){
                dates[i][j] = TFrameTools.createTButton(" ");
                dates[i][j].setFont(TFrameTools.BUTTONFONT);
                calenderPanel.add(dates[i][j]);
            }
        }
    }

    private void paintCalenderPanel(){

    }

    private void initDateDescriptionPanel(){

    }

    private void paintDateDescriptionPanel(){

    }

    private void initTodoListPanel(){

    }

    private void paintTodoListPanel(){

    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("日历测试");
        TCalenderPanel panel = new TCalenderPanel();
        frame.setLayout(new BorderLayout());
        frame.add(panel.getPanel(),BorderLayout.CENTER);


        frame.pack();
        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

}
