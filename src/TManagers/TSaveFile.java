package TManagers;

import java.io.*;
import java.util.ArrayList;

//使用stream存储数据？
public class TSaveFile {
    //
    public static int saveFile(Object obj, String path){
        try{
            FileOutputStream fileOutputStream = new FileOutputStream(path);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(obj);
            objectOutputStream.close();
        }
        catch (Exception e){
            //System.out.println(e.getCause());
            System.out.println("创建文件失败！");
            return 0;
        }
        return 1;
    }

    public static <T> T loadFile(String path){
        try{
            FileInputStream fileInputStream = new FileInputStream(path);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            Object got = objectInputStream.readObject();
            return (T)got;
        }
        catch (Exception e){
            //System.out.println(e.getCause());
            System.out.println("读取 "+path+" 文件失败！");
            return null;
        }
    }

    //是否有该文件
    public static boolean hasFile(String path){
        try{
            FileInputStream fileInputStream = new FileInputStream(path);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            Object got = objectInputStream.readObject();
            return got!=null;
        }
        catch (InvalidClassException e1){
            System.out.println("类对象无法获取！可能存档后对象发生结构变化！");
            return false;
        }
        catch (Exception e2){
            System.out.println("获取 "+path+" 文件失败！");
            return false;
        }
    }

    public static void main(String[] args) {
        Test testfile = new Test();
        testfile.add((Integer) 1);
        testfile.add((Integer) 3);
        testfile.add((Integer) 2);
        saveFile(testfile,"test.txt");

        Test loaded = loadFile("test.txt");
        System.out.println(loaded);
    }
}

class Test extends ArrayList<Integer> implements Serializable{
    public Test(){
        super();
    }
}
