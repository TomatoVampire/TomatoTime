package TTimepkg;

import java.util.GregorianCalendar;
import java.util.TimeZone;

//todo TTime包装类
public class TClock extends TTimeFlowSkeleton{

    private TTime time;
    //setclock

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
        //temp.append()
        return temp.toString();
    }

    //todo
    public String getClock(){
        StringBuffer temp = new StringBuffer();
        //temp.append()
        return temp.toString();
    }

    @Override
    public void setTask() {
        time.getCalender().add(GregorianCalendar.SECOND,1);
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
