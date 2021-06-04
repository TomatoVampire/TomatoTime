package TCalenders;

import java.util.ArrayList;
import java.util.List;

public class TTodoList {
    //集合Item
    protected List<TTodoItem> todolist;

    public TTodoList(){
        todolist = new ArrayList<>();
    }

    public void insertItem(TTodoItem item){
        //创建一个新的，放入
        if(item == null) throw new NullPointerException();
        TTodoItem ni = item.clone();
        if(ni.getDescription().equals("")) throw new IllegalArgumentException("待办事项不能输入为空！");
        todolist.add(ni);
    }

    public void insertItem(String item){
        TTodoItem ni = new TTodoItem(item);
        insertItem(ni);
    }

    public int deleteItem(TTodoItem item){
        for(TTodoItem t : todolist){
            if(t.equals(item)){
                todolist.remove(t);
                return 1;
            }
        }
        return 0;
    }

    public int deleteItem(String item){
        for(TTodoItem t : todolist){
            if(t.getDescription().equals(item)){
                todolist.remove(t);
                return 1;
            }
        }
        return 0;
    }

    public int deleteItem(int index){
        if(index >= getSize() || index < 0){return 0;}
        todolist.remove(index);
        return 1;
    }


    public String getContent(int index){
        if(getSize() == 0) return "待办事项为空";
        if(index >= getSize() || index < 0) throw new IllegalArgumentException("待办事项输入的访问索引有误！");
        return todolist.get(index).getDescription();
    }

    public TTodoItem getItem(int index){
        if(getSize() == 0) return null;
        if(index >= getSize() || index < 0) throw new IllegalArgumentException("待办事项输入的访问索引有误！");
        return todolist.get(index);
    }

    public void setItem(int index, String memo){
        if(memo == null) return;
        if(index >= getSize() || index < 0) throw new IllegalArgumentException("待办事项输入的访问索引有误！");
        todolist.get(index).setDescription(memo);
    }

    public int getSize(){
        return todolist.size();
    }
}
