package TFrames;

import TFrames.AFLayouts.AfAnyWhere;
import TFrames.AFLayouts.AfMargin;
import TManagers.TManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TCountdownPanel extends TPanel{

    JPanel cardPanel;
    JPanel selectbtnpanel;
    JPanel countdownPanel;
    JPanel tomatoPanel;

    boolean isCounting;

    public TCountdownPanel(){
        isCounting = false;
        panel = new JPanel(new BorderLayout());
        selectbtnpanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        cardPanel = new JPanel(new CardLayout());
        JButton countdownbtn = TFrameTools.createTButton("倒计时");
        JButton tomatobtn = TFrameTools.createTButton("番茄钟");
        selectbtnpanel.add(countdownbtn);
        selectbtnpanel.add(tomatobtn);
        panel.add(selectbtnpanel, BorderLayout.NORTH);
        panel.add(cardPanel,BorderLayout.CENTER);
        countdownPanel = new CountdownPanel();
        tomatoPanel = new TomatoPanel();

        cardPanel.add(countdownPanel,"countdown");
        cardPanel.add(tomatoPanel,"tomato");
        countdownbtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CardLayout cardLayout = (CardLayout) cardPanel.getLayout();
                cardLayout.show(cardPanel,"countdown");
            }
        });
        tomatobtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CardLayout cardLayout = (CardLayout) cardPanel.getLayout();
                cardLayout.show(cardPanel,"tomato");
            }
        });
    }


    class CountdownPanel extends JPanel{
        JLabel timeRemain;
        JPanel btnpanel;
        JButton timerdisp;//?
        JButton startbtn;
        JButton editbtn;
        JButton cancelbtn;
        JPanel editTimeBar;
        Timer timer;


        public CountdownPanel(){
            this.setLayout(new AfAnyWhere());
            this.setBorder(TFrameTools.EmptyDateBorder);
            //this.add(new JLabel("倒计时"),AfMargin.CENTER);
            this.setBackground(new Color(176, 234, 214));
            timeRemain = TFrameTools.createLabel("剩余时间");
            startbtn = TFrameTools.createTButton("开始计时");
            editbtn = TFrameTools.createTButton("修改时间");
            cancelbtn = TFrameTools.createTButton("取消");
            editTimeBar = new TEditTimeBar();
            editTimeBar.setVisible(false);

            //计时器
            timer = new Timer(1000,e->paintTimer());
            timer.start();

            timerdisp = TFrameTools.createTButton("00:00:00",new Font("Consolas",1,50));
            //timer.setToolTipText("点击中央的时间来设置倒计时时间");
            timerdisp.setContentAreaFilled(false);
            timerdisp.setPreferredSize(new Dimension(400,200));
            timerdisp.setBorderPainted(false);
            timerdisp.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if(!isCounting) editTimeBar.setVisible(!editTimeBar.isVisible());
                    repaint();
                }
            });
            //timer.set
            add(timerdisp,AfMargin.CENTER);

            startbtn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    startCountdown();
                }
            });

            editbtn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if(!isCounting) editTimeBar.setVisible(!editTimeBar.isVisible());
                    repaint();
                }
            });

            cancelbtn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int result = JOptionPane.showConfirmDialog(null,"确定要停止计时吗？","停止计时",JOptionPane.PLAIN_MESSAGE);
                    if(result==0){
                        System.out.println("取消计时");
                        cancelCountdown();
                        isCounting = false;
                    }
                }
            });

            btnpanel = TFrameTools.createPanel(new FlowLayout(FlowLayout.CENTER,50,10));
            btnpanel.setPreferredSize(new Dimension(350,200));
            btnpanel.add(startbtn);
            btnpanel.add(editbtn);
            btnpanel.add(editTimeBar);
            btnpanel.add(cancelbtn);
            cancelbtn.setVisible(false);
            add(btnpanel,AfMargin.BOTTOM_CENTER);
        }
        public void loadNowTimerFromManager(){
            String remain = TManager.getInstance().getCountdown().getNowTimerHour()+":"
                    +TManager.getInstance().getCountdown().getNowTimerMinute()+":"
                    +TManager.getInstance().getCountdown().getNowTimerSecond();

            timerdisp.setText(remain);
            System.out.println("load timer from manager");
            //manager的倒计时结束
            if(TManager.getInstance().getCountdown().isCountingDown()==false && isCounting){
                endCountdownCheck();
                repaint();
                isCounting = false;
                JOptionPane.showMessageDialog(null,"计时完成。","Tomato Time",JOptionPane.PLAIN_MESSAGE);
            }
        }
        public void paintTimer(){
            loadNowTimerFromManager();
            repaint();
        }
        public void startCountdown(){
            if(isCounting) System.out.println("正在倒计时，无法开始新的倒计时！！");
            if(TManager.getInstance().getCountdown().isCountingDown()==false){
                TManager.getInstance().getCountdown().startCount();
                startbtn.setVisible(false);
                editbtn.setVisible(false);
                editTimeBar.setVisible(false);
                cancelbtn.setVisible(true);
                isCounting = true;
            }
            else{
                System.out.println("manager的countdown正在倒计时！无法开始新的倒计时！");
            }
        }

        public void endCountdownCheck(){
           // if(TManager.getInstance().getCountdown().isCountingDown()==false){
            btnpanel.setVisible(false);
                startbtn.setVisible(true);
                editbtn.setVisible(true);
                //editTimeBar.setVisible(true);
                cancelbtn.setVisible(false);
                btnpanel.setVisible(true);
          // }
        }

        public void cancelCountdown(){
            TManager.getInstance().getCountdown().endCount();
            endCountdownCheck();
            isCounting = false;
        }

        class TEditTimeBar extends JPanel{

            JTextField hour;
            JTextField minute;
            JTextField second;

            public TEditTimeBar(){
                super();
                this.setLayout(new BorderLayout());
                setBackground(TFrameTools.TRANSPARANT);
                setSize(new Dimension(700,500));
                JPanel inpanel = new JPanel(new GridLayout(1,6,3,10));
                JLabel label = TFrameTools.createLabel("请输入新的时间：");

                hour = TFrameTools.createTextField("0");
                minute = TFrameTools.createTextField("30");
                second = TFrameTools.createTextField("0");

                JLabel shi = TFrameTools.createLabel("时");
                shi.setBackground(TFrameTools.TRANSPARANT);
                JLabel fen = TFrameTools.createLabel("分");
                JLabel miao = TFrameTools.createLabel("秒");

                inpanel.add(hour);
                inpanel.add(shi);

                inpanel.add(minute);
                inpanel.add(fen);

                inpanel.add(second);
                inpanel.add(miao);

                JPanel p = TFrameTools.createPanel(new GridLayout(1,2,10,0));
                p.setBackground(TFrameTools.TRANSPARANT);
                JButton sub = TFrameTools.createTButton("确定");
                sub.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        try{
                            int a1 = Integer.valueOf(hour.getText()).intValue();
                            int a2 = Integer.valueOf(minute.getText()).intValue();
                            int a3 = Integer.valueOf(second.getText()).intValue();
                            TManager.getInstance().getCountdown().setTimer(a1,a2,a3);
                            paintTimer();
                            //JOptionPane.showMessageDialog("");
                        }catch (Exception ex){
                            JOptionPane.showMessageDialog(null,"输入时间有误！","出错",JOptionPane.PLAIN_MESSAGE);
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
        }

    }

    class TomatoPanel extends CountdownPanel{
        public TomatoPanel(){
            this.setLayout(new AfAnyWhere());
            this.setBorder(TFrameTools.EmptyDateBorder);
            this.add(new JLabel("番茄钟"),AfMargin.CENTER);
            setBackground(new Color(239, 202, 199));
        }
    }



    public static void main(String[] args) {
        JFrame frame = new JFrame("倒计时");
        TCountdownPanel panel = new TCountdownPanel();
        frame.setLayout(new BorderLayout());
        frame.setBounds(500,0,960,960);
        frame.add(panel.getPanel(),BorderLayout.CENTER);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
}

