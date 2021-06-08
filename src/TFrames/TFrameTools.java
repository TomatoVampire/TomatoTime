package TFrames;

import javax.swing.*;
import java.awt.*;

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

    public static final Font CLOCKFONT = new Font("Consolas",1,20);
    public static final Font BUTTONFONT = new Font("Consolas",1,20);
    //public static final Color TODOITEMCOLOR = new Color(248, 202, 96);

    public static Color copyColor(Color color){
        return new Color(color.getRed(),color.getGreen(),color.getBlue(),color.getAlpha());
    }

    public static JButton createTButton(String name){
        JButton button = new JButton(name);
        button.setFont(TFrameTools.BUTTONFONT);
        button.setBackground(TFrameTools.copyColor(TFrameTools.BUTTONCOLOR));
        //button.setContentAreaFilled(false);
        button.setFocusPainted(false);
        return button;
    }
}
