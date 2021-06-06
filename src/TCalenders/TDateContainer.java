package TCalenders;

import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

//某一日的日期，包含日期形式、番茄数、
public class TDateContainer implements Serializable {
    //当天的番茄计数
    private long tomatoCount;
    //当天的时间标记(必填！)
    private TDateMark datemark;
    //当天的自定义日期
    private List<TDateModified> modifiedDayMarks;
    //当天的待办事项
    private TTodoList todoList;

    public TDateContainer(){
        tomatoCount = 0;
        datemark = TDateMark.InitDateMark(TDateMark.DateType.WORKDAY);
        modifiedDayMarks = new ArrayList<>();
        todoList = new TTodoList();
    }

    public TDateContainer(TDateMark mark){
        this();
        datemark = mark;
    }
    public TDateContainer(TDateMark.DateType t){
        this();
        datemark = TDateMark.InitDateMark(t);
    }

    //todo 自定义日期的添加、修改、删除、获取
    public void addModifiedDate(TDateModified t){
        modifiedDayMarks.add(t);
    }

    public int changeModifiedDate(int index, String t, Color c){
        if(index >= modifiedDayMarks.size() || index < 0) throw new IllegalArgumentException("访问自定义日期容器的下标越界！");
        modifiedDayMarks.get(index).setMemo(t);
        modifiedDayMarks.get(index).setColor(c);
        return 1;
    }

    public int changeModifiedDate(int index, String t){
        if(index >= modifiedDayMarks.size() || index < 0) throw new IllegalArgumentException("访问自定义日期容器的下标越界！");
        modifiedDayMarks.get(index).setMemo(t);
        return 1;
    }

    public int changeModifiedDate(int index, Color c){
        if(index >= modifiedDayMarks.size() || index < 0) throw new IllegalArgumentException("访问自定义日期容器的下标越界！");
        modifiedDayMarks.get(index).setColor(c);
        return 1;
    }

    public int deleteModifiedDate(int index){
        if(index >= modifiedDayMarks.size() || index < 0) throw new IllegalArgumentException("访问自定义日期容器的下标越界！");
        modifiedDayMarks.remove(index);
        return 1;
    }

    //访问
    public TDateModified getModifiedDate(int index){
        if(index >= modifiedDayMarks.size() || index < 0) throw new IllegalArgumentException("访问自定义日期容器的下标越界！");
        return modifiedDayMarks.get(index);
    }

    //todo 普通日期的修改
    public void setDatemark(TDateMark datemark){
        this.datemark = datemark;
    }
    public void setDatemark(TDateMark.DateType t){
        this.datemark.changeDateMark(t);
    }
    public void setDatemark(TDateMark.DateType type,String memo){
        this.datemark.changeDateMark(type,memo);
    }
    //访问
    public TDateMark getDatemark(){
        return this.datemark;
    }

    //todo 待办事项的添加、修改、删除
    public void addTodoItem(TTodoItem item){
        todoList.insertItem(item);
    }

    public void addTodoItem(String item){
        todoList.insertItem(item);
    }

    public void editTodoItem(int index, String item){
        todoList.setItem(index,item);
    }

    public int deleteTodoItem(int index){
        if(index >= todoList.getSize() || index < 0) throw new IllegalArgumentException("访问自定义日期容器的下标越界！");
        todoList.deleteItem(index);
        return 1;
    }
    //访问
    public  TTodoItem getTodoItem(int index){
        if(index >= todoList.getSize() || index < 0) throw new IllegalArgumentException("访问自定义日期容器的下标越界！");
        return todoList.getItem(index);
    }

    //todo 修改本日番茄
    public void addTomatoCount(){
        tomatoCount++;
    }
    //修改本日番茄
    public void setTomatoCount(int ncount){tomatoCount = ncount;}
    //重置本日番茄
    public void resetTomatoCount(){tomatoCount = 0;}
    //访问
    public long getTomatoCount(){return tomatoCount;}

    //获取大小
    public int getModifiedDateCount(){
        return modifiedDayMarks.size();
    }

    public int getTodoListSize(){
        return todoList.getSize();
    }

    public String getFullIntro(){
        StringBuffer temp = new StringBuffer();
        temp.append("今日类型：  " + datemark.getMemo() + '\n');
        temp.append("特殊日期：  " + modifiedDayMarks.get(0).getMemo() + '\n');
        temp.append("待办事项：  " + todoList.getItem(0).getMemo() + '\n');
        temp.append("今日番茄：  " + Long.toString(tomatoCount) + '\n');
        return temp.toString();
    }
}
