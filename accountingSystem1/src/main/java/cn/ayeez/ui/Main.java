package cn.ayeez.ui;

import cn.ayeez.javabean.AccountingData;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.*;
import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;



import cn.ayeez.javabean.AccountingData;


public class Main {

    //记录已登录的用户
    static String userName;


    private JPanel rootJPanel;
    private JButton accountingButton;
    private JButton detialButton;
    private JButton sourceButton;
    private JButton habitButton;
    private JButton analyzeButton;
    private JButton manageButton;
    private JButton settingButton;
    private CardLayout cardLayout;

    public Main(String userName) throws IOException {


        //记录用户名
        this.userName = userName;

        JFrame jFrame = new JFrame("主页面-个人记账系统");
//      LoginJFrame loginJFrame = new LoginJFrame();

        rootJPanel = new JPanel();
        cardLayout = new CardLayout();
        rootJPanel.setLayout(cardLayout);

        //侧边栏的面板
        JPanel buttonPanel = createButtonPanel();

//        Main mainJFrame = this;
//        cardLayout = new CardLayout();
//        rootJPanel.setLayout(cardLayout);

        //布局
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(buttonPanel, BorderLayout.WEST);

        //添加卡片面板到主面板
        mainPanel.add(rootJPanel, BorderLayout.CENTER);

        jFrame.setContentPane(mainPanel);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //媒体查询来查询屏幕大小，大概占屏幕七八十就差不多了
        int width = jFrame.getToolkit().getScreenSize().width;
        int height = jFrame.getToolkit().getScreenSize().height;
        jFrame.setSize((int) (width * 0.7), (int) (height * 0.7));
        jFrame.setLocationRelativeTo(null);
        jFrame.setResizable(false);


        //添加各个面板
        addFuntionPanels();

        //调用按钮事件监听方法
        addButtonListeners();

        //窗体可见
        jFrame.setVisible(true);


    }

    //创建各个面板并添加
    private void addFuntionPanels() throws IOException {
        JPanel accountingPanel = new AccountingPanel();
//        accountingPanel.add(new JLabel("记账面板"));

        JPanel detialPanel = new DetailPanel();
//        detialPanel.add(new JLabel("记账明细"));

        JPanel sourcePanel = new SourcePanel();
//        sourcePanel.add(new JLabel("收入来源"));

        JPanel habitPanel = new HabitPanel();
//        habitPanel.add(new JLabel("记账习惯"));

        JPanel analyzePanel = new AnalyzePanel();
//        analyzePanel.add(new JLabel("数据分析"));

        JPanel managePanel = new ManagePanel();
//        managePanel.add(new JLabel("消费管理"));

        JPanel settingPanel = new SettingPanel();
//        settingPanel.add(new JLabel("系统设置"));

        rootJPanel.add(accountingPanel, "accounting");
        rootJPanel.add(detialPanel, "detial");
        rootJPanel.add(sourcePanel, "source");
        rootJPanel.add(habitPanel, "habit");
        rootJPanel.add(analyzePanel, "analyze");
        rootJPanel.add(managePanel, "manage");
        rootJPanel.add(settingPanel, "setting");
//        cardJPanel = (CardLayout) rootJPanel.getLayout();

    }

    private JPanel createButtonPanel() {
        JPanel panel = new JPanel(new GridLayout(7, 1, 0, 4));
        accountingButton = new JButton("记账");
        detialButton = new JButton("收支明细");
        sourceButton = new JButton("收入来源");
        habitButton = new JButton("消费习惯");
        analyzeButton = new JButton("资产分析");
        manageButton = new JButton("管理");
        settingButton = new JButton("设置");

        panel.add(accountingButton);
        panel.add(detialButton);
        panel.add(sourceButton);
        panel.add(habitButton);
        panel.add(analyzeButton);
        panel.add(manageButton);
        panel.add(settingButton);

        return panel;
    }


    private void addButtonListeners() {
        accountingButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(rootJPanel, "accounting");
            }
        });

        detialButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                /// 重置一下面板（为什么没用？）
//                new DetailPanel();
                rootJPanel.add(new DetailPanel(),"detial");//原来是没有加到rootJPanel上
                cardLayout.show(rootJPanel, "detial");
            }
        });

        sourceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(rootJPanel, "source");

                //刷新那个饼图
                Component sourceComponet = null;
                for (Component component : rootJPanel.getComponents()) {
                    if (component instanceof SourcePanel){
                        sourceComponet = component;
                        break;
                    }
                }

                if (sourceComponet != null){
                    try {
                        ((SourcePanel)sourceComponet).refreshData();
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        });

        habitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(rootJPanel, "habit");

                // 刷新消费习惯饼图
                Component habitComponent = null;
                for (Component component : rootJPanel.getComponents()) {
                    if (component instanceof HabitPanel){
                        habitComponent = component;
                        break;
                    }
                }

                if (habitComponent != null){
                    try {
                        ((HabitPanel)habitComponent).refreshData();
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        });

        analyzeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(rootJPanel, "analyze");
            }
        });

        manageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(rootJPanel, "manage");
            }
        });

        settingButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(rootJPanel, "setting");
            }
        });


    }
}

/**
 * 下面是每个功能的具体面板类
 * 1. 记账面板
 * 2. 收支明细面板
 * 3. 收入来源面板
 * 4. 消费习惯面板
 * 5. 资产分析面板
 * 6. 管理面板
 * 7. 设置面板
 */


//1.记账面板
class AccountingPanel extends JPanel {
    /**
     * 这里声明组件,
     * 放外面方便事件监听以及业务逻辑调用这里的组件
     * <p>
     * 用到的组件（前面为组件类的解释，后半为具体对象用途）
     * JTextField文本框 amountField用于输入金额，
     * JSpinner时间选择器 dateSpinner用于选择时间
     * JRadioButton圆形勾选框按钮 incomeRadio用于选择收入， expenseRadio用于选择支出
     * ButtonGroup按钮组，typeGroup用于管理incomeRadio和expenseRadio,让这两个按钮只能选一个
     */
    private JTextField amountField;//输入金额的文本框
    private JSpinner dateSpinner;
    private JRadioButton incomeRadio;
    private JRadioButton expenseRadio;
    private ButtonGroup typeGroup;
    private JComboBox<String> categoryComboBox;
    private JButton saveButton;
    private JButton resetButton;


    public AccountingPanel() throws IOException {
        //布局
        /**
         * 这里的布局首先整个界面是 BorderLayout布局，东西南北中，
         * 其中南边放了一个按钮JPanel，里面放了两个按钮，
         * BorderLayout布局，上面放了一个名字JPanel（nameJPanel），里面放了一个JLabel记录面板名字
         * 中间放了一个面板JPanel(formPanel)，里面放了填信息的地方
         * formPanel的布局为GridBagLayout，然后用行列来规定那些组件的排布
         */
        setLayout(new BorderLayout());


        JPanel nameJPanel = new JPanel();
        nameJPanel.add(new JLabel("记账面板"));
        this.add(nameJPanel, BorderLayout.NORTH);//放在上面

        //填信息的地方
        //这里是面板和里面的布局
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;

        //金额输入
        gbc.gridx = 0;//这俩是定位，让文本框在jlabel右边一列
        gbc.gridy = 0;
//        gbc.gridheight=5;//这个不是行列的合并吗，为什么没起作用
        formPanel.add(new JLabel("金额："), gbc);
        gbc.gridx = 1;
        amountField = new JTextField(20);//长度
        formPanel.add(amountField, gbc);

        /**
         * 时间选择
         * 刚刚试了一下发现只是给了一个默认的格式，然后可以选择，
         * 我们手动输入的话，格式不会固定住，
         * 所以待会事件监听的时候，要正则一下，或者判断一下
         */
        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(new JLabel("日期："), gbc);
        gbc.gridx = 1;
        dateSpinner = new JSpinner(new SpinnerDateModel());
        dateSpinner.setEditor(new JSpinner.DateEditor(dateSpinner, "yyyy-MM-dd"));//格式
        formPanel.add(dateSpinner, gbc);

        //收入还是支出的选择
        gbc.gridx = 0;
        gbc.gridy = 2;
        formPanel.add(new JLabel("收入/支出："), gbc);
        gbc.gridx = 1;
        JPanel radioPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        incomeRadio = new JRadioButton("收入", true);//默认勾选
        expenseRadio = new JRadioButton("支出");
        //这两个东西想要不能同时勾选，要用一个ButtonGroup包起来，声明放在上面
        typeGroup = new ButtonGroup();
        typeGroup.add(incomeRadio);
        typeGroup.add(expenseRadio);
        radioPanel.add(incomeRadio);
        radioPanel.add(expenseRadio);
        formPanel.add(radioPanel, gbc);


        /**
         * 类型选择，消费类型或者收入类型，后面用来分析消费习惯
         * 突然发现这里写的不好，后续可能会改成两个下拉框，
         * 两个框都会根据我选择的是收入还是支出改变内容，然后第一个选择大类
         * 第二个框会根据第一个选择的大类来改变小类内容
         *
         * 目前简单粗暴一点，所有东西hanbala怼里面
         */
        gbc.gridx = 0;
        gbc.gridy = 3;
        formPanel.add(new JLabel("类型："), gbc);
        gbc.gridx = 1;
        String[] categoryArr =
                {"投资", "工资", "餐饮", "交通", "购物", "娱乐", "医疗",
                        "生活缴费"};//待补充
        categoryComboBox = new JComboBox<>(categoryArr);//参数是下拉框里面的东西
        formPanel.add(categoryComboBox, gbc);


        /**
         * 保存和重置按钮
         * 放在页面布局的底部，两个按钮用buttonPanel包起来
         */
        JPanel buttonPanel = new JPanel();
        saveButton = new JButton("保存");
        resetButton = new JButton("重置");
        buttonPanel.add(saveButton);
        buttonPanel.add(resetButton);
        add(buttonPanel, BorderLayout.SOUTH);


        add(formPanel, BorderLayout.CENTER);

        /**
         * 读写文件,记录具体账单
         * 一个用户的账单信息记录一个文件
         * 要有时间，金额，收入还是支出，什么类型的收入或支出
         * type=Income&money=100$date=2025-11-24&class=eat
         */
//        File file = new File("src\\username_and_password\\" + Main.userName);
//        FileWriter fileWriter = new FileWriter(file, true);
//        fileWriter.write("type=Income&money=100$date=2025-11-24&class=eat");//先记录着这个，测试用，后面改成动态的


        //事件监听
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //调用添加账单方法
                try {
                    addAccounting();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //重置
                amountField.setText("");
                dateSpinner.setValue(new Date());
                incomeRadio.setSelected(true);
                categoryComboBox.setSelectedIndex(0);

            }
        });


    }

    //==============上面是构造方法======================
    //这里处理添加账单的业务逻辑

    /**
     * 读写文件,记录具体账单
     * 一个用户的账单信息记录一个文件
     * 要有时间，金额，收入还是支出，什么类型的收入或支出
     * type=Income&money=100$date=2025-11-24&class=eat
     */
    public void addAccounting() throws IOException {
        /*
        后面看看设置和管理这些会不会添加新的信息，到时候可以在注册的时候创建用户文件夹
        然后一个用户的各种信息都分文件放在这个文件夹里面,下面这条路径要改动
         */
        File file = new File("src\\User\\" + Main.userName);
        FileWriter fileWriter = new FileWriter(file, true);
        //判断收入还是支出,
        String type = "";
        if (incomeRadio.isSelected()) {
            //收入
            type = "收入";
        } else {
            //支出
            type = "支出";
        }
        //判断一下金额合不合规，正则一下
        if (!amountField.getText().matches("[0-9]+")) {
            JOptionPane.showMessageDialog(this, "金额格式不正确");
        } else {
            /**
             * 这里本来想着修改一下记录日期的格式的，但是写着写着感觉没必要
             * 到时候读取的时候直接截取就好了，实在有需要就单独写几个方法来单独获取年月日这些信息
             */
//        //修改一下日期的格式，变成2025-11-24，查表法来对应
//        String monthStr="";
//        String date = dateSpinner.getValue().toString();
////        System.out.println(date);
//        date = date.substring(0, 10);
//        String[] monthArr = {"","Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"};
//        System.out.println(date);
//        for (int i = 0; i < monthArr.length; i++) {
//            if(monthArr[i].equals(date.substring(4,7))){
//                monthStr = i +"";
//                break;
//            }
//        }
//        System.out.println(monthStr);//测试没问题
//

            fileWriter.write("type=" + type + "&money=" + amountField.getText() +
                    "&date=" + dateSpinner.getValue() + "&class=" + categoryComboBox.getSelectedItem() + "\n");
            //应该是isShowing，看名字猜的（测试没问题）
            //不是，发现不对，查了一下是getSelectedItem()
            fileWriter.close();//傻逼记得关流，害我找了那么久的bug
            JOptionPane.showMessageDialog(this, "保存成功");
            System.out.println("保存成功");
        }
    }


}

//=================收支明细面板======================

class DetailPanel extends JPanel {

    //组件声明
    //好像没有业务逻辑在这里，没必要写在外面
    //可能会写个集合用来填表
    List<AccountingData> dataList = new ArrayList<>();
    private JTable table;
    private DefaultTableModel tableModel;

    int allMoney = 0;
    int allIncome = 0;
    int allExpense = 0;
    int thisMonthIncome = 0;
    int thisMonthExpense = 0;
    int thisYearIncome = 0;
    int thisYearExpense = 0;


    private JLabel allMoneyLabel;
    private JLabel thisMonthIncomeLabel;
    private JLabel thisMonthExpenseLabel;
    private JLabel thisYearIncomeLabel;
    private JLabel thisYearExpenseLabel;



    public DetailPanel() {

        //到时候这里写一个读信息的方法

        //布局
        GridBagConstraints gbc = new GridBagConstraints();
        setLayout(new BorderLayout());

        //添加删除按钮
        JButton deleteButton = new JButton("删除");
        add(deleteButton, BorderLayout.SOUTH);
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //获取选中的行
                int[] selectedRows = table.getSelectedRows();
                System.out.println(selectedRows[0]);//没问题
                //弹窗确认
                int result = JOptionPane.showConfirmDialog(DetailPanel.this, "确定删除吗？", "提示", JOptionPane.YES_NO_OPTION);
                if (result != JOptionPane.YES_OPTION) {
                    return;
                }

                try {
                    deleteAccounting(selectedRows[0]);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }

                //删除选中的行
                for (int i = selectedRows.length - 1; i >= 0; i--) {
                    tableModel.removeRow(selectedRows[i]);
                }
                //重新计算金额
                calculateMoney();
            }
        });

        JPanel nameJPanel = new JPanel();
        nameJPanel.add(new JLabel("收支明细面板"));
        JPanel headerJPanel = new JPanel();
        headerJPanel.setLayout(new GridBagLayout());
        gbc.gridx = 0;
        gbc.gridy = 0;
        headerJPanel.add(nameJPanel, gbc);
//        this.add(nameJPanel,BorderLayout.NORTH);
        JPanel infoJPanel = new JPanel();
        infoJPanel.setLayout(new GridBagLayout());
        /**
         * （括号代表坐标）
         * 余额 （00）（10）
         * 本月收入（01）（11）   本月支出（31）（41）
         * 本年收入   本年支出
         */
//        JPanel infoJPanel = new JPanel();
//        infoJPanel.setLayout(new GridBagLayout());

        gbc.gridx = 0;
        gbc.gridy = 0;
        infoJPanel.add(new JLabel("账户余额："), gbc);
        gbc.gridx = 1;
        allMoneyLabel = new JLabel("0");  // 创建并保存引用
        infoJPanel.add(allMoneyLabel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        infoJPanel.add(new JLabel("本月收入："), gbc);
        gbc.gridx = 1;
        thisMonthIncomeLabel = new JLabel("0");  // 创建并保存引用
        infoJPanel.add(thisMonthIncomeLabel, gbc);

        gbc.gridx = 3;
        gbc.gridy = 1;
        infoJPanel.add(new JLabel("本月支出："), gbc);
        gbc.gridx = 4;
        thisMonthExpenseLabel = new JLabel("0");  // 创建并保存引用
        infoJPanel.add(thisMonthExpenseLabel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        infoJPanel.add(new JLabel("本年收入："), gbc);
        gbc.gridx = 1;
        thisYearIncomeLabel = new JLabel("0");  // 创建并保存引用
        infoJPanel.add(thisYearIncomeLabel, gbc);

        gbc.gridx = 3;
        gbc.gridy = 3;
        infoJPanel.add(new JLabel("本年支出："), gbc);
        gbc.gridx = 4;
        thisYearExpenseLabel = new JLabel("0");  // 创建并保存引用
        infoJPanel.add(thisYearExpenseLabel, gbc);

        //以上加到面板中
        gbc.gridx=0;
        gbc.gridy=1;
        headerJPanel.add(infoJPanel, gbc);

        add(headerJPanel,BorderLayout.NORTH);

//        this.add(new DetailPanel(),BorderLayout.CENTER);

//        //这里放一些静态的测试数据
//        String[] headerNames = {"类型","金额","时间","分类"};
//        Object[][] data = {//要是这里能用vue写就好了[doge]
//                {"收入","100","2025-11-24","生活"},
//                {"支出","100","2025-11-24","生活"},
//                {"收入","100","2025-11-24","生活"},
//                {"支出","100","2025-11-24","生活"},
//                {"收入","100","2025-11-24","生活"},
//                {"支出","100","2025-11-24","生活"},
//                {"收入","100","2025-11-24","生活"},
//                //静态数据测试没问题
//        };

        // 表格部分
        String[] headerNames = {"类型", "金额（元）", "时间", "分类"};
        tableModel = new DefaultTableModel(headerNames, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // 加载数据
        try {
            loadAccountingData();
        } catch (IOException e) {
            System.out.println("加载失败");
            JOptionPane.showMessageDialog(this, "加载失败");
            throw new RuntimeException(e);
        }

    }

    //读取数据，加载数据的方法
    private void loadAccountingData() throws IOException {
        File file = new File("src\\User\\"+Main.userName);
        if(!file.exists()){
            System.out.println("毛都没有");
            return;
        }

        BufferedReader br = new BufferedReader(new FileReader(file));
        String line;


        //解析文本每行的内容
        while ((line = br.readLine()) != null) {
            //解析字符串
            //type=Income&money=123456&date=Tue Nov 25 12:33:51 CST 2025&class=投资
            String[] split = line.split("&");
            String type = split[0].split("=")[1];
            Double money = Double.parseDouble(split[1].split("=")[1]);
            Date date = new Date(split[2].split("=")[1]);
            String category = split[3].split("=")[1];
            //构造方法格式：
            // public AccountingData(String name, double price, Date date, String type, String category) {
            AccountingData data = new AccountingData(Main.userName, money, date, type, category);
            if(data != null)
                dataList.add(data);
        }
        br.close();

        //按照时间排序
        dataList.sort(new Comparator<AccountingData>() {
            @Override
            public int compare(AccountingData o1, AccountingData o2) {
                return o2.getDate().compareTo(o1.getDate());
            }
        });


        //把数据添加到表格中
        for (AccountingData data : dataList) {
            Object[] row ={
                    data.getType(),
                    String.valueOf(data.getPrice()),
                    formatDateString(data.getDate().toString()),//这里调用了下面格式化时间的方法
                    data.getCategory()
            };
            tableModel.addRow(row);
        }


        calculateMoney();
    }

    //删除数据的方法
    public void deleteAccounting(int rowIndex) throws IOException {
        /// 发现可以直接用dataList，好像可以不用读了
//        File file = new File("src\\User" + Main.userName);
//        if(!file.exists()){
//            System.out.println("毛都没有");
//            return;
//        }
        System.out.println(dataList.get(rowIndex).toString());//测试没问题，是这条
        dataList.remove(rowIndex);
        //重新写入文件，覆盖掉
        File file = new File("src\\User\\"+Main.userName);
        FileWriter fileWriter = new FileWriter(file);
        for (AccountingData data : dataList) {
            fileWriter.write("type=" + data.getType() + "&money=" + data.getPrice() + "&date=" + data.getDate() + "&class=" + data.getCategory() + "\n");
        }
        fileWriter.close();

    }

    /**
     * 把Tue Nov 25 12:33:51 CST 2025
     * 解析成2025-11-25 12:33:51
     */
    public String formatDateString(String dateStr) {
        try {
            SimpleDateFormat inputFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH);//搜了一下，这个格式要用英文环境
            Date date = inputFormat.parse(dateStr);
            SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return outputFormat.format(date);
        } catch (Exception e) {
            e.printStackTrace();
            return dateStr; // 如果解析失败，返回原始字符串
        }
    }

    /**
     * 这里计算各个时间段的收支和，记录在表头
     */
    private void calculateMoney() {
        /// 搬到外面了
//        int allIncome = 0;
//        int allExpense = 0;
//        int thisMonthIncome = 0;
//        int thisMonthExpense = 0;
//        int thisYearIncome = 0;
//        int thisYearExpense = 0;

        //遍历、判断、累加
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            if(dataList.get(i).getType().equals("收入")){
                allIncome+=dataList.get(i).getPrice();
                allMoney += dataList.get(i).getPrice();
                if (dataList.get(i).getDate().getYear() == new Date().getYear()){
                    thisYearIncome += dataList.get(i).getPrice();
                    if (dataList.get(i).getDate().getMonth() == new Date().getMonth()){
                        thisMonthIncome += dataList.get(i).getPrice();
                    }
                }
            }else{
                allExpense+=dataList.get(i).getPrice();
                allMoney-= dataList.get(i).getPrice();
                if (dataList.get(i).getDate().getYear() == new Date().getYear()){
                    thisYearExpense += dataList.get(i).getPrice();
                    if (dataList.get(i).getDate().getMonth() == new Date().getMonth()){
                        thisMonthExpense += dataList.get(i).getPrice();
                    }
                }
            }
        }

//        new DetailPanel();
//        getRootPane().add(new DetailPanel(),"detial");
//爆了
        allMoneyLabel.setText(String.valueOf(allMoney));
        thisMonthIncomeLabel.setText(String.valueOf(thisMonthIncome));
        thisMonthExpenseLabel.setText(String.valueOf(thisMonthExpense));
        thisYearIncomeLabel.setText(String.valueOf(thisYearIncome));
        thisYearExpenseLabel.setText(String.valueOf(thisYearExpense));

    }


}


class SourcePanel extends JPanel {









    //把这个挪到构造方法外面方便后面刷新数据
    private JPanel pieChartPanel;

    /**
     * 这里存放北边的组件声明
     * 未开发
     */

//    private JLabel totalMoneyLabel;

    public SourcePanel() throws IOException {

        /**
         *      * 本来是打算把这个统计次数也放到饼图里面的，但是这个饼图没用熟练，这东西查文档过于麻烦
         *      * 所以打算改一下布局，让这个SourcePanel.CENTER里面再塞一个JPanel，然后flow布局，左边放统计信息，饼图放右边
         *      未完成
         */

        setLayout(new BorderLayout());

        JPanel nameJPanel = new JPanel();
        nameJPanel.add(new JLabel("收入来源面板"));
        add(nameJPanel,BorderLayout.NORTH);

        //创建放饼图的面板
        pieChartPanel = new JPanel(new BorderLayout());


        JPanel incomeCategoryPanel = createIncomePieChart();
        //把饼图面板加到饼图面板的面板[doge]
        pieChartPanel.add(incomeCategoryPanel,BorderLayout.CENTER);

//        ///这个是同时存放饼图和统计信息的面版，后来加的
//        JPanel rootJPanel =new JPanel();
//        rootJPanel.setLayout(new FlowLayout());
//        //创建一个信息面板,这里面用网格的方式来布局
//        JPanel sourceInfoPanel = new JPanel();
//        sourceInfoPanel.setLayout(new GridBagLayout());
//        //加到根面板中
//        rootJPanel.add(sourceInfoPanel);
//        //然后把饼图面板加到根面板中
//        rootJPanel.add(pieChartPanel);
//
//        add(rootJPanel,BorderLayout.CENTER);
//
//        GridBagConstraints gbc = new GridBagConstraints();
//        gbc.gridx = 0;
//        gbc.gridy = 0;
//        sourceInfoPanel.add(new JLabel("各个收入来源"),gbc);
//        gbc.gridy = 1;
//


        //把这些东西hangbalan加到根面板中
        add(pieChartPanel,BorderLayout.CENTER);
    }

    //创建收入来源的饼图
    private JPanel createIncomePieChart() throws IOException {
        Map<String,Double> incomeData = getCategoryData("收入");
        Map<String, Integer> incomeCounts = getCategoryCounts("收入");
        //调用创建饼图的方法，把从获取处理数据方法得来的数据丢里面生成饼图并返回
        return createChartPanel(incomeData,incomeCounts,"收入类型分布");
    }

    /**
     * 这个方法用来读数据，解析数据，返回Map<String,Double>
     * 本来是打算把这个统计次数也放到饼图里面的，但是这个饼图没用熟练，这东西查文档过于麻烦
     * 所以打算改一下布局，让这个SourcePanel.CENTER里面再塞一个JPanel，然后flow布局，左边放统计信息，饼图放右边
     */
    private Map<String,Double> getCategoryData(String type) throws IOException {
        Map<String,Double> categoryMap = new HashMap<>();
//        Map<String,Integer> countMap = new HashMap<>();

        File file = new File("src\\User\\"+Main.userName);
        if (!file.exists()){
            return categoryMap;
        }

        BufferedReader br = new BufferedReader(new FileReader(file));
        String line;

        while ((line = br.readLine()) != null){
            String[] split = line.split("&");
            String recordType = split[0].split("=")[1];
            Double money = Double.parseDouble(split[1].split("=")[1]);
            String category = split[3].split("=")[1];

            //只统计指定类型的金额
            if (recordType.equals( type)){
                categoryMap.put(category,categoryMap.getOrDefault(category,0.0)+money);
            }

        }
        br.close();

        return categoryMap;
    }

    //发现还要统计次数我没写，懒得修改原有代码，直接从getCategoryData复制粘贴一个新的getCategoryCount
    //然后到时候在饼图那里合并一下数据
    // 在 SourcePanel 中添加新方法
private Map<String, Integer> getCategoryCounts(String type) throws IOException {
    Map<String, Integer> countMap = new HashMap<>();

    File file = new File("src\\User\\" + Main.userName);
    if (!file.exists()) {
        return countMap;
    }

    BufferedReader br = new BufferedReader(new FileReader(file));
    String line;

    while ((line = br.readLine()) != null) {
        String[] split = line.split("&");
        String recordType = split[0].split("=")[1];
        String category = split[3].split("=")[1];

        // 只统计指定类型的次数
        if (recordType.equals(type)) {
            countMap.put(category, countMap.getOrDefault(category, 0) + 1);
        }
    }
    br.close();

    return countMap;
}


    //创建饼图面板
    private JPanel createChartPanel(Map<String,Double> data,Map<String,Integer> counts,String title) {

        //如果是空的饼图
        if (data.isEmpty()){
            JPanel emptyJPanel = new JPanel();
            emptyJPanel.add(new JLabel("没有数据"));
            return emptyJPanel;
        }

        //创建饼图数据
        DefaultPieDataset dataSet = new DefaultPieDataset();
        for (Map.Entry<String, Double> entry : data.entrySet()) {
            dataSet.setValue(entry.getKey(), entry.getValue());
        }

        //创建饼图
        JFreeChart chart = ChartFactory.createPieChart(
                title,
                dataSet,
                true,//显示图例
                true,//显示工具提示信息
                false//不生成URL
        );

        //配置饼图样式和图例(翻文档累死个人)
        PiePlot plot = (PiePlot) chart.getPlot();
        plot.setLabelGenerator(new StandardPieSectionLabelGenerator("{0}:{1}({2})"));

        //设置图例标签生成器
//        plot.setLegendLabelGenerator(new StandardPieSectionLabelGenerator("{0}:{1}"));
        //配置饼图样式和图例
        //这里图例要设置字体啊，不然没有中文，全用框框代替，文档又查不到这个，搞得我查了好久
        plot.setLabelFont(new Font("SansSerif", Font.PLAIN, 12));
        plot.setLabelGenerator(new StandardPieSectionLabelGenerator("{0}:{1}({2})"));

        // 设置图例字体
        chart.getLegend().setItemFont(new Font("SansSerif", Font.PLAIN, 12));



        //创建图表面板,把饼图塞里面
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(400, 300));



        return chartPanel;
    }

    //刷新数据的方法
    public void refreshData() throws IOException {
        pieChartPanel.removeAll();

        JPanel incomeCategoryPanel = createIncomePieChart();
        pieChartPanel.add(incomeCategoryPanel,BorderLayout.CENTER);
        pieChartPanel.revalidate();
        pieChartPanel.repaint();
    }

}

//4. 消费习惯面板
class HabitPanel extends JPanel {
    private JPanel pieChartPanel;

    public HabitPanel() throws IOException {
        setLayout(new BorderLayout());

        JPanel nameJPanel = new JPanel();
        nameJPanel.add(new JLabel("消费习惯面板"));
        add(nameJPanel, BorderLayout.NORTH);

        //创建放饼图的面板
        pieChartPanel = new JPanel(new BorderLayout());

        //创建支出分类饼图
        JPanel expenseCategoryPanel = createExpensePieChart();
        pieChartPanel.add(expenseCategoryPanel, BorderLayout.CENTER);

        //添加到主面板
        add(pieChartPanel, BorderLayout.CENTER);
    }

    //创建支出分类饼图
    private JPanel createExpensePieChart() throws IOException {
        // 获取支出数据（注意这里传入"支出"作为筛选条件）
        Map<String, Double> expenseData = getCategoryData("支出");
        // 创建饼图面板
        return createChartPanel(expenseData, "支出分类分布");
    }

    /**
     * 这个方法用来读数据，解析数据，返回Map<String,Double>
     */
    private Map<String, Double> getCategoryData(String type) throws IOException {
        Map<String, Double> categoryMap = new HashMap<>();

        File file = new File("src\\User\\" + Main.userName);
        if (!file.exists()) {
            return categoryMap;
        }

        BufferedReader br = new BufferedReader(new FileReader(file));
        String line;

        while ((line = br.readLine()) != null) {
            String[] split = line.split("&");
            String recordType = split[0].split("=")[1];
            Double money = Double.parseDouble(split[1].split("=")[1]);
            String category = split[3].split("=")[1];

            //只统计指定类型的记录
            if (recordType.equals(type)) {
                categoryMap.put(category, categoryMap.getOrDefault(category, 0.0) + money);
            }
        }
        br.close();

        return categoryMap;
    }

    //创建饼图面板（复用SourcePanel中的方法）
    private JPanel createChartPanel(Map<String, Double> data, String title) {
        //如果是空的饼图
        if (data.isEmpty()) {
            JPanel emptyJPanel = new JPanel();
            emptyJPanel.add(new JLabel("没有数据"));
            return emptyJPanel;
        }

        //创建饼图数据集
        DefaultPieDataset dataSet = new DefaultPieDataset();
        for (Map.Entry<String, Double> entry : data.entrySet()) {
            dataSet.setValue(entry.getKey(), entry.getValue());
        }



        //创建饼图
        JFreeChart chart = ChartFactory.createPieChart(
                title,
                dataSet,
                true,  //显示图例
                true,  //显示工具提示信息
                false  //不生成URL
        );

        //配置饼图样式
        PiePlot plot = (PiePlot) chart.getPlot();
        plot.setLabelGenerator(new StandardPieSectionLabelGenerator("{0}:{1}({2})"));

        //设置字体避免中文显示为方块
        plot.setLabelFont(new Font("SansSerif", Font.PLAIN, 12));
        chart.getLegend().setItemFont(new Font("SansSerif", Font.PLAIN, 12));

        //创建图表面板
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(400, 300));

        return chartPanel;
    }

    //刷新数据的方法（可选）
    public void refreshData() throws IOException {
        pieChartPanel.removeAll();
        JPanel expenseCategoryPanel = createExpensePieChart();
        pieChartPanel.add(expenseCategoryPanel, BorderLayout.CENTER);
        pieChartPanel.revalidate();
        pieChartPanel.repaint();
    }
}


class AnalyzePanel extends JPanel {
    public AnalyzePanel() {
        JPanel panel = new JPanel();
        panel.add(new JLabel("资产分析面板"));
        this.add(panel);
    }
}

class ManagePanel extends JPanel {
    public ManagePanel() {
        JPanel panel = new JPanel();
        panel.add(new JLabel("管理面板"));
        this.add(panel);
    }
}

class SettingPanel extends JPanel {
    public SettingPanel() {
        JPanel panel = new JPanel();
        panel.add(new JLabel("设置面板"));
        this.add(panel);
    }
}

