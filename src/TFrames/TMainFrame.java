package TFrames;

import TManagers.TManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TMainFrame {
    JFrame mainFrame;
    Container root;
    JPanel mainPanel;
    TManager manager;
    JPanel sidePanel;
    JPanel cardPanel;
    JPanel sideMemoPanel;
    JButton clockbtn;
    JButton calenderbtn;
    JButton countdownbtn;
    JButton aboutbtn;

    //四个页面
    JPanel clockPanel;
    JPanel calenderPanel;
    JPanel countdownPanel;
    JPanel aboutPanel;

    //todo 单例
    private static TMainFrame instance;
    public static TMainFrame getInstance(){
        if(instance == null) instance = new TMainFrame();
        return instance;
    }
    private TMainFrame(){
        manager = TManager.getInstance();
        initMainframe();
    }

    private void initCardPanel(){
        cardPanel = new JPanel();
        cardPanel.setLayout(new CardLayout());
        //if(clockPanel==null || calenderPanel==null || countdownPanel==null || aboutPanel==null){ throw new NullPointerException("出错！时钟/日历/倒计时面板为null!"); }
        //加面板进卡片
        cardPanel.add(clockPanel, "clockPanel");
        //cardPanel.add(calenderPanel, "calenderPanel");
        //cardPanel.add(countdownPanel, "countdownPanel");
        //cardPanel.add(aboutPanel, "aboutPanel");
    }

    private void setSideMemoPanel(){
        sideMemoPanel = new JPanel();
        sideMemoPanel.setLayout(new BorderLayout());
        sideMemoPanel.setBackground(TFrameTools.copyColor(TFrameTools.SIDEPANELCOLOR));
        JLabel label = new JLabel();
        //label.setFont();
        label.setText(TManager.getInstance().getNowTime().getClock());
        //label.setHorizontalAlignment(SwingConstants.CENTER);
        Timer timer = new Timer(1000, e->label.setText(TManager.getInstance().getNowTime().getClock()) );
        timer.start();
        sideMemoPanel.add(label,BorderLayout.SOUTH);
    }

    private void initPanels(){
        //侧边面板
        sidePanel = new JPanel();
        sidePanel.setBackground(TFrameTools.copyColor(TFrameTools.SIDEPANELCOLOR));
        sidePanel.setLayout(new GridLayout(5,1));
        sidePanel.setPreferredSize(new Dimension(320,960));
        //侧边按钮们
        clockbtn = TFrameTools.createTButton("CLOCK");
        calenderbtn = TFrameTools.createTButton("CALENDER");
        countdownbtn = TFrameTools.createTButton("COUNTDOWN");
        aboutbtn = TFrameTools.createTButton("ABOUT");
        //侧边memo显示
        setSideMemoPanel();


        clockbtn.addActionListener(new SwitchCardAction("cardLayout"));
        //calenderbtn =;
        //countdownbtn =;
        //aboutbtn = TFr;


        sidePanel.add(clockbtn);
        sidePanel.add(calenderbtn);
        sidePanel.add(countdownbtn);
        sidePanel.add(aboutbtn);
        sidePanel.add(sideMemoPanel);
    }

    private class SwitchCardAction implements ActionListener{
        String whereto;
        public SwitchCardAction(String whereTo){whereto = whereTo;}
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                CardLayout layout = (CardLayout) cardPanel.getLayout();
                layout.show(cardPanel, whereto);
            }catch (Exception ex){
                System.out.println("无法切换到" + whereto + "场景！");
            }
        }
    }


    private void initMainframe(){
        mainFrame = new JFrame("Tomato Time");
        mainFrame.setBounds(100,0,1280,960);
        mainFrame.setResizable(false);
        mainFrame.setBackground(new Color(255, 255, 255, 255));
        mainFrame.setLayout(new BorderLayout());
        //todo 关闭行为：保存instance，关闭程序
        mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        root = mainFrame.getContentPane();
        //各个子页面
        clockPanel = new TClockPanel().getPanel();

        //卡片布局

        initCardPanel();
        root.add(cardPanel,BorderLayout.CENTER);
        //侧边栏
        initPanels();
        root.add(sidePanel,BorderLayout.WEST);


        //root.add(cardPanel,BorderLayout.CENTER);

        mainFrame.setVisible(true);
    }

    public static void main(String[] args) {
        TMainFrame mainFrame = TMainFrame.getInstance();
    }
}
