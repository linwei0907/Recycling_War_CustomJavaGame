import java.awt.Color;
import java.awt.Font;
import java.awt.event.*;
import java.util.Vector;

import javax.swing.*;

import com.musicg.wave.Wave;

public class Player extends Sprite implements KeyListener{
    boolean[] keysDown;
    Hook hook;
    int score;
    int ability;
    AudioInput recorder;
    JTextField label;
    public Player(Scene scene, String imageFile, int height, int width){
        super(scene, imageFile, height, width);
        keysDown = new boolean[256];
        for (int i = 0; i < 256; i++){
            keysDown[i] = false;
        }
        hook = new Hook(scene, "Grappling Hook.png", 25, 25);
        scene.add(hook);
        this.setFocusable(true);
        score = 0;
        recorder = new AudioInput("Player.wav");
        label = new JTextField();
        label.setFont(new Font("Serif", Font.BOLD, 10));
        label.setBounds(10, 10, this.width, this.height);
        label.setForeground(Color.RED);
        this.add(label);
        label.setVisible(false);
    }

    @Override
    public void update(){
        // changes based on which keys are currently down
        if (keysDown[KeyEvent.VK_A]){
            changeAngle(-3);
        }
        if (keysDown[KeyEvent.VK_D]){
            changeAngle(3);
        }
        if (keysDown[KeyEvent.VK_W]){
            setSpeed(0.7);
        }
        if (keysDown[KeyEvent.VK_S]){
            setSpeed(-0.7);
        }
        if (keysDown[KeyEvent.VK_SPACE]){
            if (!hook.fired){
                hook.setPosition(this.x, this.y);
                hook.fire(.9, this.moveAngle);
            }
        }
        // check the bounds of the player and it's hook
        checkBounds();
        hook.checkBounds();
        Vector<Tile> boxes = scene.getBoxTiles();
        for (int i = 0; i < boxes.size(); i ++){
            if (this.collideWith(boxes.get(i))){
                this.setPosition(this.x - dx, this.y - dy);
            }
        }
        // paint the player
        this.repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) {
        
    }

    @Override
    public void keyPressed(KeyEvent e) {
        // when keys are pressed, change the array of the corresponding
        // key code to true;
        if(e.getKeyCode() == KeyEvent.VK_A){
            keysDown[KeyEvent.VK_A] = true;
        }
        if(e.getKeyCode() == KeyEvent.VK_D){
            keysDown[KeyEvent.VK_D] = true;
        }
        if(e.getKeyCode() == KeyEvent.VK_W){
            keysDown[KeyEvent.VK_W] = true;
        }
        if(e.getKeyCode() == KeyEvent.VK_S){
            keysDown[KeyEvent.VK_S] = true;
        }
        if(e.getKeyCode() == KeyEvent.VK_SPACE){
            keysDown[KeyEvent.VK_SPACE] = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // set the corresponding position in the array to false
        // when the key is released.
        if(e.getKeyCode() == KeyEvent.VK_A){
            keysDown[KeyEvent.VK_A] = false;      
        }
        if(e.getKeyCode() == KeyEvent.VK_D){
            keysDown[KeyEvent.VK_D] = false;
        }
        if(e.getKeyCode() == KeyEvent.VK_W){
            keysDown[KeyEvent.VK_W] = false;
            setSpeed(0);
        }
        if(e.getKeyCode() == KeyEvent.VK_S){
            keysDown[KeyEvent.VK_S] = false;
            setSpeed(0);
        }
        if(e.getKeyCode() == KeyEvent.VK_SPACE){
            keysDown[KeyEvent.VK_SPACE] = false;
        }
    }

    // check the bounds, make sure that player doesn't go aross the other
    // side of the road.
    public void checkBounds(){
        double rightBorder = scene.getWidth() - this.width;
        double leftBorder = 0;
        double upperBorder = 0;
        double lowerBorder = scene.getHeight() - this.height;

        if (this.x > rightBorder){
           this.x = leftBorder;
        }
        if (this.x < leftBorder){
            this.x = rightBorder;
        }
        if (this.y < upperBorder){
            this.y = lowerBorder;
        }
        if (this.y > lowerBorder){
            this.y = upperBorder;
        }
    }

    public void castAbility(){
        if (ability >= 5){
            ability = 0;
            label.setText("Recording");
            System.out.println(label.isVisible());
            if(recordAudio()){
                scene.p1Can.get(0).setState(0);
                scene.p1Can.remove(0);
                score++;
            }
        }
    }

    public boolean recordAudio(){
        recorder.record();
        label.setVisible(false);
        Wave w1 = new Wave("Player.wav");
        Wave w2 = new Wave("test1.wav");
        byte[] b1 = w1.getBytes();
        double[] test = new double[b1.length];
        for (int i = 0; i < b1.length; i++){
            if (b1[i] < 0){
                test[i] = -1;
            }else{
                test[i] = 1;
            }
        }
        byte[] b2 = w2.getBytes();
        double similarScore = w1.getFingerprintSimilarity(w2).getScore();
        System.out.println(w1.getFingerprintSimilarity(w2).getScore());
        int score = 0;
        int length = b1.length;
        if (b2.length < b1.length){
            length = b2.length;
        }
        int desireScore = (int)Math.round(length * .65);
        for (int i = 0; i < length; i++){
            if (b1[i] == b2[i]){
                score++;
            }
        }
        // debug code
        System.out.println("score: " + score);
        System.out.println("Desire score: " + desireScore);
        if (score > desireScore || similarScore > .8){
            return true;
        }else{
            System.out.println("Not Similar");
        }
        return false;
    }
}
