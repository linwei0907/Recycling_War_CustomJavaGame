import java.awt.*;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.util.Vector;
import java.io.BufferedReader;
import java.util.Scanner;


public class Scene extends JPanel{
    BufferedImage background;
    final int ROWS = 15;
    final int COLUMNS = 20;
    Tile[][] tileset;
    Vector<Tile> boxTiles;
    Vector<Tile> p1Can;
    Vector<Tile> p2Can;
    public Scene(String imageFile){
        this.setSize(640, 480);
        setBackground(imageFile);
        tileset = new Tile[ROWS][COLUMNS];
        boxTiles = new Vector<Tile>();
        p1Can = new Vector<Tile>();
        p2Can = new Vector<Tile>();
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D)g;
        // g2d.drawImage(background, -110, 0 , null);
    }

    public void setBackground(String ImageFile){
        BufferedImage imageTemp = new BufferedImage(680, 480, BufferedImage.TYPE_INT_RGB);

        File input = new File(ImageFile);
        try{
            imageTemp = ImageIO.read(input);
        }catch (IOException e){
            System.out.println(e.getMessage());
        }
        background = imageTemp;
    }

    public void update(){
        this.repaint();
    }

    public void setupTiles(){
        for (int row = 0; row < ROWS; row++){
            for (int column = 0; column < COLUMNS; column++){
                Tile tile = new Tile(this, "grassCenter.png", 32, 32);
                tileset[row][column] = tile;
                double xpos = 32*column;
                double ypos = 32*row;
                tileset[row][column].setPosition(xpos, ypos);
                tileset[row][column].setRow(row);
                tileset[row][column].setColumn(column);
                this.add(tileset[row][column]);
            }
        }       
    }

    public void updateTiles(){
        for (int row = 0; row < ROWS; row++){
            for (int column = 0; column < COLUMNS; column++){
                tileset[row][column].update();
                if (tileset[row][column].state == 1){
                    boxTiles.add(tileset[row][column]);
                }
            }
        }
    }

    public void loadTile(String excelPath){
        try{
            Scanner csvReader = new Scanner(new File(excelPath));
            for (int i = 0; i < ROWS; i++){
                String line = csvReader.nextLine();
                String[] values = line.split(",");
                for (int j = 0; j < COLUMNS; j++){
                    tileset[i][j].setState(Integer.parseInt(values[j]));
                    if (tileset[i][j].state == 1){
                        boxTiles.add(tileset[i][j]);
                    }
                    if (tileset[i][j].state == 2){
                        p1Can.add(tileset[i][j]);
                    }
                    if (tileset[i][j].state == 3){
                        p2Can.add(tileset[i][j]);
                    }
                }
            }
        }catch(FileNotFoundException e){
            System.out.println("File doesn't exist");
        }
    }

    public Vector<Tile> getBoxTiles(){
        return this.boxTiles;
    }
}
