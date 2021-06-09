package TFrames;

import TFrames.AFLayouts.AfAnyWhere;
import TFrames.AFLayouts.AfMargin;
import TManagers.TManager;
import TTimepkg.TTime;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class TCalenderPanel extends TPanel{
    //JPanel toolPanel?
    JPanel calenderFullPanel;
    JPanel toolPanel;
    JLabel toolLabel;
    JPanel calenderPanel;
    TDateButton dates[][] = new TDateButton[6][7];
    JPanel dateDescriptionPanel;
    JPanel todoListPanel;
    TTime selectedDate;

    static final int MONTH_LENGTH[]
            = {31,28,31,30,31,30,31,31,30,31,30,31};

    public TCalenderPanel(){
        panel = new JPanel();
        panel.setLayout(new BorderLayout());
        initCalenderPanel();
        paintCalenderPanel(TManager.getInstance().getNowTime().getTTime());
        panel.add(calenderFullPanel,BorderLayout.CENTER);
    }

    private void initCalenderPanel(){
        calenderFullPanel = new JPanel(new BorderLayout());

        //工具栏？
        toolPanel = new JPanel(new BorderLayout());
        JPanel t1=new JPanel(new FlowLayout());
        JButton yearsubbtn = TFrameTools.createTButton("<<");
        JButton monthsubbtn = TFrameTools.createTButton("<");
        JButton yearaddbtn = TFrameTools.createTButton(">>");
        JButton monthaddbtn = TFrameTools.createTButton(">");
        yearsubbtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectedDate = new TTime(selectedDate.getYear()-1, selectedDate.getMonth(), 1);
                paintCalenderPanel(selectedDate);
            }
        });
        yearaddbtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectedDate = new TTime(selectedDate.getYear()+1, selectedDate.getMonth(), 1);
                paintCalenderPanel(selectedDate);
            }
        });
        monthsubbtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectedDate = new TTime(selectedDate.getYear(), selectedDate.getMonth()-1, 1);
                paintCalenderPanel(selectedDate);
            }
        });
        monthaddbtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectedDate = new TTime(selectedDate.getYear(), selectedDate.getMonth()+1, 1);
                paintCalenderPanel(selectedDate);
            }
        });



        t1.add(yearsubbtn);
        t1.add(monthsubbtn);
        toolLabel = new JLabel("当前时间");
        toolLabel.setFont(TFrameTools.TEXTFONT);
        toolLabel.setHorizontalAlignment(SwingConstants.CENTER);
        toolLabel.setBackground(new Color(0,0,0,0));
        JPanel t2 = new JPanel(new FlowLayout());
        t2.add(monthaddbtn);
        t2.add(yearaddbtn);
        toolPanel.add(t1,BorderLayout.WEST);
        toolPanel.add(toolLabel,BorderLayout.CENTER);
        toolPanel.add(t2,BorderLayout.EAST);


        //日历
        calenderPanel = new JPanel();
        calenderPanel.setLayout(new GridLayout(7,7));
        JLabel label[] = new JLabel[7];
        String weekdays[] = {"MON","TUE","WED","THU","FRI","SAT","SUN"};
        for(int i=0;i<7;i++){
            label[i] = new JLabel();
            label[i].setText(weekdays[i]);
            label[i].setFont(TFrameTools.BUTTONFONT);
            label[i].setHorizontalAlignment(SwingConstants.CENTER);
            calenderPanel.add(label[i]);
        }
        label[5].setForeground(TFrameTools.copyColor(TFrameTools.HOLIDAYCOLOR));
        label[6].setForeground(TFrameTools.copyColor(TFrameTools.HOLIDAYCOLOR));

        for(int i=0;i<6;i++){
            for(int j=0;j<7;j++){
                dates[i][j] = new TDateButton(" ");
                dates[i][j].addActionListener(new TDateAction(dates[i][j]));
                calenderPanel.add(dates[i][j]);
            }
        }

        calenderFullPanel.add(toolPanel,BorderLayout.NORTH);
        calenderFullPanel.add(calenderPanel,BorderLayout.CENTER);
    }

    private void paintCalenderPanel(TTime selected){
        try {
            selectedDate = selected;
            //工具栏更新字
            toolLabel.setText(selected.getYear()+"年"+selected.getMonth()+"月");

            //当月第一天为星期几
            TTime first = new TTime(selected.getYear(), selected.getMonth(), 1);
            int offset = first.get(Calendar.DAY_OF_WEEK)-1;//星期几
            offset = offset == 7 ? 0 : offset;
            int sum = MONTH_LENGTH[first.getMonth()-1];

            int count = 0;
            int temp = 1;
            for (int i = 0; i < 6; i++) {
                for (int j = 0; j < 7; j++) {
                    count++;
                    if (count < offset || temp >= sum+1) {
                        //取消按钮显示
                        dates[i][j].reset();
                    } else {
                        //更新按钮显示
                        dates[i][j].activeButton(temp);
                        TTime ntime = new TTime(selected.getYear(), selected.getMonth(), temp);
                        dates[i][j].setReftime(ntime);
                        temp++;
                    }
                }//j
            }//i
        }
        catch (Exception e){
            JOptionPane.showMessageDialog(null,e.getCause(),"出错！",JOptionPane.ERROR_MESSAGE);
        }
    }

    private void initDateDescriptionPanel(){

    }

    private void paintDateDescriptionPanel(){

    }

    private void initTodoListPanel(){

    }

    private void paintTodoListPanel(){

    }

    private void paintAll(TTime selected){
        paintCalenderPanel(selected);
    }



    class TDateAction implements ActionListener{
        TDateButton button;
        public TDateAction(TDateButton b){ button = b;}
        @Override
        public void actionPerformed(ActionEvent e) {
            selectedDate = button.getRefTime();
            System.out.println("选中时间："+selectedDate.toString());
            paintAll(selectedDate);
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("日历测试");
        TCalenderPanel panel = new TCalenderPanel();
        frame.setLayout(new BorderLayout());
        frame.setBounds(500,100,700,700);
        frame.add(panel.getPanel(),BorderLayout.CENTER);


        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
}

class TDateButton extends JButton{

    TTime reftime;
    JLabel memolabel;
    int tomatoCount;

    public TDateButton(){
        super();
        setLayout(new AfAnyWhere());
        setFont(TFrameTools.TEXTFONT);
        setBackground(TFrameTools.copyColor(TFrameTools.BUTTONCOLOR));
        //button.setContentAreaFilled(false);
        setFocusPainted(false);
        //addActionListener(new DateActionListener());
        memolabel = new JLabel();
        memolabel.setFont(new Font("微软雅黑",0,5));
    }

    public TDateButton(String name){
        this();
        setName(name);
    }

    public void setReftime(TTime time){
        reftime = time;
    }

    public TTime getRefTime(){
        return reftime;
    }

    public void setWordColor(Color color){
        setForeground(color);
    }

    public void addMemo(String memo,Color color){
        memolabel.setText(memo);
        memolabel.setBackground(new Color(0,0,0,0));
        memolabel.setForeground(color);
        add(memolabel, AfMargin.TOP_RIGHT);
    }

    public void reset(){
        setEnabled(false);
        setText(" ");
        memolabel.setText(" ");
    }

    public void activeButton(String content){
        setEnabled(true);
        setText(content);
        memolabel.setText(" ");
    }
    public void activeButton(int content){
        activeButton(Integer.toString(content));
    }


    class DateActionListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            //reftime;
        }
    }



}