package cn.ayeez.ui;

import cn.hutool.log.Log;

import javax.swing.*;
import java.awt.event.*;


/**
 * 这个文件的界面除了事件监听，基本上都是idea的swing ui designer插件生成的
 */

public class passwordError extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
//    private JButton buttonCancel;

    public passwordError() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        //初始化
        this.setLocationRelativeTo( null);
        this.setSize(350, 250);

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

//        buttonCancel.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//                onCancel();
//            }
//        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
//        buttonOK.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                //关闭
//
//            }
//        });

    }

    private void onOK() {
        // add your code here
        dispose();
    }

    private void onCancel() {
        //调用注册跳转方法
        //突然不知道怎么调用，如何获取原来那个JFrame的对象
        //既然如此，先把按钮删了吧
        dispose();
    }

    public static void main(String[] args) {
        passwordError dialog = new passwordError();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }
}
