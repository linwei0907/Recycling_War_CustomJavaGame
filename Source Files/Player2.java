import java.awt.event.*;
import java.util.Vector;

// it's the same with player class, but looks for different keyboard press
public class Player2 extends Player{
    public Player2(Scene scene, String imageFile, int height, int width){
        super(scene, imageFile, height, width);
        hook.setImage("Grappling Hook2.png");
        this.setPosition(500, 200);
    }

    @Override
    public void update(){
        if (keysDown[KeyEvent.VK_RIGHT]){
            changeAngle(3);
        }
        if (keysDown[KeyEvent.VK_LEFT]){
            changeAngle(-3);
        }
        if (keysDown[KeyEvent.VK_UP]){
            setSpeed(-0.7);
        }
        if (keysDown[KeyEvent.VK_DOWN]){
            setSpeed(0.7);
        }
        if (keysDown[96]){
            if (!hook.fired){
                hook.setPosition(this.x, this.y);
                hook.fire(-0.9, this.moveAngle);
            }
        }
        checkBounds();
        hook.checkBounds();
        Vector<Tile> boxes = scene.getBoxTiles();
        for (int i = 0; i < boxes.size(); i ++){
            if (this.collideWith(boxes.get(i))){
                this.setPosition(this.x - dx, this.y - dy);
            }
        }
        this.repaint();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_RIGHT){
            keysDown[KeyEvent.VK_RIGHT] = true;
        }
        if(e.getKeyCode() == KeyEvent.VK_LEFT){
            keysDown[KeyEvent.VK_LEFT] = true;
        }
        if(e.getKeyCode() == KeyEvent.VK_UP){
            keysDown[KeyEvent.VK_UP] = true;
        }
        if(e.getKeyCode() == KeyEvent.VK_DOWN){
            keysDown[KeyEvent.VK_DOWN] = true;
        }
        if(e.getKeyCode() == 96){
            keysDown[96] = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_RIGHT){
            keysDown[KeyEvent.VK_RIGHT] = false;      
        }
        if(e.getKeyCode() == KeyEvent.VK_LEFT){
            keysDown[KeyEvent.VK_LEFT] = false;
        }
        if(e.getKeyCode() == KeyEvent.VK_UP){
            keysDown[KeyEvent.VK_UP] = false;
            setSpeed(0);
        }
        if(e.getKeyCode() == KeyEvent.VK_DOWN){
            keysDown[KeyEvent.VK_DOWN] = false;
            setSpeed(0);
        }
        if(e.getKeyCode() == 96){
            keysDown[96] = false;
        }
    }
}
