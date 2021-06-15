package TFrames;

import TFrames.AFLayouts.AfAnyWhere;
import TFrames.AFLayouts.AfMargin;
import TManagers.TManager;
import TTimepkg.TTime;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
        panel = new JPanel();
        panel.setBorder(BorderFactory.createLineBorder(new Color(0,0,0,0),10));
        panel.setLayout(new AfAnyWhere());
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

        panel.add(temppanel, AfMargin.CENTER);
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

    public static JPanel simpleClockPanel(){
        JPanel panel = new JPanel();


        return panel;
    }

}

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

          JLabel nian   = TFrameTools.createLabel("年");
          JLabel yue    = TFrameTools.createLabel("月");
          JLabel ri     = TFrameTools.createLabel("日");
          JLabel shi    = TFrameTools.createLabel("时");
          JLabel fen    = TFrameTools.createLabel("分");
          JLabel miao   = TFrameTools.createLabel("秒");

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
                      JOptionPane.showMessageDialog(inpanel,"修改时间成功！");
                  }catch (Exception ex){
                      TManager.getInstance().setNowtime(temp);
                      JOptionPane.showMessageDialog(inpanel,"输入参数有误！");

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
