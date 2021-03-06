package TManagers;

import TCalenders.TCalender;
import TCalenders.TDateContainer;
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
    public String getClockString(){
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
            if(loadManager() == 0) {
                instance = new TManager();
                System.out.println("读取文件失败，Manager已重置。");
            }
        }
        //else; //读取数据？
        return instance;
    }

    //读取数据（仅需读取calender）
    private static int loadManager(){
        if(TSaveFile.hasFile(saveFilePath)) {
            instance = new TManager();
            //仅读取calender
            instance.calender = TSaveFile.loadFile(saveFilePath);
            //instance.isCountingDown = false;
            //instance.nowtime = new TClock();
            //instance.countdown = new TCountdown();
            //instance.tomatoClock = new TTomatoClock();
            return 1;
        }
        else
        {
            return 0;
        }
    }
    //保存数据（仅需保存calender）
    public void saveFile(){
        TSaveFile.saveFile(instance.calender, saveFilePath);
    }

    private TManager(){
        reset();
    }

    //重置所有的内容，删除所有记录的待办事项等
    public void reset(){
        nowtime = new TClock();
        countdown = new TCountdown(30);
        tomatoClock = new TTomatoClock();
        calender = new TCalender();
        isCountingDown = false;
        nowtime.start();
        //System.out.println("Manager已重置。");
    }

    public TClock getClock(){return nowtime;}
    public TTime getNowTime(){return nowtime.getTTime();}
    public void setNowtime(TTime t){nowtime.setTTime(t);}
    public TCalender getCalender(){return calender;}
    public TCountdown getCountdown(){return countdown;}
    public TTomatoClock getTomatoClock(){return tomatoClock;}

    //获取当日的日历
    public TDateContainer getContainerofDate(TTime time){
        try{
            return calender.getDateEvents(time);
        }catch (Exception e){
            System.out.println("Manager：找不到当天的container!");
            return null;
        }
    }

    //当日是否有日历容器
    public boolean hasContainerofDate(TTime time){
        return calender.getDateEvents(time)!=null;
    }


}
