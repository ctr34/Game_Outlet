package src.main;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable{
    //Set up the main map of game
    final int initTile = 16; // 16 x 16 tile
    final int scale = 3;

    final int tileSize = initTile * scale; // 48 x 48 tile
    final int maxRow = tileSize * 16; // 768 tile
    final int maxColumn = tileSize * 9; // 432 tile

    Thread player;

    KeyHandler keyHandler = new KeyHandler();

    int playerX = 80, playerY = 80;
    int strike = 4;

    int fps = 60;

    public GamePanel(){
        this.setPreferredSize(new Dimension(maxRow, maxColumn));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyHandler);
        this.setFocusable(true); //With this, the GamePanel can "focus" to receive the key pressing
    }

    public void startGame(){
        player = new Thread(this);
        player.start();
    }

    public void update(){
        //obtain the new coordinates
        if (keyHandler.up == true) {
            playerY -= strike;
        }
        if (keyHandler.down == true) {
            playerY += strike;
        }
        if (keyHandler.left == true) {
            playerX -= strike;
        }
        if (keyHandler.right == true) {
            playerX += strike;
        }
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
        graphics2D.setColor(Color.white);
        graphics2D.fillRect(playerX, playerY, tileSize, tileSize);
        graphics2D.dispose();
    }
}
