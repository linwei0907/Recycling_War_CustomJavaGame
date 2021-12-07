import java.awt.*;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.geom.*;

public class Sprite extends JPanel{
    Scene scene;
    BufferedImage image;
    int height;
    int width;
    double x;
    double y;
    double dx;
    double dy;
    double centerx;
    double centery;
    double moveAngle;
    double speed;
    boolean visibility;
    
    public Sprite(Scene scene, String imageFile, int height, int width){
        this.scene = scene;
        this.height = height;
        this.width = width;
        this.x = 100;
        this.y = 100;
        this.dx = 0;
        this.dy = 0;
        this.centerx = width/2;
        this.centery = height/2;
        this.moveAngle = 0;
        this.speed = 0;
        this.visibility = true;
        this.setSize(width, height);
        setImage(imageFile);
        this.setFocusable(true);
        this.setBounds((int)Math.round(x), (int)Math.round(y), width, height);
        // make the background transparent
        this.setOpaque(false);
    }

    @Override
    public void paintComponent(Graphics g){
        // calculate dy and dx by calcVector
        calcVector();
        // calculate the current position of the sprite
        this.x += this.dx;
        this.y += this.dy;
        // since sprite is a jpanel that contains the image, I simply
        // move the JPanel
        this.setBounds((int)Math.round(x), (int)Math.round(y), width, height);
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D)g;
        AffineTransform t = g2d.getTransform();
        // rotate about the center of the image
        g2d.rotate(moveAngle, centerx, centery);
        // if it is visible, set draw the image
        if (visibility){
            g2d.drawImage(image, 0, 0, null);
        }
        g2d.setTransform(t);
    }

    public BufferedImage getImage(){
        // returns the sprite image
        return image;
    }

    public void update(){
        this.repaint();
    }

    public void setImage(String ImageFile){
        // load the character
        BufferedImage imageTemp = new BufferedImage(height, width, BufferedImage.TYPE_INT_ARGB);

        File input = new File(ImageFile);
        try{
            Image image = ImageIO.read(input).getScaledInstance(width, height, imageTemp.SCALE_SMOOTH);
            Graphics2D g2d = (Graphics2D)imageTemp.createGraphics();
            g2d.drawImage(image, 0, 0, null);
            g2d.dispose();
            
        }catch (IOException e){
            System.out.println(e.getMessage());
        }
        image = imageTemp;
    }

    public void setPosition(double x, double y){
        // set position to desired x, y
        this.x = x;
        this.y = y;
    }

    public void calcVector(){
        // calclate the dx dy base on speed.
        this.dx = this.speed * Math.cos(this.moveAngle);
        this.dy = this.speed * Math.sin(this.moveAngle);
    }

    public void setSpeed(double speed){
        // return speed
        this.speed = speed;
    }

    public void changeAngle(double degreeAngle){
        // change the angle based on degree
        this.moveAngle += (degreeAngle * Math.PI / 180);
    }

    public void setMoveAngle(double degreeAngle){
        // set the angle based on degree
        this.moveAngle = (degreeAngle * Math.PI / 180);
    }

    public void setMoveAngleRad(double radAngle){
        // set the angle based on radian
        this.moveAngle = radAngle;
    }

    public double getAngleInDegree(){
        // return the angle in degrees
        return (this.moveAngle * 180 / Math.PI) + 90;
    }

    public void setVisibility(boolean visible){
        // set the visibility
        this.visibility = visible;
    }

    public Boolean collideWith(Sprite other){
        boolean collision = false;
        if (other.visibility){
            // variables to get the bounding box of both sprite
            double myLeft = this.x;
            double myRight = this.x + this.width;
            double myTop = this.y;
            double myBottom = this.y + this.height;
            double otherLeft = other.x;
            double otherRight = other.x + other.width;
            double otherTop = other.y;
            double otherBottom = other.y + other.height;
            // assume collision
            collision = true;
            if ((myBottom < otherTop) ||
	            (myTop > otherBottom) ||
	            (myRight < otherLeft) ||
	            (myLeft > otherRight)) {
                    // any of the statement is true would mean
                    // there is no collision
	                collision = false;
	            }
        }
        return collision;
    }

    public void calcSpeedAngle(){
        // calculates the speed and angle based on current dx dy
        this.speed = Math.sqrt((this.dx * this.dx) + (this.dy * this.dy));
        this.moveAngle = Math.atan2(this.dy, this.dx);
    }

    public void addVector(double angle, double speed){
        // calcualte the angle to radians
        angle -= 90;
        angle = angle * Math.PI / 180;
        // calculate the new dy and new dx
        double newDX = speed * Math.cos(angle);
        double newDY = speed * Math.sin(angle);
        this.dx += newDX;
        this.dy += newDY;
        // calcualte new speed and new move angle
        calcSpeedAngle();
    }

    public double distanceTo(Sprite other){
        // calculate the distance to another sprite
        double diffX = this.x - other.x;
        double diffY = this.y - other.y;
        double dist = Math.sqrt((diffX * diffX) + (diffY * diffY));
        return dist;
    }
}