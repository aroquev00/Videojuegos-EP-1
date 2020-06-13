/**
 * Armando Roque Villasana A01138717
 */

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package videogame;

import java.awt.image.BufferedImage;

/**
 *
 * @author antoniomejorado
 */
public class Assets {

    public static BufferedImage background; // to store background image
    public static BufferedImage player;     // to store the player image
    public static BufferedImage enemy; // cannon bullet
    public static BufferedImage friend; // star
    public static BufferedImage gameOver; // final background for when game is over
    public static SoundClip backSound; // background music
    public static SoundClip enemyHit; // sound enemy hit
    public static SoundClip friendHit; // sound friend hit
    public static SoundClip gameOverSound; // game over sound
    

    /**
     * initializing the images of the game
     */
    public static void init() {
        background = ImageLoader.loadImage("/images/background.png");
        player = ImageLoader.loadImage("/images/jugador.png");
        enemy = ImageLoader.loadImage("/images/enemigo-bala.png");
        friend = ImageLoader.loadImage("/images/amigo.png");
        gameOver = ImageLoader.loadImage("/images/game-over.png");
        backSound = new SoundClip("/sounds/background-music.wav");
        enemyHit = new SoundClip("/sounds/enemy-hit.wav");
        friendHit = new SoundClip("/sounds/friend-hit.wav");
        gameOverSound = new SoundClip("/sounds/game-over.wav");
    }

}
