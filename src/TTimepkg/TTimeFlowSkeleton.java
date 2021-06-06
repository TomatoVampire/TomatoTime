package TTimepkg;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Date;
import javax.swing.Timer;
//import java.util.Timer;
//import java.util.TimerTask;

//todo 抽象骨架类 时间流动类
public abstract class TTimeFlowSkeleton implements TTimeFlow, Serializable {

    protected Timer timer;
    protected ActionListener task;
    protected boolean isFlowing;

    protected class MyTimeAction implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            setTask();
        }
    }

    public TTimeFlowSkeleton(){
        isFlowing = false;
        task = new MyTimeAction();
        timer = new Timer(1000,task);

       // timer.s
    }

    //todo 新建个无限循环start()的线程，计时结束后kill该进程（或许在frame中会修好？）
    @Override
    public void start() {
        //从现在开始，进行this的操作,单位为秒
        if(!isFlowing){
            timer.start();
            isFlowing = true;
        }
    }

    //暂停和停止会导致timer设置为Null!
    @Override
    public void pause() {
        if(timer == null || task == null) throw new NullPointerException();
        if(isFlowing){
            timer.stop();
            isFlowing = false;
        }
    }

    @Override
    public void stop() {
        pause();
    }

    //运行:需要子类编写！！！
    //todo @Override public void setTask() { }
    public abstract void setTask();

    //监听器？？？
    //放进gui的时候可能需要重写
}
