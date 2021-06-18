package TFrames;

import TCalenders.*;
import TFrames.AFLayouts.AfAnyWhere;
import TFrames.AFLayouts.AfMargin;
import TManagers.TManager;
import TTimepkg.TTime;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class TCalenderPanel extends TPanel{
    //JPanel toolPanel?
    JPanel calenderFullPanel;
    JPanel toolPanel;
    JLabel toolLabel;
    JPanel calenderPanel;
    TDateButton dates[][] = new TDateButton[6][7];
    TDateButton selectedButton;
    JPanel dateDescriptionPanel;
    JPanel todoListPanel;
    TTime selectedDate;
    JPanel listpanel;
    JLabel tomatoCount;

    TDateContainer selectedcontainer;

    static final int MONTH_LENGTH[]
            = {31,28,31,30,31,30,31,31,30,31,30,31};

    public TCalenderPanel(){
        panel = new JPanel();
        panel.setBorder(BorderFactory.createLineBorder(new Color(0,0,0,0),10));
        panel.setLayout(new AfAnyWhere());
        selectedDate = TManager.getInstance().getNowTime();
        initCalenderPanel();
        paintCalenderPanel(TManager.getInstance().getNowTime());
        initDateDescriptionPanel();
        initTodoListPanel();

        dateDescriptionPanel.setBackground(new Color(246, 210, 210));
        dateDescriptionPanel.setPreferredSize(new Dimension(620,250));

        todoListPanel.setPreferredSize(new Dimension(300,900));

        panel.add(calenderFullPanel,AfMargin.TOP_LEFT);
        panel.add(dateDescriptionPanel,AfMargin.BOTTOM_LEFT);
        panel.add(todoListPanel,AfMargin.CENTER_RIGHT);
    }

    private void initCalenderPanel(){
        calenderFullPanel = new JPanel(new BorderLayout());
        calenderFullPanel.setPreferredSize(new Dimension(620,620));

        //工具栏？
        toolPanel = new JPanel(new BorderLayout());
        JPanel t1=new JPanel(new FlowLayout());
        JButton yearsubbtn = TFrameTools.createTButton("<<");
        JButton monthsubbtn = TFrameTools.createTButton("<");
        JButton yearaddbtn = TFrameTools.createTButton(">>");
        JButton monthaddbtn = TFrameTools.createTButton(">");
        yearsubbtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectedDate = new TTime(selectedDate.getYear()-1, selectedDate.getMonth(), 1);
                paintCalenderPanel(selectedDate);
            }
        });
        yearaddbtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectedDate = new TTime(selectedDate.getYear()+1, selectedDate.getMonth(), 1);
                paintCalenderPanel(selectedDate);
            }
        });
        monthsubbtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectedDate = new TTime(selectedDate.getYear(), selectedDate.getMonth()-1, 1);
                paintCalenderPanel(selectedDate);
            }
        });
        monthaddbtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectedDate = new TTime(selectedDate.getYear(), selectedDate.getMonth()+1, 1);
                paintCalenderPanel(selectedDate);
            }
        });



        t1.add(yearsubbtn);
        t1.add(monthsubbtn);
        toolLabel = new JLabel("当前时间");
        toolLabel.setFont(TFrameTools.TEXTFONT);
        toolLabel.setHorizontalAlignment(SwingConstants.CENTER);
        toolLabel.setBackground(new Color(0,0,0,0));
        JPanel t2 = new JPanel(new FlowLayout());
        t2.add(monthaddbtn);
        t2.add(yearaddbtn);
        toolPanel.add(t1,BorderLayout.WEST);
        toolPanel.add(toolLabel,BorderLayout.CENTER);
        toolPanel.add(t2,BorderLayout.EAST);


        //日历
        calenderPanel = new JPanel();
        calenderPanel.setLayout(new GridLayout(7,7));
        JLabel label[] = new JLabel[7];
        String weekdays[] = {"MON","TUE","WED","THU","FRI","SAT","SUN"};
        for(int i=0;i<7;i++){
            label[i] = new JLabel();
            label[i].setText(weekdays[i]);
            label[i].setFont(TFrameTools.BUTTONFONT);
            label[i].setHorizontalAlignment(SwingConstants.CENTER);
            calenderPanel.add(label[i]);
        }
        label[5].setForeground(TFrameTools.copyColor(TFrameTools.HOLIDAYCOLOR));
        label[6].setForeground(TFrameTools.copyColor(TFrameTools.HOLIDAYCOLOR));

        for(int i=0;i<6;i++){
            for(int j=0;j<7;j++){
                dates[i][j] = new TDateButton(" ");
                dates[i][j].addActionListener(new TDateAction(dates[i][j]));
                calenderPanel.add(dates[i][j]);
            }
        }

        calenderFullPanel.add(toolPanel,BorderLayout.NORTH);
        calenderFullPanel.add(calenderPanel,BorderLayout.CENTER);
    }

    //绘制日历，包含日历按钮，可以选择各个日期
    private void paintCalenderPanel(TTime selected){
        try {
            if(selectedButton!=null) selectedButton.losefocus();
            selectedDate = selected;
            //工具栏更新字
            toolLabel.setText(selected.getYear()+"年"+selected.getMonth()+"月");

            //当月第一天为星期几
            TTime first = new TTime(selected.getYear(), selected.getMonth(), 1);
            int offset = first.get(Calendar.DAY_OF_WEEK)-1;//星期几
            offset = offset == 7 ? 0 : offset;
            int sum = MONTH_LENGTH[first.getMonth()-1];

            int count = 0;
            int temp = 1;
            for (int i = 0; i < 6; i++) {
                for (int j = 0; j < 7; j++) {
                    count++;
                    if (count < offset || temp >= sum+1) {
                        //取消按钮显示
                        dates[i][j].reset();
                    } else {
                        //更新按钮显示
                        dates[i][j].activeButton(temp);
                        TTime ntime = new TTime(selected.getYear(), selected.getMonth(), temp);
                        dates[i][j].setReftime(ntime);
                        if(temp == selected.getDay()) {selectedButton = dates[i][j];selectedButton.selectButton();}
                        if(TManager.getInstance().getContainerofDate(ntime)==null){
                            dates[i][j].setDateType(getDefaultType(ntime));
                            dates[i][j].loseModifiedDateMark();
                        }else{
                            dates[i][j].setDateType(TManager.getInstance().getContainerofDate(ntime).getDatemark().getDateType());
                            TDateContainer container = TManager.getInstance().getContainerofDate(ntime);
                            String ntemp = container.getModifiedDate(0).getMemo();
                            //有自定义日期，且不是空
                            if(container.getModifiedDate(0)!=null
                            &&(!container.getModifiedDate(0).getMemo().equals("")))
                            {
                                dates[i][j].activeModifiedDateMark();
                            }
                            else dates[i][j].loseModifiedDateMark();
                        }
                        temp++;
                    }
                }//j
            }//i
            panel.repaint();
        }
        catch (Exception e){
            JOptionPane.showMessageDialog(null,e.getCause(),"出错！",JOptionPane.ERROR_MESSAGE);
        }
    }


    String weekdaychinese[] = {"一","二","三","四","五","六","日"};
    JPanel leftPanel;
    JPanel rightPanel;
    JLabel yeardes;
    JLabel weekdaydes;
    JLabel datemark;
    JLabel modifieddatemark;

    private void initDateDescriptionPanel(){
        dateDescriptionPanel = TFrameTools.createPanel(new GridLayout(1,2,15,0));
        leftPanel = TFrameTools.createPanel(new GridLayout(4,1,0,10));
        rightPanel = TFrameTools.createPanel(new GridLayout(2,1,0,10));
        leftPanel.setBorder(TFrameTools.EmptyDateBorder);
        rightPanel.setBorder(TFrameTools.EmptyDateBorder);
        dateDescriptionPanel.add(leftPanel);
        dateDescriptionPanel.add(rightPanel);

        //左面板
        yeardes = TFrameTools.createLabel(TManager.getInstance().getNowTime().getYear()+"年"
        +TManager.getInstance().getNowTime().getMonth()+"月"
        +TManager.getInstance().getNowTime().getDay()+"日");
        yeardes.setFont(TFrameTools.TEXTFONT);
        yeardes.setVerticalAlignment(SwingConstants.BOTTOM);
        weekdaydes = TFrameTools.createLabel("");
        weekdaydes.setFont(TFrameTools.TEXTFONT);
        weekdaydes.setVerticalAlignment(SwingConstants.TOP);
        JPanel temppanel = TFrameTools.createPanel(new AfAnyWhere());
        JButton todaybtn = TFrameTools.createTButton("回到今天");
        //当天番茄面板
        JPanel tpanel = TFrameTools.createPanel(new FlowLayout(FlowLayout.LEFT));
        tpanel.setPreferredSize(new Dimension(200,50));
        tomatoCount = TFrameTools.createLabel("0");//当天番茄数
        tpanel.add(TFrameTools.createLabel("今日番茄："));
        tpanel.add(tomatoCount);
        todaybtn.addActionListener(e->paintAll(TManager.getInstance().getNowTime()));
        //星期几，返回今天按钮面板
        //temppanel.add(weekdaydes,AfMargin.TOP_LEFT);
        //temppanel.add(todaybtn,AfMargin.BOTTOM_LEFT);

        leftPanel.add(yeardes);
        leftPanel.add(weekdaydes);
        leftPanel.add(tpanel);
        leftPanel.add(todaybtn);

        //右面板
        JPanel up = TFrameTools.createPanel(new AfAnyWhere());
        up.setBorder(TFrameTools.EmptyDateBorder);
        JPanel down = TFrameTools.createPanel(new AfAnyWhere());
        down.setBorder(TFrameTools.EmptyDateBorder);

        JLabel s1 = TFrameTools.createLabel("今日：");
        s1.setVerticalAlignment(SwingConstants.BOTTOM);//???
        JLabel s2 = TFrameTools.createLabel("纪念：");
        s2.setVerticalAlignment(SwingConstants.CENTER);
        datemark = TFrameTools.createLabel("无");
        modifieddatemark = TFrameTools.createLabel("");

        //修改日期类型按钮
        JButton upbtn = TFrameTools.createTButton("修改");
        upbtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //修改日期类型
                TDateMark.DateType types[] = {TDateMark.DateType.WORKDAY,TDateMark.DateType.RESTDAY,TDateMark.DateType.ULTRAWORK};
                TDateMark.DateType mark = (TDateMark.DateType)JOptionPane.showInputDialog(null,
                        "请选择日期类型","Tomato Time",
                        1,null,types,types[0]);
                if(mark!=null) {
                    System.out.println(selectedDate.toString() + "日期类型修改为：" + mark);
                    changeDateMark(selectedDate, mark);
                    paintAll(selectedDate);
                }
            }
        });
        //修改自定义类型按钮
        JButton downbtn = TFrameTools.createTButton("修改");
        downbtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //修改自定义日期
                String memo =JOptionPane.showInputDialog(null,
                        "请输入纪念日名称：\n", "Tomato Time", JOptionPane.PLAIN_MESSAGE);
                if(memo!=null) {
                    if(memo.equals(""))
                        selectedButton.loseModifiedDateMark();
                        else
                            selectedButton.activeModifiedDateMark();
                    changeModifiedDateMark(selectedDate, memo);
                    System.out.println(selectedDate.toString() + "纪念日修改为：" + memo);
                }
                paintAll(selectedDate);
            }
        });

        up.add(s1,AfMargin.CENTER_LEFT);
        up.add(datemark,AfMargin.CENTER);
        up.add(upbtn,AfMargin.CENTER_RIGHT);
        down.add(s2,AfMargin.CENTER_LEFT);
        down.add(modifieddatemark,AfMargin.CENTER);
        down.add(downbtn,AfMargin.CENTER_RIGHT);

        rightPanel.add(up);
        rightPanel.add(down);


        paintDateMarks(selectedDate);
        paintDateDescriptionPanel(selectedDate);

    }

    private void paintDateDescriptionPanel(TTime selected){
        try {

            String s1 = selected.getYear() + "年"
                    + selected.getMonth() + "月"
                    + selected.getDay() + "日";
            int t =selected.get(GregorianCalendar.DAY_OF_WEEK) - 2;
            if(t < 0) t = 6;
            String s2 = "星期" + weekdaychinese[t];
            yeardes.setText(s1);
            weekdaydes.setText(s2);

            //读取当天的番茄
            if(TManager.getInstance().hasContainerofDate(selected))
                tomatoCount.setText(TManager.getInstance().getContainerofDate(selected).getTomatoCount()+"");
            else
                tomatoCount.setText("0");

            panel.repaint();
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    private void paintDateMarks(TTime selected){
        try {
            //有容器
            if(TManager.getInstance().getContainerofDate(selected)!=null){
                TDateContainer container = TManager.getInstance().getContainerofDate(selected);
                datemark.setText(container.getDatemark().getMemo());
                if(container.getModifiedDateCount()>0){
                    modifieddatemark.setText(container.getModifiedDate(0).getMemo());
                }
                else{
                    modifieddatemark.setText("无");
                }
            }
            else{
                int i= selectedDate.get(GregorianCalendar.DAY_OF_WEEK);
                String str = i==1||i==7?"休息日":"工作日";
                datemark.setText(str);
                modifieddatemark.setText("无");
                //if((selectedDate.get(GregorianCalendar.DAY_OF_WEEK)-2)==6 || selectedDate.get(GregorianCalendar.DAY_OF_WEEK))
            }
        }catch (Exception e){
                System.out.println(e.getMessage());
        }
    }

    public void changeDateMark(TTime selected, TDateMark.DateType ntype){
        try{
            //有容器
            if(TManager.getInstance().getContainerofDate(selected)!=null){
                TDateContainer container = TManager.getInstance().getContainerofDate(selected);
                container.setDatemark(ntype);
            }
            //没有容器：创建新的容器
            else{
                TDateContainer container = TManager.getInstance().getCalender().createDateContainer(selected);
                container.setDatemark(ntype);
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public void changeModifiedDateMark(TTime selected,String memo){
        try{
            //有容器
            if(TManager.getInstance().getContainerofDate(selected)!=null){
                TDateContainer container = TManager.getInstance().getContainerofDate(selected);
                if(container.getModifiedDateCount()==0){
                    container.addModifiedDate(memo);
                }
                else{
                    container.changeModifiedDate(0,memo);
                }
            }
            //没有容器：创建新的容器
            else{
                TDateContainer container = TManager.getInstance().getCalender().createDateContainer(selected);
                container.addModifiedDate(memo);
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    private TDateMark.DateType getDefaultType(TTime selected){
        try {
            int i= selected.get(GregorianCalendar.DAY_OF_WEEK);
            TDateMark.DateType type = i==1||i==7? TDateMark.DateType.RESTDAY: TDateMark.DateType.WORKDAY;
            return type;
        }
        catch (Exception e){
            //
            return null;
        }
    }

    ArrayList<TodoItemPanel> todoItemPanelList;
    final int MaxTodoItemPanelCount = 12;
    ArrayList<String> todoStringList = new ArrayList<String>();
    //待办事项
    private void initTodoListPanel(){
        //待办事项条及添加待办事项的按钮
        todoListPanel = new JPanel(new BorderLayout());
        JLabel todolabel = TFrameTools.createLabel("待办事项");
        JButton addbtn = TFrameTools.createTButton("+");
        //addbtn.setFont(new Font("Consolas",0,20));
        addbtn.setPreferredSize(new Dimension(50,40));
        JPanel headp = TFrameTools.createPanel(new AfAnyWhere());
        headp.setPreferredSize(new Dimension(300,80));
        headp.setBorder(TFrameTools.EmptyDateBorder);
        headp.add(todolabel,AfMargin.CENTER_LEFT);
        headp.add(addbtn,AfMargin.CENTER_RIGHT);

        //箱式布局？
        listpanel = TFrameTools.createPanel(null);
        //listpanel.setLayout(new BoxLayout(listpanel,BoxLayout.Y_AXIS));
        listpanel.setLayout(new FlowLayout());
        listpanel.setBackground(new Color(156, 194, 215, 255));
        //listpanel.add(new JLabel("测试字样"));
        todoItemPanelList = new ArrayList<>();

        //最大数量为12
        for(int i=0;i<MaxTodoItemPanelCount;i++){
            todoItemPanelList.add(new TodoItemPanel("测试"+i,i));
            todoItemPanelList.get(i).setVisible(false);
            listpanel.add(todoItemPanelList.get(i).getPanel());
        }

        //todoItemPanelList = new ArrayList<>();
        addbtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //添加待办事项
                //boolean b = todoItemPanelList.get(3).getPanel().isVisible();
                //todoItemPanelList.get(3).getPanel().setVisible(!b);
                if(todoStringList.size()>=MaxTodoItemPanelCount){
                    JOptionPane.showMessageDialog(null,"待办事项最多可以添加"+MaxTodoItemPanelCount+"个！",
                            "Tomato Time",
                            JOptionPane.PLAIN_MESSAGE);
                    return;
                }
                String nstr = JOptionPane.showInputDialog(null, "请输入待办事项的新内容：",
                        "Tomato Time", JOptionPane.PLAIN_MESSAGE);
                if(nstr==null) return;
                if(nstr.equals("")){
                    JOptionPane.showMessageDialog(null,"输入待办事项内容不可为空！","Tomato Time",JOptionPane.PLAIN_MESSAGE);
                }
                else {
                    todoStringList.add(nstr);
                    addTodoItem2Container(selectedDate, nstr);
                    paintAll(selectedDate);
                }
                panel.repaint();

            }
        });

        todoListPanel.add(headp,BorderLayout.NORTH);
        todoListPanel.add(listpanel,BorderLayout.CENTER);

        paintAll(selectedDate);
        panel.repaint();

    }

    private void paintTodoListPanel(TTime time){
        try{
            //将container中的内容拷贝进selectedstrlist
            loadFromContainer(selectedDate);
            int size = todoStringList.size();
            //将selectedstrlist中的内容放进panel中
            //if(size>0){
                for(int i=0;i<size||i<MaxTodoItemPanelCount;i++){
                    if(i >= size){
                        todoItemPanelList.get(i).setVisible(false);
                    }else {
                        todoItemPanelList.get(i).setText(todoStringList.get(i));
                        todoItemPanelList.get(i).setOrderInContainer(i);
                        todoItemPanelList.get(i).setVisible(true);
                        //System.out.println("已绘制新的待办事项");
                    }
                }
            //}
           // else{
           // }

        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    private void clearListPanel(){
        //?
        try {
            if (todoItemPanelList.size() > 0) {
                for (TodoItemPanel p : todoItemPanelList) {
                    p.setVisible(false);
                }
            } else return;
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    private void loadFromContainer(TTime t){
        todoStringList.clear();
        selectedcontainer = TManager.getInstance().getContainerofDate(t);
        if(selectedcontainer==null) return;

        int size = selectedcontainer.getTodoListSize();
        if(size > 0) {
            for(int i=0;i<size;i++){
                todoStringList.add(selectedcontainer.getTodoItem(i).toString());
            }
        }
    }

    private void addTodoItem2Container(TTime t,String str){
        selectedDate = t;
        selectedcontainer = TManager.getInstance().getContainerofDate(t);

        //创建该日期的container
        if(selectedcontainer==null){
            selectedcontainer = TManager.getInstance().getCalender().createDateContainer(t);
        }

        selectedcontainer.addTodoItem(str);

    }

    private void editTodoItemofContainer(TTime t,int index, String str){
        selectedcontainer = TManager.getInstance().getContainerofDate(t);
        try{
            selectedcontainer.editTodoItem(index,str);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    private void deleteTodoItemofContainer(TTime t, int index){
        selectedcontainer = TManager.getInstance().getContainerofDate(t);
        try {
            selectedcontainer.deleteTodoItem(index);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    private void paintAll(TTime selected){
        selectedDate = selected;
        selectedcontainer = TManager.getInstance().getContainerofDate(selected);

        paintCalenderPanel(selected);
        paintDateDescriptionPanel(selected);
        paintDateMarks(selected);
        paintTodoListPanel(selected);
    }

    //别的页面调用：重新加载日历页面
    public void reloadFromManager(){
        selectedDate = TManager.getInstance().getNowTime();
        paintAll(selectedDate);
    }

    //日历按钮
    class TDateButton extends JButton{

        TTime reftime;
        JLabel memolabel;
        JLabel modifiedmark;//如果有自定义日期，则添加这个标志
        int tomatoCount;

        public TDateButton(){
            super();
            setLayout(new AfAnyWhere());
            setFont(TFrameTools.TEXTFONT);
            setBackground(TFrameTools.copyColor(TFrameTools.BUTTONCOLOR));
            //button.setContentAreaFilled(false);
            setFocusPainted(false);
            //addActionListener(new DateActionListener());
            memolabel = new JLabel();
            memolabel.setFont(new Font("微软雅黑",0,14));
            modifiedmark = new JLabel("*");
            modifiedmark.setFont(new Font("Consolas",1,18));
            modifiedmark.setForeground(TFrameTools.MODIFIEDDAYCOLOR);
            modifiedmark.setVisible(false);//todo
            this.add(memolabel,AfMargin.TOP_RIGHT);
            this.add(modifiedmark,AfMargin.BOTTOM_CENTER);
            setBorder(TFrameTools.EmptyDateBorder);
        }

        public TDateButton(String name){
            this();
            setName(name);
        }

        public void setReftime(TTime time){
            reftime = time;
        }

        public TTime getRefTime(){
            return reftime;
        }

        public void setWordColor(Color color){
            setForeground(color);
        }

        public void addMemo(String memo,Color color){
            memolabel.setText(memo);
            memolabel.setBackground(new Color(0,0,0,0));
            memolabel.setForeground(color);
            add(memolabel, AfMargin.TOP_RIGHT);
        }

        public void reset(){
            setEnabled(false);
            setText(" ");
            memolabel.setText(" ");
            modifiedmark.setVisible(false);
            losefocus();
        }

        public void activeButton(String content){
            setEnabled(true);
            setText(content);
            memolabel.setText(" ");
        }
        public void activeButton(int content){
            activeButton(Integer.toString(content));
        }
        public void selectButton(){
            setBorder(TFrameTools.SelectedDateBorder);
        }
        public void losefocus(){
            setBorder(TFrameTools.EmptyDateBorder);
        }
        //自定义日期标记相关
        public void activeModifiedDateMark(){modifiedmark.setVisible(true);}
        public void loseModifiedDateMark(){modifiedmark.setVisible(false);}

        public void setDateType(TDateMark.DateType type){
            if(type==null){
                memolabel.setText("");
                return;
            }
            switch (type){
                case RESTDAY:memolabel.setText("假");memolabel.setForeground(TFrameTools.HOLIDAYCOLOR);break;
                case WORKDAY:memolabel.setText("");break;
                case ULTRAWORK:memolabel.setText("班");memolabel.setForeground(TFrameTools.ULTRAWORKDAYCOLOR);break;
                default:break;
            }
        }


        class DateActionListener implements ActionListener{
            @Override
            public void actionPerformed(ActionEvent e) {
                //reftime;
            }
        }
    }
    class TDateAction implements ActionListener{
        TDateButton button;
        public TDateAction(TDateButton b){ button = b;}
        @Override
        public void actionPerformed(ActionEvent e) {
            selectedDate = button.getRefTime();
            if(selectedButton!=null) selectedButton.losefocus();
            selectedButton = button;
            selectedButton.selectButton();

            System.out.println("选中时间："+selectedDate.toString());
            paintAll(selectedDate);
        }
    }

    //待办事项
    class TodoItemPanel {
        int orderInContainer;
        JLabel label;
        JPanel panel;
        JButton finishbtn;
        JButton editbtn;

        TodoItemPanel() {
            panel = TFrameTools.createPanel(new AfAnyWhere());
            panel.setPreferredSize(new Dimension(300,60));
            panel.setBackground(new Color(255, 255, 255));
            panel.setBorder(TFrameTools.EmptyDateBorder);
            label = TFrameTools.createLabel("",TFrameTools.SMALLTEXTFONT);
            finishbtn = TFrameTools.createTButton("完成",TFrameTools.SMALLTEXTFONT);
            finishbtn.setForeground(new Color(30, 130, 201));
            editbtn = TFrameTools.createTButton("修改",TFrameTools.SMALLTEXTFONT);
            //editbtn.setForeground();

            JPanel temppanel = TFrameTools.createPanel(new FlowLayout());
            temppanel.add(editbtn);
            temppanel.add(finishbtn);
            panel.add(label, AfMargin.CENTER_LEFT);
            panel.add(temppanel, AfMargin.CENTER_RIGHT);

            //editbtn行为，编辑待办事项
            editbtn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String nstr = JOptionPane.showInputDialog(null, "请输入待办事项的新内容：",
                            "Tomato Time", JOptionPane.PLAIN_MESSAGE);
                    if(nstr==null) return;
                    if(nstr != "") {
                        setText(nstr);
                        try {
                            //修改strlist的内容
                            todoStringList.set(orderInContainer, nstr);
                            editTodoItemofContainer(selectedDate, orderInContainer, nstr);
                        } catch (Exception ex) {
                            System.out.println("获取的index和string列表保存的不一致！列表越界！");
                        }
                    }
                    else{
                        JOptionPane.showMessageDialog(null,"输入待办事项不可为空！",
                                "Tomato Time",JOptionPane.PLAIN_MESSAGE);
                    }
                }
            });

            //finish按钮行为
            finishbtn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    boxTicked();
                }
            });
        }

        TodoItemPanel(String memo, int index){
            this();
            setText(memo);
            setOrderInContainer(index);
        }

        TodoItemPanel(TTodoList list, int index){
            this();
            loadFromContainer(list,index);
        }


        public void setText(String s){
            label.setText(s);
            panel.repaint();
        }

        public void setOrderInContainer(int i){orderInContainer = i;}

        public void loadFromContainer(TTodoList list, int index){
            try{
                setText(list.getItem(index).getMemo());
                setOrderInContainer(index);
            }
            catch (Exception e){
                System.out.println("设置待办事项面板内容失败！"+e.getMessage());
            }
        }

        public boolean isVisible(){return panel.isVisible();}
        public void setVisible(boolean b){panel.setVisible(b);}

        public void boxTicked(){
            setVisible(false);
            todoStringList.remove(orderInContainer);
            deleteTodoItemofContainer(selectedDate,orderInContainer);
            paintTodoListPanel(selectedDate);
        }

        public JPanel getPanel(){return panel;}
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("日历测试");
        TCalenderPanel panel = new TCalenderPanel();
        frame.setLayout(new BorderLayout());
        frame.setBounds(500,0,960,960);
        frame.add(panel.getPanel(),BorderLayout.CENTER);

        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
}

