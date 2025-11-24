package cn.ayeez.ui;

import cn.ayeez.javabean.User;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static cn.ayeez.ui.LoginJFrame.userInfoList;

public class RegisterJFrame {
    private JPanel rootJPanel;
    private JTextField registerUsernameField;
    private JTextField registerPasswordField;
    private JTextField RegisterPasswordAgaintField;
    private JButton checkRegister;
    private JButton registerReturnLogin;

    public RegisterJFrame(){
        //页面初始化信息
        JFrame jFrame = new JFrame("登录界面");
//        LoginJFrame loginJFrame = new LoginJFrame();
        RegisterJFrame registerJFrame = this;
        jFrame.setContentPane(registerJFrame.rootJPanel);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setSize(400, 300);
        jFrame.setLocationRelativeTo(null);
        jFrame.setResizable(false);

        //窗体可见
        jFrame.setVisible(true);


        checkRegister.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //这里调用注册方法
                try {
                    checkRegister();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });


        registerReturnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //返回登录界面
                new LoginJFrame();
                jFrame.setVisible(false);
            }
        });
    }

        boolean checkRegister() throws IOException {
            for (User user : userInfoList) {
                /**
                 * 注册失败判定：
                 * 1.用户名已存在
                 * 2.用户名不能为空
                 * 3.密码长度应为6-20位
                 * 4.两次密码要一致
                 */
                if(user.getUsername().equals(registerUsernameField.getText())){
                    //靠构造方法来传参
                    registerErr dialog = new registerErr("注册失败，用户已存在");
                    dialog.pack();
                    dialog.setVisible(true);
//                    System.exit(0);
                    System.out.println("注册失败，用户已存在");
                    return false;
                }if(registerUsernameField.getText().length()==0){
                    registerErr dialog = new registerErr("注册失败，用户名不能为空");
                    dialog.pack();
                    dialog.setVisible(true);
//                    System.exit(0);
                    System.out.println("注册失败，用户名不能为空");
                    return false;
                }if(registerPasswordField.getText().length()<6 || registerPasswordField.getText().length()>20){
                    registerErr dialog = new registerErr("注册失败，密码应为6-20位");
                    dialog.pack();
                    dialog.setVisible(true);
//                    System.exit(0);
                    System.out.println("注册失败，密码应为6-20位");
                    return false;
                }if(!RegisterPasswordAgaintField.getText().equals(registerPasswordField.getText())){
                    registerErr dialog = new registerErr("注册失败，两次密码不一致");
                    dialog.pack();
                    dialog.setVisible(true);
//                    System.exit(0);
                    System.out.println("注册失败，两次密码不一致");
                    return false;
                }
            }
            File file = new File("src\\username_and_password\\UAP.txt");
            FileWriter fileWriter = new FileWriter(file, true);
            //username=阿叶Ayeez&password=123456
            fileWriter.write("username="+registerUsernameField.getText()+
                    "&password="+registerPasswordField.getText()+"\n");
            fileWriter.close();

            System.out.println("注册成功");
            return true;
            //没问题
        }

}
