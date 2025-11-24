package cn.ayeez.ui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.*;

import cn.ayeez.javabean.User;
import cn.hutool.core.io.FileUtil;

/**
 * 登录界面
 * 登录逻辑也暂时写在这里
 */


public class LoginJFrame extends JFrame {
    private JPanel rootJPanel;
    private JLabel LogInLabel;
    private JPasswordField passwordField;
    private JTextField userNameField;
    private JButton checkLogin;
    private JButton registerButton;
    //改成静态是因为隔壁注册也要读这个东西
    static ArrayList<User> userInfoList = new ArrayList<>();


    //提到外面方便我随时关掉这个窗口(不用了，传参)
    JFrame jFrame;

    //空参构造
    public LoginJFrame() {
        //自动生成
        $$$setupUI$$$();
        checkLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    //确认登录
                    checkLogin();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });



        //在new登录界面的时候读取用户登录信息
        readUserInfo();


        //页面初始化信息
        jFrame = new JFrame("登录界面-个人记账系统");
//        LoginJFrame loginJFrame = new LoginJFrame();
        LoginJFrame loginJFrame = this;
        jFrame.setContentPane(loginJFrame.rootJPanel);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setSize(400, 300);
        jFrame.setLocationRelativeTo(null);
        jFrame.setResizable(false);



        //窗体可见
        jFrame.setVisible(true);


        registerButton.addActionListener(new ActionListener() {

            //注册页面跳转按钮
            @Override
            public void actionPerformed(ActionEvent e) {
                gotoRegister();
            }
        });
    }


    public void gotoRegister(){
        new RegisterJFrame();
        jFrame.setVisible(false);
    }

    //读取用户信息
    public static void readUserInfo(){
        File file = new File("src\\username_and_password\\UAP.txt");
        List<String> userInfoStrList = FileUtil.readUtf8Lines(file);
        //遍历获取信息,创建user对象
        for (String str : userInfoStrList) {
//            System.out.println(str);//没问题
            String[] strArr = str.split("&");
            String[] arr1 = strArr[0].split("=");
            String[] arr2 = strArr[1].split("=");
            User user = new User(arr1[1],arr2[1]);
            userInfoList.add(user);
        }
            System.out.println(userInfoList);//没问题


//        int ch;
//        while((ch = fileReader.read())!=-1) {
//            //规定一下格式username=xxx&password=xxx;
//            //我默认写入了一个username=阿叶Ayeez&password=123456;
//            //System.out.print((char)(ch));//测试读取数据没问题
//            //切分
//            String str = new String(String.valueOf((char) ch));
//            //System.out.print(str);//没问题
//        }

    }


    //检查用户密码
    /*
     */
    boolean checkLogin() throws IOException {
        for (User user : userInfoList) {
            if(user.equals(new User(userNameField.getText(), passwordField.getText()))){
                System.out.println("登录成功");//没问题
                //进入主页面Main
                new Main(user.getUsername());//传入参数记录登录的用户名，方便在Main里面获取用户信息
                jFrame.setVisible(false);
                return true;
            }
        }
        System.out.println("登录失败");
        passwordError pswErr = new passwordError();
        pswErr.setVisible(true);



        return false;

    }

    //idea自动生成的
    private void $$$setupUI$$$() {
        rootJPanel = new JPanel();
        rootJPanel.setLayout(new com.jgoodies.forms.layout.FormLayout(
                "fill:d:grow",
                "center:max(d;4px):noGrow,top:4dlu:noGrow,center:max(d;4px):noGrow,top:4dlu:noGrow,center:d:grow,top:4dlu:noGrow,center:max(d;4px):noGrow"));

    }


    /**
     * 测试，程序启动入口移动到了App类
     */
//    public static void main(String[] args) {
//        JFrame jFrame = new JFrame("登录界面");
//        LoginJFrame loginJFrame = new LoginJFrame();
//        jFrame.setContentPane(loginJFrame.rootJPanel);
//        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        jFrame.setSize(400, 300);
//        jFrame.setLocationRelativeTo(null);
//
//
//
//        //窗体可见
//        jFrame.setVisible(true);
//    }

}


