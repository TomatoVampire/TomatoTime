package TCount;

public class TTomatoClock extends TCountdown{
    //连续番茄计数:中途中断番茄，关闭应用重开都为0！
    private int continuecount;
    //当前倒计时
    //private TCountdown currentCountdown;
    private boolean isBreakTime;

    public TTomatoClock(){
        continuecount = 0;
        isBreakTime = true;
    }

    public void performTomato(){
        if(isBreakTime){
            System.out.println("now let's start work!");
            setTimer(3);
        }
        else{
            System.out.println("now let's take a break!");
            setTimer(2);
            continuecount++;
        }
        startCount();
        isBreakTime = !isBreakTime;
        System.out.println("连续番茄数：" + continuecount);
    }

    //中止番茄
    public void stopTomato(){
        stopCount();
        isBreakTime = true;
        continuecount = 0;
    }

    //todo 完成番茄后加入当日番茄数（应从manager处调用？）
}
