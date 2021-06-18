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
import java.util.TimeZone;

public class TClockPanel extends TPanel {
    JPanel toolpanel;
    JLabel hour;
    JLabel minute;
    JLabel second;
    JLabel colon1 = new JLabel(":");
    JLabel colon2 = new JLabel(":");
    TEditTimeBar editBar;
    Timer timer ;//= new Timer(1000,e->second.setText(TManager.getInstance().getClock()));
    String webzone = "http://www.ntsc.ac.cn";//当前时区的网络时间网址，初始化为中国时间
    StillClock stillClock;

    private class TimeAction implements ActionListener{
        public TimeAction(){}

        @Override
        public void actionPerformed(ActionEvent e) {
            second.setText(String.format("%02d",TManager.getInstance().getNowTime().getSecond()));
            minute.setText(String.format("%02d",TManager.getInstance().getNowTime().getMinute()));
            hour.setText(String.format("%02d",TManager.getInstance().getNowTime().getHour()));
            stillClock.setCurrentTime(TManager.getInstance().getNowTime());
            stillClock.repaint();
        }
    }

    private void initClockFrame(){
        panel = new JPanel();
        panel.setBorder(BorderFactory.createLineBorder(new Color(0,0,0,0),10));
        panel.setLayout(new AfAnyWhere());
        //panel.setBackground(new Color(234, 219, 148, 255));
        stillClock = new StillClock(TManager.getInstance().getNowTime());
        //JPanel clockpanel = TFrameTools.createPanel(new FlowLayout());
        //clockpanel.add(stillClock);
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


        stillClock.setPreferredSize(new Dimension(800,800));
        JPanel clockntimepanel = TFrameTools.createPanel(new GridLayout(2,1,10,0));
        //clockntimepanel.setPreferredSize(new Dimension(400,400));
        clockntimepanel.add(stillClock);
        clockntimepanel.add(temppanel);

        panel.add(clockntimepanel, AfMargin.CENTER);
        //panel.add(temppanel, AfMargin.CENTER);
        //panel.add(clockpanel,AfMargin.TOP_RIGHT);
        //rpanel.add(colon1,AfMargin.TOP_CENTER);

        timer = new Timer(100,new TimeAction());
        timer.start();
    }



    private void initToolPanel(){
        toolpanel = new JPanel(new FlowLayout());
        JButton setTime = TFrameTools.createTButton("设置时间");

        editBar = new TEditTimeBar();
        editBar.setVisible(false);

        setTime.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //new TEditTimeDialog(panel);
                editBar.updateDate();
                editBar.setVisible(!editBar.isVisible());
            }
        });



        JLabel timez = new JLabel("设置时区");
        timez.setFont(TFrameTools.TEXTFONT);
        JComboBox<String> comboBox = new JComboBox();
        comboBox.addItem("北京时间");
        comboBox.addItem("洛杉矶时间");
        comboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                switch (comboBox.getSelectedIndex()){
                    case 0:
                        TManager.getInstance().getNowTime().synchronizeTime(TimeZone.getTimeZone("Asia/Chongqing"));
                        webzone="http://www.ntsc.ac.cn";//中国网络时间网址
                    break;
                    case 1:
                        TManager.getInstance().getNowTime().synchronizeTime(TimeZone.getTimeZone("America/Los_Angeles"));
                        webzone="https://www.nist.gov/";//美国时间网址？？
                        break;
                    default:break;
                }
            }
        });

        //同步时间按钮
        JButton syncbtn = TFrameTools.createTButton("同步网络时间");
        syncbtn.setToolTipText("将会根据您设备的当前地区获取网络时间");
        syncbtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    TManager.getInstance().getClock().syncronizeWebTime(webzone);
                    JOptionPane.showMessageDialog(null,"同步网络时间成功！","Tomato Time",JOptionPane.PLAIN_MESSAGE);
                    panel.repaint();
                }
                catch (Exception ex){
                    JOptionPane.showMessageDialog(null,"获取网络时间失败！","Tomato Time",JOptionPane.PLAIN_MESSAGE);
                }
            }
        });


        toolpanel.add(setTime);
        toolpanel.add(timez);
        toolpanel.add(comboBox);
        toolpanel.add(syncbtn);
        panel.add(toolpanel,AfMargin.TOP_CENTER);
        panel.add(editBar,AfMargin.BOTTOM_CENTER);
    }

    public TClockPanel(){
        super();
        initClockFrame();
        initToolPanel();
    }

    @Override
    public JPanel getPanel(){return panel;}

    public static void main(String[] args) {
        JFrame test = new JFrame("clocktest");
        test.setBounds(100,100,500,500);
        test.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        test.setLayout(new BorderLayout());

        TClockPanel frame = new TClockPanel();
        test.add(frame.getPanel(),BorderLayout.CENTER);
        test.setVisible(true);
    }

}

//修改时间的按钮
class TEditTimeBar extends JPanel{

     JTextField year    = TFrameTools.createTextField(Integer.toString(TManager.getInstance().getNowTime().getYear()));
     JTextField month   = TFrameTools.createTextField(Integer.toString(TManager.getInstance().getNowTime().getMonth()));
     JTextField day     = TFrameTools.createTextField(Integer.toString(TManager.getInstance().getNowTime().getDay()));
     JTextField hour    = TFrameTools.createTextField(Integer.toString(TManager.getInstance().getNowTime().getHour()));
     JTextField minute  = TFrameTools.createTextField(Integer.toString(TManager.getInstance().getNowTime().getMinute()));
     JTextField second  = TFrameTools.createTextField(Integer.toString(TManager.getInstance().getNowTime().getSecond()));

      public TEditTimeBar(){
          super();
          this.setLayout(new BorderLayout());
          setSize(new Dimension(700,500));
          JPanel inpanel = new JPanel(new GridLayout(1,12,3,10));
          JLabel label = new JLabel("请输入新的时间：");

          JLabel nian   = TFrameTools.createLabel("年",new Font("微软雅黑",1,10));
          JLabel yue    = TFrameTools.createLabel("月",new Font("微软雅黑",1,10));
          JLabel ri     = TFrameTools.createLabel("日",new Font("微软雅黑",1,10));
          JLabel shi    = TFrameTools.createLabel("时",new Font("微软雅黑",1,10));
          JLabel fen    = TFrameTools.createLabel("分",new Font("微软雅黑",1,10));
          JLabel miao   = TFrameTools.createLabel("秒",new Font("微软雅黑",1,10));

          inpanel.add(year);
          inpanel.add(nian);

          inpanel.add(month);
          inpanel.add(yue);

          inpanel.add(day);
          inpanel.add(ri);

          inpanel.add(hour);
          inpanel.add(shi);

          inpanel.add(minute);
          inpanel.add(fen);

          inpanel.add(second);
          inpanel.add(miao);

          JPanel p = new JPanel(new GridLayout(1,2,10,0));
          JButton sub = TFrameTools.createTButton("确定");
          sub.addActionListener(new ActionListener() {
              @Override
              public void actionPerformed(ActionEvent e) {
                  TTime temp = new TTime(
                          TManager.getInstance().getNowTime().getYear(),
                          TManager.getInstance().getNowTime().getMonth(),
                          TManager.getInstance().getNowTime().getDay());
                  temp.setClock(
                          TManager.getInstance().getNowTime().getHour(),
                          TManager.getInstance().getNowTime().getMinute(),
                          TManager.getInstance().getNowTime().getSecond());
                  try{
                      TManager.getInstance().getNowTime().setDate(
                              Integer.valueOf(year.getText()).intValue(),
                              Integer.valueOf(month.getText()).intValue(),
                              Integer.valueOf(day.getText()).intValue());

                      TManager.getInstance().getNowTime().setClock(
                              Integer.valueOf(hour.getText()).intValue(),
                              Integer.valueOf(minute.getText()).intValue(),
                              Integer.valueOf(second.getText()).intValue());
                      JOptionPane.showMessageDialog(inpanel,"修改时间成功！","Tomato Time",JOptionPane.PLAIN_MESSAGE);
                  }catch (Exception ex){
                      TManager.getInstance().setNowtime(temp);
                      JOptionPane.showMessageDialog(inpanel,"输入参数有误！","Tomato Time",JOptionPane.PLAIN_MESSAGE);

                  }
              }
          });
          //JButton exit = TFrameTools.createTButton("取消");//??
          //exit.addActionListener();
          p.add(sub);
          //p.add(exit);

          this.add(label,BorderLayout.NORTH);
          this.add(inpanel,BorderLayout.CENTER);
          this.add(p,BorderLayout.SOUTH);
      }

      public void updateDate(){
          year  .setText(Integer.toString(TManager.getInstance().getNowTime().getYear()));
          month .setText(Integer.toString(TManager.getInstance().getNowTime().getMonth()));
          day   .setText(Integer.toString(TManager.getInstance().getNowTime().getDay()));
          hour  .setText(Integer.toString(TManager.getInstance().getNowTime().getHour()));
          minute.setText(Integer.toString(TManager.getInstance().getNowTime().getMinute()));
          second.setText(Integer.toString(TManager.getInstance().getNowTime().getSecond()));
      }
}


//圆盘时钟
class StillClock extends JPanel {

    private int hour;
    private int minute;
    private int second;

    public StillClock(TTime time) {
        setCurrentTime(time);
    }

    public StillClock(int hour, int minute, int second){
        this.hour = hour;
        this.minute = minute;
        this.second = second;
        setBackground(TFrameTools.TRANSPARANT);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(200, 200);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int xCenter = getWidth()/2;
        int yCenter = getHeight()/2;
        int clockRadius = (int) (Math.min(getWidth(), getHeight()) * 0.8 * 0.5);

        g.setColor(Color.black);
        g.drawOval(xCenter - clockRadius, yCenter - clockRadius, 2 * clockRadius, 2 * clockRadius);
        g.drawString("12", xCenter - 5, yCenter - clockRadius + 12);
        g.drawString("3", xCenter + clockRadius - 10, yCenter + 3);
        g.drawString("6", xCenter - 3, yCenter + clockRadius - 3);
        g.drawString("9", xCenter - clockRadius + 3, yCenter + 3);

        int sLength = (int) (clockRadius * 0.8);
        int xSecnd = (int) (xCenter + sLength * Math.sin(2 * Math.PI/60 * second));
        int ySecond = (int) (yCenter - sLength * Math.cos(2 * Math.PI/60 * second));
        g.setColor(new Color(213, 78, 78));
        g.drawLine(xCenter, yCenter, xSecnd, ySecond);

        int mLength = (int) (clockRadius * 0.65);
        int xMinute = (int) (xCenter + mLength * Math.sin((minute + second/60) * 2 * Math.PI/60));
        int yMinute = (int) (yCenter - mLength * Math.cos((minute + second/60) * (2 * Math.PI/60)));
        g.setColor(new Color(37, 134, 187));
        g.drawLine(xCenter, yCenter, xMinute, yMinute);

        int hLength = (int) (clockRadius * 0.5);
        int xHour = (int) (xCenter + hLength * Math.sin((hour % 12 + minute/60) * (2 * Math.PI/12)));
        int yHour = (int) (yCenter - hLength * Math.cos((hour % 12 + minute/60) * (2 * Math.PI/12)));
        g.setColor(new Color(47, 172, 60));
        g.drawLine(xCenter, yCenter, xHour, yHour);
    }

    public void setCurrentTime(TTime time){
        Calendar calendar = time.getCalender();
        //Calendar calendar = new GregorianCalendar();
        this.hour = calendar.get(Calendar.HOUR_OF_DAY);
        this.minute = calendar.get(Calendar.MINUTE);
        this.second = calendar.get(Calendar.SECOND);
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
        repaint();
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
        repaint();
    }

    public int getSecond() {
        return second;
    }

    public void setSecond(int second) {
        this.second = second;
        repaint();
    }

    // 非原创，来源：
    // https://blog.csdn.net/General_Fate/article/details/84472119?ops_request_misc=%257B%2522request%255Fid%2522%253A%2522162381290516780261947018%2522%252C%2522scm%2522%253A%252220140713.130102334.pc%255Fall.%2522%257D&request_id=162381290516780261947018&biz_id=0&utm_medium=distribute.pc_search_result.none-task-blog-2~all~first_rank_v2~rank_v29-2-84472119.pc_search_result_cache&utm_term=java+swing+%E6%97%B6%E9%92%9F&spm=1018.2226.3001.4187
}