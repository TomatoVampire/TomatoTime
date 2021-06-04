import TCalenders.TCalender;
import TCount.TCountdown;
import TCount.TTomatoClock;
import TTimepkg.TTime;

public class TManager {

    //当前时间:包含年月日时分秒
    private TTime nowtime;

    //当前时刻是否在倒计时：倒计时和番茄钟不可以同时进行！
    //并发编程？
    private boolean isCountingDown;

    //todo 获取当前日期（年：月：日）
    public String getDate(){
        return null;
    }

    //todo 获取当前时间（时：分：秒）
    public String getClock(){
        return null;
    }

    //倒计时，番茄
    private TCountdown countdown;
    private TTomatoClock tomatoClock;
    //日历
    private TCalender calender;



    //todo TManager类的单例
    private TManager instance = new TManager();
    public TManager getInstance(){return instance;}
    private TManager(){
        //初始化
        nowtime = new TTime();
        countdown = new TCountdown();
        tomatoClock = new TTomatoClock();
        calender = new TCalender();
        isCountingDown = false;
    }

    //重置所有的内容，删除所有记录的待办事项等
    public void reset(){

    }
}
