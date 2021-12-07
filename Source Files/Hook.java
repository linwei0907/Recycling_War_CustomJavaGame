import java.util.Vector;

public class Hook extends Sprite{
    boolean fired;
    Audio shootSE;
    int state;
    String[] hookImages = {"jar.png", "canSp.png", "cup.png", "box.png", "bottle.png", "detergent.png", "bag.png", "paper.png"};
    public Hook(Scene scene, String imageFile, int height, int width){
        super(scene, imageFile, height, width);
        // set this position to where it wouldn't effect anything
        // also make it invisible, only visible when player fires the hook
        this.setPosition(-100, -100);
        this.setVisible(false);
        // shoudln't be moving, so set speed to 0
        this.setSpeed(0);
        fired = false;
        shootSE = new Audio("Archers-shooting.wav");
        state = 0;
        //hookImages = new String[1];
        //hookImages[0] = imageFile;
    }


    public void fire(double speed, double angle){
        // fire is set to true, so player wouldn't be able to
        // keep firing when there is only 1 hook
        fired = true;
        // play the shooting sound effect
        shootSE.play();
        // make it visible, and shoot it out
        this.setVisible(true);
        this.setMoveAngleRad(angle);
        this.setSpeed(speed);
        // check the bounds
        this.checkBounds();
        this.changeState();
    }

    public void checkBounds(){
        // reached to a edge, reset the position
        if (this.x > scene.getWidth() - this.width || this.x < 0 || this.y > scene.getHeight() - this.height || this.y < 0){
            reset();
        }
        Vector<Tile> boxes = scene.getBoxTiles();
        for (int i = 0; i < boxes.size(); i ++){
            if (this.collideWith(boxes.get(i))){
                reset();
            }
        }
    }

    public void reset(){
        setPosition(-100, -100);
        this.setVisible(false);
        this.setSpeed(0);
        this.fired = false;
    }

    public void changeState(){
        state++;
        if (state == hookImages.length){
            state = 0;
        }
        this.setImage(hookImages[state]);
    }
}
