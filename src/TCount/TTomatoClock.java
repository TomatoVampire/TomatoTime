package TCount;

import java.io.Serializable;

public class TTomatoClock extends TCountdown implements Serializable {
    //连续番茄计数:中途中断番茄，关闭应用重开都为0！
    private int consecutivecount;
    //当前倒计时
    //private TCountdown currentCountdown;
    private boolean isBreakTime;
    private final long worktime = 5;//1500;//25min
    private final long breaktime = 3;//300;//5min
    private final long longbreaktime = 5;//长休息25min

    public TTomatoClock(){
        consecutivecount = 0;
        isBreakTime = true;
        initialTimer = worktime;
        nowtimer = initialTimer;
    }

    public void performTomato(){
        if(isBreakTime){
            System.out.println("now let's start work!");
            //setTimer(worktime);
        }
        else{
            System.out.println("now let's take a break!");
            //if(consecutivecount==4) setTimer(longbreaktime);
            //else setTimer(breaktime);
            consecutivecount++;
        }
        isBreakTime = !isBreakTime;
        startCount();
        System.out.println("连续番茄数：" + consecutivecount);
    }

    //当前是否为休息时间
    public boolean isBreaktime(){return isBreakTime;}

    //中止番茄
    public void stopTomato(){
        stopCount();
        isBreakTime = true;
        consecutivecount = 0;
        setTimer(worktime);
    }

    //正常计时结束而终止，此时设置timer，但不修改isbreaktime
    @Override
    public void endCount(){
        stop();
        isCounting = false;
        System.out.println("番茄计时完成。");
        if(isBreakTime){
            setTimer(worktime);
        }
        else{
            if(consecutivecount==4) setTimer(longbreaktime);
            else setTimer(breaktime);
        }
    }

    public int getConsecutivecount(){return consecutivecount;}

    //todo 完成番茄后加入当日番茄数（应从manager处调用？）
}
