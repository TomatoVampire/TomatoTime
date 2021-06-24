package TFrames;

import TManagers.TManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

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
    TCalenderPanel tCalenderPanel;
    TClockPanel tClockPanel;
    TCountdownPanel tCountdownPanel;
    TAboutPanel tAboutPanel;

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
        cardPanel.add(calenderPanel, "calenderPanel");
        cardPanel.add(countdownPanel, "countdownPanel");
        cardPanel.add(aboutPanel, "aboutPanel");
    }

    private void setSideMemoPanel(){
        sideMemoPanel = new JPanel();
        sideMemoPanel.setLayout(new BorderLayout());
        sideMemoPanel.setBackground(TFrameTools.copyColor(TFrameTools.SIDEPANELCOLOR));
        JLabel label = new JLabel();
        //label.setFont();
        label.setText(TManager.getInstance().getClock().getClock());
        //label.setHorizontalAlignment(SwingConstants.CENTER);
        Timer timer = new Timer(1000, e->label.setText(TManager.getInstance().getClock().getClock()) );
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
        clockbtn = TFrameTools.createTButton("时钟");
        calenderbtn = TFrameTools.createTButton("日历");
        countdownbtn = TFrameTools.createTButton("倒计时");
        aboutbtn = TFrameTools.createTButton("关于");
        //侧边memo显示
        setSideMemoPanel();


        clockbtn.addActionListener(new SwitchCardAction("clockPanel"));
        calenderbtn.addActionListener(new SwitchCardAction("calenderPanel"));
        countdownbtn.addActionListener(new SwitchCardAction("countdownPanel"));
        aboutbtn.addActionListener(new SwitchCardAction("aboutPanel"));


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
                System.out.println("点击了切换"+whereto+"界面按钮");
                CardLayout layout = (CardLayout) cardPanel.getLayout();
                layout.show(cardPanel, whereto);
                tCalenderPanel.reloadFromManager();

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
        //todo 应用程序图标
        mainFrame.setIconImage(Toolkit.getDefaultToolkit().getImage(TMainFrame.class.getResource("/Resources/TomatoIcon.png")));


        //todo 关闭行为：保存instance，关闭程序
        mainFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                //暂时不保存
                manager.saveFile();
                System.exit(0);
            }
        });
        root = mainFrame.getContentPane();
        //各个子页面
        tClockPanel = new TClockPanel();
        tCalenderPanel = new TCalenderPanel();
        tCountdownPanel = new TCountdownPanel();
        tAboutPanel = new TAboutPanel();

        clockPanel = tClockPanel.getPanel();
        calenderPanel = tCalenderPanel.getPanel();
        aboutPanel = tAboutPanel.getPanel();
        countdownPanel = tCountdownPanel.getPanel();

        //卡片布局
        initCardPanel();
        root.add(cardPanel,BorderLayout.CENTER);
        //侧边栏
        initPanels();
        root.add(sidePanel,BorderLayout.WEST);


        //root.add(cardPanel,BorderLayout.CENTER);

        mainFrame.setVisible(true);
    }

    public void reset(){
        tCountdownPanel.reset();
    }

    public static void main(String[] args) {
        TMainFrame mainFrame = TMainFrame.getInstance();
    }
}
