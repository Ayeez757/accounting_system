package cn.ayeez.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

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

    public Main(String userName) {

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
    private void addFuntionPanels() {
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
                cardLayout.show(rootJPanel, "detial");
            }
        });

        sourceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(rootJPanel, "source");
            }
        });

        habitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(rootJPanel, "habit");
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
    public AccountingPanel() throws IOException {
        JPanel panel = new JPanel();
        panel.add(new JLabel("记账面板"));
        this.add(panel);

        /**
         * 读写文件,记录具体账单
         * 一个用户的账单信息记录一个文件
         * 要有时间，金额，收入还是支出，什么类型的收入或支出
         * type=Income&money=100$date=2025-11-24&class=eat
         */
        File file = new File("src\\username_and_password\\"+ Main.userName);
        FileWriter fileWriter = new FileWriter(file, true);
        fileWriter.write("type=Income&money=100$date=2025-11-24&class=eat");//先记录着这个，测试用，后面改成动态的

    }
}

class DetailPanel extends JPanel {
    public DetailPanel() {
        JPanel panel = new JPanel();
        panel.add(new JLabel("收支明细面板"));
        this.add(panel);
    }
}


class SourcePanel extends JPanel {
    public SourcePanel() {
        JPanel panel = new JPanel();
        panel.add(new JLabel("收入来源面板"));
        this.add(panel);
    }
}

class HabitPanel extends JPanel {
    public HabitPanel() {
        JPanel panel = new JPanel();
        panel.add(new JLabel("消费习惯面板"));
        this.add(panel);
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

