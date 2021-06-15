package TTimepkg;

import java.io.Serializable;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;

//包装的时间类，包含年月日时分秒信息，比较时仅比较年月日
public class TTime implements Serializable {
    //全新的Date类？？
    protected GregorianCalendar time;

    public TTime(){
        time = new GregorianCalendar();
    }

    public TTime(GregorianCalendar calendar){
        time = calendar;
    }

    public TTime(int year,int month,int day){
        time = new GregorianCalendar();
        setDate(year,month,day);
    }

    //todo 联网获取时间，参数：时区 会更新时钟和日期
    public void synchronizeTime(TimeZone timezone){
        //todo 使用库进行同步
        time.setTimeZone(timezone);
    }

    //设置日期，参数：年月日
    public void setDate(int year,int month,int day){
        if(year<0 || month>12 || month<0 || day>31 || day<0) throw new IllegalArgumentException("出错！日期参数无效！");
        time.set(GregorianCalendar.YEAR,year);
        time.set(GregorianCalendar.MONTH,month-1);
        time.set(GregorianCalendar.DAY_OF_MONTH,day);
    }

    //设置时钟，参数为时分秒
    public void setClock(int hour,int minute,int second){
        if(hour<0 || hour>24||minute<0 || minute>60 || second<0 || second>60) throw new IllegalArgumentException("出错！时钟参数无效！");
        //???todo
        if(hour > 12) time.set(GregorianCalendar.HOUR_OF_DAY,hour);
        else time.set(GregorianCalendar.HOUR_OF_DAY,hour);
        time.set(GregorianCalendar.MINUTE,minute);
        time.set(GregorianCalendar.SECOND,second);
    }

    public int getYear(){return time.get(GregorianCalendar.YEAR);}
    public int getMonth(){return time.get(GregorianCalendar.MONTH)+1;}
    public int getDay(){return time.get(GregorianCalendar.DAY_OF_MONTH);}

    public int getHour(){return time.get(GregorianCalendar.HOUR_OF_DAY);}
    public int getMinute(){return time.get(GregorianCalendar.MINUTE);}
    public int getSecond(){return time.get(GregorianCalendar.SECOND);}

    public GregorianCalendar getCalender(){return time;}

    public int get(int field){
        return time.get(field);
    }

    public Calendar getCalenderObj(){
        return time;
    }

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
        code =  code*31+time.get(GregorianCalendar.MONTH);
        code =  code*31+time.get(GregorianCalendar.DAY_OF_MONTH);
        return code;
    }

    @Override
    public String toString(){
        StringBuffer temp = new StringBuffer();
        temp.append(getYear() + "年");
        temp.append(getMonth() + "月");
        temp.append(getDay() + "日 ");
        temp.append(getHour() + "时");
        temp.append(getMinute() + "分");
        temp.append(getSecond() + "秒");
        return temp.toString();
    }




}
