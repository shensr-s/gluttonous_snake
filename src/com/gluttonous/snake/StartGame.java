package com.gluttonous.snake;

import javax.swing.*;

/**
 * <p>
 * description: 贪吃蛇小游戏
 * </p>
 *
 * @author shensr
 * @version V1.0
 * @create 2019/10/28
 **/

public class StartGame {
    public static void main(String[] args) {
        try {
            //调用Windows的文件系统
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {

        }
        new SnakeFrame();
    }
}
