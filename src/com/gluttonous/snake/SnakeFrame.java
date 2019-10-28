package com.gluttonous.snake;

import javax.swing.*;
import java.awt.*;

/**
 * <p>
 * description:
 * </p>
 *
 * @author shensr
 * @version V1.0
 * @create 2019/10/28
 **/

public class SnakeFrame extends JFrame {

    SnakeFrame(){
        this.setTitle("贪吃蛇小游戏");
        // 窗口大小
        this.setSize(900,720);
        // 居中显示
        this.setLocationRelativeTo(null);
        // 不可拖动修改
        this.setResizable(false);
        // 设置窗体图标
        try {
            ImageIcon imageIcon = Snake.favicon;
            Image image = imageIcon.getImage();
            this.setIconImage(image);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "图标异常");
        }
        // 点击X关闭
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // 设置画布
        this.add(new GluSnake());
        // 设置窗口为可见
        this.setVisible(true);

    }



}
