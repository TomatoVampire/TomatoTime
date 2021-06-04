package TTimepkg;

import java.awt.event.ActionListener;

//接口，让相关的时间类实现时间流动的效果？刷新频率为1s
public interface TTimeFlow {
    void start();//开始时间流动
    void pause();//暂停
    void stop();//停止时间流动
}
