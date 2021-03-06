package TCalenders;

import TFrames.TFrameTools;

import java.awt.*;
import java.io.Serializable;

//工作日、假日、调休日。一天只能有一个该对象！
public class TDateMark implements DateMarker, Serializable {
    private String memo;    //日期的信息(可以自定义，不自定义的话默认是工作日、休息日这样)
    private DateType type;  //日期的种类
    private Color color;

    private TDateMark(DateType type,String m){
        this.type = type;
        memo = m;
        color = TFrameTools.copyColor(type.color);
    }

    private TDateMark(DateType type){
        this.type = type;
        memo = type.note;
        color = TFrameTools.copyColor(type.color);
    }

    //todo 枚举类
    public enum DateType{
        WORKDAY("工作日", TFrameTools.WORKDAYCOLOR),//工作日，无标记
        ULTRAWORK("调休日", TFrameTools.ULTRAWORKDAYCOLOR),//调休日，红色
        RESTDAY("休息日", TFrameTools.HOLIDAYCOLOR);//休息日（包含法定节假日），绿色//自定义日期，紫色
        public final String note;
        public final Color color;
        private DateType(String n, Color color){
            this.note = n;
            this.color= TFrameTools.copyColor(color);
        }
        @Override public String toString(){return note;}
    }

    //修改
    void changeDateMark(DateType t){this.type = t;this.memo = type.note;}
    void changeDateMark(DateType t, String memo){this.type = t;this.memo=memo;}

    //todo 静态工厂
    public static TDateMark InitDateMark(DateType type){
        return new TDateMark(type);
    }

    public static TDateMark InitDateMark(DateType type, String m){
        return new TDateMark(type,m);
    }

    public TDateMark.DateType getDateType(){return type;}

    @Override
    public Color getColor() {
        return color;
    }

    @Override
    public String getMemo() {
        return memo;
    }
}
