/**
 * Armando Roque Villasana A01138717
 */

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package videogame;

import java.awt.Graphics;

/**
 *
 * @author antoniomejorado
 */
public class Player extends Item{

    private int direction;
    private int width;
    private int height;
    private Game game;
    private int speed = 3;
    
    /**
     * set the initial value for the player
     * @param x
     * @param y
     * @param width
     * @param height
     * @param game 
     */
    public Player(int x, int y, int width, int height, Game game) {
        super(x, y, width, height);
        this.game = game;
    }



    @Override
    public void tick() {
        // moving player depending on flags
        if (game.getKeyManager().rightUp) {
           setX(getX() + speed);
           setY(getY() - speed);
           game.beep();
        }
        if (game.getKeyManager().rightDown) {
           setX(getX() + speed);
           setY(getY() + speed);
           game.beep();
        }
        if (game.getKeyManager().leftUp) {
           setX(getX() - speed);
           setY(getY() - speed);
           game.beep();
        }
        if (game.getKeyManager().leftDown) {
           setX(getX() - speed);
           setY(getY() + speed);
           game.beep();
        }
        
        // reset x position and y position if colision
        if (getX() + 60 >= game.getWidth()) {
            setX(game.getWidth() - 60);
        }
        else if (getX() <= -30) {
            setX(-30);
        }
        if (getY() + 80 >= game.getHeight()) {
            setY(game.getHeight() - 80);
        }
        else if (getY() <= -20) {
            setY(-20);
        }

    }

    @Override
    public void render(Graphics g) {
        g.drawImage(Assets.player, getX(), getY(), getWidth(), getHeight(), null);
    }
}
