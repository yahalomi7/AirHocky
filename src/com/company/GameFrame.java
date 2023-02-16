package com.company;
import javax.swing.*;
import javax.swing.JFrame;
import java.awt.*;
import java.util.*;
public class GameFrame extends JFrame {
    public GamePanel panel;

    public GameFrame() {


        panel = new GamePanel();
        this.add(panel);
        this.setTitle("pong game");
        this.setResizable(false);
        this.setBackground(Color.black);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }
}

