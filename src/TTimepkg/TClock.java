package TTimepkg;

import java.util.GregorianCalendar;
import java.util.TimeZone;

//todo TTime包装类
public class TClock extends TTimeFlowSkeleton{

    private TTime time;
    //setclock

    public TClock(){
        super();
        time = new TTime();
    }

    public TClock(int y,int m,int d){
        this();
        setDate(y,m,d);
    }

    public TClock(int y,int month,int d,int h,int minute,int s){
        this(y,month,d);
        setClock(h,minute,s);
    }

    public void setClock(int h,int m,int s){
        time.setClock(h,m,s);
    }

    public void setDate(int y,int m,int d){
        time.setDate(y,m,d);
    }

    public void synchronizeTime(TimeZone timezone){
        time.synchronizeTime(timezone);
    }

    //todo
    public String getDate(){
        StringBuffer temp = new StringBuffer();
        temp.append(time.getYear() + "年");
        temp.append(time.getMonth() + "月");
        temp.append(time.getDay() + "日");
        return temp.toString();
    }

    //todo
    public String getClock(){
        StringBuffer temp = new StringBuffer();
        temp.append(time.getHour() + "时");
        temp.append(time.getMinute() + "分");
        temp.append(time.getSecond() + "秒");
        return temp.toString();
    }

    public String getTime(){
        StringBuffer temp = new StringBuffer();
        temp.append(getDate()+" ");
        temp.append(getClock());
        return temp.toString();
    }

    @Override
    public void setTask() {
        time.getCalender().add(GregorianCalendar.SECOND,1);
        System.out.println("当前时间："+getTime());
    }

    @Override
    public String toString() {
        StringBuffer temp = new StringBuffer();
        temp.append(Integer.toString(1));
        return temp.toString();
    }

    /*@Override public int hashCode(){
        int result = time.get(GregorianCalendar.HOUR_OF_DAY);
        result = result*31+time.get(GregorianCalendar.MINUTE);
        result = result*31+time.get(GregorianCalendar.SECOND);
        return result;
    }*/
}
