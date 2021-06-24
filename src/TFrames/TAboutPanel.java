package TFrames;

import TFrames.AFLayouts.AfAnyWhere;
import TFrames.AFLayouts.AfMargin;
import TManagers.TManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;

public class TAboutPanel extends TPanel{
    JLabel tomatoicon;
    JTextArea info;
    JLabel linklabel;
    JButton savebtn;

    public TAboutPanel(){
        panel = new JPanel(new AfAnyWhere());
        panel.setBackground(new Color(168, 226, 193));
        savebtn = TFrameTools.createTButton("保存");
        savebtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    TManager.getInstance().saveFile();
                    JOptionPane.showMessageDialog(null, "保存成功！",
                            "Tomato Time",JOptionPane.PLAIN_MESSAGE);
                }
                catch (Exception ex){
                    JOptionPane.showMessageDialog(null,"出错！保存失败\n"+ex.getMessage(),"Tomato Time",JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        JButton button = TFrameTools.createTButton("重置");
        button.setToolTipText("将日历的内容清空");
        //清空日历
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int r = JOptionPane.showConfirmDialog(null,
                        "该操作会将应用所有的内容清空，确定要这么做吗？","Tomato Time",JOptionPane.YES_NO_OPTION);
                if(r==0){
                    TMainFrame.getInstance().reset();
                    TManager.getInstance().reset();
                    JOptionPane.showMessageDialog(null,"成功重置应用内容。","Tomato Time",
                            JOptionPane.PLAIN_MESSAGE);
                }
            }
        });
        panel.add(button, AfMargin.CENTER);

        //番茄图片
        tomatoicon = TFrameTools.createLabel("");
        try {
            URL url = getClass().getResource("/Resources/tomatoresize.png");
            tomatoicon.setIcon(new ImageIcon(url));
            tomatoicon.setToolTipText("点我试试~");
            tomatoicon.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    try {
                        Runtime.getRuntime().exec("cmd.exe /c start " + "https://github.com/TomatoVampire");
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            });
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
        info = TFrameTools.createTextArea("");
        info.setText("Tomato Time \n" +
                "创作者：TomatoVampire @2021\n" +
                "集成了时钟、日历、倒计时、番茄钟的应用。\n" +
                "使用Java Swing实现。\n" +
                "希望能给你愉快的体验~");
        //info.setAlignmentY();
        info.setBackground(new Color(168, 226, 193));
        info.setEditable(false);
        info.setFocusable(false);

        linklabel = TFrameTools.createLabel("<html><a href='https://github.com/TomatoVampire'>Github</a></html>");
        linklabel.setForeground(new Color(20, 70, 83));
        linklabel.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                try {
                    Runtime.getRuntime().exec("cmd.exe /c start " + "https://github.com/TomatoVampire");
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                panel.repaint();
            }
        });

        JPanel temppanel = TFrameTools.createPanel(new BorderLayout());
        JPanel tpanel = TFrameTools.createPanel(new GridLayout(1,2,30,0));
        JPanel ttpanel = TFrameTools.createPanel(new FlowLayout());
        //JPanel ttepanel = TFrameTools.createPanel(new FlowLayout(FlowLayout.LEFT))
        tpanel.add(info);
        tpanel.add(tomatoicon);
        ttpanel.add(savebtn);
        ttpanel.add(button);
        temppanel.add(tpanel,BorderLayout.CENTER);
        temppanel.add(ttpanel,BorderLayout.SOUTH);

        panel.add(temppanel,AfMargin.CENTER);
        //panel.add(tomatoicon,AfMargin.TOP_RIGHT);
        panel.repaint();
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("关于");
        TAboutPanel panel = new TAboutPanel();
        frame.setLayout(new BorderLayout());
        frame.setBounds(500,0,960,960);
        frame.add(panel.getPanel(),BorderLayout.CENTER);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

}
