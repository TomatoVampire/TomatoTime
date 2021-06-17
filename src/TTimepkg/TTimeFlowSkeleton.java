package TTimepkg;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;
import javax.swing.Timer;
//import java.util.Timer;
//import java.util.TimerTask;

//todo 抽象骨架类 时间流动类
public abstract class TTimeFlowSkeleton implements TTimeFlow,Runnable {

    protected Timer timer;
    protected ActionListener task;
    protected boolean isFlowing;
    protected Thread thread;
    protected boolean threadStarted;

    protected class MyTimeAction implements ActionListener,Serializable{
        @Override
        public void actionPerformed(ActionEvent e) {
            setTask();
        }
    }

    public TTimeFlowSkeleton(){
        isFlowing = false;
        task = new MyTimeAction();
        timer = new Timer(1000,task);
        thread = new Thread(this, "Time flow");
        threadStarted = false;
       // timer.s
    }

    //todo 新建个无限循环start()的线程，计时结束后kill该进程（或许在frame中会修好？）
    public void startMethod() {
        //从现在开始，进行this的操作,单位为秒
        if(thread == null) thread = new Thread (this, "Time flow");
        if(!threadStarted) {thread.start();threadStarted = true;}
        if(!isFlowing){
            isFlowing = true;
            timer.start();
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

    @Override
    public void run() {
        while(true) {
            if(!isFlowing) startMethod();
        }
    }

    public void start(){
        startMethod();
    }
}
