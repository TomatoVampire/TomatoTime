package TManagers;

import TCalenders.*;
import TCount.TCountdown;
import TCount.TTomatoClock;
import TTimepkg.TClock;
import TTimepkg.TTime;

import java.util.Scanner;
import java.util.TimeZone;


public class MainTestCMD {
    //private MainTest maim = new MainTest();
    public static void main(String[] args) {
        //countTest();
        //todolistTest();
        //calenderTest();
        //timeTest();
        managerTest();
    }

    private static void loadManagerTest(){
        TManager manager = TManager.getInstance();
        System.out.println(manager.getCalender().getDateEvents(new TTime()).getTodoItem(0));
    }

    private static void managerTest(){
        TManager manager = TManager.getInstance();
        manager.getCalender().registerDateEvents(new TTime(),new TDateContainer());
        manager.getCalender().getDateEvents(new TTime()).addTodoItem("作业a");
        System.out.println(manager.getCalender().getDateEvents(new TTime()).getTodoItem(0));

        manager.saveFile();
    }

    private static void timeTest(){
        TClock clock= new TClock();
        clock.synchronizeTime(TimeZone.getTimeZone("America/Los_Angeles"));
        //clock.start();
        //TCountdown countdown = new TCountdown(2);
        //todo 多线程！！！！新建个无限循环的线程，计时结束后kill该进程
        while(true)
        {
            //countdown.start();
            clock.start();
        }
    }

    private static void calenderTest(){
        TCalender calender = new TCalender();
        calender.registerDateEvents(new TTime(),new TDateContainer(TDateMark.DateType.WORKDAY));
        TDateContainer container = calender.getDateEvents(new TTime());
        if(container != null) System.out.println("found key : value");
        container.addModifiedDate(new TDateModified("纪念日!"));
        container.addTodoItem("计系2作业3");
        container.addTodoItem("计网实验报告7");
        System.out.println(container.getTodoItem(1));
        container.addTomatoCount();
        System.out.println(container.getTomatoCount());
        System.out.println(container.getDatemark().getColor());
        System.out.println(container.getFullIntro());
    }

    private static void todolistTest(){
        TTodoList todoList = new TTodoList();
        todoList.insertItem("第一件事！");
        todoList.insertItem("第二件事！");
        for(int i=0;i<todoList.getSize();i++){
            System.out.println(todoList.getContent(i));
        }
        todoList.deleteItem("第一件事！");
        todoList.deleteItem("第二件事！");
        System.out.println(todoList.getContent(1));

    }

    private static void countTest(){
        TCountdown countdown = new TCountdown(3);
        //countdown.startCount();
        TTomatoClock tomatoClock = new TTomatoClock();
        while(true){
            Scanner s = new Scanner(System.in);
            if(s.next() != null)
            tomatoClock.performTomato();
            //{countdown.setTimer(3); countdown.startCount();}
            else break;
        }
    }

    private void cmd(){
        printHelpInfo();
        while (true){
            System.out.print("> ");
        }
    }

    private void printHelpInfo(){
        System.out.println("欢迎来到TomatoTime命令行版本！输入1：显示时间");
    }

}
