/**
 * Armando Roque Villasana A01138717
 */

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package videogame;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.util.LinkedList;

/**
 *
 * @author antoniomejorado
 */
public class Game implements Runnable {
    private BufferStrategy bs;      // to have several buffers when displaying
    private Graphics g;             // to paint objects
    private Display display;        // to display in the game
    String title;                   // title of the window
    private int width;              // width of the window
    private int height;             // height of the window
    private Thread thread;          // thread to create the game
    private boolean running;        // to set the game
    private Player player;          // to use a player
    private KeyManager keyManager;  // to manage the keyboard
    
    private LinkedList<Enemigo> listaEnemigo; // list of enemies
    private LinkedList<Amigo> listaAmigo; // list of friends
    
    private int vidas;              // to keep track of lives left
    private int puntos;             // to track score of player
    private int contChoquesEnemigo;         // to count how many enemies player hits
    private Font font;              // to count how many friends player hits
    
    private boolean gameOver; // to see if game has ended
    private int contGameOver; // to count game over frames
    
    
    
    /**
     * to create title, width and height and set the game is still not running
     * @param title to set the title of the window
     * @param width to set the width of the window
     * @param height  to set the height of the window
     */
    public Game(String title, int width, int height) {
        this.title = title;
        this.width = width;
        this.height = height;
        running = false;
        keyManager = new KeyManager();
    }

    /**
     * To get the width of the game window
     * @return an <code>int</code> value with the width
     */
    public int getWidth() {
        return width;
    }

    /**
     * To get the height of the game window
     * @return an <code>int</code> value with the height
     */
    public int getHeight() {
        return height;
    }
    
    /**
     * initializing the display window of the game
     */
    private void init() {
        gameOver = false; // game running
        contGameOver = 0; // initalize value
         display = new Display(title, getWidth(), getHeight());  
         Assets.init();
         player = new Player((getWidth() / 2) - (50), (getHeight() / 2) - 50, 100, 100, this); // place player in screen center
         
         listaEnemigo = new LinkedList<Enemigo>();
         int azarEnemigo = (int) ((Math.random() * 3) + 8); // number of enemies
         
         for (int i = 1; i <= azarEnemigo; i++) {
            //Enemigo enemigo = new Enemigo(50, 50, 100, 100, this);
            Enemigo enemigo = new Enemigo(getWidth() + (int) (Math.random() * (getWidth() * 2)), (int) (Math.random() * (getHeight() - 100)), 50, 50, this); // place enemies
            listaEnemigo.add(enemigo);
        }
        
         contChoquesEnemigo = 0;
         
         listaAmigo = new LinkedList<Amigo>();
         int azarAmigo = (int) ((Math.random() * 6) + 10); // number of friends
         for (int i = 1; i <= azarAmigo; i++) { // 
            //Enemigo enemigo = new Enemigo(50, 50, 100, 100, this);
            Amigo amigo = new Amigo((int) ((-1) * Math.random() * (getWidth() * 2)  - 100), (int) (Math.random() * (getHeight() - 100)), 100, 100, this); // place friends
            listaAmigo.add(amigo);
        }
         
         
         vidas = (int) ((Math.random() * 3) + 3); // random number of lives
         System.out.println(vidas);
         
         display.getJframe().addKeyListener(keyManager);
         Assets.backSound.setLooping(true);
         Assets.backSound.play();
         
         font = new Font("SansSerif", 1, 16); // set font
    }
    
    @Override
    public void run() {
        init();
        // frames per second
        int fps = 50;
        // time for each tick in nano segs
        double timeTick = 1000000000 / fps;
        // initializing delta
        double delta = 0;
        // define now to use inside the loop
        long now;
        // initializing last time to the computer time in nanosecs
        long lastTime = System.nanoTime();
        while (running) {
            // setting the time now to the actual time
            now = System.nanoTime();
            // acumulating to delta the difference between times in timeTick units
            delta += (now - lastTime) / timeTick;
            // updating the last time
            lastTime = now;
            
            // if delta is positive we tick the game
            if (delta >= 1) {
                if (! gameOver) {
                    tick();
                }
                
                render();
                delta --;
            }
        }
        stop();
    }

    public KeyManager getKeyManager() {
        return keyManager;
    }
   
    public void beep() {
        //Assets.gunShot.play();
    }
    
    public void enemyHitSound() {
        Assets.enemyHit.play();
    }
    
    public void friendHitSound() {
        Assets.friendHit.play();
    }
    
    private void tick() {
        keyManager.tick();
        // avancing player with colision
        player.tick();
        
        for (Enemigo enemigo : listaEnemigo) {
            enemigo.tick();
            if (player.collision(enemigo)) {
                enemyHitSound();
                // recolocar enemigo
                enemigo.setX(getWidth() + (int) (Math.random() * (getWidth() * 2)));
                enemigo.setY((int) (Math.random() * (getHeight() - 100)));
                enemigo.setRandomSpeed();
                contChoquesEnemigo ++;
                if (contChoquesEnemigo == 5) {
                    contChoquesEnemigo = 0;
                    vidas --;
                    if (vidas == 0) {
                        gameOver = true; // no more lives
                    }
                }
                
            }
            else if ((enemigo.getX() + enemigo.getWidth()) < 0) { // if it leaves screen
                enemigo.setX(getWidth() + (int) (Math.random() * (getWidth() * 2)));
                enemigo.setY((int) (Math.random() * (getHeight() - 100)));
                enemigo.setRandomSpeed();
            }
        }
        
        // recolocar amigo
        for (Amigo amigo : listaAmigo) {
            amigo.tick();
            if (player.collision(amigo)) {
                friendHitSound();
                amigo.setX((int) ((-1) * Math.random() * (getWidth() * 2) - 100));
                amigo.setY((int) (Math.random() * (getHeight() - 100)));
                amigo.setRandomSpeed();
                puntos += 5; // add points
            }
            else if (amigo.getX() > getWidth()) { // if it leaves screen
                amigo.setX((int) ((-1) * Math.random() * (getWidth() * 2)  - 100));
                amigo.setY((int) (Math.random() * (getHeight() - 100)));
                amigo.setRandomSpeed();
            }
        }
        
    }
    
    private void render() {
        // get the buffer strategy from the display
        bs = display.getCanvas().getBufferStrategy();
        /* if it is null, we define one with 3 buffers to display images of
        the game, if not null, then we display every image of the game but
        after clearing the Rectanlge, getting the graphic object from the 
        buffer strategy element. 
        show the graphic and dispose it to the trash system
        */
        if (bs == null) {
            display.getCanvas().createBufferStrategy(3);
        }
        else
        {
            
            g = bs.getDrawGraphics();
            g.setColor(Color.RED);
            g.setFont(font);
            if (! gameOver) { // game running
                g.drawImage(Assets.background, 0, 0, width, height, null);
                player.render(g);
                for (Enemigo enemigo : listaEnemigo) {
                    enemigo.render(g); // render all falling objects
                }

                for (Amigo amigo : listaAmigo) {
                    amigo.render(g);
                }
            }
            else { // display game over screen and sound
                Assets.backSound.stop();
                if (contGameOver == 0) {
                    Assets.gameOverSound.play();
                }
                contGameOver ++;
                g.drawImage(Assets.gameOver, 0, 0, width, height, null);
                if (contGameOver == 60) {
                    running = false;
                }
            }
            
            // display game data
            g.drawString("Enemigos chocados en esta vida: " + contChoquesEnemigo, 10, 20); // draw number of dropped objects
            g.drawString("Score: " + puntos, getWidth() / 2 - 35, 20); // draw score of player
            g.drawString("Vidas: " + vidas, getWidth() - 100, 20); // draw number of lives left
            
            bs.show();
            g.dispose();
        }
       
    }
    
    /**
     * setting the thead for the game
     */
    public synchronized void start() {
        if (!running) {
            running = true;
            thread = new Thread(this);
            thread.start();
        }
    }
    
    /**
     * stopping the thread
     */
    public synchronized void stop() {
        if (running) {
            running = false;
            try {
                thread.join();
            } catch (InterruptedException ie) {
                ie.printStackTrace();
            }           
        }
    }

 
    


}
