package TFrames;

import TFrames.AFLayouts.AfAnyWhere;
import TFrames.AFLayouts.AfMargin;
import TManagers.TManager;

import javax.swing.*;

public class TAboutPanel extends TPanel{

    public TAboutPanel(){
        panel = new JPanel(new AfAnyWhere());
        JButton button = TFrameTools.createTButton("reset");
        button.addActionListener(e-> TManager.getInstance().reset());
        panel.add(button, AfMargin.CENTER);
    }

}
