package TFrames;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.net.URL;


//todo 工具类
//图形界面的通用属性
public class TFrameTools {
    public static final Color HOLIDAYCOLOR = new Color(60, 172, 30);
    public static final Color WORKDAYCOLOR = new Color(255, 255, 255,0);
    public static final Color ULTRAWORKDAYCOLOR = new Color(241, 98, 98);
    public static final Color MODIFIEDDAYCOLOR = new Color(131, 61, 212);
    public static final Color TODOITEMCOLOR = new Color(248, 202, 96);
    public static final Color SIDEPANELCOLOR = new Color(63, 182, 48);
    public static final Color BUTTONCOLOR = new Color(255, 255, 255);
    public static final Color TRANSPARANT = new Color(0,0,0,0);
    public static final Color SELECTEDDATE = new Color(36, 154, 227);


    public static final Font CLOCKFONT = new Font("Consolas",1,50);
    public static final Font BUTTONFONT = new Font("Consolas",1,20);
    public static final Font TEXTFONT = new Font("微软雅黑",1,20);
    public static final Font SMALLTEXTFONT = new Font("微软雅黑",0,15);
    public static final Border SelectedDateBorder = BorderFactory.createLineBorder(SELECTEDDATE,5);
    public static final Border EmptyDateBorder = BorderFactory.createLineBorder(new Color(0,0,0,0),5);


    //public static final Color TODOITEMCOLOR = new Color(248, 202, 96);

    public static Color copyColor(Color color){
        return new Color(color.getRed(),color.getGreen(),color.getBlue(),color.getAlpha());
    }

    public static JButton createTButton(String name){
        JButton button = new JButton(name);
        button.setFont(TFrameTools.TEXTFONT);
        button.setBackground(TFrameTools.copyColor(TFrameTools.BUTTONCOLOR));
        //button.setContentAreaFilled(false);
        button.setFocusPainted(false);
        return button;
    }

    public static JButton createTButton(String name,Font font){
        JButton button = createTButton(name);
        button.setFont(font);
        return button;
    }

    public static JButton createTButton(String url,String name){
        //注意需要使用相对路径！！
        try {
            Icon icon = new ImageIcon(url);
            JButton button = createTButton(name);
            button.setIcon(icon);
            return button;
        }catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    public static JLabel createLabel(String name){
        JLabel label = new JLabel(name);
        label.setFont(TFrameTools.TEXTFONT);
        label.setBackground(TFrameTools.TRANSPARANT);
        return label;
    }
    public static JLabel createLabel(String name,Font font){
        JLabel label = createLabel(name);
        label.setFont(font);
        return label;
    }

    public static JPanel createPanel(LayoutManager manager){
        JPanel panel = new JPanel(manager);
        panel.setBackground(TFrameTools.TRANSPARANT);
        return panel;
    }

    public static JTextField createTextField(String text){
        JTextField textField = new JTextField(text);
        textField.setFont(TFrameTools.SMALLTEXTFONT);
        //textField.setBackground(TFrameTools.TRANSPARANT);
        return textField;
    }

    public static JTextArea createTextArea(String text){
        JTextArea textField = new JTextArea(text);
        textField.setFont(TFrameTools.TEXTFONT);
        //textField.setBackground(TFrameTools.TRANSPARANT);
        return textField;
    }

    /*
    public static JLabel createIconLabel(String path){
        JLabel label = TFrameTools.createLabel("");
        //label.setIcon();

        return label;
    }*/
}
