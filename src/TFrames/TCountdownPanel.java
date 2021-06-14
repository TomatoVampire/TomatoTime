package TFrames;

import TCalenders.TDateContainer;
import TFrames.AFLayouts.AfAnyWhere;
import TFrames.AFLayouts.AfMargin;
import TManagers.TManager;
import TTimepkg.TTime;

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
        boolean thiscounting;


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
            thiscounting = false;//自己的倒计时是否在计时

            //计时器
            timer = new Timer(10,e->paintTimer());
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
                    int result = JOptionPane.showConfirmDialog(null,
                            "确定要停止计时吗？","停止计时",JOptionPane.YES_NO_OPTION);
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
            //System.out.println("load timer from manager");
            //manager的倒计时结束
            if(TManager.getInstance().getCountdown().isCountingDown()==false && isCounting && thiscounting){
                isCounting = false;
                thiscounting = false;
                JOptionPane.showMessageDialog(null,"计时完成。","Tomato Time",JOptionPane.PLAIN_MESSAGE);
                endCountdownCheck();
                repaint();
            }
        }
        public void paintTimer(){
            loadNowTimerFromManager();
            repaint();
        }
        public void startCountdown(){
            boolean countsuccess = false;
            if(isCounting) {
                System.out.println("正在倒计时，无法开始新的倒计时！！");
            }
            if(TManager.getInstance().getCountdown().isCountingDown()==false){
                TManager.getInstance().getCountdown().startCount();
                startbtn.setVisible(false);
                editbtn.setVisible(false);
                editTimeBar.setVisible(false);
                cancelbtn.setVisible(true);
                isCounting = true;
                thiscounting = true;
                countsuccess = true;
            }
            else{
                System.out.println("manager的countdown正在倒计时！无法开始新的倒计时！");
            }
            if(!countsuccess){
                JOptionPane.showMessageDialog(null,"当前有倒计时/番茄正在进行，无法开始倒计时！","Tomato Time",
                        JOptionPane.PLAIN_MESSAGE);
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
            thiscounting = false;
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
        JLabel timeRemain;
        JPanel btnpanel;
        JButton timerdisp;//?
        JButton startbtn;
        JButton editbtn;
        JButton cancelbtn;
        JPanel editTimeBar;
        Timer timer;
        JLabel tomatoCount;
        JLabel consecutivetomato;
        JLabel hint;

        boolean isbreak;

        public TomatoPanel(){
            //super
            thiscounting = false;
            this.setLayout(new AfAnyWhere());
            this.setBorder(TFrameTools.EmptyDateBorder);
            //this.add(new JLabel("倒计时"),AfMargin.CENTER);
            timeRemain = TFrameTools.createLabel("剩余时间");
            startbtn = TFrameTools.createTButton("开始计时");
            editbtn = TFrameTools.createTButton("修改时间");
            cancelbtn = TFrameTools.createTButton("取消");
            editTimeBar = new TEditTimeBar();
            editTimeBar.setVisible(false);

            //计时器
            timer = new Timer(10,e->paintTimer());
            timer.start();

            timerdisp = TFrameTools.createTButton("00:00:00",new Font("Consolas",1,50));
            //timer.setToolTipText("点击中央的时间来设置倒计时时间");
            timerdisp.setContentAreaFilled(false);
            timerdisp.setPreferredSize(new Dimension(400,200));
            timerdisp.setBorderPainted(false);
            //timer.set
            add(timerdisp,AfMargin.CENTER);

            startbtn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    startCountdown();
                }
            });

            cancelbtn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int result = JOptionPane.showConfirmDialog(null,
                            "确定要停止计时吗？当前连续番茄会清零。","停止计时",JOptionPane.YES_NO_OPTION);
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
            //btnpanel.add(editbtn);
            //btnpanel.add(editTimeBar);
            btnpanel.add(cancelbtn);
            cancelbtn.setVisible(false);
            add(btnpanel,AfMargin.BOTTOM_CENTER);


            //番茄钟部分！
            setBackground(new Color(239, 202, 199));
            //显示今日番茄和连续番茄
            JPanel temp1 = TFrameTools.createPanel(new FlowLayout());
            JLabel label = TFrameTools.createLabel("今日番茄：");
            tomatoCount=  TFrameTools.createLabel("0");
            temp1.add(label);
            temp1.add(tomatoCount);
            JPanel temp2 = TFrameTools.createPanel(new FlowLayout());
            JLabel label1 = TFrameTools.createLabel("连续番茄：");
            consecutivetomato = TFrameTools.createLabel("0");
            label1.setToolTipText("连续完成4个番茄后，将会休息更长时间。");
            temp2.add(label1);
            temp2.add(consecutivetomato);
            JPanel temp3 = TFrameTools.createPanel(new GridLayout(2,1,0,10));
            temp3.add(temp1);
            temp3.add(temp2);
            add(temp3,AfMargin.TOP_LEFT);
            hint = TFrameTools.createLabel("开始专注",new Font("微软雅黑",3,30));
            hint.setForeground(new Color(27, 106, 35));
            hint.setHorizontalAlignment(SwingConstants.CENTER);
            hint.setPreferredSize(new Dimension(300,300));
            add(hint,AfMargin.TOP_CENTER);
        }

        @Override
        public void loadNowTimerFromManager(){
            String remain = TManager.getInstance().getTomatoClock().getNowTimerHour()+":"
                    +TManager.getInstance().getTomatoClock().getNowTimerMinute()+":"
                    +TManager.getInstance().getTomatoClock().getNowTimerSecond();

            timerdisp.setText(remain);
            //System.out.println("load timer from manager");
            //manager的倒计时结束
            if(TManager.getInstance().getTomatoClock().isCountingDown()==false && isCounting && thiscounting){
                isCounting = false;
                thiscounting = false;
                isbreak= TManager.getInstance().getTomatoClock().isBreaktime();
                int result;
                if(isbreak) {
                    result = JOptionPane.showConfirmDialog(null, "休息结束，恭喜你完成了一个番茄！\n接下来是专注时间！（点击是直接开始下一次计时）",
                            "Tomato Time", JOptionPane.YES_NO_OPTION);
                    addTomato();

                }
                else{
                    result = JOptionPane.showConfirmDialog(null, "专注结束，接下来休息一下吧！（点击是直接开始下一次计时）",
                            "Tomato Time", JOptionPane.YES_NO_OPTION);
                }

                //点击确认，直接开始下一次计时
                if(result==0){
                    startCountdown();
                }
                else{
                    //显示缓冲界面
                    paintInterval();
                    endCountdownCheck();
                }
                isbreak = !isbreak;
                repaint();
            }
        }

        //开始计时
        @Override
        public void startCountdown(){
            boolean countsuccess = false;
            if(isCounting) {
                System.out.println("正在倒计时，无法开始新的倒计时！！");
            }
            if(TManager.getInstance().getCountdown().isCountingDown()==false &&
                    TManager.getInstance().getTomatoClock().isCountingDown()==false){
                TManager.getInstance().getTomatoClock().performTomato();
                startbtn.setVisible(false);
                editbtn.setVisible(false);
                editTimeBar.setVisible(false);
                cancelbtn.setVisible(true);
                isCounting = true;
                thiscounting = true;
                if(TManager.getInstance().getTomatoClock().isBreaktime()){
                    hint.setText("休息中...");
                }
                else{
                    hint.setText("专注中...");
                }
                repaint();
                countsuccess = true;
            }
            else{
                System.out.println("manager正在倒计时/番茄钟！无法开始新的倒计时！");

            }
            if(!countsuccess){
                JOptionPane.showMessageDialog(null,"当前有倒计时/番茄正在进行，无法开始倒计时！","Tomato Time",
                        JOptionPane.PLAIN_MESSAGE);
            }
        }

        @Override
        public void endCountdownCheck(){
            // if(TManager.getInstance().getCountdown().isCountingDown()==false){
            btnpanel.setVisible(false);
            startbtn.setVisible(true);
            cancelbtn.setVisible(false);
            btnpanel.setVisible(true);
            // }
        }

        @Override
        public void cancelCountdown(){
            TManager.getInstance().getTomatoClock().stopTomato();
            endCountdownCheck();
            isCounting = false;
            thiscounting = false;
            hint.setText("开始专注");
            consecutivetomato.setText("0");
        }

        @Override
        public void paintTimer(){
            loadNowTimerFromManager();
            repaint();
        }

        //上一次计时和下一次计时之间的缓冲（更新hint，更新显示倒计时，更新）
        public void paintInterval(){
            if(isbreak) hint.setText("开始专注");
            else hint.setText("休息一下吧");
            btnpanel.setVisible(false);
            startbtn.setVisible(true);
            cancelbtn.setVisible(true);
            btnpanel.setVisible(true);
            repaint();
        }

        //从日历里装载tomato
        public void loadTomatoFromCalender(){
            TTime now = TManager.getInstance().getNowTime();
            tomatoCount.setText(TManager.getInstance().getContainerofDate(now).getTomatoCount()+"");
            consecutivetomato.setText(TManager.getInstance().getTomatoClock().getConsecutivecount()+"");
            repaint();
        }

        //对日历的番茄计数+1
        public void addTomatoToCalender(){
            TTime now = TManager.getInstance().getNowTime();
            TDateContainer container = TManager.getInstance().getContainerofDate(now);
            if(container != null){
                container.addTomatoCount();
                System.out.println("当天番茄已加一");
            }
            else{
                System.out.println("未找到当日的容器。");
                //创建新容器
                container = TManager.getInstance().getCalender().createDateContainer(now);
                container.addTomatoCount();
            }
        }

        public void addTomato(){
            tomatoCount.setText((Integer.valueOf(tomatoCount.getText())+1) + "");
            consecutivetomato.setText((Integer.valueOf(consecutivetomato.getText())+1) + "");
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

