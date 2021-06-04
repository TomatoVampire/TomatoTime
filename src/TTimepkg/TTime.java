package TTimepkg;

import java.util.GregorianCalendar;
import java.util.TimeZone;

//包装的时间类，包含年月日时分秒信息，比较时仅比较年月日
public class TTime{
    //全新的Date类？？
    protected GregorianCalendar time;

    public TTime(){
        time = new GregorianCalendar();
    }

    public TTime(GregorianCalendar calendar){
        time = calendar;
    }

    public TTime(int year,int month,int day){
        time = new GregorianCalendar(year, month, day);
    }

    //todo 联网获取时间，参数：时区 会更新时钟和日期
    public void synchronizeTime(TimeZone timezone){
        time.setTimeZone(timezone);
    }

    //设置日期，参数：年月日
    public void setDate(int year,int month,int day){
        time.set(GregorianCalendar.YEAR,year);
        time.set(GregorianCalendar.MONTH,month);
        time.set(GregorianCalendar.DAY_OF_MONTH,day);
    }

    //设置时钟，参数为时分秒
    public void setClock(int hour,int minute,int second){
        time.set(GregorianCalendar.HOUR,hour);
        time.set(GregorianCalendar.MINUTE,hour);
        time.set(GregorianCalendar.SECOND,hour);
    }

    public GregorianCalendar getCalender(){return time;}

    //todo equals方法仅比较年月日！！！！
    @Override
    public boolean equals(Object obj){
        if(obj == this) return true;
        if(obj instanceof TTime){
            TTime o = (TTime) obj;
            if(this.time.get(GregorianCalendar.YEAR) != o.time.get(GregorianCalendar.YEAR)) return false;
            if(this.time.get(GregorianCalendar.MONTH) != o.time.get(GregorianCalendar.MONTH)) return false;
            if(this.time.get(GregorianCalendar.DAY_OF_MONTH) != o.time.get(GregorianCalendar.DAY_OF_MONTH)) return false;
            return true;
        }
        else return false;
    }

    //todo hashcode方法用于map!用于寻找key！！
    @Override
    public int hashCode(){
        int code;
        code = time.get(GregorianCalendar.YEAR);
        code +=  time.get(GregorianCalendar.MONTH);
        code +=  time.get(GregorianCalendar.DAY_OF_MONTH);
        return code;
    }

    //todo 多线程：时间流逝，以秒算。不需要和manager的时间同步

}
