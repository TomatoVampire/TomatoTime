package TCalenders;

import TFrames.TFrameAttributes;

import java.awt.*;
import java.io.Serializable;

public class TTodoItem implements DateMarker,Comparable<TTodoItem>,Cloneable, Serializable {
    //描述
    private String description;
    //在todolist中的序号？
    private int orderInList;
    private Color color = TFrameAttributes.copyColor(TFrameAttributes.TODOITEMCOLOR);

    public TTodoItem(){description = null;}
    public TTodoItem(String i){setDescription(i);}

    public void setDescription(String description) {
        this.description = new String(description);
    }

    public String getDescription(){
        //todo 复制，保护内存
        return new String(description);
    }

    //todo 重写clone,equals,hashcode
    @Override
    public boolean equals(Object obj) {
        if(obj == this) return true;
        if(obj instanceof TTodoItem){
            TTodoItem o = (TTodoItem) obj;
            if(!o.description.equals(this.description)) return false;
            //if(o.orderInList != )
            return true;
        }
        else return false;
    }

    @Override
    protected TTodoItem clone(){
        try {
            return (TTodoItem)super.clone();
        }
        catch(CloneNotSupportedException e) {
            throw new AssertionError(); // Can't happen
        }

    }

    @Override
    public String toString() {
        return description;
    }

    @Override
    public int hashCode() {
        return description.hashCode();
    }

    @Override
    public int compareTo(TTodoItem o) {
        return description.compareTo(o.description);
    }

    @Override
    public String getMemo() {
        return description;
    }

    @Override
    public Color getColor() {
        //待办事项的颜色为黄色
        return TFrameAttributes.TODOITEMCOLOR;
    }
}
