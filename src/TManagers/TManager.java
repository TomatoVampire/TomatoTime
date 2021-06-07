package TManagers;

import TCalenders.TCalender;
import TCount.TCountdown;
import TCount.TTomatoClock;
import TTimepkg.TClock;
import TTimepkg.TTime;

import java.io.Serializable;

public class TManager implements Serializable {

    //当前时间:包含年月日时分秒
    private TClock nowtime;

    //当前时刻是否在倒计时：倒计时和番茄钟不可以同时进行！
    //并发编程？
    private boolean isCountingDown;

    //todo 获取当前日期（年：月：日）
    public String getDate(){
        return null;
    }

    //todo 获取当前时间（时：分：秒）
    public String getClock(){
        return nowtime.getClock();
    }

    //倒计时，番茄
    private TCountdown countdown;
    private TTomatoClock tomatoClock;
    //日历
    private TCalender calender;

    //todo TManager类的单例
    private static final String saveFilePath = "savefile.txt";
    private static TManager instance;// = new TManager();
    public static TManager getInstance(){
        //todo 饿汉式
        if(instance==null) {
            //instance = new TManager();
            if(loadManager() == 0) instance = new TManager();
        }
        //else; //读取数据？
        return instance;
    }

    private static int loadManager(){
        if(TSaveFile.hasFile(saveFilePath)) {
            instance = TSaveFile.loadFile(saveFilePath);
            return 1;
        }
        else
        {
            return 0;
        }
    }
    private TManager(){
        nowtime.start();
        reset();
    }

    //重置所有的内容，删除所有记录的待办事项等
    public void reset(){
        nowtime = new TClock();
        countdown = new TCountdown();
        tomatoClock = new TTomatoClock();
        calender = new TCalender();
        isCountingDown = false;
    }

    public TClock getNowTime(){return nowtime;}
    public TCalender getCalender(){return calender;}
    public TCountdown getCountdown(){return countdown;}
    public TTomatoClock getTomatoClock(){return tomatoClock;}

    //保存数据
    public void saveFile(){
        TSaveFile.saveFile(instance, saveFilePath);
    }

}
