package TCalenders;

import java.awt.*;
import java.io.Serializable;

//todo 日期的自定义颜色，选项等，待办事项的颜色选项也包含在内
//日期的
public interface DateMarker extends Serializable {
    //void setMemo(String);
    String getMemo();   //获取描述
    //void setColor();
    Color getColor();   //获取颜色
}
