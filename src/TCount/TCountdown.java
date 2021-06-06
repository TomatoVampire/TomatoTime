package TCount;
import TTimepkg.TTimeFlow;
import TTimepkg.TTimeFlowSkeleton;

import java.awt.event.ActionEvent;
import java.io.Serializable;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Timer;

public class TCountdown extends TTimeFlowSkeleton implements Serializable {

    protected long initialTimer;
    protected long nowtimer;

    public TCountdown() {
        super();
        initialTimer = 0;
        nowtimer = 0;
    }

    //使用小时-分钟-秒创建倒计时
    public TCountdown(int hour,int minute,int second){
        setTimer(hour,minute,second);
    }

    public TCountdown(long initial){
        setTimer(initial);
    }

    //可对timer重新设置以重新开始倒计时
    public void setTimer(int hour,int minute,int second){
        setTimer(convertTime2Countdown(hour,minute,second));
    }

    public void setTimer(long initial){
        if(initial < 0) throw new IllegalArgumentException("出错！倒计时的数字小于0！");
        initialTimer = initial;
        nowtimer=initial;
    }

    public static long convertTime2Countdown(int hour,int minute,int second){
        if(hour < 0 || minute > 60 || minute < 0 || second > 60 || second < 0)
            throw new IllegalArgumentException("出错！倒计时的数字小于0！");
        long timer = 0L;
        timer += second;
        timer += minute * 60L;
        timer += hour * 3600L;
        return timer;
    }

    //将倒计时数字以Calender格式显示?
    public static GregorianCalendar convertCountdown2Time(long time){
        long h,m,s;
        long remain = time;
        h = remain / 3600L;
        remain -= h * 3600L;
        m = remain / 60L;
        remain -= m * 60L;
        s = remain;
        GregorianCalendar c = new GregorianCalendar();
        c.set(GregorianCalendar.HOUR_OF_DAY, (int)h);
        c.set(GregorianCalendar.MINUTE, (int)m);
        c.set(GregorianCalendar.SECOND, (int)s);
        return  c;
    }

    //todo 开始计时
    public void startCount(){
        start();
        System.out.println("开始计时");
    }

    //todo 终止计时
    public void stopCount(){
        stop();
        nowtimer = initialTimer;
        System.out.println("终止计时");
    }

    //todo 暂停计时？
    public void pauseCount(){
        pause();
        //nowtimer不改变
        System.out.println("暂停计时");
    }

    public void endCount(){
        stop();
        nowtimer = initialTimer;
        System.out.println("计时完成。");
    }

    //todo 运行函数
    @Override
    public void setTask() {
        //System.out.println("countdown: " + initialTimer + " second(s)");
        System.out.println("countdown: " + Calendar2Str(convertCountdown2Time(nowtimer)));
        if(nowtimer == 0){
            endCount();
        }
        nowtimer--;
    }

    private String Calendar2Str(GregorianCalendar g){
        String t = "";
        t += g.get(GregorianCalendar.HOUR_OF_DAY) + " : ";
        t += g.get(GregorianCalendar.MINUTE) + " : ";
        t += g.get(GregorianCalendar.SECOND);
        return  t;
    }
}
