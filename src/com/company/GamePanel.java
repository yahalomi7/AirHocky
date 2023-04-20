package com.company;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import java.util.Timer;
import java.util.TimerTask;


public class GamePanel extends JPanel implements Runnable {

    static final int GAME_WIDTH = 1000;
    static final int GAME_HEIGHT = (int) (GAME_WIDTH * (0.5555));
    static final Dimension SCREEN_SIZE = new Dimension(GAME_WIDTH, GAME_HEIGHT);
    static final int BALL_DIAMETER = 20;
    static final int PADDLE_WIDTH = 10;
    static final int PADDLE_HEIGHT = 75;
    Thread gameThread;
    Image image;
    Graphics graphics;
    Random random;
    public Paddle paddle1;
    public Paddle paddle2;
    public Paddle paddle3;
    public Ball ball1;
    public Ball ball2;
    public Score score;
    Timer timer;
    static final int GAME_DURATION = 60000; // 30 seconds in milliseconds
    static final int GAME_DURATION2 = 30000; // 30 seconds in milliseconds

    boolean gameEnded = false;
    Clock clock;



    GamePanel() {
        newPaddles();
        newBall();
        score = new Score(GAME_WIDTH, GAME_HEIGHT);
        this.setFocusable(true);
        this.addKeyListener(new AL());

        this.setPreferredSize(SCREEN_SIZE);

        gameThread = new Thread(this);
        gameThread.start();
    }

    public void newBall() {
        random = new Random();
        ball1 = new Ball((GAME_WIDTH / 2) - (BALL_DIAMETER / 2), random.nextInt(GAME_HEIGHT - BALL_DIAMETER), BALL_DIAMETER, BALL_DIAMETER);
        ball2 = new Ball((GAME_WIDTH / 2) - (BALL_DIAMETER / 2), random.nextInt((GAME_HEIGHT - BALL_DIAMETER)+2), BALL_DIAMETER, BALL_DIAMETER);


    }
    class EndGameTask extends TimerTask {
        public void run() {
            // stop the game and display the result
           if(score.player1>score.player2){
               System.out.println("player1 won");
           }
           else{
               System.out.println("player 2 won");
           }
            timer.cancel();
            gameEnded =true;
        }
    }

    public void run() {
        timer = new Timer();
        timer.schedule(new EndGameTask(), GAME_DURATION);
        while (!gameEnded) {
            move();
            checkCollision();
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            repaint();
        }
        if (gameEnded=true&&score.player2==score.player1){
            while (score.player2==score.player1) {
                move();
                checkCollision();
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                repaint();
            }
        }
    }



    public void newPaddles() {
        paddle1 = new Paddle(0, (GAME_HEIGHT / 2) - (PADDLE_HEIGHT / 2), PADDLE_WIDTH, PADDLE_HEIGHT, 1);
        paddle2 = new Paddle(GAME_WIDTH - PADDLE_WIDTH, (GAME_HEIGHT / 2) - (PADDLE_HEIGHT / 2), PADDLE_WIDTH, PADDLE_HEIGHT, 2);
        paddle3 = new Paddle(((GAME_WIDTH/2)-10), (GAME_HEIGHT / 2), 20, PADDLE_HEIGHT, 3);

    }

    public void paint(Graphics g) {
        image = createImage(getWidth(), getHeight());
        graphics = image.getGraphics();
        draw(graphics);
        g.drawImage(image, 0, 0, this);
    }

    public void draw(Graphics g) {
        paddle1.draw(g);
        paddle2.draw(g);
        paddle3.draw(g);
        ball1.draw(g);
        ball2.draw(g);
        score.draw(g);
        //clock.draw(g);
        Toolkit.getDefaultToolkit().sync(); // I forgot to add this line of code in the video, it helps with the animation

    }

    public void move() {
        paddle1.move();
        paddle2.move();
        paddle3.move();
        ball1.move();
        ball2.move();
    }

    public void checkCollision() {
        //bounce ball off top & bottom window edges
        if (ball1.y <= 0) {
            ball1.setYDirection(-ball1.yVelocity);
        }
        if (ball1.y >= GAME_HEIGHT - BALL_DIAMETER) {
            ball1.setYDirection(-ball1.yVelocity);
        }
        //bounce ball off paddles
        if (ball1.intersects(paddle1)) {
            ball1.xVelocity = Math.abs(ball1.xVelocity);
            ball1.xVelocity++; //optional for more difficulty
            if (ball1.yVelocity > 0)
                ball1.yVelocity++; //optional for more difficulty
            else
                ball1.yVelocity--;
            ball1.setXDirection(ball1.xVelocity);
            ball1.setYDirection(ball1.yVelocity);
        }
        if (ball1.intersects(paddle2)) {
            ball1.xVelocity = Math.abs(ball1.xVelocity);
            ball1.xVelocity++; //optional for more difficulty
            if (ball1.yVelocity > 0)
                ball1.yVelocity++; //optional for more difficulty
            else
                ball1.yVelocity--;
            ball1.setXDirection(-ball1.xVelocity);
            ball1.setYDirection(ball1.yVelocity);
        }
            if (ball1.intersects(paddle3)) {
                ball1.xVelocity = Math.abs(ball1.xVelocity);
                ball1.xVelocity++; //optional for more difficulty
                if (ball1.yVelocity > 0)
                    ball1.yVelocity++; //optional for more difficulty
                else
                    ball1.yVelocity--;
                ball1.setXDirection(ball1.xVelocity);
                ball1.setYDirection(ball1.yVelocity);
            }
            if (ball1.x <= 0) {
                score.player2++;
                newPaddles();
                newBall();
            }
            if (ball1.x >= GAME_WIDTH - BALL_DIAMETER) {
                score.player1++;
                newPaddles();
                newBall();
            }

        if (ball2.y <= 0) {
            ball2.setYDirection(-ball2.yVelocity);
        }
        if (ball2.y >= GAME_HEIGHT - BALL_DIAMETER) {
            ball2.setYDirection(-ball2.yVelocity);
        }
        //bounce ball off paddles
        if (ball2.intersects(paddle1)) {
            ball2.xVelocity = Math.abs(ball2.xVelocity);
            ball2.xVelocity++; //optional for more difficulty
            if (ball2.yVelocity > 0)
                ball2.yVelocity++; //optional for more difficulty
            else
                ball2.yVelocity--;
            ball2.setXDirection(ball2.xVelocity);
            ball2.setYDirection(ball2.yVelocity);
        }
        if (ball2.intersects(paddle2)) {
            ball2.xVelocity = Math.abs(ball2.xVelocity);
            ball2.xVelocity++; //optional for more difficulty
            if (ball2.yVelocity > 0)
                ball2.yVelocity++; //optional for more difficulty
            else
                ball2.yVelocity--;
            ball2.setXDirection(-ball2.xVelocity);
            ball2.setYDirection(ball2.yVelocity);

        }
        if (ball2.intersects(paddle3)) {
            ball2.xVelocity = Math.abs(ball2.xVelocity);
            ball2.xVelocity++; //optional for more difficulty
            if (ball2.yVelocity > 0)
                ball2.yVelocity++; //optional for more difficulty
            else
                ball2.yVelocity--;
            ball2.setXDirection(-ball2.xVelocity);
            ball2.setYDirection(ball2.yVelocity);

        }
        //stops paddles at window edges
        if (paddle1.y <= 0)
            paddle1.y = 0;
        if (paddle1.y >= (GAME_HEIGHT - PADDLE_HEIGHT))
            paddle1.y = GAME_HEIGHT - PADDLE_HEIGHT;
        if (paddle2.y <= 0)
            paddle2.y = 0;
        if (paddle2.y >= (GAME_HEIGHT - PADDLE_HEIGHT))
            paddle2.y = GAME_HEIGHT - PADDLE_HEIGHT;
        if (paddle3.y <= 0)
            paddle3.y = 0;
        if (paddle3.y >= (GAME_HEIGHT - PADDLE_HEIGHT))
            paddle3.y = GAME_HEIGHT - PADDLE_HEIGHT;

        //give a player 1 point and creates new paddles & ball
        if (ball2.x <= 0) {
            score.player2++;
            newPaddles();
            newBall();
        }
        if (ball2.x >= GAME_WIDTH - BALL_DIAMETER) {
            score.player1++;
            newPaddles();
            newBall();
        }
        paddle3.yVelocity=3;

        if (paddle3.y <= 0) {
            paddle3.setYDirection(-paddle3.yVelocity);
        }
        if (paddle3.y >= GAME_HEIGHT - PADDLE_HEIGHT) {
            paddle3.setYDirection(-paddle3.yVelocity);
        }

        }

    public class AL extends KeyAdapter {
        public void keyPressed(KeyEvent e) {
            paddle1.keyPressed(e);
            paddle2.keyPressed(e);
        }

        public void keyReleased(KeyEvent e) {
            paddle1.keyReleased(e);
            paddle2.keyReleased(e);
        }
    }
}

