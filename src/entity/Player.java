package entity;

import main.*;

import java.awt.*;

public class Player extends Entity{
    GamePanel gamePanel;
    KeyHandler keyHandler;

    public Player(GamePanel gp, KeyHandler kh){
        this.gamePanel = gp;
        this.keyHandler = kh;

        setDefault();
    }

    public void setDefault(){
        x = 100;
        y = 100;
        strike = 4;
    }

    public void update(){
        if (keyHandler.up == true) {
            y -= strike;
        }
        if (keyHandler.down == true) {
            y += strike;
        }
        if (keyHandler.left == true) {
            x -= strike;
        }
        if (keyHandler.right == true) {
            x += strike;
        }
    }

    public void draw(Graphics2D gp2D){
        gp2D.setColor(Color.white);
        gp2D.fillRect(x, y, gamePanel.tileSize, gamePanel.tileSize);
    }
}
