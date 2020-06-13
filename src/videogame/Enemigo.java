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
 * @author armandoroque
 */
public class Enemigo extends Item {
    
    private Game game; // the game to which it belongs
    private int speed; // the speed with which it moves
    
    /**
     * to create a new enemy 
     * @param x to set the initial x pos
     * @param y to set the initial y pos
     * @param width to set the width of enemy
     * @param height to set the height of enemy
     * @param game to set the game that it belongs to
     */
    public Enemigo(int x, int y, int width, int height, Game game) {
        super(x, y, width, height);
        this.game = game;
        this.speed = (int) ((Math.random() * 3) + 3); // set random speed in range
    }
    
    /**
     * get speed with which it falls
     * @return an <code>int</code> value with the speed
     */
    public int getSpeed() {
        return speed;
    }
    
    /**
     * set speed with which it falls
     * @param speed an <code>int</code> value with the speed
     */
    public void setSpeed(int speed) {
        this.speed = speed;
    }

    /**
     * ticking the enemy for movement in each frame
     */
    @Override
    public void tick() {
        //setSpeed((int) ((Math.random() * 3) + 3));
        //System.out.println(getSpeed());
        setX(getX() - 1 * getSpeed() ); // move at the speed which can vary
    }
    
    public void setRandomSpeed() {
        setSpeed((int) ((Math.random() * 3) + 3)); // set random speed in range
    }

    /**
     * to render the enemy in the canvas
     * @param g the game to which it belongs
     */
    @Override
    public void render(Graphics g) {
        g.drawImage(Assets.enemy, getX(), getY(), getWidth(), getHeight(), null); // paint enemy on canvas to display
    }
    
}
