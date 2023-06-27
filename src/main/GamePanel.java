package main;

import entity.Player;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable{
    //Set up the main map of game
    final int initTile = 16; // 16 x 16 tile
    final int scale = 3;

    public final int tileSize = initTile * scale; // 48 x 48 tile
    final int maxRow = tileSize * 16; // 768 tile
    final int maxColumn = tileSize * 9; // 432 tile

    Thread game;

    KeyHandler keyHandler = new KeyHandler();

    int fps = 60;

    Player player;

    public GamePanel(){
        this.setPreferredSize(new Dimension(maxRow, maxColumn));
        this.setBackground(Color.GRAY);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyHandler);
        this.setFocusable(true); //With this, the GamePanel can "focus" to receive the key pressing

        player = new Player(this, keyHandler);
    }

    public void startGame(){
        game = new Thread(this);
        game.start();
    }

    public void update(){
        //obtain the new coordinates
        player.update();
    }

    @Override
    public void run() {
        //Set interval
        long interval = 1000000000 / fps;

        while (player != null) {
            long next = System.nanoTime() + interval;

            //Update position
            update();
            //Perform operation
            repaint();

            try {
                long remainder = next - System.nanoTime();

                if (remainder < 0) remainder = 0;

                Thread.sleep(remainder / 1000000);

            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void paintComponent(Graphics graphics){
        super.paintComponent(graphics);
        Graphics2D graphics2D = (Graphics2D) graphics;
        player.draw(graphics2D);
        graphics2D.dispose();
    }
}
