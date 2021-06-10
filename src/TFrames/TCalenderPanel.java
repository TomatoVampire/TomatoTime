package TFrames;

import TCalenders.TDateContainer;
import TCalenders.TDateMark;
import TCalenders.TDateModified;
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
    TDateButton selectedButton;
    JPanel dateDescriptionPanel;
    JPanel todoListPanel;
    TTime selectedDate;

    static final int MONTH_LENGTH[]
            = {31,28,31,30,31,30,31,31,30,31,30,31};

    public TCalenderPanel(){
        panel = new JPanel();
        panel.setBorder(BorderFactory.createLineBorder(new Color(0,0,0,0),10));
        panel.setLayout(new AfAnyWhere());
        initCalenderPanel();
        paintCalenderPanel(TManager.getInstance().getNowTime());
        initDateDescriptionPanel();
        selectedDate = TManager.getInstance().getNowTime();

        dateDescriptionPanel.setBackground(new Color(239, 145, 145));
        dateDescriptionPanel.setPreferredSize(new Dimension(620,340));
        panel.add(calenderFullPanel,AfMargin.TOP_LEFT);
        panel.add(dateDescriptionPanel,AfMargin.BOTTOM_LEFT);
    }

    private void initCalenderPanel(){
        calenderFullPanel = new JPanel(new BorderLayout());
        calenderFullPanel.setPreferredSize(new Dimension(620,620));

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
            if(selectedButton!=null) selectedButton.losefocus();
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
                        if(temp == selected.getDay()) {selectedButton = dates[i][j];selectedButton.selectButton();}
                        temp++;
                    }
                }//j
            }//i
            panel.repaint();
        }
        catch (Exception e){
            JOptionPane.showMessageDialog(null,e.getCause(),"出错！",JOptionPane.ERROR_MESSAGE);
        }
    }


    String weekdaychinese[] = {"一","二","三","四","五","六","日"};
    JPanel leftPanel;
    JPanel rightPanel;
    JLabel yeardes;
    JLabel weekdaydes;
    JLabel datemark;
    JLabel modifieddatemark;

    private void initDateDescriptionPanel(){
        dateDescriptionPanel = TFrameTools.createPanel(new GridLayout(1,2,15,0));
        leftPanel = TFrameTools.createPanel(new GridLayout(2,1,0,10));
        rightPanel = TFrameTools.createPanel(new GridLayout(2,1,0,10));
        leftPanel.setBorder(TFrameTools.EmptyDateBorder);
        rightPanel.setBorder(TFrameTools.EmptyDateBorder);
        dateDescriptionPanel.add(leftPanel);
        dateDescriptionPanel.add(rightPanel);

        //左面板
        yeardes = TFrameTools.createLabel(TManager.getInstance().getNowTime().getYear()+"年"
        +TManager.getInstance().getNowTime().getMonth()+"月"
        +TManager.getInstance().getNowTime().getDay()+"日");
        yeardes.setFont(TFrameTools.TEXTFONT);
        yeardes.setVerticalAlignment(SwingConstants.BOTTOM);
        weekdaydes = TFrameTools.createLabel("");
        weekdaydes.setFont(TFrameTools.TEXTFONT);
        weekdaydes.setVerticalAlignment(SwingConstants.TOP);
        JPanel temppanel = TFrameTools.createPanel(new AfAnyWhere());
        JButton todaybtn = TFrameTools.createTButton("回到今天");
        todaybtn.addActionListener(e->paintAll(TManager.getInstance().getNowTime()));
        temppanel.add(weekdaydes,AfMargin.TOP_LEFT);
        temppanel.add(todaybtn,AfMargin.BOTTOM_LEFT);

        leftPanel.add(yeardes);
        //leftPanel.add(weekdaydes);
        leftPanel.add(temppanel);

        //右面板
        JPanel up = TFrameTools.createPanel(new AfAnyWhere());
        up.setBorder(TFrameTools.EmptyDateBorder);
        JPanel down = TFrameTools.createPanel(new AfAnyWhere());
        down.setBorder(TFrameTools.EmptyDateBorder);

        JLabel s1 = TFrameTools.createLabel("今日：");
        s1.setVerticalAlignment(SwingConstants.BOTTOM);//???
        JLabel s2 = TFrameTools.createLabel("纪念：");
        s2.setVerticalAlignment(SwingConstants.CENTER);
        datemark = TFrameTools.createLabel("无");
        modifieddatemark = TFrameTools.createLabel("无");

        //修改按钮
        JButton upbtn = TFrameTools.createTButton("修改");
        upbtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //修改日期类型
                TDateMark.DateType types[] = {TDateMark.DateType.WORKDAY,TDateMark.DateType.RESTDAY,TDateMark.DateType.ULTRAWORK};
                TDateMark.DateType mark = (TDateMark.DateType)JOptionPane.showInputDialog(null,
                        "请选择日期类型","修改日期类型",
                        1,null,types,types[0]);
                System.out.println(selectedDate.toString()+"日期类型修改为："+mark);
                changeDateMark(selectedDate,mark);
                paintAll(selectedDate);
            }
        });
        JButton downbtn = TFrameTools.createTButton("修改");
        downbtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //修改自定义日期
                String memo =JOptionPane.showInputDialog(null,
                        "请输入纪念日名称：\n", "修改纪念日", JOptionPane.PLAIN_MESSAGE);
                System.out.println(selectedDate.toString()+"纪念日修改为："+memo);
                changeModifiedDateMark(selectedDate,memo);
                paintAll(selectedDate);
            }
        });

        up.add(s1,AfMargin.CENTER_LEFT);
        up.add(datemark,AfMargin.CENTER);
        up.add(upbtn,AfMargin.CENTER_RIGHT);
        down.add(s2,AfMargin.CENTER_LEFT);
        down.add(modifieddatemark,AfMargin.CENTER);
        down.add(downbtn,AfMargin.CENTER_RIGHT);

        rightPanel.add(up);
        rightPanel.add(down);


        paintDateMarks(selectedDate);
        paintDateDescriptionPanel(selectedDate);

    }

    private void paintDateDescriptionPanel(TTime selected){
        try {

            String s1 = selected.getYear() + "年"
                    + selected.getMonth() + "月"
                    + selected.getDay() + "日";
            int t =selected.get(GregorianCalendar.DAY_OF_WEEK) - 2;
            if(t < 0) t = 6;
            String s2 = "星期" + weekdaychinese[t];
            yeardes.setText(s1);
            weekdaydes.setText(s2);

            panel.repaint();
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    private void paintDateMarks(TTime selected){
        try {
            //有容器
            if(TManager.getInstance().getContainerofDate(selected)!=null){
                TDateContainer container = TManager.getInstance().getContainerofDate(selected);
                datemark.setText(container.getDatemark().getMemo());
                if(container.getModifiedDateCount()>0){
                    modifieddatemark.setText(container.getModifiedDate(0).getMemo());
                }
                else{
                    modifieddatemark.setText("无");
                }
            }
            else{
                int i= selectedDate.get(GregorianCalendar.DAY_OF_WEEK);
                String str = i==1||i==7?"休息日":"工作日";
                datemark.setText(str);
                modifieddatemark.setText("无");
                //if((selectedDate.get(GregorianCalendar.DAY_OF_WEEK)-2)==6 || selectedDate.get(GregorianCalendar.DAY_OF_WEEK))
            }
        }catch (Exception e){
                System.out.println(e.getMessage());
        }
    }

    public void changeDateMark(TTime selected, TDateMark.DateType ntype){
        try{
            //有容器
            if(TManager.getInstance().getContainerofDate(selected)!=null){
                TDateContainer container = TManager.getInstance().getContainerofDate(selected);
                container.setDatemark(ntype);
            }
            //没有容器：创建新的容器
            else{
                TDateContainer container = TManager.getInstance().getCalender().createDateContainer(selected);
                container.setDatemark(ntype);
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public void changeModifiedDateMark(TTime selected,String memo){
        try{
            //有容器
            if(TManager.getInstance().getContainerofDate(selected)!=null){
                TDateContainer container = TManager.getInstance().getContainerofDate(selected);
                if(container.getModifiedDateCount()==0){
                    container.addModifiedDate(memo);
                }
                else{
                    container.changeModifiedDate(0,memo);
                }
            }
            //没有容器：创建新的容器
            else{
                TDateContainer container = TManager.getInstance().getCalender().createDateContainer(selected);
                container.addModifiedDate(memo);
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    private void initTodoListPanel(){

    }

    private void paintTodoListPanel(){

    }

    private void paintAll(TTime selected){
        paintCalenderPanel(selected);
        paintDateDescriptionPanel(selected);
        paintDateMarks(selected);
    }



    class TDateAction implements ActionListener{
        TDateButton button;
        public TDateAction(TDateButton b){ button = b;}
        @Override
        public void actionPerformed(ActionEvent e) {
            selectedDate = button.getRefTime();
            if(selectedButton!=null) selectedButton.losefocus();
            selectedButton = button;
            selectedButton.selectButton();

            System.out.println("选中时间："+selectedDate.toString());
            paintAll(selectedDate);
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("日历测试");
        TCalenderPanel panel = new TCalenderPanel();
        frame.setLayout(new BorderLayout());
        frame.setBounds(500,0,960,960);
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
        setBorder(TFrameTools.EmptyDateBorder);
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
        losefocus();
    }

    public void activeButton(String content){
        setEnabled(true);
        setText(content);
        memolabel.setText(" ");
    }
    public void activeButton(int content){
        activeButton(Integer.toString(content));
    }
    public void selectButton(){
        setBorder(TFrameTools.SelectedDateBorder);
    }
    public void losefocus(){
        setBorder(TFrameTools.EmptyDateBorder);
    }


    class DateActionListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            //reftime;
        }
    }



}