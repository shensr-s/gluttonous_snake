package com.gluttonous.snake;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

/**
 * <p>
 * description: 画布
 * </p>
 *
 * @author shensr
 * @version V1.0
 * @create 2019/10/28
 **/

public class GluSnake extends JPanel implements KeyListener, ActionListener {


    /**
     * 蛇身体长度
     */
    private int len = 3;
    private int score;
    /**
     * 蛇身体长度坐标
     */
    private int[] snakeX = new int[750];
    private int[] snakeY = new int[750];
    /**
     * 游戏区域 最大宽度
     */
    private final static int MAX_WIDTH = 850;
    /**
     * 游戏区域 最小宽度
     */
    private final static int MIN_WIDTH = 25;
    /**
     * 游戏区域 最大高度
     */
    private final static int MAX_HIGH = 650;
    /**
     * 游戏区域 最小高度
     */
    private final static int MIN_HIGH = 75;
    /**
     * 蛇头方向 默认向右
     * 上下左右-->U D L R
     */
    private String direction;
    private final static String RIGHT = "RIGHT";
    private final static String LEFT = "LEFT";
    private final static String UP = "UP";
    private final static String DOWN = "DOWN";


    /**
     * 食物坐标
     */
    private int foodX;
    private int foodY;


    /**
     * 是否开始
     */
    private boolean isStarted;
    /**
     * 是否死亡
     */
    private boolean isFailed;

    private int speed = 150;
    private Timer timer = new Timer(speed, this);


    public GluSnake() {
        initSnake();
        // 设置可以获取焦点
        this.setFocusable(true);
        // 设置keyListener监听
        this.addKeyListener(this);
    }

    /**
     * @param g 画笔
     */
    @Override
    public void paintComponent(Graphics g) {
        // 调用父类方法
        super.paintComponent(g);
        // 设置背景颜色
        this.setBackground(Color.WHITE);
        // 设置title
        Snake.title.paintIcon(this, g, 25, 11);

        // 设置分数和长度
        Color defaultColor = g.getColor();
        g.setColor(Color.YELLOW);
        g.drawString("长度：" + len, 750, 35);
        g.drawString("分数：" + score, 750, 50);
        g.setColor(defaultColor);


        // 设置游戏区
        g.fillRect(25, 75, 850, 600);

        // 画出蛇
        switch (direction) {
            case RIGHT:
                Snake.right.paintIcon(this, g, snakeX[0], snakeY[0]);
                break;
            case LEFT:
                Snake.left.paintIcon(this, g, snakeX[0], snakeY[0]);
                break;
            case DOWN:
                Snake.down.paintIcon(this, g, snakeX[0], snakeY[0]);
                break;
            case UP:
                Snake.up.paintIcon(this, g, snakeX[0], snakeY[0]);
                break;
            default:
        }
        for (int i = 1; i < len; i++) {
            Snake.body.paintIcon(this, g, snakeX[i], snakeY[i]);
        }
        // 绘制食物
        Snake.foods.paintIcon(this, g, foodX, foodY);

        // 蛇吃食物
        if (snakeX[0] == foodX && snakeY[0] == foodY) {
            len++;
            // 分数
            if(speed>50){
                speed-=2;
                timer.setDelay(speed);
              }
            score += 10;
            // 随机生成 食物
            initFoods();
        }

        // 游戏开始提示

        if (!isStarted) {
            g.setColor(Color.WHITE);
            g.setFont(new Font("arial", Font.BOLD, 40));
            g.drawString("Press Space to start", 300, 300);
        }
        if (isFailed) {
            g.setColor(Color.RED);
            g.setFont(new Font("arial", Font.BOLD, 40));
            g.drawString("Failed:Press Space to start", 300, 300);
        }
    }

    /**
     * 初始化数据
     */
    private void initSnake() {
        // 蛇身初始长度
        len = 3;
        score = 0;
        // 绘制出初始蛇身
        snakeX[0] = 100;
        snakeY[0] = 100;
        snakeX[1] = 75;
        snakeY[1] = 100;
        snakeX[2] = 50;
        snakeY[2] = 100;
        // 蛇头方向
        direction = RIGHT;


        // 是否失败
        isFailed = false;
        // 是否开始
        isStarted = false;

        // 速度
        speed=150;
        timer.setDelay(speed);
        // 启动时钟
        timer.start();
        // 初始化食物
        initFoods();

    }

    /**
     * 随机生成 食物
     */
    private void initFoods() {

        foodX = 25 + 25 * (new Random().nextInt(34));
        foodY = 75 + 25 * (new Random().nextInt(24));
    }


    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        // 空格键切换 开始/暂停
        if (keyCode == KeyEvent.VK_SPACE) {
            // 失败重启
            if (isFailed) {
                initSnake();
            } else {
                isStarted = !isStarted;
            }
            // 重新绘制
            repaint();
        } else if (keyCode == KeyEvent.VK_LEFT) {
            direction = LEFT;
        } else if (keyCode == KeyEvent.VK_RIGHT) {
            direction = RIGHT;
        } else if (keyCode == KeyEvent.VK_UP) {
            direction = UP;
        } else if (keyCode == KeyEvent.VK_DOWN) {
            direction = DOWN;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    /**
     * 定时 每100毫秒刷新
     *
     * @param e ActionEvent
     */
    @Override
    public void actionPerformed(ActionEvent e) {

        if (isStarted && !isFailed) {
            // 蛇身移动
            for (int i = len - 1; i > 0; i--) {
                snakeX[i] = snakeX[i - 1];
                snakeY[i] = snakeY[i - 1];
            }
            // 蛇头移动 越界游戏结束

            switch (direction) {
                case RIGHT:
                    snakeX[0] += 25;
                    if (snakeX[0] > MAX_WIDTH) {
                        isFailed = true;
                    }
                    break;
                case LEFT:
                    snakeX[0] -= 25;
                    if (snakeX[0] < MIN_WIDTH) {
                        isFailed = true;
                    }
                    break;
                case UP:
                    snakeY[0] -= 25;
                    if (snakeY[0] < MIN_HIGH) {
                        isFailed = true;
                    }
                    break;
                case DOWN:
                    snakeY[0] += 25;
                    if (snakeY[0] > MAX_HIGH) {
                        isFailed = true;
                    }
                    break;
                default:

            }
            repaint();
        }


        timer.start();
    }
}
