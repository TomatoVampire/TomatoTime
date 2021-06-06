package TCalenders;

import TTimepkg.TTime;

import java.io.Serializable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

//日期集合？
public class TCalender implements Serializable {
    //记录的那些日期的所有事件，key为TTime!
    private Map<TTime, TDateContainer> dates;

    public TCalender(){
        //并发hashmap?
        dates = new ConcurrentHashMap<>();
    }

    //todo 生产者消费者模型
    //注册容器
    public int registerDateEvents(TTime t, TDateContainer container){
        //已被注册过，不操作
        if(dates.containsKey(t)) return 0;
        dates.put(t,container);
        return 1;
    }

    //获得容器
    public TDateContainer getDateEvents(TTime t){
        if(!dates.containsKey(t)) {return null;}
        return dates.get(t);
    }

    //todo 删除本日的container
    public int deleteDateEvents(TTime t){
        if(!dates.containsKey(t)) {return 0;}
        dates.remove(t);
        return 1;
    }
}
