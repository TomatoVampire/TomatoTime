package TCalenders;

import TFrames.TFrameAttributes;

import java.awt.*;

//自定义的纪念日，可以无限添加（？）
public class TDateModified implements DateMarker{
    protected String memo;
    protected Color color = TFrameAttributes.copyColor(TFrameAttributes.MODIFIEDDAYCOLOR);

    public TDateModified(){memo=null;}
    public TDateModified(String s){
        setMemo(s);
    }

    public void setMemo(String s){
        memo = new String(s);
    }

    //支持自定义颜色
    public void setColor(Color c){
        color = c;
    }

    @Override
    public String getMemo() {
        return new String(memo);
    }

    @Override
    public Color getColor() {
        return color;
    }
}
