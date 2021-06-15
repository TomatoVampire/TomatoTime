package TTimepkg;

import java.io.Serializable;
import java.net.URL;
import java.net.URLConnection;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

//todo TTime包装类
public class TClock extends TTimeFlowSkeleton implements Serializable {

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

    //public void setTime(TTime t){time.}

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
        temp.append(time.getYear() + "年");
        temp.append(time.getMonth() + "月");
        temp.append(time.getDay() + "日 ");
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

    public int getHour(){
        return time.getHour();
    }

    public int getMinute(){
        return time.getMinute();
    }

    public int getSecond(){
        return time.getSecond();
    }

    public int getYear(){return time.getYear();}
    public int getMonth(){return time.getMonth();}
    public int getDay(){return time.getDay();}

    @Override
    public void setTask() {
        time.getCalender().add(GregorianCalendar.SECOND,1);
        System.out.println("当前时间："+getTime());
    }

    @Override
    public String toString() {
        return getClock();
    }

    public Calendar getCalenderObj(){
        return time.getCalenderObj();
    }

    public TTime getTTime(){return time;}
    public void setTTime(TTime t){time = t;}

    //同步网络时间
    public void syncronizeWebTime(String web){
        try{
            URL url = new URL(web);//获取资源对象
            URLConnection uc = url.openConnection();//生成连接对象
            uc.connect();//建立连接
            Long ld = uc.getDate();//读取网站时间
            System.out.println(ld);//毫秒数
            Date date = new Date(ld);
            int year = date.getYear()+1900;
            int month = date.getMonth()+1;
            int day = date.getDate();
            int hour = date.getHours();
            int minute = date.getMinutes();
            int second = date.getSeconds();

            //装载
            time.setDate(year,month,day);
            time.setClock(hour,minute,second);
            //test
            //time.setDate(date.getYear()+1900,date.getMonth()+1,date.getDate());
            //time.setClock(date.getHours(),date.getMinutes(),date.getSeconds());
            //System.out.println(time);
            //System.out.println(new Date(ld));
        }catch (Exception e){
            System.out.println("同步网络时间失败！");
            System.out.println(e.getMessage());
        }
    }
    /*@Override public int hashCode(){
        int result = time.get(GregorianCalendar.HOUR_OF_DAY);
        result = result*31+time.get(GregorianCalendar.MINUTE);
        result = result*31+time.get(GregorianCalendar.SECOND);
        return result;
    }*/
}
