package TFrames;

import TManagers.TManager;

import javax.swing.*;
import java.awt.*;

public class TMainFrame {
    JFrame mainFrame;
    Container root;
    JPanel mainPanel;
    TManager manager;

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

    private void initMainframe(){
        mainFrame = new JFrame("Tomato Time");
        mainFrame.setVisible(true);
        mainFrame.setBounds(100,0,1280,960);
        mainFrame.setResizable(false);
        root = mainFrame.getContentPane();

    }

    public static void main(String[] args) {
        TMainFrame mainFrame = TMainFrame.getInstance();
    }
}
