

public class Tile extends Sprite{
    int state;
    final int GRASS = 0;
    final int BOX = 1;
    int row;
    int column;
    String[] images =  {"grassCenter.png", "boxAlt.png", "can1.png", "can2.png"};
    public Tile(Scene scene, String imageFile, int height, int width){
        super(scene, imageFile, height, width);
        this.setSpeed(0);
        this.state = 0;
        this.row = 0;
        this.column = 0;
    }

    public void setState(int state){
        this.state = state;
        this.setImage(images[state]);
    }

    public int getRow(){
        return this.row;
    }

    public void setRow(int row){
        this.row = row;
    }

    public int getColumn(){
        return this.column;
    }

    public void setColumn(int column){
        this.column = column;
    }

    public int getState(){
        return this.state;
    }

}
